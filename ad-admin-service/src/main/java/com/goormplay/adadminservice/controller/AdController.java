package com.goormplay.adadminservice.controller;

import com.goormplay.adadminservice.dto.AdMetaGroupDTO;
import com.goormplay.adadminservice.dto.AdvertiserAccountDTO;
import com.goormplay.adadminservice.dto.RechargeRequestDTO;
import com.goormplay.adadminservice.entity.AdvertiserAccount;
import com.goormplay.adadminservice.request.CreateAdMultipartRequest;
import com.goormplay.adadminservice.request.CreateAdRequest;
import com.goormplay.adadminservice.response.AdResponseDTO;
import com.goormplay.adadminservice.service.budget.AdBudgetService;
import com.goormplay.adadminservice.service.meta.AdMetaGroupService;
import jakarta.ws.rs.QueryParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ads")
@RequiredArgsConstructor
@Slf4j
public class AdController {
    private final AdMetaGroupService adMetaGroupService;
    private final AdBudgetService adBudgetService;
    @PostMapping(value = "/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdMetaGroupDTO> createAd(@ModelAttribute CreateAdMultipartRequest request){
        return ResponseEntity.ok(adMetaGroupService.createAd(request));
    }

    @GetMapping
    public ResponseEntity<List<AdResponseDTO>> getAds(
            @RequestParam String advertiserId) {
        return ResponseEntity.ok(adMetaGroupService.getAds(advertiserId));
    }
    @PostMapping("/recharge")
    public ResponseEntity<AdvertiserAccountDTO> rechargeBalance(@RequestBody RechargeRequestDTO request) {
        try {
            AdvertiserAccountDTO account = adBudgetService.rechargeBalance(request);
            return ResponseEntity.ok(account);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/balance")
    public ResponseEntity<AdvertiserAccountDTO> checkBalance(@RequestParam String advertiserId) {
        try {
            AdvertiserAccountDTO account = adBudgetService.checkBalance(advertiserId);
            return ResponseEntity.ok(account);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
