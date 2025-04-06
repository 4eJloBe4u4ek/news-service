package backend.newsservice.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class CommonKafkaConfig {
    private final NewsKafkaTopicProperties newsKafkaTopicProperties;
    private final KafkaProperties kafkaProperties;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        return new KafkaAdmin(
                Map.of(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,
                        kafkaProperties.getBootstrapServers())
        );
    }

    @Bean
    KafkaAdmin.NewTopics newsTopics() {
        return newsKafkaTopicProperties.toNewTopics();
    }
}
