package com.goormplay.memberservice.member.client;

import com.goormplay.memberservice.Security.FeignHeaderConfig;
import com.goormplay.memberservice.member.dto.ContentIdsRequest;
import com.goormplay.memberservice.member.dto.Member.ContentCardDto;
import com.goormplay.memberservice.member.dto.Member.VideoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "content-service",  configuration = FeignHeaderConfig.class)
public interface ContentClient {
    @PostMapping("/contents/bulk-ids")
    List<VideoDto> getContentCardsByContentIds(@RequestBody ContentIdsRequest request);
}
