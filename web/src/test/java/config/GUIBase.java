package config;

import config.config_log4j.Test;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web_driver_config.WebDriverInitial;

@Tag("GUI")
public abstract class GUIBase {

    protected static WebDriver driver;

    @BeforeEach
    protected void setUp() {
        driver = WebDriverInitial.getWebDriver().getDriver();
    }

    @AfterEach
    protected void tearDown() {
        WebDriverInitial.closeWebBrowser();
    }
}
