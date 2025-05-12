package com.goormplay.uiservice.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InteractionRequestDto {
    
    private String userId;
    private String contentId;
    private boolean liked;



}
