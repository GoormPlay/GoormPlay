package com.goormplay.uiservice.ui.service;

import com.goormplay.uiservice.ui.dto.LikedContentDto;
import com.goormplay.uiservice.ui.entity.InteractionEntity;
import com.goormplay.uiservice.ui.repository.InteractionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InteractionService {

    // 데이터를 DB에 저장하거나 가져오기 위한 저장소
    private final InteractionRepository interactionRepository;


    public void updateLike(String userId, String contentId, boolean liked) {
        Optional<InteractionEntity> optional = interactionRepository.findByUserIdAndContentId(userId, contentId);

        InteractionEntity entity = optional.orElseGet(() -> {
            InteractionEntity newInteraction = new InteractionEntity();
            newInteraction.setUserId(userId);
            newInteraction.setContentId(contentId);
            return newInteraction;
        });

        entity.setLiked(liked);
        entity.setUpdatedAt(LocalDateTime.now());

        interactionRepository.save(entity);
    }

    public List<LikedContentDto> getLikedContent(String userId) {
        List<InteractionEntity> likedInteractions = getLikedInteractions(userId);
        return likedInteractions.stream()
                .map(interaction -> new LikedContentDto(interaction.getContentId()))
                .toList();
    }

    private List<InteractionEntity> getLikedInteractions(String userId) {
        return interactionRepository.findByUserIdAndLikedIsTrue(userId);
    }


    
}
