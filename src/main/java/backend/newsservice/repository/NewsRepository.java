package backend.newsservice.repository;

import backend.newsservice.entity.NewsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Long> {
    @NonNull
    Page<NewsEntity> findAll(@NonNull Pageable pageable);
}
