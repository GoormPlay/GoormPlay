package com.goormplay.adadminservice.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UpdateAdRequest {
    private String title;
    private Integer status;
    private List<AdVariantUpdateRequest> adsUpdate;
}
