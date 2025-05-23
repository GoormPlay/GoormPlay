package com.goormplay.contentservice.content.dto.response;

import com.goormplay.contentservice.content.dto.ReviewDTO;
import com.goormplay.contentservice.content.dto.VideoDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ContentDetailResponse {
    private VideoDTO content;
    private Boolean isLiked;
    private List<ReviewDTO> reviews;
    private Double averageRating;
}
