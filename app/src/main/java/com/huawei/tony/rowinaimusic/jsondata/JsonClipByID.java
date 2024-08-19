package com.huawei.tony.rowinaimusic.jsondata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonClipByID {
    private String id;
    @JsonProperty("video_url")
    private String videoUrl;
    @JsonProperty("audio_url")
    private String audioUrl;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("image_large_url")
    private String imageLargeUrl;
    @JsonProperty("is_video_pending")
    private boolean isVideoPending;
    @JsonProperty("major_model_version")
    private String majorModelVersion;
    @JsonProperty("model_name")
    private String modelName;
    private Metadata metadata;
    private String reaction;
    @JsonProperty("display_name")
    private String displayName;
    private String handle;
    @JsonProperty("is_handle_updated")
    private boolean isHandleUpdated;
    @JsonProperty("avatar_image_url")
    private String avatarImageUrl;
    @JsonProperty("is_following_creator")
    private boolean isFollowingCreator;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("created_at")
    private String createdAt;
    private String status;
    private String title;
    @JsonProperty("play_count")
    private int playCount;
    @JsonProperty("upvote_count")
    private int upvoteCount;
    @JsonProperty("is_public")
    private boolean isPublic;

    // Getters and setters...

    public static class Metadata {
        private String tags;
        @JsonProperty("negative_tags")
        private String negativeTags;
        private String prompt;
        @JsonProperty("gpt_description_prompt")
        private String gptDescriptionPrompt;
        @JsonProperty("audio_prompt_id")
        private String audioPromptId;
        private String history;
        @JsonProperty("concat_history")
        private String concatHistory;
        @JsonProperty("stem_from_id")
        private String stemFromId;
        private String type;
        private double duration;
        @JsonProperty("refund_credits")
        private boolean refundCredits;
        private boolean stream;
        private String infill;
        @JsonProperty("has_vocal")
        private String hasVocal;
        @JsonProperty("is_audio_upload_tos_accepted")
        private String isAudioUploadTosAccepted;
        @JsonProperty("error_type")
        private String errorType;
        @JsonProperty("error_message")
        private String errorMessage;
        private String configurations;
        @JsonProperty("artist_clip_id")
        private String artistClipId;
        @JsonProperty("cover_clip_id")
        private String coverClipId;

        // Getters and setters...
        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getNegativeTags() {
            return negativeTags;
        }

        public void setNegativeTags(String negativeTags) {
            this.negativeTags = negativeTags;
        }

        public String getPrompt() {
            return prompt;
        }

        public void setPrompt(String prompt) {
            this.prompt = prompt;
        }

        public String getGptDescriptionPrompt() {
            return gptDescriptionPrompt;
        }

        public void setGptDescriptionPrompt(String gptDescriptionPrompt) {
            this.gptDescriptionPrompt = gptDescriptionPrompt;
        }

        public String getAudioPromptId() {
            return audioPromptId;
        }

        public void setAudioPromptId(String audioPromptId) {
            this.audioPromptId = audioPromptId;
        }

        public String getHistory() {
            return history;
        }

        public void setHistory(String history) {
            this.history = history;
        }

        public String getConcatHistory() {
            return concatHistory;
        }

        public void setConcatHistory(String concatHistory) {
            this.concatHistory = concatHistory;
        }

        public String getStemFromId() {
            return stemFromId;
        }

        public void setStemFromId(String stemFromId) {
            this.stemFromId = stemFromId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public double getDuration() {
            return duration;
        }

        public void setDuration(double duration) {
            this.duration = duration;
        }

        public boolean isRefundCredits() {
            return refundCredits;
        }

        public void setRefundCredits(boolean refundCredits) {
            this.refundCredits = refundCredits;
        }

        public boolean isStream() {
            return stream;
        }

        public void setStream(boolean stream) {
            this.stream = stream;
        }

        public String getInfill() {
            return infill;
        }

        public void setInfill(String infill) {
            this.infill = infill;
        }

        public String getHasVocal() {
            return hasVocal;
        }

        public void setHasVocal(String hasVocal) {
            this.hasVocal = hasVocal;
        }

        public String getIsAudioUploadTosAccepted() {
            return isAudioUploadTosAccepted;
        }

        public void setIsAudioUploadTosAccepted(String isAudioUploadTosAccepted) {
            this.isAudioUploadTosAccepted = isAudioUploadTosAccepted;
        }

        public String getErrorType() {
            return errorType;
        }

        public void setErrorType(String errorType) {
            this.errorType = errorType;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getConfigurations() {
            return configurations;
        }

        public void setConfigurations(String configurations) {
            this.configurations = configurations;
        }

        public String getArtistClipId() {
            return artistClipId;
        }

        public void setArtistClipId(String artistClipId) {
            this.artistClipId = artistClipId;
        }

        public String getCoverClipId() {
            return coverClipId;
        }

        public void setCoverClipId(String coverClipId) {
            this.coverClipId = coverClipId;
        }
    }

    // Getters and setters for JsonData class...

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageLargeUrl() {
        return imageLargeUrl;
    }

    public void setImageLargeUrl(String imageLargeUrl) {
        this.imageLargeUrl = imageLargeUrl;
    }

    public boolean isVideoPending() {
        return isVideoPending;
    }

    public void setVideoPending(boolean videoPending) {
        isVideoPending = videoPending;
    }

    public String getMajorModelVersion() {
        return majorModelVersion;
    }

    public void setMajorModelVersion(String majorModelVersion) {
        this.majorModelVersion = majorModelVersion;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public boolean isHandleUpdated() {
        return isHandleUpdated;
    }

    public void setHandleUpdated(boolean handleUpdated) {
        isHandleUpdated = handleUpdated;
    }

    public String getAvatarImageUrl() {
        return avatarImageUrl;
    }

    public void setAvatarImageUrl(String avatarImageUrl) {
        this.avatarImageUrl = avatarImageUrl;
    }

    public boolean isFollowingCreator() {
        return isFollowingCreator;
    }

    public void setFollowingCreator(boolean followingCreator) {
        isFollowingCreator = followingCreator;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreatedAt()   {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public int getUpvoteCount() {
        return upvoteCount;
    }

    public void setUpvoteCount(int upvoteCount) {
        this.upvoteCount = upvoteCount;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
}
