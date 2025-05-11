package backend.newsservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app.comment-service")
public record NewsConfig(String baseUrl) {
}
