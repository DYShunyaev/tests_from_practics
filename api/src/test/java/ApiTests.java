import config_provider.ConfigProvider;
import ddo.GoodsRowDDO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import specifications.Specifications;
import utils.file_helper.FileHelper;
import utils.json_utils.JsonUtil;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ApiTests {

    @Test
    public void getListOfGoods() {
        Specifications.installSpecification(Specifications.requestSpec(ConfigProvider.URL),Specifications.responseSpecOK200());
        List<GoodsRowDDO> goodsRowDDOList = given()
                .when()
                .get("/api/food")
                .then().log().all()
                .extract().body().jsonPath().getList(".", GoodsRowDDO.class);
        Assertions.assertEquals(4, goodsRowDDOList.size());
    }
    @Test
    public void setGoodToListOfGoods() {
        Specifications.installSpecification(Specifications.requestSpec(ConfigProvider.URL),Specifications.responseSpecOK200());
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
        //добавить проверку с БД
    }

    @Test
    public void resetDataFromAPI() {
        Specifications.installSpecification(Specifications.requestSpec(ConfigProvider.URL),Specifications.responseSpecOK200());
        int code = given()
                .when()
                .post("/api/data/reset")
                .then()
                .log().all()
                .extract().statusCode();
        Assertions.assertEquals(code, StatusCode.OK_200.getCode());
        //добавить проверку с БД
    }
}
