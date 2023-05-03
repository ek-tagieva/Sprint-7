package scooter;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import io.restassured.response.ValidatableResponse;
import static org.apache.http.HttpStatus.*;



public class CreatingCourierTest {
    CourierRegisterPojo courierRegisterPojo;
    String courierId;

   @Before
   public void setUp() {

       courierRegisterPojo = CourierRegisterPojo.generateCourierRandom();
   }
    @Test
    @DisplayName("Создание нового курьера")
    public void courierCanBeCreated() {
        ValidatableResponse regResponse = CourierClient.createCourier(courierRegisterPojo);
        ValidatableResponse loginResponse = CourierClient.loginCourier(CourierLoginPojo.getCredentials(courierRegisterPojo));
        courierId = loginResponse.extract().path("id").toString();
        regResponse
                .statusCode(SC_CREATED)
                .assertThat()
                .body("ok", is(true));
    }
    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    public void identicalCourierCreation(){
        CourierClient.createCourier(courierRegisterPojo);
        courierId = CourierClient.loginCourier(CourierLoginPojo.getCredentials(courierRegisterPojo))
                .extract()
                .path("id")
                .toString();

        ValidatableResponse response = CourierClient.createCourier(courierRegisterPojo);
        response
                .statusCode(SC_CONFLICT)
                .assertThat()
                .body("code",equalTo(SC_CONFLICT))
                .and()
                .body("message",equalTo("Этот логин уже используется. Попробуйте другой."));
    }
    @Test
    @DisplayName("Проверка создания курьера без логина")
    public void creatingCourierWithoutLogin(){
        courierRegisterPojo.setLogin(null);
        ValidatableResponse response = CourierClient.createCourier(courierRegisterPojo);
        response
                .statusCode(SC_BAD_REQUEST)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Проверка создания курьера без пароля")
    public void creatingCourierWithoutPassword(){
        courierRegisterPojo.setLogin(null);
        ValidatableResponse response = CourierClient.createCourier(courierRegisterPojo);
            response
                    .statusCode(SC_BAD_REQUEST)
                    .assertThat()
                    .body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }

    @Test
    @DisplayName("Проверка создания курьера без имени")
    public void creatingCourierWithoutName(){
        courierRegisterPojo.setFirstName(null);
        ValidatableResponse regResponse = CourierClient.createCourier(courierRegisterPojo);
        ValidatableResponse loginResponse = CourierClient.loginCourier(CourierLoginPojo.getCredentials(courierRegisterPojo));
        courierId = loginResponse.extract().path("id").toString();
        regResponse
                .statusCode(SC_CREATED)
                .assertThat()
                .body("ok", is(true));

    }
    @After
    public void cleanUp(){
        CourierClient.deleteCourier(courierId);
    }
}
