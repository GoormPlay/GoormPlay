package com.goormplay.adservice.ad.service;

import com.goormplay.adservice.ad.dto.AdResponseDto;
import com.goormplay.adservice.ad.entity.Advertisement;
import com.goormplay.adservice.ad.repository.AdRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdServiceTest {

    @Mock
    private AdRepository adRepository;

    @InjectMocks
    private AdService adService;

    private Advertisement testAd;

    @BeforeEach
    void setUp() {
        testAd = new Advertisement();
        testAd.setId("test-id");
        testAd.setTitle("Test Advertisement");
        testAd.setType("A-type");
        testAd.setThumbnailUrl("http://example.com/test.jpg");
    }

    @Test
    void getRandomAd_ShouldReturnAdResponseDto() {
        // Given
        when(adRepository.findByType(anyString()))
                .thenReturn(List.of(testAd));

        // When
        AdResponseDto result = adService.getRandomAd();

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Test Advertisement");
        assertThat(result.getType()).isEqualTo("A-type");
    }
}