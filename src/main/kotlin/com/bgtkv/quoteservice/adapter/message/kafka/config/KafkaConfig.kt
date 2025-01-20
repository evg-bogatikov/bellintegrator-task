package com.bgtkv.quoteservice.adapter.message.kafka.config

import com.bgtkv.quoteservice.domain.entity.Quote
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.springframework.kafka.support.serializer.JsonSerializer
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer


@Configuration
class KafkaConfig {

    @Bean
    fun producerFactory(): ProducerFactory<String, Quote> {
        val config: MutableMap<String, Any> = HashMap()
        config[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        config[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        config[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java

        val jsonDeserializer = JsonDeserializer(Quote::class.java)
        jsonDeserializer.addTrustedPackages("*")

        return DefaultKafkaProducerFactory(config)
    }

    @Bean
    fun consumerFactory(): ConsumerFactory<String, Quote> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        props[ConsumerConfig.GROUP_ID_CONFIG] = "quote-consumers"
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java

        val jsonDeserializer = JsonDeserializer(Quote::class.java)
        jsonDeserializer.addTrustedPackages("*")

        return org.springframework.kafka.core.DefaultKafkaConsumerFactory(
            props,
            StringDeserializer(),
            jsonDeserializer
        )
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Quote> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, Quote>()
        factory.consumerFactory = consumerFactory()
        return factory
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, Quote> {
        return KafkaTemplate(producerFactory())
    }
}