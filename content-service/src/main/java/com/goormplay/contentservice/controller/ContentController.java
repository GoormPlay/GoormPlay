package com.goormplay.contentservice.controller;

import com.goormplay.contentservice.repository.dto.ContentCardDTO;
import com.goormplay.contentservice.repository.dto.ContentDetailDTO;
import com.goormplay.contentservice.entity.Content;
import com.goormplay.contentservice.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/contents")
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;
    // JSON 파일에서 테스트용 컨텐츠 가져와서 DB에 저장
    @PostMapping("/import")
    public ResponseEntity<List<Content>> importTestContents() {
        try {
            List<Content> contents = contentService.createContents();
            return ResponseEntity.ok(contents);
        } catch (IOException e) {
            throw new RuntimeException("Failed to import contents", e);
        }
    }
    @PostMapping("/import-test")
    public ResponseEntity<String> saveTestLatestContents() {
        try {
            contentService.saveTestContents();
            return ResponseEntity.ok("success");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/latest-test")
    public ResponseEntity<List<ContentCardDTO>> getLatestTestContentCards() {
        return ResponseEntity.ok(contentService.getTestLatestContentCards());
    }
    @GetMapping("/latest")
    public ResponseEntity<Page<ContentCardDTO>> getLatestContents(int page, int size) {
        return ResponseEntity.ok(contentService.getLatestContents(page, size));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ContentDetailDTO> getContentDetail(@PathVariable String id) {
        return ResponseEntity.ok(contentService.getContentDetailById(id));
    }




}
