package backend.newsservice.dto;

import java.util.List;

public record ListCommentResponse (List<CommentResponse> comments, int size) {
}
