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

    @GetMapping("/content/{contentId}/liked/{userId}")
    public boolean isContentLikedByUser(
            @PathVariable String contentId, @PathVariable String userId) {
        return interactionService.isContentLikedByUser(contentId, userId);
    }

    @PostMapping("/like")
    public void likeContent(@RequestBody InteractionRequestDto requestDto, Authentication authentication) {
        log.info("Interaction Controller :  좋아요 상태 변경 시작");
        String userId = authentication.getName();
        interactionService.toggleLike(
            userId,
            requestDto.getContentId()
        );
    }
    // contentIds로 content 조회
   @GetMapping("/content/{userId}/liked")
   public List<String>  getLikedContentsId(@PathVariable String userId) {
        return interactionService.getLikedContentIds(userId);
   }



    
}
