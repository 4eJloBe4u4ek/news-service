package backend.newsservice.dto;

import java.time.Instant;
import java.util.List;

public record NewsResponse(Long id, Instant time, String title, String text, Long authorId ,String authorName, List<CommentResponse> comments) {
}
