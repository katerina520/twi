package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    TwitterClient client;
    EditText TweetText;
    String TweetMessage;
    Tweet tmp;

    ImageView ivProfileImage;
    TextView myName;


    TextView charCount;

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            charCount.setText("Characters left: "  + String.valueOf(140 - s.length()));
        }

        public void afterTextChanged(Editable s) {
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        client = TwitterApp.getRestClient(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        TweetText = (EditText) findViewById(R.id.message);
        charCount = (TextView)  findViewById(R.id.charCount);
        TweetText.addTextChangedListener(mTextEditorWatcher);
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);

        myName = (TextView)  findViewById(R.id.myName);

        client.getCurrentUser(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    User user = User.fromJSON(response);
                    myName.setText("@" + user.screenName);
                    Glide.with(getApplicationContext())
                            .load(user.profileImageUrl)
                            .into(ivProfileImage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


      //  Glide.with(this)
      //          .load(tmp.user.profileImageUrl)
      //          .into(ivProfileImage);



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
