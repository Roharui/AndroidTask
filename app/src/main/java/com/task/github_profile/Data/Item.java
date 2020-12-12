package com.task.github_profile.Data;

import org.json.JSONException;
import org.json.JSONObject;

public class Item {
    int image;
    String title;
    String description;


    public int getImage() {
        return this.image;
    }
    public String getTitle() {
        return this.title;
    }
    public String getDescription() {
        return this.description;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Item(){}

    public Item(int image, String title, String description) {
        this.image = image;
        this.title = title;
        this.description = description;
    }

    public Item(JSONObject json) throws JSONException {

        this.setTitle(json.getString("name"));
        this.setDescription(json.getString("description"));
    }
}