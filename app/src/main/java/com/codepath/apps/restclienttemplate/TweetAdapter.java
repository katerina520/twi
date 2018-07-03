package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {


    private List<Tweet> mTweets;
    // pass the tweet array in the adaptor

    public TweetAdapter(List<Tweet>tweets) {
        mTweets = tweets;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;

    }

    // for eacj raw inflate the layout and cache references into viewholder
    // bind the values based on the position of the element

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get the dsta according to postoon

        Tweet tweet = mTweets.get(position);


        // populate the views according to data
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);

    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }


    // create viewholder class

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivProfileImage;
        public TextView tvUsername;

        public TextView tvBody;

        public ViewHolder(View itemView) {
            super(itemView);
            // perform findViewById lookup
            ivProfileImage = (ImageView)  itemView.findViewById(R.id.ivProfileImage);

            tvUsername = (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);


        }
    }



}
