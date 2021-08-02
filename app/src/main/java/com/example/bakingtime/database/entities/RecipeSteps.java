package com.example.bakingtime.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class RecipeSteps {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int stepsId;
    private String recipeName;
    private String description;
    private String videoUrl;
    private String imageURL;


    @Ignore
    public RecipeSteps(String recipeName, String description, String videoUrl, String imageURL) {
        this.description = description;
        this.videoUrl = videoUrl;
        this.imageURL = imageURL;
        this.recipeName = recipeName;
    }
    public RecipeSteps(int stepsId, String recipeName, String description, String videoUrl, String imageURL) {
        this.stepsId = stepsId;
        this.description = description;
        this.videoUrl = videoUrl;
        this.imageURL = imageURL;
        this.recipeName = recipeName;

    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getStepsId() {
        return stepsId;
    }

    public void setStepsId(int stepsId) {
        this.stepsId = stepsId;
    }
}
