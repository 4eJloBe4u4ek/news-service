package backend.newsservice.controller;

import backend.newsservice.dto.ListNewsResponse;
import backend.newsservice.dto.NewsCreate;
import backend.newsservice.dto.NewsResponse;
import backend.newsservice.dto.NewsUpdate;
import backend.newsservice.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @GetMapping("/news")
    public ResponseEntity<ListNewsResponse> getNewsPage(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok().body(newsService.getNewsPage(page, size));
    }

    @GetMapping("/news/{newsId}")
    public ResponseEntity<NewsResponse> getNews(@PathVariable Long newsId) {
        return ResponseEntity.ok().body(newsService.getNewsById(newsId));
    }

    @PostMapping("/news")
    public ResponseEntity<NewsResponse> createNews(@RequestBody NewsCreate newsCreate) {
        return ResponseEntity.ok().body(newsService.createNews(newsCreate.title(), newsCreate.text()));
    }

    @PutMapping("/news/{newsId}")
    public ResponseEntity<NewsResponse> updateNews(@PathVariable Long newsId, @RequestBody NewsUpdate newsUpdate) {
        return ResponseEntity.ok().body(newsService.updateNews(newsId, newsUpdate.title(), newsUpdate.text()));
    }

    @DeleteMapping("/news/{newsId}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long newsId) {
        newsService.deleteNews(newsId);
        return ResponseEntity.ok().build();
    }
}
