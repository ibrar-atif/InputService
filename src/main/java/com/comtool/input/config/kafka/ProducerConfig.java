package com.comtool.input.config.kafka;

import static org.apache.kafka.clients.producer.ProducerConfig.ACKS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.BATCH_SIZE_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.LINGER_MS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.MAX_BLOCK_MS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.MAX_REQUEST_SIZE_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.RETRIES_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.comtool.input.dto.ProductDto;

@Configuration
public class ProducerConfig {
	
	@Value("${payload.producer.kafka.bootstrap.servers}")
	private String bootstrapServers;

	@Value("${payload.producer.key.serializer}")
	private String keySerializer;

	@Value("${payload.producer.value.serializer}")
	private String valueSerializer;

	@Value("${payload.producer.max.block.ms}")
	private String maxBlockMsConfig;

	@Value("${payload.producer.block.on.buffer.full}")
	private String blockOnBufferFull;

	@Value("${payload.producer.acks}")
	private String acks;

	@Value("${payload.producer.retries}")
	private int retries;

	@Value("${payload.producer.batch.size}")
	private int batchSize;

	@Value("${payload.producer.linger.ms}")
	private int linger;

	@Value("${payload.producer.max.request.size}")
	private int maxRequestSize;
	
	@Bean
	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<>();

		props.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
		props.put(VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);

		props.put(MAX_BLOCK_MS_CONFIG, maxBlockMsConfig);
		props.put(ACKS_CONFIG, acks);
		props.put(RETRIES_CONFIG, retries);
		props.put(BATCH_SIZE_CONFIG, batchSize);
		props.put(LINGER_MS_CONFIG, linger);
		props.put(MAX_REQUEST_SIZE_CONFIG, maxRequestSize);
		return props;
	}
	
	@Bean
	public ProducerFactory<String, ProductDto> getFactory(){
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	@Bean
	public KafkaTemplate<String,ProductDto> getKafkaTemplate(){
		return new KafkaTemplate<>(getFactory());
	}
	

}
