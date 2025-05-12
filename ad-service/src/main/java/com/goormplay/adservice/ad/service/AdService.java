package com.goormplay.adservice.ad.service;

import com.goormplay.adservice.ad.dto.AdResponseDto;
import com.goormplay.adservice.ad.entity.Advertisement;
import com.goormplay.adservice.ad.repository.AdRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdService {
    private final AdRepository adRepository;
    private final Random random = new Random();

    public AdResponseDto getRandomAd() {
        log.info("Ad Service Random Ad 선택");
        // A-type 또는 B-type 랜덤 선택
        String selectedType = random.nextBoolean() ? "A-type" : "B-type";

        // 선택된 타입의 광고 도큐먼트 호출
        List<Advertisement> ads = adRepository.findByType(selectedType);

        if (ads.isEmpty()) {
            throw new RuntimeException("No advertisements found for type: " + selectedType);
        }

        Advertisement selectedAd = ads.get(random.nextInt(ads.size()));

        // Entity를 DTO로 변환
        return AdResponseDto.builder()
                .id(selectedAd.getId())
                .title(selectedAd.getTitle())
                .adSnId(selectedAd.getAdSnId())
                .contentId(selectedAd.getContentId())
                .type(selectedAd.getType())
                .thumbnailUrl(selectedAd.getThumbnailUrl())
                .embedUrl(selectedAd.getEmbedUrl())
                .build();
    }

    public void test() {
        adRepository.save(Advertisement.builder().
                id("11id11").title("제목1").adSnId("adSnid11").contentId("ContentID").type("A-type").thumbnailUrl("www").embedUrl("emb").build());
     adRepository.save(Advertisement.builder().
                id("22id22").title("제목2").adSnId("adSnid22").contentId("ContentID").type("A-type").thumbnailUrl("www").embedUrl("emb").build());
    }
}