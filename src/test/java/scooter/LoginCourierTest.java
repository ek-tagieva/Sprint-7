package scooter;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.apache.http.HttpStatus.*;

public class LoginCourierTest {
    CourierRegisterPojo courierRegisterPojo;
    CourierClient courierClient;
    String id;
    @Before
    public void setUp() {
        courierRegisterPojo = CourierRegisterPojo.generateCourierRandom();
        CourierClient.createCourier(courierRegisterPojo);
    }

    @Test
    @DisplayName("Проверка авторизации курьера в системе")
    public void courierAuthorizationSystem(){
        ValidatableResponse response = CourierClient.loginCourier(CourierLoginPojo.getCredentials(courierRegisterPojo));

        response
                .statusCode(SC_OK)
                .assertThat()
                .body("id",is(notNullValue()));
    }
    @Test
    @DisplayName("Проверка авторизации курьера без логина")
    public void courierAuthorizationWithoutLogin(){
        courierRegisterPojo.setLogin("");
        ValidatableResponse response = CourierClient.loginCourier(CourierLoginPojo.getCredentials(courierRegisterPojo));
        response
                .statusCode(SC_BAD_REQUEST)
                .assertThat()
                .body("code",equalTo(SC_BAD_REQUEST))
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));

    }
    @Test
    @DisplayName("Проверка авторизации курьера без пароля")
    public void courierAuthorizationWithoutPassword(){
        courierRegisterPojo.setPassword("");
        ValidatableResponse response = CourierClient.loginCourier(CourierLoginPojo.getCredentials(courierRegisterPojo));
        response
                .statusCode(SC_BAD_REQUEST)
                .assertThat()
                .body("code",equalTo(SC_BAD_REQUEST))
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));

    }
    @Test
    @DisplayName("Проверка авторизации курьера с неверным логином")
    public void courierAuthorizationWithInvalidLogin(){
        courierRegisterPojo.setLogin("vfkgrp");
        ValidatableResponse response = CourierClient.loginCourier(CourierLoginPojo.getCredentials(courierRegisterPojo));
        response
                .statusCode(SC_NOT_FOUND)
                .assertThat()
                .body("code",equalTo(SC_NOT_FOUND))
                .and()
                .body("message", equalTo("Учетная запись не найдена"));

    }
    @Test
    @DisplayName("Проверка авторизации курьера с неверным паролем")
    public void courierAuthorizationWithInvalidPassword(){
        courierRegisterPojo.setPassword("sdefr");
        ValidatableResponse response = CourierClient.loginCourier(CourierLoginPojo.getCredentials(courierRegisterPojo));
        response
                .statusCode(SC_NOT_FOUND)
                .assertThat()
                .body("code",equalTo(SC_NOT_FOUND))
                .and()
                .body("message", equalTo("Учетная запись не найдена"));

    }
    @After
    public void cleanUp(){

        courierClient.deleteCourier(id);
    }

}
