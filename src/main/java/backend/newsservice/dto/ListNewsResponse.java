package backend.newsservice.dto;

import java.util.List;

public record ListNewsResponse (List<NewsResponse> news, int size)  {
}
