package com.goormplay.uiservice.ui.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.goormplay.uiservice.ui.entity.InteractionEntity;

@Repository
public interface InteractionRepository extends MongoRepository<InteractionEntity, String> {
    // 특정 사용자가 특정 컨텐츠 좋아요 했는지 확인
    boolean existsByUserIdAndContentIdAndLikedTrue(String userId, String contentId);
    // 좋아요 상태 조회
    Optional<InteractionEntity> findByUserIdAndContentId(String userId, String contentId);
    // 유저 좋아요한 contentId 리스트
    List<InteractionEntity> findByUserIdAndLikedIsTrue(String userId);
}
