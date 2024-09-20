package alexlib.restaurantapp;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public static final String ORDER_CREATED_TOPIC = "orderCreated";
    public static final String ORDER_CONFIRMED_TOPIC = "orderConfirmed";
    public static final String ORDER_PROCESSED_TOPIC = "orderProcessed";
    public static final String REVENUE_TOPIC = "revenue";

    @Bean
    public NewTopic orderCreatedTopic() {
        return TopicBuilder.name(ORDER_CREATED_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic orderConfirmedTopic() {
        return TopicBuilder.name(ORDER_CONFIRMED_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic orderProcessedTopic() {
        return TopicBuilder.name(ORDER_PROCESSED_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic revenueTopic() {
        return TopicBuilder.name(REVENUE_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
