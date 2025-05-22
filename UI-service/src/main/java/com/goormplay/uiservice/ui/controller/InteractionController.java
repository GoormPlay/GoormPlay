package com.goormplay.uiservice.ui.controller;

import com.goormplay.uiservice.ui.dto.InteractionRequestDto;
import com.goormplay.uiservice.ui.dto.LikedContentDto;
import com.goormplay.uiservice.ui.service.InteractionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/ui")
public class InteractionController {
    
    private final InteractionService interactionService;


    @PostMapping("/like")
    public void likeContent(@RequestBody InteractionRequestDto requestDto, Authentication  authentication) {
        log.info("Interaction Controller :  좋아요 상태 변경 시작");
        String userId = authentication.getName();
        interactionService.updateLike(
            userId,
            requestDto.getContentId(),
            requestDto.isLiked()
        );
    }

    //Get으로 좋아요 정보 보내는 Feign controller 필요
   @GetMapping("/content/{userId}/liked")
   public List<LikedContentDto>  getLikedContent(@PathVariable String userId) {
        return interactionService.getLikedContent(userId);
   }



    
}
