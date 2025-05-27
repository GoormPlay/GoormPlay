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
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
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
                .orElseThrow(() -> new NotFoundException("Content not found"));

        // 리뷰 목록 조회
        List<ReviewDTO> reviews = contentReviewClient.getReviews(contentId);

        // 사용자별 좋아요 상태 확인 (비로그인 사용자는 false)
        boolean isLiked = userId != null && checkIsLiked(userId, contentId);
        Double averageRating = 2.0; //임시
        // 리뷰에 사용자 작성 여부 표시
        reviews = reviews.stream()
                .peek(review -> {
                    boolean isAuthor = userId != null && userId.equals(review.getUserId());
                    review.setAuthor(isAuthor);
                })
                .collect(Collectors.toList());

        return ContentDetailResponse.builder()
                .content(content)
                .isLiked(isLiked)
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


    // 최신 컨텐츠 카드 조회 (페이징)
    public Map<String, Object> getLatestContentsWithMeta(Pageable pageable) {
        Page<VideoDTO> page = contentRepository.findLatestContents(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("contents", page.getContent());
        response.put("page", page.getNumber());
        response.put("size", page.getSize());
        response.put("totalElements", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());
        response.put("isLast", page.isLast());

        return response;
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
