package alexlib.restaurantapp.orderapi.service;

import alexlib.restaurantapp.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static alexlib.restaurantapp.KafkaTopicConfig.ORDER_CREATED_TOPIC;

@Service
public class OrderService {

    Logger logger = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private KafkaTemplate<Integer, Order> kafkaTemplate;

    private void blockingSendMessage(Order order) {
        try {
            kafkaTemplate.send(ORDER_CREATED_TOPIC, order.orderNumber(), order).get(10, TimeUnit.SECONDS);
            logger.info("Write to topic=["+ORDER_CREATED_TOPIC+"] success! message=["+order.toString()+"]");
        }
        catch (Exception e) {
            logger.error("Message =["+order.toString()+"] cannot be written to topic=["+ORDER_CREATED_TOPIC+"]", e);
        }
    }

    public void createOrder(Order order){
        blockingSendMessage(order);
    }
}
