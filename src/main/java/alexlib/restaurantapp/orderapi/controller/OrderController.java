package alexlib.restaurantapp.orderapi.controller;

import alexlib.restaurantapp.model.Order;
import alexlib.restaurantapp.model.OrderStatus;
import alexlib.restaurantapp.orderapi.dto.OrderDTO;
import alexlib.restaurantapp.orderapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/order")
    OrderDTO newOrder(@RequestBody OrderDTO orderDto){
        Order order = new Order(
                orderDto.orderNumber(),
                orderDto.items(),
                orderDto.customerId(),
                orderDto.totalPrice(),
                OrderStatus.CREATED,
                Instant.now().toString()
        );
        orderService.createOrder(order);
        return orderDto;
    }

}
