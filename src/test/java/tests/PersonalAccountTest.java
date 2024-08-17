package tests;

import apiclient.UserService;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobjects.LoginPage;
import pageobjects.MainPage;
import static webdriver.WebDriverFactory.createWebDriver;
import static org.junit.Assert.assertEquals;

public class PersonalAccountTest {
    private UserService userService;
    private String token;
    private WebDriver driver;
    private static final String pageUrl = "https://stellarburgers.nomoreparties.site/";

    @BeforeClass
    @Step("Глобальная настройка тестов")
    public static void globalSetUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Before
    @Step("Подготовка данных для теста")
    public void setUp() {
        driver = createWebDriver();
        userService = new UserService();
    }

    @Test
    @Step("Переход в личный кабинет")
    @DisplayName("Тест перехода в личный кабинет по клику на «Личный кабинет»")
    @Description("Тест проверяет, что пользователь может успешно перейти в личный кабинет по клику на «Личный кабинет»")
    public void PersonalAccountPageSwitchTest() {
        User user = new User("liannogght@yandex.ru", "houndrommi", "istaterbag");
        ValidatableResponse createResponse = userService.create(user);
        LoginPage loginPage = new LoginPage(driver);
        MainPage mainStellarBurgersPage = new MainPage(driver);
        driver.get(pageUrl);
        loginPage.enterLoginPage(".//div/button[text()='Войти в аккаунт']");
        loginPage.loginUserPage("liannogght@yandex.ru", "houndrommi");
        mainStellarBurgersPage.checkPersonalAccount();
        token = createResponse.extract().path("accessToken");
        String expectedPersonalAccountText = "В этом разделе вы можете изменить свои персональные данные";
        String actualPersonalAccountText = mainStellarBurgersPage.getAuthorizedAccountPageText();
        assertEquals(expectedPersonalAccountText, actualPersonalAccountText);
    }

    @Step("Очистка данных и завершение работы браузера после теста")
    @After
    public void tearDownAndClearData() {
        driver.quit();
        userService.delete(token);
    }
}
