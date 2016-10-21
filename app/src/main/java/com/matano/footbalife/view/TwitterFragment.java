package com.matano.footbalife.view;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.matano.footbalife.R;
import com.matano.footbalife.TweetsAdapter;
import com.matano.footbalife.model.Tweet;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link TwitterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TwitterFragment extends Fragment
{
    Twitter mTwitter;
    User mUser;
    Toast mToast;
    RecyclerView mRecyclerView;
    TweetsAdapter recycleViewAdapter;
    RecyclerView.LayoutManager recycleViewLayoutManager;
    List<Tweet> mTweetList = new ArrayList<>();
    private final String TAG = TwitterFragment.class.getSimpleName();
    List<twitter4j.Status> statuses;



    public TwitterFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment TwitterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TwitterFragment newInstance()
    {
        TwitterFragment fragment = new TwitterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_twitter, container, false);


            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.twitter_RecycleView);
            recycleViewLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(recycleViewLayoutManager);
            recycleViewAdapter = new TweetsAdapter(mTweetList, getContext());
            mRecyclerView.setAdapter(recycleViewAdapter);

            initTwitterSDK();

//            Toast.makeText(getContext(), "Failed to get statuses" , Toast.LENGTH_LONG).show();


        return rootView;
    }

    public void initTwitterSDK()
    {

        if (isNetAvailable()) //Check if net is available if true
        {
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(getString(R.string.consumer_key));
            builder.setOAuthConsumerSecret(getString(R.string.consumer_secret));
            builder.setOAuthAccessToken(getString(R.string.access_token));
            builder.setOAuthAccessTokenSecret(getString(R.string.access_token_secret));

            Configuration configuration = builder.build();

            TwitterConnection connection = new TwitterConnection();
            connection.execute(configuration);



        }
        else // Else show network error toast
        {
            mToast = Toast.makeText(getActivity().getApplication().getApplicationContext(),
                    "No network Connection", Toast.LENGTH_SHORT);
            mToast.show();
        }

    }

    public boolean isNetAvailable()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        //Check if network is connected if true return true else return false
        if (networkInfo != null && networkInfo.isConnected())
            return true;

        return false;
    }

    /**
     * Created by M.Matano on 15-Oct-16.
     */

    public  class TwitterConnection extends AsyncTask <Configuration , Void, Void>
    {
        private final String TAG = TwitterConnection.class.getSimpleName();

        @Override
        protected Void doInBackground(Configuration ...params)
        {
            TwitterFactory mTwitterFactory;
            try
            {
                mTwitterFactory = new TwitterFactory(params[0]);
                mTwitter = mTwitterFactory.getInstance();
                mUser = mTwitter.verifyCredentials();

            }
            catch (TwitterException te)
            {
                Log.e(TAG, "failed to verify credentials of" + mUser.getScreenName());
                return null;
            }

            statuses = getStatuses(mTwitter);

            for (twitter4j.Status status : statuses)
            {
                mTweetList.add(new Tweet(status));
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            recycleViewAdapter.updateTwitterUI(mTweetList);
        }

        private List<twitter4j.Status> getStatuses(Twitter twitter)
        {

            try
            {
                List<twitter4j.Status> statusList = twitter.getHomeTimeline();
                return statusList;
            }
            catch (TwitterException te)
            {
                return null;
            }
        }



    }

}
