package com.goormplay.contentservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormplay.contentservice.repository.dto.ContentCardDTO;
import com.goormplay.contentservice.repository.dto.ContentDTO;
import com.goormplay.contentservice.repository.dto.ContentDetailDTO;
import com.goormplay.contentservice.entity.Content;
import com.goormplay.contentservice.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContentService {
    private final ContentRepository contentRepository;
    private final MongoTemplate mongoTemplate;
    private final ObjectMapper objectMapper;

    @Transactional
    public List<Content> createContents() throws IOException {
        List<ContentDTO> contentDTOs = importContentsFromJson();
        return contentDTOs.stream()
                .map(dto -> Content.builder()
                        .title(dto.getTitle())
                        .contentType(dto.getContentType())
                        .genre(dto.getGenre())
                        .year(dto.getYear())
                        .KMRB(dto.getKMRB())
                        .cast(dto.getCast())
                        .embedUrl(dto.getEmbedUrl())
                        .releaseDate(dto.getReleaseDate())
                        .thumbnailUrl(dto.getThumbnailUrl())
                        .build())
                .map(contentRepository::save)
                .toList();
    }

//    private String title;
//    private String contentType;
//    private String[] genre;
//    private int year;
//    private String KMRB;
//    private String[] cast;
//    private String embedUrl;
//    private String thumbnailUrl;
//    private LocalDate releaseDate;

    // 컨텐츠 상세 페이지 조회
    public ContentDetailDTO getContentDetailById(String id) {
        Content content = contentRepository.findById(id).orElseThrow();
        return contentToContentDetailDto(content);

    }

    // 최신 컨텐츠 조회
    public Page<ContentCardDTO> getLatestContents(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size,
                Sort.by("releaseDate").descending());
        return contentRepository.findLatestContents(pageRequest)
                .map(this::contentToContentCardDto);
    }

    // 사용자 맞춤 컨텐츠 조회 (예정)
    public List<ContentCardDTO> getRecommendedContents(String userId) {
        // Kafka에서 받아오기
        return null;
    }

    // 컨텐츠 Card DTO
    public ContentCardDTO contentToContentCardDto(Content content){
        return ContentCardDTO.builder()
                .contentId(content.getId())
                .title(content.getTitle())
                .contentType(content.getContentType())
                .genre(content.getGenre())
                .thumbnailUrl(content.getThumbnailUrl())
                .embedUrl(content.getEmbedUrl())
                .build();
    }

    // 컨텐츠 Detail DTO
    public ContentDetailDTO contentToContentDetailDto(Content content){
        return ContentDetailDTO.builder()
                .id(content.getId())
                .title(content.getTitle())
                .contentType(content.getContentType())
                .genre(content.getGenre())
                .year(content.getYear())
                .KMRB(content.getKMRB())
                .cast(content.getCast())
                .embedUrl(content.getEmbedUrl())
                .releaseDate(content.getReleaseDate())
                .thumbnailUrl(content.getThumbnailUrl())
                .build();
    }

    // 임시 Content 더미데이터 만들기
    public List<ContentDTO> importContentsFromJson() throws IOException {
        try (InputStream is = getClass().getResourceAsStream("/test-contents.json")) {
            JsonNode root = objectMapper.readTree(is);
            return objectMapper.convertValue(root.get("test-contents"),
                    new TypeReference<List<ContentDTO>>() {});
        }
    }

}
