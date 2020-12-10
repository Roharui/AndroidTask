package com.task.github_profile;

public class Item {
    int image;
    String title;
    String description;

    int getImage() {
        return this.image;
    }
    String getTitle() {
        return this.title;
    }
    String getDescription() {
        return this.title;
    }

    Item(int image, String title, String description) {
        this.image = image;
        this.title = title;
        this.description = description;
    }
}