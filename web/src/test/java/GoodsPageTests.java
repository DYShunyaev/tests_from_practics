import config.GUIBase;
import ddo.GoodsRowDDO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.GoodsPage;
import pages.MainPage;

import java.util.List;

public class GoodsPageTests extends GUIBase {
    private final GoodsPage goodsPage = new GoodsPage();
    private final MainPage mainPage = new MainPage();

    @Test
    public void checkingProductAddition() {
        String name = "Огурец";
        List<GoodsRowDDO> goodsRowDDOS = goodsPage.addInsertButton()
                .addDataFromTable(name,true,true).getTableListOfGoods();
        Assertions.assertEquals(goodsRowDDOS.get(goodsRowDDOS.size()-1).getName(),name);
    }

    @Test
    public void dataResetCheck() {
        List.of("Манго", "Свекла", "Алыча").forEach(name -> goodsPage.addInsertButton().addDataFromTable(name, true, true));

        List<GoodsRowDDO> goodsRowDDOSBefore = goodsPage.getTableListOfGoods();
        mainPage.resetData();
        List<GoodsRowDDO> goodsRowDDOSAfter = goodsPage.getTableListOfGoods();

        Assertions.assertNotEquals(goodsRowDDOSBefore.size(), goodsRowDDOSAfter.size());
    }

    @AfterEach
    private void resetData() {
        mainPage.resetData();
    }
}
