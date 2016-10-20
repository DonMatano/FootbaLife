package com.matano.footbalife.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.User;

/**
 * Created by M.Matano on 20-Oct-16.
 */

public class Tweet
{
    private Status mStatus;
    private final static String TAG = Tweet.class.getSimpleName();
    private User mUser;
    private String mTweetText;
    private String mUserProfilePicUrl;
    private String mTweetPicUrl;
    private String mUserName;
    private String mUserHandle;
    private int mRetweets;
    private boolean hasPhoto = false;
    private int mFavourites;
    private Date mTime;

    public Tweet(Status status)
    {
        mStatus = status;
        mUser = status.getUser();
        Log.v(TAG, "User is " + mUser);
        mTweetText = status.getText();
        mUserProfilePicUrl = mUser.getProfileImageURL();
        getTweetPhotoUrlAndSethasPhoto();
        mUserName = mUser.getName();
        Log.v(TAG, "Username is " + mUserName);
        mUserHandle = "@" + mUser.getScreenName();
        mRetweets = status.getRetweetCount();
        mFavourites = status.getFavoriteCount();
        mTime = status.getCreatedAt();
    }

    public boolean isHasPhoto()
    {
        return hasPhoto;
    }

    public Date getTime()
    {
        return mTime;
    }

    public int getFavourites()
    {
        return mFavourites;
    }

    public int getRetweets()
    {
        return mRetweets;
    }

    public Status getStatus()
    {
        return mStatus;
    }

    public String getTweetPicUrl()
    {
        return mTweetPicUrl;
    }

    public String getTweetText()
    {
        return mTweetText;
    }

    public User getUser()
    {
        return mUser;
    }

    public String getUserHandle()
    {
        return mUserHandle;
    }

    public String getUserName()
    {
        return mUserName;
    }

    public String getUserProfilePicUrl()
    {
        return mUserProfilePicUrl;
    }

    private void getTweetPhotoUrlAndSethasPhoto()
    {
        for (MediaEntity m: mStatus.getMediaEntities()
             )
        {
            if (m.getType().equals("photo"))
            {
                mTweetPicUrl = m.getMediaURL();
                hasPhoto = true;
            }
        }

    }


}
