// BlogPostRepository.java
package com.enesderin.softvra_backend.repo;

import com.enesderin.softvra_backend.model.BlogPost;
import com.enesderin.softvra_backend.model.BlogStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

    Optional<BlogPost> findBySlug(String slug);

    List<BlogPost> findByStatusOrderByPublishedDateDesc(BlogStatus status);

    List<BlogPost> findByStatusAndCategoryOrderByPublishedDateDesc(BlogStatus status, String category);

    List<BlogPost> findByIsFeaturedTrueAndStatus(BlogStatus status);

    List<BlogPost> findAllByOrderByCreatedAtDesc();

    boolean existsBySlug(String slug);

    @Modifying
    @Query("UPDATE BlogPost b SET b.viewCount = b.viewCount + 1 WHERE b.id = :id")
    void incrementViewCount(Long id);

    Long countByStatus(BlogStatus status);
}