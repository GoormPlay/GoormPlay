package com.goormplay.adadminservice.service.meta;

import com.goormplay.adadminservice.dto.AdMetaGroupDTO;
import com.goormplay.adadminservice.entity.AdMetaGroup;
import com.goormplay.adadminservice.entity.AdStatus;
import com.goormplay.adadminservice.repository.AdMetaGroupRepository;
import com.goormplay.adadminservice.request.CreateAdMultipartRequest;
import com.goormplay.adadminservice.request.CreateAdRequest;
import com.goormplay.adadminservice.request.UpdateAdRequest;
import com.goormplay.adadminservice.response.AdResponseDTO;
import com.goormplay.adadminservice.service.budget.AdBudgetService;
import com.goormplay.adadminservice.tool.AdMetaGroupMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdMetaGroupService {
    private final AdMetaGroupRepository repository;
    private final AdMetaGroupMapper mapper;
    private final FileUploadService  fileUploadService;
    private final AdBudgetService  adBudgetService;
    // 광고 등록
    public AdMetaGroupDTO createAd(CreateAdMultipartRequest multipartRequest) {
        // 1. 파일 업로드
        String thumbnailUrlA = fileUploadService.uploadToImgbb(multipartRequest.getThumbnailA());
        String thumbnailUrlB = fileUploadService.uploadToImgbb(multipartRequest.getThumbnailB());


        // 2. 기존 CreateAdRequest로 변환
        CreateAdRequest request = CreateAdRequest.builder()
                .title(multipartRequest.getTitle())
                .budget(multipartRequest.getBudget())
                .embedUrlA(multipartRequest.getEmbedUrlA())
                .embedUrlB(multipartRequest.getEmbedUrlB())
                .thumbnailUrlA(thumbnailUrlA)
                .thumbnailUrlB(thumbnailUrlB)
                .build();
        List<AdMetaGroup.AdVariant> adVariants = createInitialVariants(request);

        // 3. AdMetaGroupDTO 변환
        AdMetaGroup adMetaGroup = AdMetaGroup.builder()
                .adSnId(generateAdSnId())
                .advertiserId("ADV_001")
                .title(request.getTitle())
                .status(AdStatus.ONGOING)
                .ads(adVariants)
                .createdAt(LocalDateTime.now())
                .build();

        return mapper.toDto(repository.save(adMetaGroup));
    }

    private List<AdMetaGroup.AdVariant> createInitialVariants(CreateAdRequest request) {
        return Arrays.asList(
                AdMetaGroup.AdVariant.builder()
                        .type("A")
                        .thumbnail(request.getThumbnailUrlA())
                        .embedUrl(request.getEmbedUrlA())
                        .impressions(0)
                        .clicks(0)
                        .build(),
                AdMetaGroup.AdVariant.builder()
                        .type("B")
                        .thumbnail(request.getThumbnailUrlB())
                        .embedUrl(request.getEmbedUrlB())
                        .impressions(0)
                        .clicks(0)
                        .build()
        );
    }

    // 단일 광고 조회
    public AdMetaGroupDTO findByAdSnId(String adSnId) {
        return repository.findByAdSnId(adSnId)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Ad not found"));
    }

    // 광고주의 모든 광고 조회 (A/B 타입 분리해서 반환)
    public List<AdResponseDTO> getAds(String advertiserId) {
        List<AdMetaGroup> adGroups = repository.findByAdvertiserId(advertiserId)
                .orElse(Collections.emptyList());

        return adGroups.stream()
                .flatMap(adGroup -> adGroup.getAds().stream()
                        .map(variant -> mapper.toAdResponse(adGroup, variant)))
                .collect(Collectors.toList());
    }

    // 광고 수정
    public AdMetaGroupDTO updateAd(String adSnId, UpdateAdRequest request) {
        AdMetaGroup adMetaGroup = repository.findByAdSnId(adSnId)
                .orElseThrow(() -> new EntityNotFoundException("Ad not found"));

        updateAdFields(adMetaGroup, request);
        return mapper.toDto(repository.save(adMetaGroup));
    }

    private void updateAdFields(AdMetaGroup adMetaGroup, UpdateAdRequest request) {
        Optional.ofNullable(request.getTitle())
                .ifPresent(adMetaGroup::setTitle);
        Optional.ofNullable(request.getStatus())
                .ifPresent(adMetaGroup::updateStatus);
    }

    // 노출/클릭 집계 업데이트
    public void updateMetrics(String adSnId, String type, String eventType) {
        AdMetaGroup adMetaGroup = repository.findByAdSnId(adSnId)
                .orElseThrow(() -> new EntityNotFoundException("Ad not found"));

        AdMetaGroup.AdVariant variant = findVariant(adMetaGroup, type);
        updateVariantMetrics(variant, eventType);

        repository.save(adMetaGroup);
    }

    private AdMetaGroup.AdVariant findVariant(AdMetaGroup adMetaGroup, String type) {
        return adMetaGroup.getAds().stream()
                .filter(ad -> ad.getType().equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid ad type"));
    }

    private void updateVariantMetrics(AdMetaGroup.AdVariant variant, String eventType) {
        if ("impression".equals(eventType)) {
            variant.incrementImpressions();
        } else if ("click".equals(eventType)) {
            variant.incrementClicks();
        }
    }

    private String generateAdSnId() {
        return "AD" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }



}

