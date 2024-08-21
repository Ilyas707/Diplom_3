package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ConstructorPage {
    private WebDriver driver;

    public ConstructorPage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectTab(String burgerTab) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(burgerTab)));
        driver.findElement(By.xpath(burgerTab)).click();
    }

    public boolean isTabActive(String currentTab) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(currentTab)));
        String getAttribute = driver.findElement(By.xpath(currentTab)).getAttribute("class");

        return (getAttribute.contains("current"));
    }
}
