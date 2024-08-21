package apiclient;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import entity.User;
import entity.UserCredentials;
import static io.restassured.RestAssured.given;

public class UserService extends UserClient {
    private static final String USER_URI = BASE_URI + "auth/";

    @Step("Создание пользователя")
    public ValidatableResponse create(User user) {
        return given()
                .spec(getBaseReqSpec())
                .body(user)
                .when()
                .post(USER_URI + "register/")
                .then();
    }

    @Step("Вход пользователя")
    public ValidatableResponse login(UserCredentials userCredentials) {
        return given()
                .spec(getBaseReqSpec())
                .body(userCredentials)
                .when()
                .post(USER_URI + "login/")
                .then();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse delete(String token) {
        return given()
                .spec(getBaseReqSpec())
                .header("Authorization", token)
                .when()//при удалении в тело запроса ничего не передается (не пишем body)
                .delete(USER_URI+"user/")
                .then();
    }
}
