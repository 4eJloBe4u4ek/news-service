package backend.newsservice.client;

import backend.newsservice.config.NewsConfig;
import backend.newsservice.dto.UserInfoResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthClient {
    private final WebClient authClient;

    public AuthClient(NewsConfig newsConfig) {
        this.authClient = WebClient.builder().baseUrl(newsConfig.authService().baseUrl()).build();
    }

    public UserInfoResponse getUserById(Long userId) {
        return authClient.get()
                .uri("/auth/users/{id}", userId)
                .retrieve()
                .bodyToMono(UserInfoResponse.class)
                .block();
    }
}
