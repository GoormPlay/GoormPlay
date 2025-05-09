package com.goormplay.uiservice.ui.dto;

public class InteractionRequestDto {
    
    private String userId;
    private String contentId;
    private boolean liked;

    public InteractionRequestDto() {}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

}
