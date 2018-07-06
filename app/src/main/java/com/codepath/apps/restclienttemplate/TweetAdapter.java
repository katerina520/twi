package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.MyGlideAppModule;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {


    private List<Tweet> mTweets;

    Context context;
    // pass the tweet array in the adaptor

    public TweetAdapter(List<Tweet>tweets) {
        mTweets = tweets;

    }


    // Clean all elements of the recycler
    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        mTweets.addAll(list);
        notifyDataSetChanged();
    }





    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
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
        holder.created_at.setText(tweet.createdAt);
        holder.tvScreenName.setText("@" + tweet.screenName);


        int radius = 30; // corner radius, higher value = more rounded
        int margin = 10; // crop margin, set to 0 for corners with no crop

        Glide.with(context).load(tweet.user.profileImageUrl).into(holder.ivProfileImage);

            // leaving this lines here because MyGlideApp doesn't properly work but maybe it will
            // in the future I have my hopes
              //  .transform(new RoundedCornersTransformation(radius, margin))
              //  .into(holder.ivProfileImage);

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

        public TextView created_at;
        public TextView tvScreenName;

        public ImageButton retweet;


        public ViewHolder(View itemView) {

            super(itemView);
            // perform findViewById lookup
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);

            tvUsername = (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvScreenName = (TextView) itemView.findViewById(R.id.tvScreenName);
            created_at = (TextView) itemView.findViewById(R.id.created_at);
            retweet = (ImageButton) itemView.findViewById(R.id.retweet);


            tvScreenName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String userScreenName = tvScreenName.getText().toString();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://twitter.com/"+userScreenName));
                    view.getContext().startActivity(browserIntent);


                }
            });

            retweet.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {
                    Intent replyIntent = new Intent (view.getContext(), ComposeActivity.class );
                    String userScreenName = tvScreenName.getText().toString();
                    replyIntent.putExtra("userScreenName", userScreenName);
                    String tweetText = tvBody.getText().toString();
                   //  replyIntent.putExtra("tweetText", tweetText);
                    view.getContext().startActivity(replyIntent);



                }
            });


        }
    }




}
