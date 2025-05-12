package com.goormplay.adservice.ad.repository;

import com.goormplay.adservice.ad.entity.Advertisement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
public class AdRepositoryTest {

    @Autowired
    private AdRepository adRepository;

    @Test
    void findByType_ShouldReturnAdsOfSpecificType() {
        // Given
        Advertisement ad1 = new Advertisement();
        ad1.setTitle("Test Ad 1");
        ad1.setType("A-type");
        ad1.setThumbnailUrl("http://example.com/1.jpg");

        Advertisement ad2 = new Advertisement();
        ad2.setTitle("Test Ad 2");
        ad2.setType("B-type");
        ad2.setThumbnailUrl("http://example.com/2.jpg");

        adRepository.saveAll(List.of(ad1, ad2));

        // When
        List<Advertisement> aTypeAds = adRepository.findByType("A-type");

        // Then
        assertThat(aTypeAds).hasSize(1);
        assertThat(aTypeAds.get(0).getTitle()).isEqualTo("Test Ad 1");
    }
}