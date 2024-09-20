package alexlib.restaurantapp;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class ContainerConfiguration {

    @Bean
    @ServiceConnection(name="openzipkin/zipkin")
    GenericContainer<?> zipkinContainer(){
        return new GenericContainer<>(DockerImageName.parse("openzipkin/zipkin:3-arm64"))
                .withExposedPorts(9411);
    }

    @Bean
    @ServiceConnection
    KafkaContainer kafkaContainer(DynamicPropertyRegistry properties) {
        KafkaContainer container = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
        properties.add("spring.kafka.bootstrap-servers", container::getBootstrapServers);
        return container;
    }
}
