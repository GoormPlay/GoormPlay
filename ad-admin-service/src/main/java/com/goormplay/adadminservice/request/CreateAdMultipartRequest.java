package com.goormplay.adadminservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdMultipartRequest {
    private String title;
    private Long budget;

    private String embedUrlA;
    private String embedUrlB;

    private MultipartFile thumbnailA;
    private MultipartFile thumbnailB;
}
