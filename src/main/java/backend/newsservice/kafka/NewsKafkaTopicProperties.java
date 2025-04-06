package backend.newsservice.kafka;

import lombok.Data;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.kafka.topics")
public class NewsKafkaTopicProperties {
    private String newsDeleted;
    private int partitions = 1;
    private short replicas = 1;

    public KafkaAdmin.NewTopics toNewTopics() {
        return new KafkaAdmin.NewTopics(
                new NewTopic(newsDeleted, partitions, replicas)
        );
    }
}
