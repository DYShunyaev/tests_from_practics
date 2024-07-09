package pages;

import config.config_provider.config_provider.ConfigProvider;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import web_driver_config.WebDriverInitial;

import java.time.Duration;

public class MainPage {

    private final By sandboxList = By.xpath("//*[contains(@class,\"nav-link dropdown-toggle\")]");
    private final By sandboxListGoods = By.xpath("//a[text()='Товары']");
    private final By sandboxListResetData = By.xpath("//a[contains(@id,\"reset\")]");

    private final WebDriver driver = WebDriverInitial.getWebDriver().getDriver();

    public MainPage goToGoodsPage() {
        Allure.step("Переход на страницу \"Товары\"",() -> {
            waitingToClickableSandboxList(0);
            driver.findElement(sandboxListGoods).click();
        });
        return this;
    }

    public void resetData() {
        Allure.step("Переход на страницу \"Сброс данных\"",() -> {
            waitingToClickableSandboxList(0);
            driver.findElement(sandboxListResetData).click();
        });
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    private void waitingToClickableSandboxList(int count) {
        if (count > 5) throw new RuntimeException();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.elementToBeClickable(sandboxList))
                    .findElement(sandboxList)
                    .click();
        }
        catch (Exception e) {
            waitingToClickableSandboxList(count+1);
        }
    }
}
