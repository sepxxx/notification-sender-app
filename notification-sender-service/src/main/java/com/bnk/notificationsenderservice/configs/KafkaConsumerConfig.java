package com.bnk.notificationsenderservice.configs;

import com.bnk.notificationsenderservice.dtos.NotificationMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;
//    @Value(value = "${kafka.backoff.initial_interval}")
//    private Long initialInterval;
//    @Value(value = "${kafka.backoff.max_elapsed_time}")
//    private Long maxElapsedTime;
//    @Value(value = "${kafka.backoff.multiplier}")
//    private Long multiplier;

    @Bean
    public ConsumerFactory<String, NotificationMessage> NotificationMessageConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "foo");
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new JsonDeserializer<>(NotificationMessage.class, false)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, NotificationMessage>
    NotificationMessageKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, NotificationMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(NotificationMessageConsumerFactory());
//        factory.setCommonErrorHandler(errorHandler());
        return factory;
    }

//    @Bean
//    public DefaultErrorHandler errorHandler() {
//        BackOff fixedbackOff =  new FixedBackOff(interval, maxAttempts);
//        ExponentialBackOff expBackOff =  new ExponentialBackOff(initialInterval, multiplier);
//        expBackOff.setMaxElapsedTime(maxElapsedTime);
//        DefaultErrorHandler errorHandler = new DefaultErrorHandler((consumerRecord, e) -> {
//            TODO: логика когда все попытки исчерпаны
//            log.info("ПОПЫТКИ ИСЧЕРПАНЫ СООБЩЕНИЕ СКИПНУТО KEY: {} OFFSET: {}", consumerRecord.key(), consumerRecord.offset());
//        }, expBackOff);
//        errorHandler.addNotRetryableExceptions(HttpClientErrorException.class);//TODO: fixedbackOff??
//        errorHandler.addRetryableExceptions(ResourceAccessException.class); //default?
//        errorHandler.addRetryableExceptions(HttpServerErrorException.class);//default?
//
//        return errorHandler;
//    }
}