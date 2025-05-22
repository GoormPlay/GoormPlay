package com.goormplay.contentservice.controller;

import com.goormplay.contentservice.dto.ContentCardDTO;
import com.goormplay.contentservice.dto.ContentDetailDTO;
import com.goormplay.contentservice.dto.ContentIdsRequest;
import com.goormplay.contentservice.dto.VideoDTO;
import com.goormplay.contentservice.entity.Content;
import com.goormplay.contentservice.service.ContentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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
    public ResponseEntity<Page<VideoDTO>> getLatestContents(int page, int size) {
        return ResponseEntity.ok(contentService.getLatestContents(page, size));
    }
    @GetMapping("/{id}")
    public ResponseEntity<VideoDTO> getContentDetail(@PathVariable String id) {
        return ResponseEntity.ok(contentService.getContentDetailById(id));
    }




}
