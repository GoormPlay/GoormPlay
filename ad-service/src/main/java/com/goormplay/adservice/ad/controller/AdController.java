package com.goormplay.adservice.ad.controller;

import com.goormplay.adservice.ad.dto.AdResponseDto;
import com.goormplay.adservice.ad.service.AdService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ads")
@RequiredArgsConstructor
public class AdController {
    private final AdService adService;

    @GetMapping("/display")
    public AdResponseDto getRandomAd() {
        return adService.getRandomAd();
    }
}