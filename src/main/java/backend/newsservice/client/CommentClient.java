package backend.newsservice.client;

import backend.newsservice.config.NewsConfig;
import backend.newsservice.dto.ListCommentResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class CommentClient {
    private final WebClient commentClient;

    public CommentClient(NewsConfig newsConfig) {
        this.commentClient = WebClient.builder().baseUrl(newsConfig.commentService().baseUrl()).build();
    }

    public ListCommentResponse getAllCommentsForNews(Long newsId) {
        return commentClient.get()
                .uri(uri -> uri
                        .path("/news/{id}/comments")
                        .queryParam("page", 0)
                        .queryParam("size", Integer.MAX_VALUE)
                        .build(newsId)
                )
                .retrieve()
                .bodyToMono(ListCommentResponse.class)
                .block();
    }
}
