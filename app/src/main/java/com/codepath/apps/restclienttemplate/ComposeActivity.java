package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    TwitterClient client;
    EditText TweetText;
    String TweetMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        client = TwitterApp.getRestClient(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        TweetText = (EditText) findViewById(R.id.message);

    }

    public void onTweet(View view) {
        String TweetMessage = TweetText.getText().toString();
        client.sendTweet(TweetMessage, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
               // super.onSuccess(statusCode, headers, response);
                try {
                    Tweet t = Tweet.fromJSON(response);

                    Intent data = new Intent();



                    // Pass relevant data back as a result
                    data.putExtra("tweet", Parcels.wrap(t));
                //    data.putExtra("code", 200); // ints work too
                    // Activity finished ok, return the data
                    setResult(RESULT_OK, data); // set result code and bundle data for response
                    finish(); // closes the activity, pass data to parent
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("ComposeActivity", errorResponse.toString());
                throwable.printStackTrace();
            }
        });
    }
}
