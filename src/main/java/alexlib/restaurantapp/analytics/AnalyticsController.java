package alexlib.restaurantapp.analytics;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalyticsController {

    @Autowired
    private StreamsBuilderFactoryBean factoryBean;

    @GetMapping("/revenue/{orderNumber}")
    public Integer getWordCount(@PathVariable Integer orderNumber) {
        KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();
        ReadOnlyKeyValueStore<Integer, Integer> revenueStore = kafkaStreams.store(
                StoreQueryParameters.fromNameAndType("revenue", QueryableStoreTypes.keyValueStore())
        );

        return revenueStore.get(orderNumber);
    }

    @GetMapping("/revenue")
    public Integer getTotalWordCount() {
        KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();
        ReadOnlyKeyValueStore<Integer, Integer> revenueStore = kafkaStreams.store(
                StoreQueryParameters.fromNameAndType("revenue", QueryableStoreTypes.keyValueStore())
        );

        KeyValueIterator<Integer, Integer> range = revenueStore.all();

        Integer totalRevenue = 0;
        while (range.hasNext()){
            KeyValue<Integer, Integer> record = range.next();
            totalRevenue += record.value;
        }

        return totalRevenue;
    }

}
