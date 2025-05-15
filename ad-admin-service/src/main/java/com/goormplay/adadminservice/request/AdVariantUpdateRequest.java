package com.goormplay.adadminservice.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdVariantUpdateRequest {
    private String type;
    private String thumbnail;
    private String embedUrl;
}
