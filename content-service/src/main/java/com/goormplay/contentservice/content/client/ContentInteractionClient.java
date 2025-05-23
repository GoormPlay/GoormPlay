package com.goormplay.contentservice.content.client;

import com.goormplay.contentservice.Security.FeignHeaderConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ui-service", configuration = FeignHeaderConfig.class)
public interface ContentInteractionClient {
    @GetMapping("/ui/content/{contentId}/liked/{userId}")
    boolean isContentLikedByUser(@PathVariable String contentId, @PathVariable String userId);
}
