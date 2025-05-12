package com.goormplay.uiservice.ui.controller;

import com.goormplay.uiservice.ui.dto.InteractionRequestDto;
import com.goormplay.uiservice.ui.entity.InteractionEntity;
import com.goormplay.uiservice.ui.service.InteractionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/ui")
public class InteractionController {
    
    private final InteractionService interactionService;


    @PostMapping("/like")
    public ResponseEntity<InteractionEntity> likeContent(@RequestBody InteractionRequestDto requestDto) {
        log.info("Interaction Controller :  좋아요 상태 변경 시작");

        InteractionEntity result = interactionService.likeContent(
            requestDto.getUserId(),
            requestDto.getContentId(),
            requestDto.isLiked()
        );
        return ResponseEntity.ok(result);
    }

    //Get으로 좋아요 정보 보내는 Feign controller 필요



    
}
