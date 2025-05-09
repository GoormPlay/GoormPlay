package com.goormplay.uiservice.ui.controller;

import com.goormplay.uiservice.ui.dto.InteractionRequestDto;
import com.goormplay.uiservice.ui.entity.InteractionEntity;
import com.goormplay.uiservice.ui.service.InteractionService;

import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interact")
public class InteractionController {
    
    private final InteractionService interactionService;

    public InteractionController(InteractionService interactionService) {
        this.interactionService = interactionService;
    }

    @PostMapping("/like")
    public ResponseEntity<InteractionEntity> likeContent(@RequestBody InteractionRequestDto requestDto) {
        InteractionEntity result = interactionService.likeContent(
            requestDto.getUserId(),
            requestDto.getContentId(),
            requestDto.isLiked()
        );
        return ResponseEntity.ok(result);
    }

    // 테스트
    @GetMapping("/all")
    public ResponseEntity<List<InteractionEntity>> getAllInteractions() {
        List<InteractionEntity> interactions = interactionService.getAllInteractions();
        return ResponseEntity.ok(interactions);
    }


    
}
