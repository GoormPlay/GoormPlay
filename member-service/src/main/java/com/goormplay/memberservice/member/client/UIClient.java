package com.goormplay.memberservice.member.client;

import com.goormplay.memberservice.Security.FeignHeaderConfig;
import com.goormplay.memberservice.member.dto.Member.LikedContentIdDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ui-service", configuration = FeignHeaderConfig.class)
public interface UIClient {
    @GetMapping("/ui/content/{userId}/liked")
    List<LikedContentIdDto> getLikedContent(@PathVariable String userId);
}
