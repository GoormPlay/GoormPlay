package com.goormplay.reviewservice.review.repository;

import com.goormplay.reviewservice.review.domain.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends MongoRepository<Review, String> {
    List<Review> findByContentId(String contentId);
    Optional<Review> findByContentIdAndUserId(String contentId, String userId);
    void deleteByContentIdAndUserId(String contentId, String userId);
}
