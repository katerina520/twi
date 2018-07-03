package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    // list the attributres
    public String name;
    public long uid;
    public  String screenName;
    public String profileImageUrl;

    public static User fromJSON(JSONObject json) throws JSONException{
        User user = new User();

        // exctract and fill the values
        user.name = json.getString("name");
        user.uid = json.getLong("id");
        user.screenName = json.getString("screen_name");
        user.profileImageUrl = json.getString("profile_image_url");
        return user;

    }
}