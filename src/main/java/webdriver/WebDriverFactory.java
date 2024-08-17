package webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriverException;

public class WebDriverFactory {
    private static final String YANDEX_DRIVER_PATH = "C:\\chromedriver\\chromedriver.exe";
    private static final String YANDEX_BROWSER_PATH = "C:\\Users\\Lera\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe";

    public static WebDriver createWebDriver() {
        String browser = System.getProperty("browser");
        if (browser == null) {
            return createChromeDriver();
        }
        switch (browser) {
            case "yandex":
                return createYandexDriver();
            case "chrome":
                return createChromeDriver();
            default:
                throw new UnsupportedOperationException("Неподдерживаемый браузер: " + browser);
        }
    }

    private static WebDriver createChromeDriver() {
        try {
            ChromeOptions chromeOptions = new ChromeOptions();
            return new ChromeDriver(chromeOptions);
        } catch (WebDriverException e) {
            throw new RuntimeException("Не удалось создать ChromeDriver", e);
        }
    }

    private static WebDriver createYandexDriver() {
        try {
            System.setProperty("webdriver.chrome.driver", YANDEX_DRIVER_PATH);
            ChromeOptions yandexOptions = new ChromeOptions();
            yandexOptions.setBinary(YANDEX_BROWSER_PATH);
            return new ChromeDriver(yandexOptions);
        } catch (WebDriverException e) {
            throw new RuntimeException("Не удалось создать YandexDriver", e);
        }
    }
}
