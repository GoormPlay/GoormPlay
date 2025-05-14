package com.goormplay.adservice.ad.controller;

import com.goormplay.adservice.ad.dto.AdResponseDto;
import com.goormplay.adservice.ad.dto.ResponseDto;
import com.goormplay.adservice.ad.service.AdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ad")
@RequiredArgsConstructor
@Slf4j
public class AdController {
    private final AdService adService;

    @GetMapping("/display")
    public AdResponseDto getRandomAd() {
        log.info("Ad Controller Random Ad 선택 시작");
        return adService.getRandomAd();
    }
    @PostMapping("/test")
    public ResponseEntity<ResponseDto> test() {
        log.info("Ad Controller test");
        adService.test();
        return new ResponseEntity<>(new ResponseDto("test", null), HttpStatus.OK);
    }
}