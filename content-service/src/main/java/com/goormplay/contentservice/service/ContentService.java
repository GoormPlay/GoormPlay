package com.goormplay.contentservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormplay.contentservice.dto.ContentCardDTO;
import com.goormplay.contentservice.dto.ContentDTO;
import com.goormplay.contentservice.dto.ContentDetailDTO;
import com.goormplay.contentservice.entity.Content;
import com.goormplay.contentservice.repository.ContentRepository;
import com.goormplay.contentservice.tool.ContentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContentService {
    private final ContentRepository contentRepository;
    private final ObjectMapper objectMapper;
    private final ContentMapper contentMapper;



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

    // 테스트 데이터 변환
    public List<ContentCardDTO> getTestContentCard() throws IOException {

            File jsonFile = new File("scripts/test-contents.json");
        return objectMapper.readValue(
                jsonFile,
                new TypeReference<List<ContentCardDTO>>() {}
        );
    }

    // 테스트 데이터 변환 후 저장
    public void saveTestContents() throws IOException {

        List<ContentDTO> contentDTOS = importContentsFromJson();

        List<Content> contents = contentDTOS.stream()
                .map(contentMapper::toEntity)
                .collect(Collectors.toList());
        contentRepository.saveAll(contents);
    }

    // 테스트 데이터 cardDTO 조회
    public List<ContentCardDTO> getTestLatestContentCards()  {

        List<Content> contents = contentRepository.findAllLatestContents();
        return contents.stream()
                .map(this::contentToContentCardDto)
                .toList();
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
                .id(content.getId())
                .title(content.getTitle())
                .kind(content.getKind())
                .genre(content.getGenre())
                .thumbnail(content.getThumbnail())
                .videoId(content.getVideoId())
                .build();
    }

    // 컨텐츠 Detail DTO
    public ContentDetailDTO contentToContentDetailDto(Content content){
        return ContentDetailDTO.builder()
                .id(content.getId())
                .title(content.getTitle())
                .kind(content.getKind())
                .genre(content.getGenre())
                .year(content.getYear())
                .KMRB(content.getKMRB())
                .cast(content.getCast())
                .director(content.getDirector())
                .videoId(content.getVideoId())
                .releaseDate(content.getReleaseDate())
                .thumbnail(content.getThumbnail())
                .synopsis(content.getSynopsis())
                .provider(content.getProvider())
                .build();
    }

    // 임시 Content 더미데이터 만들기
    public List<ContentDTO> importContentsFromJson() throws IOException {
        try (InputStream is = getClass().getResourceAsStream("/scripts/test-contents.json")) {
            JsonNode root = objectMapper.readTree(is);
            return objectMapper.convertValue(root.get("test-contents"),
                    new TypeReference<List<ContentDTO>>() {});
        }
    }

}
