package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MainPage {
    private WebDriver driver;

    // Кнопка "Личный Кабинет"
    private static final By personalAccountButton = By.xpath(".//div/header/nav/a[@href='/account']");
    // Текст страницы авторизованного пользователя
    private static final By authorizedUserPageText = By.xpath(".//main/div/nav/p");
    // Кнопка "Выход"
    private static final By logoutFromAccountButton = By.xpath(".//div/nav/ul/li/button[text()='Выход']");
    // Текст страницы неавторизованного пользователя
    private static final By loginPageTextAfterLogout = By.xpath(".//main/div/h2[text()]");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // Метод для нажатия кнопки "Личный кабинет"
    public void checkPersonalAccount() {
        driver.findElement(personalAccountButton).click();
    }

    // Метод для получения текста страницы авторизованного пользователя
    public String getAuthorizedAccountPageText() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(authorizedUserPageText));
        return driver.findElement(authorizedUserPageText).getText();
    }

    // Метод для нажатия кнопки "Выход"
    public void logoutFromAccount(){
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(logoutFromAccountButton));
        driver.findElement(logoutFromAccountButton).click();
    }

    // Метод для получения текста страницы авторизованного пользователя
    public String getLoginPageTextAfterLogout() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(loginPageTextAfterLogout));
        return driver.findElement(loginPageTextAfterLogout).getText();
    }
}
