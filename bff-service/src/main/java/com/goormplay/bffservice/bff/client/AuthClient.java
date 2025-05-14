package com.goormplay.bffservice.bff.client;

import com.goormplay.bffservice.Security.FeignHeaderConfig;
import com.goormplay.bffservice.bff.dto.SignUpRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-service" , configuration = FeignHeaderConfig.class)
public interface AuthClient {
    @PostMapping("/api/auth/client/{memberId}")
    void signUpAuth(@RequestBody SignUpRequestDto dto, @PathVariable("memberId") String memberId);
}
