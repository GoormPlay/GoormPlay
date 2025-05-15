package com.goormplay.adadminservice.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAdRequest {
    private String title;
    private Long budget;
    private String thumbnailUrlA;
    private String embedUrlA;
    private String thumbnailUrlB;
    private String embedUrlB;
}
