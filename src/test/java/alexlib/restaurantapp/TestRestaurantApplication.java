package alexlib.restaurantapp;

import org.springframework.boot.SpringApplication;

public class TestRestaurantApplication {
    public static void main(String[] args) {
        SpringApplication
                .from(RestaurantApplication::main)
                .with(ContainerConfiguration.class)
                .run(args);
    }
}
