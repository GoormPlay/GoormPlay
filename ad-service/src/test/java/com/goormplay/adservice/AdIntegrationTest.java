package com.goormplay.adservice.ad.integration;

import com.goormplay.adservice.ad.entity.Advertisement;
import com.goormplay.adservice.ad.repository.AdRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class AdIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdRepository adRepository;

    @BeforeEach
    void setUp() {
        adRepository.deleteAll();

        Advertisement ad = new Advertisement();
        ad.setTitle("Integration Test Ad");
        ad.setType("A-type");
        ad.setThumbnailUrl("http://example.com/test.jpg");
        adRepository.save(ad);
    }

    @Test
    void shouldReturnRandomAd() throws Exception {
        mockMvc.perform(get("/api/ads/display"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.type").exists())
                .andExpect(jsonPath("$.thumbnailUrl").exists());
    }
}