package scooter;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final OrderPojo orderPojo;
    private OrderClient orderClient;

    public CreateOrderTest(OrderPojo orderPojo) {
        this.orderPojo = orderPojo;
    }

    @Parameterized.Parameters
    public static Object[][] orderData(){
        return new Object[][] {
                { new OrderPojo("Мари", "Иванова", "Камова 9", "Красносельская", "88988988998", 1, "29-04-2023", "Хочу лето", new String[]{"BLACK"})},
                { new OrderPojo("Мари", "Иванова", "Камова 9", "Красносельская", "88988988998", 1, "29-04-2023", "Хочу лето", new String[]{"GREY"})},
                { new OrderPojo("Мари", "Иванова", "Камова 9", "Красносельская", "88988988998", 1, "29-04-2023", "Хочу лето", new String[]{"BLACK", "GREY"})},
                { new OrderPojo("Мари", "Иванова", "Камова 9", "Красносельская", "88988988998", 1, "29-04-2023", "Хочу лето", new String[]{})},
        };
    }
    @Test
    @DisplayName("Проверка создания заказов с разными цветами")
    public void createOrdersWithDifferentColors(){
        OrderClient orderClient = new OrderClient();
        ValidatableResponse response = orderClient.createOrder(orderPojo).then();
        response.statusCode(201)
                .assertThat()
                .body("track", notNullValue());

    }
}
