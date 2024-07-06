package pages;

import org.openqa.selenium.By;
import config.GUIBase;
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
        waitingToClickableSandboxList();
        driver.findElement(sandboxListGoods).click();
        return this;
    }

    public MainPage resetData() {
        waitingToClickableSandboxList();
        driver.findElement(sandboxListResetData).click();
        return this;
    }

    private void waitingToClickableSandboxList() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.elementToBeClickable(sandboxList))
                    .findElement(sandboxList)
                    .click();
        }
        catch (Exception e) {
            waitingToClickableSandboxList();
        }
    }
}
