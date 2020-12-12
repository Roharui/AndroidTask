package com.task.github_profile.Data;

import org.json.JSONObject;

public class User {
    private String login;
    private String img;
    private int follwer;
    private int follwing;

    public int getFollwer() {
        return follwer;
    }

    public int getFollwing() {
        return follwing;
    }

    public String getImg() {
        return img;
    }

    public String getLogin() {
        return login;
    }

    public void setFollwer(int follwer) {
        this.follwer = follwer;
    }

    public void setFollwing(int follwing) {
        this.follwing = follwing;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public static User toUser(String json){

        User u = new User();

        try{
            JSONObject obj = new JSONObject(json);

            u.setLogin(obj.getString("login"));
            u.setImg(obj.getString("avatar_url"));
            u.setFollwer(obj.getInt("followers"));
            u.setFollwing(obj.getInt("following"));
        }catch (Exception e){ }

        return u;
    }
}
