package com.goormplay.uiservice.ui.service;

import com.goormplay.uiservice.ui.entity.InteractionEntity;
import com.goormplay.uiservice.ui.entity.InteractionEntity.Interactions;
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


    // 실제 '좋아요' 처리 기능
    public InteractionEntity likeContent(String userId, String contentId, Boolean liked) {
        log.info("Interaction Service :  좋아요 상태 변경");

        Optional<InteractionEntity> optional = interactionRepository.findByUserIdAndContentId(userId, contentId);

        // 없으면 새로 InteractionEntity 객체 만들기
        InteractionEntity entity = optional.orElseGet(InteractionEntity::new);
        entity.setUserId(userId);
        entity.setContentId(contentId);

        // '좋아요' 상태 저장
        Interactions interactions = new Interactions();
        interactions.setLiked(liked);
        //지금은 t/f를 받아서 집어넣는데 on/off 매서드로 바꾸는 게 낫지 않은가?
        entity.setInteractions(interactions);
        log.info("좋아요 상태 변경");


        return interactionRepository.save(entity);
    }
    
    //좋아요한 항목 조회


    
}
