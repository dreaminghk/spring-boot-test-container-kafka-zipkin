package alexlib.restaurantapp.model;

public record Order(int orderNumber, String items, int customerId, int totalPrice, OrderStatus status, String createTime) {
}

