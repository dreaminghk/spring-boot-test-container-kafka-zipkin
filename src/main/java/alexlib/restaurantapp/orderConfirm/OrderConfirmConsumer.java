package alexlib.restaurantapp.orderConfirm;

import alexlib.restaurantapp.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static alexlib.restaurantapp.KafkaTopicConfig.ORDER_CONFIRMED_TOPIC;
import static alexlib.restaurantapp.KafkaTopicConfig.ORDER_CREATED_TOPIC;

@Service
public class OrderConfirmConsumer {

    @Autowired
    private KafkaTemplate<Integer, Order> kafkaTemplate;

    @KafkaListener(topics = ORDER_CREATED_TOPIC, groupId = "ORDER_CONFIRM_GROUP_ID")
    public void listen(Order order) {
        System.out.println("==Order received==>    "+order.toString());
        kafkaTemplate.send(ORDER_CONFIRMED_TOPIC, order.orderNumber(), order);
    }
}
