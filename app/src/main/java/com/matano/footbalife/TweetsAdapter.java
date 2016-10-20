package com.matano.footbalife;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.matano.footbalife.model.Tweet;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import twitter4j.MediaEntity;
import twitter4j.Status;

/**
 * Created by M.Matano on 17-Oct-16.
 */

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder>
{
    List<Tweet> mTweets;
    final String TAG = TweetsAdapter.class.getSimpleName();

    //When object created we give the list of tweets
    public TweetsAdapter(List<Tweet> tweets)
    {
        mTweets = tweets;
    }

    @Override //The size of the list Array
    public int getItemCount()
    {
        return mTweets.size();
    }

    //Called by the layout manager. Sets the view of the tweet layout not the textView.
    //Returns the viewHolder to the layout manager.
    @Override
    public TweetsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tweet_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TweetsAdapter.ViewHolder holder, int position)
    {
        holder.usernameView.setText(mTweets.get(position).getUserName());
        holder.userHandleView.setText(mTweets.get(position).getUserHandle());
        holder.tweetText.setText(mTweets.get(position).getTweetText());
        holder.dw = new DownloadUserProfileImage(holder,
                mTweets.get(position).getUserProfilePicUrl());
        holder.dw.execute();
        if (mTweets.get(position).isHasPhoto())
        {
            holder.tw = new DownloadTweetPic(holder,
                    mTweets.get(position).getTweetPicUrl());
            holder.tw.execute();
        }

        else
            holder.tweetPic.setVisibility(View.INVISIBLE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public LinearLayout tweet;
        public TextView usernameView;
        public TextView userHandleView;
        public TextView tweetText;
        public ImageView userProfilePic;
        public ImageView tweetPic;
        DownloadUserProfileImage dw;
        DownloadTweetPic tw;

        public ViewHolder(View itemView)
        {
            super(itemView);

            tweet = (LinearLayout) itemView.findViewById(R.id.tweetView);
            usernameView = (TextView) itemView.findViewById(R.id.username_textView);
            userHandleView = (TextView) itemView.findViewById(R.id.username_handle_textview);
            tweetText = (TextView) itemView.findViewById(R.id.text_tweetView);
            userProfilePic = (ImageView) itemView.findViewById(R.id.user_profileImage);
            tweetPic = (ImageView) itemView.findViewById(R.id.tweet_imageView);
        }
    }

    public void updateTwitterUI(List<Tweet> tweets)
    {
        mTweets = tweets;
        notifyDataSetChanged();
    }

    public static class DownloadUserProfileImage extends AsyncTask<Void, Void, Void>
    {
        private ViewHolder vh;
        private String url;
        private Bitmap bitmap;

        public DownloadUserProfileImage(ViewHolder vh , String url)
        {
            this.vh = vh;
            this.url = url;
        }

        @Override
        protected Void doInBackground(Void[] params)
        {
            bitmap = getImageBitmap(url);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            vh.userProfilePic.setImageBitmap(bitmap);
            Log.v("DownloadedUserPro", " updated User Profile");
        }
    }

    public static class DownloadTweetPic extends AsyncTask<Void, Void, Void>
    {
        private ViewHolder vh;
        private String url;
        private Bitmap bitmap;

        public DownloadTweetPic(ViewHolder vh , String url)
        {
            this.vh = vh;
            this.url = url;
        }

        @Override
        protected Void doInBackground(Void[] params)
        {
            bitmap = getImageBitmap(url);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            vh.tweetPic.setImageBitmap(bitmap);
            Log.v("DownloadedTweetPic", " updated tweetPic");
        }
    }

    private static Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e("Method getImageBitmap", "Error getting bitmap", e);
        }
        return bm;
    }

}
