package com.aiberry.tony.rowinaimusic.jsondata;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonUsage {
    @JsonProperty("credits_left")
    private int creditsLeft;
    private String period;
    @JsonProperty("monthly_limit")
    private int monthlyLimit;
    @JsonProperty("monthly_usage")
    private int monthlyUsage;

    // Getters and setters...
    public int getCreditsLeft() {
        return creditsLeft;
    }

    public void setCreditsLeft(int creditsLeft) {
        this.creditsLeft = creditsLeft;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getMonthlyLimit() {
        return monthlyLimit;
    }

    public void setMonthlyLimit(int monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }

    public int getMonthlyUsage() {
        return monthlyUsage;
    }

    public void setMonthlyUsage(int monthlyUsage) {
        this.monthlyUsage = monthlyUsage;
    }
}
