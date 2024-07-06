package pages;

import config.ConfigProvider;
import org.openqa.selenium.By;
import config.GUIBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage extends GUIBase {

    private final By sandboxList = By.xpath("//*[contains(@class,\"nav-link dropdown-toggle\")]");
    private final By sandboxListGoods = By.xpath("//a[text()='Товары']");
    private final By sandboxListResetData = By.xpath("//a[contains(@id,\"reset\")]");


    public MainPage() {
        PageFactory.initElements(driver,this);
    }

    public MainPage goToGoodsPage() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(sandboxList))
                .click();
        driver.findElement(sandboxListGoods).click();
        return this;
    }

    public MainPage resetData() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(sandboxList))
                .click();
        driver.findElement(sandboxListResetData).click();
        return this;
    }
}
