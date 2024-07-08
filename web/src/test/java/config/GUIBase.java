package config;

import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.WebDriver;
import web_driver_config.WebDriverInitial;

@Slf4j
@Tag("GUI")
public abstract class GUIBase {

    protected static WebDriver driver;

    @BeforeAll
    protected static void setUp() {
        Allure.step("Запуск WebDriver", () -> {
            driver = WebDriverInitial.getWebDriver().getDriver();
        });
    }

    @AfterAll
    protected static void tearDown() {
        Allure.step("Закрытие WebDriver",() -> {
            driver.close();
            log.info(" - 'Test ends.'");
        });
    }
}
