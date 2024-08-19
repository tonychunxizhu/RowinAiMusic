package com.huawei.tony.rowinaimusic;
import com.fasterxml.jackson.annotation.JsonProperty;
public class Musics {
    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("lyric")
    private String lyric;

    @JsonProperty("audio_url")
    private String audioUrl;

    @JsonProperty("video_url")
    private String videoUrl;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("model_name")
    private String modelName;

    @JsonProperty("status")
    private String status;

    @JsonProperty("gpt_description_prompt")
    private String gptDescriptionPrompt;

    @JsonProperty("prompt")
    private String prompt;

    @JsonProperty("type")
    private String type;

    @JsonProperty("tags")
    private String tags;

    @JsonProperty("duration")
    private double duration;

    @JsonProperty("error_message")
    private String errorMessage;

    // Getters and Setters

    // Getter for title
    public String getTitle() {
        return title;
    }

    // Getter for lyric
    public String getLyric() {
        return lyric;
    }
    public String getId(){
        return id;
    }
    public String getImageUrl(){
        return imageUrl;
    }
    public String getAudioUrl(){
        return audioUrl;
    }
    public String getVideoUrl(){
        return videoUrl;
    }
}
