package backend.newsservice.service;

import backend.newsservice.client.CommentClient;
import backend.newsservice.dto.CommentResponse;
import backend.newsservice.dto.ListNewsResponse;
import backend.newsservice.dto.NewsResponse;
import backend.newsservice.entity.NewsEntity;
import backend.newsservice.exception.NewsNotFoundException;
import backend.newsservice.exception.UnauthorizedException;
import backend.newsservice.kafka.NewsDeletedEvent;
import backend.newsservice.kafka.NewsKafkaTopicProperties;
import backend.newsservice.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;
    private final KafkaTemplate<Long, Object> kafkaTemplate;
    private final NewsKafkaTopicProperties topicProperties;
    private final CommentClient commentClient;

    public ListNewsResponse getNewsPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "time");
        Page<NewsEntity> newsPage = newsRepository.findAll(pageable);
        int totalPages = newsPage.getTotalPages();

        List<NewsResponse> newsResponses = newsPage.getContent().stream().map(this::toNewsResponse).toList();
        return new ListNewsResponse(newsResponses, newsResponses.size(), totalPages);
    }

    public NewsResponse getNewsById(Long id) {
        return toNewsResponse(newsRepository.findById(id).orElseThrow(() -> new NewsNotFoundException("News not found")));
    }

    public NewsResponse createNews(String title, String text) {
        String author = getCurrentUserName();
        NewsEntity newsEntity = new NewsEntity();
        newsEntity.setAuthor(author);
        newsEntity.setTitle(title);
        newsEntity.setText(text);
        newsEntity.setTime(Instant.now());
        return toNewsResponse(newsRepository.save(newsEntity));
    }

    @Transactional
    public NewsResponse updateNews(Long id, String title, String text) {
        NewsEntity news = newsRepository.findById(id).orElseThrow(() -> new NewsNotFoundException("News not found"));
        String author = getCurrentUserName();
        if (!news.getAuthor().equals(author) && !hasAdminRole()) {
            throw new UnauthorizedException("Access denied");
        }

        news.setTitle(title);
        news.setText(text);
        return toNewsResponse(newsRepository.save(news));
    }

    @Transactional
    public void deleteNews(Long id) {
        NewsEntity news = newsRepository.findById(id).orElseThrow(() -> new NewsNotFoundException("News not found"));
        String author = getCurrentUserName();
        if (!news.getAuthor().equals(author) && !hasAdminRole()) {
            throw new UnauthorizedException("Access denied");
        }

        kafkaTemplate.send(topicProperties.getNewsDeleted(), new NewsDeletedEvent(news.getId()));

        newsRepository.delete(news);
    }

    private Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UnauthorizedException("Access denied: no authentication found");
        } else {
            return authentication;
        }
    }

    private boolean hasAdminRole() {
        return getAuthentication().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    private String getCurrentUserName() {
        return getAuthentication().getDetails().toString();
    }

    private NewsResponse toNewsResponse(NewsEntity newsEntity) {
        List<CommentResponse> comments = commentClient.getAllCommentsForNews(newsEntity.getId()).comments();
        return new NewsResponse(
                newsEntity.getId(),
                newsEntity.getTime(),
                newsEntity.getTitle(),
                newsEntity.getText(),
                newsEntity.getAuthor(),
                comments);
    }
}
