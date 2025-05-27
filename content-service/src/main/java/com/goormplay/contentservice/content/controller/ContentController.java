package com.goormplay.contentservice.content.controller;

import com.goormplay.contentservice.content.dto.ContentIdsRequest;
import com.goormplay.contentservice.content.dto.VideoDTO;
import com.goormplay.contentservice.content.dto.response.ContentDetailResponse;
import com.goormplay.contentservice.content.service.ContentService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/contents")
@RequiredArgsConstructor
@Slf4j
public class ContentController {
    private final ContentService contentService;

    @PostMapping("/import-test")
    public ResponseEntity<String> saveTestLatestContents() {
        try {
            contentService.saveTestContents();
            return ResponseEntity.ok("success");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/bulk-ids")
    public List<VideoDTO> getContentCardsByContentIds(@RequestBody ContentIdsRequest request) {

        log.info("Received request for contents with ids: {}", request.getContentIds());
        List<VideoDTO> results = contentService.getContentCardsByIds(request.getContentIds());
        log.info("Found {} contents", results.size());
        return results;
    }


    @GetMapping("/liked/{memberId}")
    public ResponseEntity<List<VideoDTO>> getLikedContentCards(@PathVariable String memberId) {
        if(memberId == null) {
            return ResponseEntity.ok(contentService.getTestLatestContentCards());
        }
        return ResponseEntity.ok(contentService.getTestLatestContentCards());
    }

    @GetMapping("/latest-test")
    public ResponseEntity<List<VideoDTO>> getLatestTestContentCards() {
        return ResponseEntity.ok(contentService.getTestLatestContentCards());
    }
    @GetMapping("/latest")
    public ResponseEntity<Map<String, Object>> getLatestContents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("releaseDate").descending());
        return ResponseEntity.ok(contentService.getLatestContentsWithMeta(pageable));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ContentDetailResponse> getContentDetail(@PathVariable String id,
                                                                  @Nullable Authentication authentication) {
        String userId = Optional.ofNullable(authentication)
                .map(Authentication::getName)
                .orElse(null);
        return ResponseEntity.ok(contentService.getContentDetailById(id, userId));
    }




}
