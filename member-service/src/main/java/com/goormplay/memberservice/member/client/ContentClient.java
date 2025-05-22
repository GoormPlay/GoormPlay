package com.goormplay.memberservice.member.client;

import com.goormplay.memberservice.Security.FeignHeaderConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "content-service",  configuration = FeignHeaderConfig.class)
public interface ContentClient {
}
