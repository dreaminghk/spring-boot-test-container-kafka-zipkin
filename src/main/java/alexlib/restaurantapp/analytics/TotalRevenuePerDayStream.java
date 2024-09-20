package alexlib.restaurantapp.analytics;

import alexlib.restaurantapp.model.Order;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Service;

import static alexlib.restaurantapp.KafkaStreamConfig.REVENUE_STREAM;
import static alexlib.restaurantapp.KafkaTopicConfig.ORDER_CONFIRMED_TOPIC;
import static alexlib.restaurantapp.KafkaTopicConfig.ORDER_PROCESSED_TOPIC;

@Service
public class TotalRevenuePerDayStream {
    public static final String TOTAL_REVENUE = "totalRevenue";

    private final Serde<Integer> integerSerdes = Serdes.Integer();

    @Bean
    public KTable<Integer, Integer> processTotalRevenueStream(StreamsBuilder kStreamBuilder) {
        JsonSerde<Order> jsonSerde = new JsonSerde<>(Order.class);
        KStream<Integer, Order> stream = kStreamBuilder.stream(ORDER_CONFIRMED_TOPIC, Consumed.with(integerSerdes, jsonSerde));
        KTable<Integer, Integer> revenueTable = stream
                .map((key, order) -> KeyValue.pair(key, order.totalPrice()))
                .groupByKey(Grouped.with(integerSerdes, integerSerdes))
                //.groupByKey()
                //.reduce(Integer::sum, Materialized.as("counts"));
                .reduce(Integer::sum, Materialized.as("revenue"));

        revenueTable.toStream().to(REVENUE_STREAM, Produced.with(integerSerdes, integerSerdes));

        stream.print(Printed.toSysOut());

        return revenueTable;
    }
}
