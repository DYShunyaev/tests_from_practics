import config.config_provider.config_provider.ConfigProvider;
import ddo.GoodsRowDDO;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.*;
import specifications.Specifications;
import utils.database.CommonSqlScript;
import utils.database.SqlExecutor;
import utils.file_helper.FileHelper;
import utils.json_utils.JsonUtil;

import java.util.List;

import static io.restassured.RestAssured.given;

@Tag("API")
@DisplayName("Проверка API")
public class ApiTests {

    private final SqlExecutor sqlExecutor = new SqlExecutor();

    @Test
    @Order(1)
    @DisplayName("Проверка получения всех товаров через API")
    public void getListOfGoods() {
        Allure.step("Подготовка спецификаций, ожидаемый код - 200",() ->
            Specifications.installSpecification(
                    Specifications.requestSpec(ConfigProvider.URL),Specifications.responseSpecOK200()));
        Allure.step("Выполнение GET запроса для получения всех товаров",() -> {
            List<GoodsRowDDO> goodsRowDDOList = given()
                    .when()
                    .get("/api/food")
                    .then().log().all()
                    .extract().body().jsonPath().getList(".", GoodsRowDDO.class);
            List<GoodsRowDDO> goodsRowDDOSInDataBase = sqlExecutor.createQuerySql(CommonSqlScript.GET_ALL)
                    .getSelectResult();
            Assertions.assertEquals(goodsRowDDOSInDataBase.size(), goodsRowDDOList.size());
        });
    }

    @Test
    @Order(2)
    @DisplayName("Проверка добавления нового товара через API")
    public void setGoodToListOfGoods() {
        Allure.step("Подготовка спецификаций, ожидаемый код - 200",() ->
            Specifications.installSpecification(
                    Specifications.requestSpec(ConfigProvider.URL),Specifications.responseSpecOK200()));
        Allure.step("Подготовка тестовых данных и отправка POST запроса",() -> {
            GoodsRowDDO goodsRowDDO =
                    JsonUtil.deserializeFromJson(
                            FileHelper.readFileAsString("test_object.json"),
                            GoodsRowDDO.class);
            int code = given()
                    .body(JsonUtil.serializeJson(goodsRowDDO))
                    .when()
                    .post("/api/food")
                    .then()
                    .log().all()
                    .extract().statusCode();
            Assertions.assertEquals(code, StatusCode.OK_200.getCode());
            List<GoodsRowDDO> goodsRowDDOSInDataBase = sqlExecutor
                    .createQuerySql(
                    CommonSqlScript.GET_DATA_WITH_NAME
                            ,goodsRowDDO.getName())
                    .getSelectResult();
            Assertions.assertNotNull(goodsRowDDOSInDataBase.get(0));
        });
    }

    @Test
    @Order(3)
    @DisplayName("Проверка отправки запроса без тела - ошибка 400")
    public void setNullObjectToListOfGood() {
        Allure.step("Подготовка спецификаций, ожидаемый код - 400",() ->
            Specifications.installSpecification(
                    Specifications.requestSpec(ConfigProvider.URL),Specifications.responseSpecBadRequest400()));
        Allure.step("Отправка POST запроса для добавления нового товара с пустым телом",() -> {
            int code = given()
                    .when()
                    .post("/api/food")
                    .then()
                    .log().all()
                    .extract().statusCode();
            Assertions.assertEquals(code, StatusCode.BAD_REQ_400.getCode());
        });
    }

    @Test
    @Order(4)
    @DisplayName("Проверка функции Сброса Данных через API")
    public void resetDataFromAPI() {
        Allure.step("Подготовка спецификаций, ожидаемый код - 200",() ->
            Specifications.installSpecification(
                    Specifications.requestSpec(ConfigProvider.URL),Specifications.responseSpecOK200()));
        Allure.step("Отправка POST запроса для выполнения Сброса Данных, проверка кода ответа",() ->{
            int code = given()
                    .when()
                    .post("/api/data/reset")
                    .then()
                    .log().all()
                    .extract().statusCode();
            Assertions.assertEquals(code, StatusCode.OK_200.getCode());
            List<GoodsRowDDO> goodsRowDDOSInDataBase = sqlExecutor.createQuerySql(CommonSqlScript.GET_ALL)
                    .getSelectResult();
            Assertions.assertEquals(goodsRowDDOSInDataBase.size(), 4);
        });
    }
}
