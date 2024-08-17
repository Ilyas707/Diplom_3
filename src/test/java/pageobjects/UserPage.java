package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class UserPage {
    private WebDriver driver;
    private static final By constructorText = By.xpath(".//main/section/h1");

    public UserPage(WebDriver driver) {
        this.driver = driver;
    }

    // Метод для ожидания загрузки страницы
    public void waitForPageLoading() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
    }

    // Метод для входа в конструктор
    public void enterConstructor(String constructorButton) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(constructorButton)));
        driver.findElement(By.xpath(constructorButton)).click();
    }
    // Метод для получения текста со страницы конструктора
    public String getLoginPageText(){
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(constructorText));
        return driver.findElement(constructorText).getText();
    }
}
