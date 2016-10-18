package com.matano.footbalife;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import twitter4j.Status;

/**
 * Created by M.Matano on 17-Oct-16.
 */

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder>
{
    List<Status> mStatuses;

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
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public LinearLayout tweet;
        public TextView usernameView;
        public TextView userHandleView;
        public TextView tweetText;

        public ViewHolder(View itemView)
        {
            super(itemView);

            tweet = (LinearLayout) itemView.findViewById(R.id.tweetView);
            usernameView = (TextView) itemView.findViewById(R.id.username_textView);
            userHandleView = (TextView) itemView.findViewById(R.id.username_handle_textview);
            tweetText = (TextView) itemView.findViewById(R.id.text_tweetView);
        }
    }

    public void updateTwitterUI(List<Status> statuses)
    {
        mStatuses = statuses;
        notifyDataSetChanged();
    }
}
