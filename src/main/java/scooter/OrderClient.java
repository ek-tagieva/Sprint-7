package scooter;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {
    private static final String ORDER_CREATE = "/api/v1/orders";

    @Step ("Создать заказ")
    public static Response createOrder(OrderPojo orderPojo) {
        Response createOrderResponse = given()
                .spec(RestClient.getBaseSpec())
                .body(orderPojo)
                .when()
                .post(ORDER_CREATE);
        return createOrderResponse;
    }
    @Step ("Получение списка заказов")
    public static Response getOrderList(){
        Response getOrderListResponse = given()
                .spec(RestClient.getBaseSpec())
                .body("")
                .get(ORDER_CREATE);
        return getOrderListResponse;
    }
}
