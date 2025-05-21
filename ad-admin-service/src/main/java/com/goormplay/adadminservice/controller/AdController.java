package com.goormplay.adadminservice.controller;

import com.goormplay.adadminservice.dto.AdMetaGroupDTO;
import com.goormplay.adadminservice.dto.AdvertiserAccountDTO;
import com.goormplay.adadminservice.dto.RechargeRequestDTO;
import com.goormplay.adadminservice.entity.AdvertiserAccount;
import com.goormplay.adadminservice.logger.CustomLogger;
import com.goormplay.adadminservice.request.CreateAdMultipartRequest;
import com.goormplay.adadminservice.request.CreateAdRequest;
import com.goormplay.adadminservice.response.AdResponseDTO;
import com.goormplay.adadminservice.service.budget.AdBudgetService;
import com.goormplay.adadminservice.service.meta.AdMetaGroupService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.QueryParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
@Slf4j
public class AdController {
    private final AdMetaGroupService adMetaGroupService;
    private final AdBudgetService adBudgetService;
    @PostMapping(value = "/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdMetaGroupDTO> createAd(@ModelAttribute CreateAdMultipartRequest request, HttpServletRequest servletRequest){
        CustomLogger.logAdAdminApiRequest(
                "CREATE_AD",
                "POST",
                request.getTitle(),
                servletRequest,
                request
        );
        return ResponseEntity.ok(adMetaGroupService.createAd(request));
    }

    @GetMapping("/list")
    public ResponseEntity<List<AdResponseDTO>> getAds(
            @RequestParam String advertiserId, HttpServletRequest servletRequest) {
        CustomLogger.logAdAdminApiRequest(
                "GET_ADS",
                "GET",
                advertiserId,
                servletRequest,
                null
        );
        return ResponseEntity.ok(adMetaGroupService.getAds(advertiserId));
    }
    @PostMapping("/recharge")
    public ResponseEntity<AdvertiserAccountDTO> rechargeBalance(@RequestBody RechargeRequestDTO request, HttpServletRequest servletRequest) {
        try {
            CustomLogger.logAdAdminApiRequest(
                    "RECHARGE",
                    "POST",
                    request.getAdvertiserId(),
                    servletRequest,
                    request
            );
            AdvertiserAccountDTO account = adBudgetService.rechargeBalance(request);
            return ResponseEntity.ok(account);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/balance")
    public ResponseEntity<AdvertiserAccountDTO> checkBalance(@RequestParam String advertiserId, HttpServletRequest servletRequest) {
        try {
            CustomLogger.logAdAdminApiRequest(
                    "CHECK_BALANCE",
                    "GET",
                    advertiserId,
                    servletRequest,
                    null
            );
            AdvertiserAccountDTO account = adBudgetService.checkBalance(advertiserId);
            return ResponseEntity.ok(account);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
