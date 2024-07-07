package pages;

import config.GUIBase;
import ddo.GoodsRowDDO;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class GoodsPage extends GUIBase {

    private final By tableListOfGoods = By.xpath("//*[contains(@class,\"table\")]");
    private final By insertButton = By.xpath("//button[contains(@class,\"btn btn-primary\")]");
    private final By inputField = By.xpath("//input[contains(@id,\"name\")]");
    private final By selectField = By.xpath("//select[contains(@id,\"type\")]");
    private final By selectExoticField = By.xpath("//input[contains(@id,\"exotic\")]");
    private final By saveButton = By.xpath("//button[contains(@id,\"save\")]");

    private final MainPage mainPage = new MainPage();

    public GoodsPage addInsertButton() {
        Allure.step("Выбор кнопки \"Добавить\" на странице \"Товары\"",() -> {
            mainPage.goToGoodsPage();
            driver.findElement(insertButton).click();
        });
        return this;
    }

    public GoodsPage addDataFromTable(String name, boolean isFruit, boolean exotic) {
        Allure.step("Добавление нового товара в таблицу на странице \"Товары\"",() -> {
            driver.findElement(inputField).sendKeys(name);
            new Select(driver.findElement(selectField)).selectByValue(isFruit ? "FRUIT" : "VEGETABLE");
            if (exotic) driver.findElement(selectExoticField).click();
            driver.findElement(saveButton).click();
        });
        return this;
    }

    public List<GoodsRowDDO> getTableListOfGoods() {
        mainPage.goToGoodsPage();
        List<GoodsRowDDO> goodsRowDDOS = new ArrayList<>();
        Allure.step("Получение данных из главной табицы на странице \"Товары\"",() -> {
            WebElement table = driver.findElement(tableListOfGoods).findElement(By.xpath("./tbody"));
            List<WebElement> rows = table.findElements(By.xpath(".//tr"));

            int count = 1;

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.xpath(".//td"));
                GoodsRowDDO goodsRowDDO = new GoodsRowDDO();

                goodsRowDDO.setId(count);
                goodsRowDDO.setName(cells.get(0).getText());
                goodsRowDDO.setType(cells.get(1).getText());
                goodsRowDDO.setExotic(Boolean.parseBoolean(cells.get(2).getText()));

                goodsRowDDOS.add(goodsRowDDO);
                count++;
            }
        });


        return goodsRowDDOS;
    }
}
