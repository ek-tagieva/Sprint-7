package scooter;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListTest {
    @Test
    @DisplayName("Проверка получения списка заказов")
    public void gettingListOrders(){
        ValidatableResponse response = OrderClient.getOrderList().then();
        response.statusCode(200)
                .assertThat()
                .body("orders", notNullValue());
    }

}
