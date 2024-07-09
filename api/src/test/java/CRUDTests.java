import ddo.GoodsRowDDO;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.*;
import utils.database.CommonSqlScript;
import utils.database.SqlExecutor;
import utils.file_helper.FileHelper;
import utils.json_utils.JsonUtil;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Tag("CRUD")
@DisplayName("Проверка CRUD операций")
public class CRUDTests {
    private final SqlExecutor sqlExecutor = new SqlExecutor();

    @Test
    @DisplayName("Проверка запроса CREATE TABLE/DROP TABLE")
    public void testFromCreateTableUsersQuery() {
        Allure.step("Проверка запроса CREATE TABLE",() -> {
            sqlExecutor.createQuerySql(CommonSqlScript.CREATE_TABLE_USERS);
            Assertions.assertEquals("USERS",sqlExecutor.createQuerySql(
                            CommonSqlScript.SHOW_TABLES)
                    .getTableNames()
                    .stream()
                    .filter(filter -> filter.contains("USERS"))
                    .collect(Collectors.toList()).get(0));
        });
        Allure.step("Проверка запроса DROP TABLE",() -> {
            sqlExecutor.createQuerySql(CommonSqlScript.DROP_TABLE_USERS);
            Assertions.assertEquals(new ArrayList<>(),sqlExecutor.createQuerySql(
                            CommonSqlScript.SHOW_TABLES)
                    .getTableNames()
                    .stream()
                    .filter(filter -> filter.contains("USERS"))
                    .collect(Collectors.toList()));
        });
    }

    @Test
    @DisplayName("Проверка запроса READ")
    public void testFromSelectQuery() {
        String name = "Капуста";
        Allure.step("Выполнение запроса SELECT ALL",() ->
            Assertions.assertNotNull(sqlExecutor.createQuerySql(CommonSqlScript.GET_ALL)
                    .getSelectResult()));
        Allure.step("Выполнение запроса SELECT WITH NAME для проверки записи",() ->
            Assertions.assertEquals(name,
                    sqlExecutor.createQuerySql(
                                    CommonSqlScript.GET_DATA_WITH_NAME,name)
                            .getSelectResult().get(0).getName()));
    }

    @Test
    @DisplayName("Проверка запроса UPDATE")
    public void testFromUpdateQuery() {
        String newName = "Мандарин";
        Allure.step("Выполнение запроса UPDATE",() -> {
            sqlExecutor.createQuerySql(
                    CommonSqlScript.SET_NAME_WITH_OLD_NAME,
                    newName,"Апельсин");

        });
        Allure.step("Выполнение запроса SELECT WITH NAME для проверки записи",() -> {
            Assertions.assertEquals(newName,
                    sqlExecutor.createQuerySql(CommonSqlScript.GET_DATA_WITH_NAME,newName)
                            .getSelectResult().get(0).getName());
        });
    }

    @Test
    @DisplayName("Проверка запроса DELETE")
    public void testFromDeleteQuery() {
        GoodsRowDDO goodsRowDDO =
                JsonUtil.deserializeFromJson(
                        FileHelper.readFileAsString("test_object_2.json"),
                        GoodsRowDDO.class);
        Allure.step("Выполнение запроса INSERT для добавления тествой записи",() -> {
            sqlExecutor.createQuerySql(
                    CommonSqlScript.SET_NEW_DATA
                    ,goodsRowDDO.getName(),goodsRowDDO.getType(),goodsRowDDO.getExotic());

        });
        Allure.step("Выполнение запроса DELETE",() -> {
            sqlExecutor.createQuerySql(CommonSqlScript.DELETE_WITH_NAME,goodsRowDDO.getName());
        });
        Allure.step("Выполнение запроса SELECT WITH NAME для проверки записи",() -> {
            Assertions.assertEquals(new ArrayList<>(),
                    sqlExecutor.createQuerySql(CommonSqlScript.GET_DATA_WITH_NAME, goodsRowDDO.getName())
                            .getSelectResult());
        });
    }

    @Test
    @DisplayName("Проверка запроса INSERT")
    public void testFromInsertQuery() {
        GoodsRowDDO goodsRowDDO =
                JsonUtil.deserializeFromJson(
                        FileHelper.readFileAsString("test_object_2.json"),
                        GoodsRowDDO.class);
        Allure.step("Выполнение запроса INSERT",() -> {
            sqlExecutor.createQuerySql(CommonSqlScript.SET_NEW_DATA,
                    goodsRowDDO.getName(),goodsRowDDO.getType(),goodsRowDDO.getExotic());
        });
        Allure.step("Выполнение запроса SELECT WITH NAME для проверки записи",() -> {
            Assertions.assertEquals(goodsRowDDO,
                    sqlExecutor.createQuerySql(
                                    CommonSqlScript.GET_DATA_WITH_NAME,goodsRowDDO.getName())
                            .getSelectResult().get(0));
        });

    }
}
