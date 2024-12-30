package com.fastcampus.kafkahandson.ugc.adapter.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.*;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.ExponentialBackOff;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Bean
    @Primary
    public ConsumerFactory<String, Object> consumerFactory(KafkaProperties kafkaProperties) {
        return new DefaultKafkaConsumerFactory<>(Map.of(
                BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers(),
                KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ALLOW_AUTO_CREATE_TOPICS_CONFIG, "false",
                ENABLE_AUTO_COMMIT_CONFIG, "false"
        ));
    }

    @Bean
    @Primary
    public CommonErrorHandler errorHandler() {
        return new CustomDefaultErrorHandler(
                new AtomicReference<>(),
                new AtomicReference<>(),
                generateBackOff(),
                JsonProcessingException.class
        );
    }

    @Bean
    @Primary
    public ConcurrentKafkaListenerContainerFactory<String, Object>
            kafkaListenerContainerFactory(
                    ConsumerFactory<String, Object> consumerFactory,
                    CommonErrorHandler errorHandler
    ) {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(errorHandler);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
        return factory;
    }

    private BackOff generateBackOff() {
        ExponentialBackOff backOff = new ExponentialBackOff(1000, 2);
        backOff.setMaxAttempts(3);
        return backOff;
    }

    private static class CustomDefaultErrorHandler extends DefaultErrorHandler {
        private final AtomicReference<Consumer<?, ?>> consumerReference;
        private final AtomicReference<MessageListenerContainer> containerReference;

        @SafeVarargs
        public CustomDefaultErrorHandler(
                AtomicReference<Consumer<?, ?>> consumerReference,
                AtomicReference<MessageListenerContainer> containerReference,
                BackOff backOff,
                Class<? extends Exception>... exceptionTypes
        ) {
            super((record, exception) -> new CommonContainerStoppingErrorHandler()
                    .handleRemaining(
                            exception,
                            Collections.singletonList(record),
                            consumerReference.get(),
                            containerReference.get()
                    ), backOff);
            this.consumerReference = consumerReference;
            this.containerReference = containerReference;
            addNotRetryableExceptions(exceptionTypes);
        }

        @Override
        public void handleRemaining(
                Exception thrownException,
                List<ConsumerRecord<?, ?>> records,
                Consumer<?, ?> consumer,
                MessageListenerContainer container
        ) {
            consumerReference.set(consumer);
            containerReference.set(container);
            super.handleRemaining(thrownException, records, consumer, container);
        }
    }
}

