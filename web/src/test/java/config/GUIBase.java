package config;

import config_provider.ConfigProvider;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

@Slf4j
public abstract class GUIBase {

    public static WebDriver driver;

    @BeforeAll
    static void setUp() throws MalformedURLException {
        Allure.step("Запуск WebDriver", () -> {
            WebDriverManager.chromiumdriver().config()
                    .setChromeDriverUrl(
                            new URL(ConfigProvider.DRIVER_URL));
            WebDriverManager.chromiumdriver().clearDriverCache().setup();
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
            driver.get(ConfigProvider.URL);
        });
    }

    @AfterAll
    static void tearDown() {
        Allure.step("Закрытие WebDriver",() -> {
            driver.close();
            log.info(" - 'Test ends.'");
        });
    }
}
