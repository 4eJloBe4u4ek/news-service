package backend.newsservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app")
public record NewsConfig(ServiceUrl commentService, ServiceUrl authService) {
    public record ServiceUrl(String baseUrl) {}
}
