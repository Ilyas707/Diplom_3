package tests;

import apiclient.UserService;
import io.qameta.allure.Description;
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
import pageobjects.ConstructorPage;
import pageobjects.LoginPage;
import static webdriver.WebDriverFactory.createWebDriver;
import static org.junit.Assert.assertTrue;

public class ConstructorTest {
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
    @DisplayName("Тест перехода к разделу Булки")
    @Description("Проверка, что можно перейти к разделу Булки")
    public void bunTabTest() {
        User user = new User("towdesom@yandex.ru", "comonici", "rthomint");
        ValidatableResponse createResponse = userService.create(user);
        LoginPage loginPage = new LoginPage(driver);
        ConstructorPage constructorPage = new ConstructorPage(driver);
        driver.get(pageUrl);
        loginPage.enterLoginPage(".//div/button[text()='Войти в аккаунт']");
        loginPage.loginUserPage("towdesom@yandex.ru", "comonici");
        token = createResponse.extract().path("accessToken");
        loginPage.waitForPageLoading();
        boolean isBunsElementActual = constructorPage.isTabActive("(.//section/div/div)[1]");
        assertTrue(isBunsElementActual);
    }

    @Test
    @DisplayName("Тест перехода к разделу Соусы")
    @Description("Проверка, что можно перейти к разделу Соусы")
    public void sauceTabTest() {
        User user = new User("uidentim@yandex.ru", "olinchid", "boatatul");
        ValidatableResponse createResponse = userService.create(user);
        LoginPage loginPage = new LoginPage(driver);
        ConstructorPage constructorPage = new ConstructorPage(driver);
        driver.get(pageUrl);
        loginPage.enterLoginPage(".//div/button[text()='Войти в аккаунт']");
        loginPage.loginUserPage("uidentim@yandex.ru", "olinchid");
        token = createResponse.extract().path("accessToken");
        constructorPage.selectTab(".//div/div/span[text()='Соусы']");
        boolean isSauceElementActual = constructorPage.isTabActive("(.//section/div/div)[2]");
        assertTrue(isSauceElementActual);
    }

    @Test
    @DisplayName("Тест перехода к разделу Начинки")
    @Description("Проверка, что можно перейти к разделу Начинки")
    public void fillingTabTest() {
        User user = new User("onallexque@yandex.ru", "ebegookupe", "looliverby");
        ValidatableResponse createResponse = userService.create(user);
        LoginPage loginPage = new LoginPage(driver);
        ConstructorPage constructorPage = new ConstructorPage(driver);
        driver.get(pageUrl);
        loginPage.enterLoginPage(".//div/button[text()='Войти в аккаунт']");
        loginPage.loginUserPage("onallexque@yandex.ru", "ebegookupe");
        token = createResponse.extract().path("accessToken");
        constructorPage.selectTab(".//div/div/span[text()='Начинки']");
        boolean isFillingElementActual = constructorPage.isTabActive("(.//section/div/div)[3]");
        assertTrue(isFillingElementActual);
    }

    @After
    public void tearDownAndClearData() {
        driver.quit();
        if (token != null) {
            userService.delete(token);
        }
    }
}
