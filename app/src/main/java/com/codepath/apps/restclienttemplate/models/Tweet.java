package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;


@Parcel
public class Tweet {
    // list out the attributes
    public String body;
    public long uid; // db id for the tweet
    public User user;
    public String createdAt;
    public String screenName;
    // desereilize the json

    public Tweet(){

    }
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException{
        Tweet tweet = new Tweet();
        // extract the values from json
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt =  getRelativeTimeAgo(jsonObject.getString("created_at"));
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));

        tweet.screenName = tweet.user.screenName;
        return tweet;



    }

    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }


}
