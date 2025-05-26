package com.goormplay.uiservice.ui.service;

import com.goormplay.uiservice.ui.dto.LikedContentDto;
import com.goormplay.uiservice.ui.entity.InteractionEntity;
import com.goormplay.uiservice.ui.repository.InteractionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InteractionService {

    // 데이터를 DB에 저장하거나 가져오기 위한 저장소
    private final InteractionRepository interactionRepository;

    public boolean isContentLikedByUser(String contentId, String userId) {
        return interactionRepository.existsByUserIdAndContentIdAndLikedTrue(userId, contentId);
    }

    @Transactional
    public boolean toggleLike(String userId, String contentId) {
        InteractionEntity interaction = interactionRepository
                .findByUserIdAndContentId(userId, contentId)
                .orElse(InteractionEntity.builder()
                        .userId(userId)
                        .contentId(contentId)
                        .liked(false)
                           .build());
        interaction.setLiked(!interaction.isLiked());
        interaction.setUpdatedAt(LocalDateTime.now());

        interactionRepository.save(interaction);
        return interaction.isLiked();
    }

    public List<String> getLikedContentIds(String userId) {
        List<InteractionEntity> likedInteractions = getLikedInteractions(userId);
        return likedInteractions.stream()
                .map(InteractionEntity::getContentId)
                .collect(Collectors.toList());
    }

    private List<InteractionEntity> getLikedInteractions(String userId) {
        return interactionRepository.findByUserIdAndLikedIsTrue(userId);
    }


    
}
