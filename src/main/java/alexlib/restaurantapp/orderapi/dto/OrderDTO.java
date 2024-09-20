package alexlib.restaurantapp.orderapi.dto;

public record OrderDTO(int orderNumber, String items, int customerId, int totalPrice) {
}
