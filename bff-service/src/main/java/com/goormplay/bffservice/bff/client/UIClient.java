package com.goormplay.bffservice.bff.client;

import com.goormplay.bffservice.Security.FeignHeaderConfig;
import com.goormplay.bffservice.bff.dto.Member.MemberProfileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ui-service" , configuration = FeignHeaderConfig.class)
public interface UIClient {
    @GetMapping("/api/ui/client/profile/{memberId}")
    MemberProfileDto getMemberProfile(@PathVariable("memberId") String memberId);

}
