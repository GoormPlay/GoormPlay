package com.goormplay.memberservice.member.client;

import com.goormplay.memberservice.Security.FeignHeaderConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ui-service", configuration = FeignHeaderConfig.class)
public interface UserInteractionClient {
    @GetMapping("/ui/content/{userId}/liked")
    public List<String>  getLikedContentsId(@PathVariable String userId);
}
