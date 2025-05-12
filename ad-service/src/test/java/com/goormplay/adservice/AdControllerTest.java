package com.goormplay.adservice;

import com.goormplay.adservice.ad.controller.AdController;
import com.goormplay.adservice.ad.dto.AdResponseDto;
import com.goormplay.adservice.ad.service.AdService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdController.class)
public class AdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdService adService;

    @Test
    void getRandomAd_ShouldReturnAdResponse() throws Exception {
        // Given
        AdResponseDto mockResponse = AdResponseDto.builder()
                .id("test-id")
                .title("Test Ad")
                .type("A-type")
                .thumbnailUrl("http://example.com/test.jpg")
                .build();

        when(adService.getRandomAd()).thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(get("/api/ads/display"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("test-id"))
                .andExpect(jsonPath("$.title").value("Test Ad"))
                .andExpect(jsonPath("$.type").value("A-type"));
    }
}