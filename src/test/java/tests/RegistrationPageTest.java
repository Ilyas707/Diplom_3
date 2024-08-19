package tests;

import apiclient.UserService;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import entity.UserCredentials;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobjects.RegistrationPage;
import static webdriver.WebDriverFactory.createWebDriver;
import static org.junit.Assert.assertEquals;

public class RegistrationPageTest {
    private UserService userService;
    private String token;
    private WebDriver driver;
    private static final String pageUrl = "https://stellarburgers.nomoreparties.site/";

    @BeforeClass
    public static void globalSetUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Before
    public void setUp() {
        driver = createWebDriver();
        userService = new UserService();
    }

    @Test
    @DisplayName("Тест успешной регистрации")
    @Description("Тест проверяет, что пользователь может успешно зарегистрироваться при вводе корректных и уникальных данных")
    public void successfulRegistrationTest() {
        UserCredentials userCredentials = new UserCredentials("ycionculli@yandex.ru", "enloquessi");
        driver.get(pageUrl);
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.waitForPageLoading();
        registrationPage.registerUserPage("hortivockt","ycionculli@yandex.ru", "enloquessi");
        ValidatableResponse loginResponse = userService.login(userCredentials);
        token = loginResponse.extract().path("accessToken");
        String expectedText = "Вход";
        String actualText = registrationPage.getLoginPageText();
        assertEquals(expectedText, actualText);
    }
    @Test
    @DisplayName("Тест неуспешной регистрации при вводе пароля длиной меньше 6 символов")
    @Description("Тест проверяет, что пользователь не сможет успешно зарегистрироваться при вводе пароля длиной меньше 6 символов")
    public void incorrectPasswordMessageTest() {
        driver.get(pageUrl);
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.waitForPageLoading();
        registrationPage.registerUserPage("aingrocer","urapygort@yandex.ru", "9876");
        token = "null";
        String expectedMessage = "Некорректный пароль";
        String actualMessage = registrationPage.getIncorrectPasswordMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @After
    public void tearDownAndClearData() {
        driver.quit();
        if (token != null) {
            userService.delete(token);
        }
    }
}
