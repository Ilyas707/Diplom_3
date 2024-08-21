package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private WebDriver driver;

    // Поле "Email"
    private static final By nameField = By.xpath("(.//div/input)[1]");
    // Поле "Пароль"
    private static final By passwordField = By.xpath("(.//div/input)[2]");
    // Кнопка "Войти"
    private static final By loginButton = By.xpath(".//button[text()='Войти']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Метод для ожидания загрузки страницы
    public void waitForPageLoading() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
    }

    // Метод для перехода на страницу входа в аккаунт
    public void enterLoginPage(String button) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(button)));
        driver.findElement(By.xpath(button)).click();
    }

    // Метод для ввода значения в поле "Email"
    public void setLoginName(String loginName) {
        driver.findElement(nameField).sendKeys(loginName);
    }

    // Метод для ввода значения в поле "Пароль"
    public void setLoginPassword(String loginPassword) {
        driver.findElement(passwordField).sendKeys(loginPassword);
    }
    // Метод для нажатия на кнопку "Войти"
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }
    public void loginUserPage(String loginName, String loginPassword){
        setLoginName(loginName);
        setLoginPassword(loginPassword);
        clickLoginButton();
    }
    // Метод проверяет что видна кнопка "Оформить заказ" (успешная авторизация)
    public String createOrderButton() {
        return driver.findElement(By.xpath(".//main/section[2]/div/button")).getText();
    }
}
