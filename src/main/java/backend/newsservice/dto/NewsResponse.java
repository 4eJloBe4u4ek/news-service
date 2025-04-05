package backend.newsservice.dto;

import java.time.Instant;

public record NewsResponse(Long id, Instant time, String title, String text, String author) {
}
