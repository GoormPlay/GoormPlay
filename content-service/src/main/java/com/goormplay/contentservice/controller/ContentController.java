package com.goormplay.contentservice.controller;

import com.goormplay.contentservice.dto.ContentCardDTO;
import com.goormplay.contentservice.dto.ContentDetailDTO;
import com.goormplay.contentservice.entity.Content;
import com.goormplay.contentservice.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;

    @PostMapping("/api/public/contents/import-test")
    public ResponseEntity<String> saveTestLatestContents() {
        try {
            contentService.saveTestContents();
            return ResponseEntity.ok("success");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/api/public/contents/latest-test")
    public ResponseEntity<List<ContentCardDTO>> getLatestTestContentCards() {
        return ResponseEntity.ok(contentService.getTestLatestContentCards());
    }
    @GetMapping("/api/contents/latest")
    public ResponseEntity<Page<ContentCardDTO>> getLatestContents(int page, int size) {
        return ResponseEntity.ok(contentService.getLatestContents(page, size));
    }
    @GetMapping("/api/contents/{id}")
    public ResponseEntity<ContentDetailDTO> getContentDetail(@PathVariable String id) {
        return ResponseEntity.ok(contentService.getContentDetailById(id));
    }




}
