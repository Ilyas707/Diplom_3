package tests;

import apiclient.UserService;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import entity.User;
import entity.UserCredentials;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import pageobjects.LoginPage;
import static webdriver.WebDriverFactory.createWebDriver;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class LoginPathTest {

    private UserService userService;
    private String token;
    private String[] buttons;

    public LoginPathTest(String[] buttons) {
        this.buttons = buttons;
    }

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

    @Parameterized.Parameters
    public static Object[][] getButtons() {
        return new Object[][] {
                {new String[]{".//div/button[text()='Войти в аккаунт']"}},
                {new String[]{".//a/p[text()='Личный Кабинет']"}},
                {new String[]{".//a/p[text()='Личный Кабинет']", ".//p/a[text()='Зарегистрироваться']", ".//p/a[text()='Войти']"}},
                {new String[]{".//a/p[text()='Личный Кабинет']", ".//p/a[text()='Восстановить пароль']", ".//p/a[text()='Войти']"}}
        };
    }

    @Test
    @DisplayName("Тест входа в аккаунт через различные кнопки")
    @Description("Тест проверяет, что пользователь может успешно войти в систему, используя различные пути.")
    public void logIntoAccountTest() {
        User user = new User("omeanticeinn@yandex.ru", "bowlandman", "iterntichi");
        ValidatableResponse createResponse = userService.create(user);
        driver.get(pageUrl);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitForPageLoading();
        for(String button : buttons){
            loginPage.enterLoginPage(button);
        }
        loginPage.loginUserPage("omeanticeinn@yandex.ru", "bowlandman");
        token = userService.login(UserCredentials.from(user)).extract().path("accessToken");
        String successMessage = createResponse.extract().path("success").toString();
        String expectedButtonForAuthorizedUser = "Оформить заказ";
        String actualButtonForAuthorizedUser = loginPage.createOrderButton();
        assertEquals(successMessage, "true");
        assertEquals(expectedButtonForAuthorizedUser, actualButtonForAuthorizedUser);
    }

    @After
    public void tearDownAndClearData() {
        driver.quit();
        if (token != null) {
            userService.delete(token);
        }
    }
}
