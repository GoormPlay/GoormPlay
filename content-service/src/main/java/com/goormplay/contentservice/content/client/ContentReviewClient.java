package com.goormplay.contentservice.content.client;

import com.goormplay.contentservice.Security.FeignHeaderConfig;
import com.goormplay.contentservice.content.dto.ReviewDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "review-service", configuration = FeignHeaderConfig.class)
public interface ContentReviewClient {
    @GetMapping("/review/{contentId}/list")
    List<ReviewDTO> getReviews(@PathVariable String contentId);
}
