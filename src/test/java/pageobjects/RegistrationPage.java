package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class RegistrationPage {
    private WebDriver driver;

    // Кнопка "Войти в аккаунт"
    private static final By loginButton = By.xpath(".//button[text()='Войти в аккаунт']");
    // Сслыка для перехода на страницу регистрации
    private static final By signUpButton = By.xpath(".//div/p/a[@href='/register']");
    // Поле "Имя"
    private static final By nameField = By.xpath("(.//div/input)[1]");
    // Поле "Email"
    private static final By emailField = By.xpath("(.//div/input)[2]");
    // Поле "Пароль"
    private static final By passwordField = By.xpath("(.//div/input)[3]");
    // Кнопка "Зарегистрироваться"
    private static final By registerButton = By.xpath(".//button[text()='Зарегистрироваться']");
    // Кнопка "Войти"
    private static final By successfulRegistrationPage = By.xpath(".//div/h2[text()='Вход']");
    // Сообщение ошибки при вводе некорректного пароля
    private static final By incorrectPasswordMessage= By.xpath("(.//div/p)[1]");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    // Метод для ожидания загрузки страницы
    public void waitForPageLoading() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
    }

    // Метод для перехода на страницу регистрации
    public void loginToSignUp() {
        driver.findElement(loginButton).click();
        driver.findElement(signUpButton).click();
    }

    // Метод для ввода значения в поле "Имя"
    public void setRegisterName(String registerName) {
        driver.findElement(nameField).sendKeys(registerName);
    }
    // Метод для ввода значения в поле "Email"
    public void setRegisterEmail(String registerEmail) {
        driver.findElement(emailField).sendKeys(registerEmail);
    }
    // Метод для ввода значения в поле "Пароль"
    public void setRegisterPassword(String registerPassword) {
        driver.findElement(passwordField).sendKeys(registerPassword);
    }
    // Метод для нажатия на кнопку "Зарегистрироваться"
    public void clickRegisterButton() {
        driver.findElement(registerButton).click();
    }
    // Метод для получения сообщения ошибки при вводе некорректного пароля
    public String getIncorrectPasswordMessage(){
        return driver.findElement(incorrectPasswordMessage).getText();
    }
    public String getLoginPageText(){
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(successfulRegistrationPage));
        return driver.findElement(successfulRegistrationPage).getText();
    }

    public void registerUserPage(String registerName, String registerEmail, String registerPassword){
        loginToSignUp();
        setRegisterName(registerName);
        setRegisterEmail(registerEmail);
        setRegisterPassword(registerPassword);
        clickRegisterButton();
    }
}
