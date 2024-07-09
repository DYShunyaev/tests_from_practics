import config.GUIBase;
import ddo.GoodsRowDDO;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.*;
import pages.GoodsPage;
import pages.MainPage;
import utils.database.CommonSqlScript;
import utils.database.SqlExecutor;
import utils.file_helper.FileHelper;

import java.util.List;

@Tag("GUI")
@DisplayName("Проверка страницы Товары")
public class GoodsPageTests extends GUIBase {
    private final GoodsPage goodsPage = new GoodsPage();
    private final MainPage mainPage = new MainPage();
    private final SqlExecutor sqlExecutor = new SqlExecutor();

    @Test
    @DisplayName("Проверка добавления нового продукта")
    public void checkingProductAddition() {
        Allure.step("Подготовка тестовых данных и выполнение теста",() -> {
            String name = "Огурец";
            List<GoodsRowDDO> goodsRowDDOS = goodsPage.addInsertButton()
                    .addDataFromTable(name,true,true).getTableListOfGoods();
            Assertions.assertEquals(goodsRowDDOS.get(goodsRowDDOS.size()-1).getName(),name);
            List<GoodsRowDDO> goodsRowDDOSInDataBase = sqlExecutor
                    .createQuerySql(
                            CommonSqlScript.GET_DATA_WITH_NAME
                            ,name)
                    .getSelectResult();
            Assertions.assertNotNull(goodsRowDDOSInDataBase.get(0));
        });
    }

    @Test
    @DisplayName("Проверка функции Сброс данных")
    public void dataResetCheck() {
        List.of("Манго", "Свекла", "Алыча").forEach(name -> goodsPage.addInsertButton().addDataFromTable(name, true, true));

        List<GoodsRowDDO> goodsRowDDOSBefore = goodsPage.getTableListOfGoods();
        mainPage.resetData();
        List<GoodsRowDDO> goodsRowDDOSAfter = goodsPage.getTableListOfGoods();
        Assertions.assertNotEquals(goodsRowDDOSBefore.size(), goodsRowDDOSAfter.size());
    }

    @AfterEach
    public void resetData() {
        mainPage.resetData();
    }
}
