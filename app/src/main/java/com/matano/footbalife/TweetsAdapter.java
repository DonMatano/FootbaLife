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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import twitter4j.Status;

/**
 * Created by M.Matano on 17-Oct-16.
 */

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder>
{
    List<Status> mStatuses;
    final String TAG = TweetsAdapter.class.getSimpleName();

    //When object created we give the list of statuses
    public TweetsAdapter(List<Status> statuses)
    {
        mStatuses = statuses;
    }

    @Override //The size of the list Array
    public int getItemCount()
    {
        return mStatuses.size();
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
        holder.usernameView.setText(mStatuses.get(position).getUser().getName());
        holder.userHandleView.setText("@" + mStatuses.get(position).getUser().getScreenName());
        holder.tweetText.setText(mStatuses.get(position).getText());
        holder.mDownloadUserProfilePic = new DownloadUserProfilePic(
                mStatuses.get(position).getUser().getProfileImageURL(),
                holder);
        holder.mDownloadUserProfilePic.execute();

    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public LinearLayout tweet;
        public TextView usernameView;
        public TextView userHandleView;
        public TextView tweetText;
        public ImageView userProfilePic;
        DownloadUserProfilePic mDownloadUserProfilePic;

        public ViewHolder(View itemView)
        {
            super(itemView);

            tweet = (LinearLayout) itemView.findViewById(R.id.tweetView);
            usernameView = (TextView) itemView.findViewById(R.id.username_textView);
            userHandleView = (TextView) itemView.findViewById(R.id.username_handle_textview);
            tweetText = (TextView) itemView.findViewById(R.id.text_tweetView);
            userProfilePic = (ImageView) itemView.findViewById(R.id.user_profileImage);
        }
    }

    public void updateTwitterUI(List<Status> statuses)
    {
        mStatuses = statuses;
        notifyDataSetChanged();
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


    /**
     * Created by M.Matano on 18-Oct-16.
     */

    public static class DownloadUserProfilePic extends AsyncTask<Void, Void, Bitmap>
    {
        private String url;
        private ViewHolder vh;

        public DownloadUserProfilePic(String url, ViewHolder vh)
        {
            this.url = url;
            this.vh = vh;
        }

        @Override
        protected Bitmap doInBackground(Void[] params)
        {
            return TweetsAdapter.getImageBitmap(url);
        }

        @Override
        protected void onPostExecute(Bitmap result)
        {
            vh.userProfilePic.setImageBitmap(result);
        }
    }
}
