package scooter;
import static io.restassured.RestAssured.given;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;


public class CourierClient extends RestClient {
    public static final String COURIER_CREATE = "/api/v1/courier/";
    public static final String COURIER_LOGIN = "/api/v1/courier/login";
    public static final String COURIER_DELETE = "/api/v1/courier/";

    @Step("Создание курьера")
    public static ValidatableResponse createCourier(CourierRegisterPojo courierRegisterPojo) {
        return given()
                .spec(RestClient.getBaseSpec())
                .when()
                .body(courierRegisterPojo).log().all()
                .post(COURIER_CREATE).then().log().all();
    }

    @Step("Получение id")
    public static String getId(ValidatableResponse response) {
        return response.extract().path("id").toString();
    }

    @Step ("Логин курьера в системе")
    public static ValidatableResponse loginCourier(CourierLoginPojo courierLoginPojoPojo){

        return given()
                .spec(RestClient.getBaseSpec())
                .when()
                .body(courierLoginPojoPojo).log().all()
                .post(COURIER_LOGIN).then().log().all();
    }
    @Step ("Удаление курьера")
    public static ValidatableResponse deleteCourier(String id){
        return given()
                .spec(RestClient.getBaseSpec())
                .when().log().all()
                .delete(COURIER_DELETE + id).then().log().all();

    }

}
