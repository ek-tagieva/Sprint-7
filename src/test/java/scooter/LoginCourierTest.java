package scooter;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static scooter.CourierClient.COURIER_CREATE;
import static scooter.CourierLoginPojo.getCredentials;

public class LoginCourierTest {
    CourierRegisterPojo courierRegisterPojo;
    CourierClient courierClient;
    String courierId;
    @Before
    public void setUp() {

        courierRegisterPojo = CourierRegisterPojo.generateCourierRandom();
        CourierClient.createCourier(courierRegisterPojo);
        courierId = given()
                .spec(RestClient.getBaseSpec())
                .when()
                .body(getCredentials(courierRegisterPojo))
                .post(COURIER_CREATE + "login")
                .then().extract().path("id").toString();
    }

    @Test
    @DisplayName("Проверка авторизации курьера в системе")
    public void courierAuthorizationSystem(){
        ValidatableResponse response = CourierClient.loginCourier(CourierLoginPojo.getCredentials(courierRegisterPojo));
        response
                .statusCode(200)
                .assertThat()
                .body("id",is(notNullValue()));
    }
    @Test
    @DisplayName("Проверка авторизации курьера без логина")
    public void courierAuthorizationWithoutLogin(){
        courierRegisterPojo.setLogin("");
        ValidatableResponse response = CourierClient.loginCourier(CourierLoginPojo.getCredentials(courierRegisterPojo));
        response
                .statusCode(400)
                .assertThat()
                .body("code",equalTo(400))
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));

    }
    @Test
    @DisplayName("Проверка авторизации курьера без пароля")
    public void courierAuthorizationWithoutPassword(){
        courierRegisterPojo.setPassword("");
        ValidatableResponse response = CourierClient.loginCourier(CourierLoginPojo.getCredentials(courierRegisterPojo));
        response
                .statusCode(400)
                .assertThat()
                .body("code",equalTo(400))
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));

    }
    @Test
    @DisplayName("Проверка авторизации курьера с неверным логином")
    public void courierAuthorizationWithInvalidLogin(){
        courierRegisterPojo.setLogin("vfkgrp");
        ValidatableResponse response = CourierClient.loginCourier(CourierLoginPojo.getCredentials(courierRegisterPojo));
        response
                .statusCode(404)
                .assertThat()
                .body("code",equalTo(404))
                .and()
                .body("message", equalTo("Учетная запись не найдена"));

    }
    @Test
    @DisplayName("Проверка авторизации курьера с неверным паролем")
    public void courierAuthorizationWithInvalidPassword(){
        courierRegisterPojo.setPassword("sdefr");
        ValidatableResponse response = CourierClient.loginCourier(CourierLoginPojo.getCredentials(courierRegisterPojo));
        response
                .statusCode(404)
                .assertThat()
                .body("code",equalTo(404))
                .and()
                .body("message", equalTo("Учетная запись не найдена"));

    }
    @After
    public void cleanUp(){

        courierClient.deleteCourier(courierId);
    }

}
