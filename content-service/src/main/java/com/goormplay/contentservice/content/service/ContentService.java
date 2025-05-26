package com.goormplay.contentservice.content.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormplay.contentservice.content.client.ContentInteractionClient;
import com.goormplay.contentservice.content.client.ContentReviewClient;
import com.goormplay.contentservice.content.dto.ContentCardDTO;
import com.goormplay.contentservice.content.dto.ContentDTO;
import com.goormplay.contentservice.content.dto.ReviewDTO;
import com.goormplay.contentservice.content.dto.VideoDTO;
import com.goormplay.contentservice.content.dto.response.ContentDetailResponse;
import com.goormplay.contentservice.content.entity.Content;
import com.goormplay.contentservice.content.repository.ContentRepository;
import com.goormplay.contentservice.content.tool.ContentMapper;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContentService {
    private final ContentRepository contentRepository;
    private final ObjectMapper objectMapper;
    private final ContentMapper contentMapper;
    private final ContentInteractionClient contentInteractionClient;
    private final ContentReviewClient contentReviewClient;

    // 상세 페이지 조회
    public ContentDetailResponse getContentDetailById(String contentId, @Nullable String userId) {
        VideoDTO content = contentRepository.findContentDetailById(contentId)
                .orElseThrow();
        List<ReviewDTO> reviews = contentReviewClient.getReviews(contentId);
        //임시
        Double averageRating = 2.0;

        return ContentDetailResponse.builder()
                .content(content)
                .isLiked(checkIsLiked(userId, contentId))
                .reviews(reviews)
                .averageRating(averageRating)
                .build();


    }

    private boolean checkIsLiked(String userId, String contentId) {
        return Optional.ofNullable(userId)
                .map(id -> contentInteractionClient.isContentLikedByUser(id, contentId))
                .orElse(false);
    }

    // 컨텐츠 ID 목록으로 카드 조회
    public List<VideoDTO> getContentCardsByIds(List<String> contentIds) {
        List<ObjectId> objectIds = contentIds.stream()
                .map(ObjectId::new)
                .collect(Collectors.toList());

        return contentRepository.findContentCardsByIds(objectIds);
    }

    // 사용자별 추천 컨텐츠 조회
    public List<VideoDTO> getRecommendedContentsForUser(List<String> contentIds) {
        if (contentIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<ObjectId> objectIds = contentIds.stream()
                .map(ObjectId::new)
                .collect(Collectors.toList());

        return contentRepository.findByIdsAsRecommended(objectIds);
    }

    // 일반적인 트렌딩/최신 컨텐츠 조회
    public List<VideoDTO> getTrendingContents() {
        return contentRepository.findAllAsTrending();
    }

    public List<VideoDTO> getLatestContents() {
        return contentRepository.findAllAsLatest();
    }

    // 최신 컨텐츠 카드 조회 (페이징)
    public Page<VideoDTO> getLatestContents(int page, int size) {
        return contentRepository.findLatestContents(
                PageRequest.of(page, size, Sort.by("releaseDate").descending())
        );
    }

    // 최신 컨텐츠 카드 조회 (리스트)
    public List<VideoDTO> getLatestContentCards(int limit) {
        return contentRepository.findLatestContentCards(limit);
    }

    // 테스트 데이터 관련 메서드
    @Transactional
    public void saveTestContents() throws IOException {
        List<ContentDTO> contents = importContentsFromJson();
        contentRepository.saveAll(contents.stream()
                .map(this::toEntity)
                .collect(Collectors.toList()));
    }

    private Content toEntity(ContentDTO dto) {
        return Content.builder()
                // ... DTO to Entity 매핑
                .build();
    }



    // 테스트 데이터 임포트
    private List<ContentDTO> importContentsFromJson() throws IOException {
        try (InputStream is = getClass().getResourceAsStream("/scripts/test-contents.json")) {
            JsonNode root = objectMapper.readTree(is);
            return objectMapper.convertValue(root.get("test-contents"),
                    new TypeReference<List<ContentDTO>>() {});
        }
    }

    // 테스트 데이터 변환
    public List<ContentCardDTO> getTestContentCard() throws IOException {

            File jsonFile = new File("scripts/test-contents.json");
        return objectMapper.readValue(
                jsonFile,
                new TypeReference<List<ContentCardDTO>>() {}
        );
    }

    // 테스트 데이터 cardDTO 조회
    public List<VideoDTO> getTestLatestContentCards()  {
      return contentRepository.findAllLatestContentCards();

    }

    // 사용자 좋아요 데이터 조회




    // 사용자 맞춤 컨텐츠 조회 (예정)
    public List<ContentCardDTO> getRecommendedContents(String userId) {
        // Kafka에서 받아오기
        return null;
    }


}
