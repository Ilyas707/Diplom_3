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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import pageobjects.LoginPage;
import pageobjects.MainPage;
import pageobjects.UserPage;
import static webdriver.WebDriverFactory.createWebDriver;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class SwitchToConstructorTest {
    private String constructorButton;

    public SwitchToConstructorTest(String constructorButton) {
        this.constructorButton = constructorButton;
    }

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

    @Parameterized.Parameters
    public static Object[][] getConstructorButtons() {
        return new Object[][] {
                {".//li/a/p[text()='Конструктор']"},
                {".//div/a[@href='/']"}
        };
    }

    @Test
    @Step("Переход из личного кабинета в конструктор")
    @DisplayName("Тест перехода из личного кабинета в конструктор по клику на «Конструктор» и на логотип Stellar Burgers")
    @Description("Тест проверяет, что пользователь может успешно перейти из личного кабинета в конструктор по клику на «Конструктор» и на логотип Stellar Burgers")
    public void switchingToConstructorTest() {
        User user = new User("aissieros@yandex.ru", "henatewit", "nadensefl");
        ValidatableResponse createResponse = userService.create(user);
        UserPage userPage = new UserPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        MainPage mainStellarBurgersPage = new MainPage(driver);
        driver.get(pageUrl);
        userPage.waitForPageLoading();
        loginPage.enterLoginPage(".//div/button[text()='Войти в аккаунт']");
        loginPage.loginUserPage("aissieros@yandex.ru", "henatewit");
        mainStellarBurgersPage.checkPersonalAccount();
        token = createResponse.extract().path("accessToken");
        userPage.enterConstructor(constructorButton);
        String expectedConstructorText = "Соберите бургер";
        String actualConstructorText = userPage.getLoginPageText();
        assertEquals(expectedConstructorText, actualConstructorText);
    }

    @Step("Очистка данных и завершение работы браузера после теста")
    @After
    public void tearDownAndClearData() {
        driver.quit();
        userService.delete(token);
    }
}
