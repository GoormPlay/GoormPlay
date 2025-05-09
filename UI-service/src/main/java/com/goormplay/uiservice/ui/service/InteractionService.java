package com.goormplay.uiservice.ui.service;

import com.goormplay.uiservice.ui.entity.InteractionEntity;
import com.goormplay.uiservice.ui.entity.InteractionEntity.Interactions;
import com.goormplay.uiservice.ui.repository.InteractionRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InteractionService {

    // 데이터를 DB에 저장하거나 가져오기 위한 저장소
    private final InteractionRepository interactionRepository;

    // 저장소 도구를 클래스에 연결
    public InteractionService(InteractionRepository interactionRepository) {
        this.interactionRepository = interactionRepository;
    }

    // 실제 '좋아요' 처리 기능
    public InteractionEntity likeContent(String userId, String contentId, Boolean liked) {
        Optional<InteractionEntity> optional = interactionRepository.findByUserIdAndContentId(userId, contentId);

        // 없으면 새로 InteractionEntity 객체 만들기
        InteractionEntity entity = optional.orElseGet(InteractionEntity::new);
        entity.setUserId(userId);
        entity.setContentId(contentId);

        // '좋아요' 상태 저장
        Interactions interactions = new Interactions();
        interactions.setLiked(liked);
        entity.setInteractions(interactions);

        entity.setUpdatedAt(LocalDateTime.now());

        return interactionRepository.save(entity);
    }

    // 테스트
    public List<InteractionEntity> getAllInteractions() {
        return interactionRepository.findAll();
    }
    
}
