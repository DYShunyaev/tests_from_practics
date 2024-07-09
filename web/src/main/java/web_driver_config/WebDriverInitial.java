package web_driver_config;

import config.config_provider.config_provider.ConfigProvider;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

@Getter
public final class WebDriverInitial {

    private static final Logger logger = LoggerFactory.getLogger(WebDriverInitial.class);
    private static WebDriverInitial webDriverInitial;
    private final WebDriver driver;


    private WebDriverInitial() {
        try {
            WebDriverManager.chromiumdriver().config()
                    .setChromeDriverUrl(
                            new URL(ConfigProvider.DRIVER_URL));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        WebDriverManager.chromiumdriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.get(ConfigProvider.URL);
        logger.info("Driver is running");
    }

    public static WebDriverInitial getWebDriver() {
        if (webDriverInitial == null) webDriverInitial = new WebDriverInitial();
        return webDriverInitial;
    }

    public static void closeWebBrowser() {
        if (webDriverInitial != null) {
            webDriverInitial.getDriver().quit();
        }
        webDriverInitial = null;
        logger.info("Driver stopped");
    }
}
