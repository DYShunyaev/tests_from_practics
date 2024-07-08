package web_driver_config;

import config_provider.ConfigProvider;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

@Getter
public final class WebDriverInitial {

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
        WebDriverManager.chromiumdriver().clearDriverCache().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.get(ConfigProvider.URL);
    }

    public static WebDriverInitial getWebDriver() {
        if (webDriverInitial == null) webDriverInitial = new WebDriverInitial();
        return webDriverInitial;
    }
}
