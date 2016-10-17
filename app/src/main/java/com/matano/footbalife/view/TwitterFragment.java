package com.matano.footbalife.view;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.matano.footbalife.R;

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
    private final String TAG = TwitterFragment.class.getSimpleName();



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
        initTwitterSDK();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_twitter, container, false);
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

    public  class TwitterConnection extends AsyncTask <Configuration , Void, User>
    {
        private final String TAG = TwitterConnection.class.getSimpleName();

        @Override
        protected User doInBackground(Configuration ...params)
        {
            TwitterFactory mTwitterFactory;
            try
            {
                mTwitterFactory = new TwitterFactory(params[0]);
                mTwitter = mTwitterFactory.getInstance();
                mUser = mTwitter.verifyCredentials();
                Log.v(TAG, "Successfully verified credentials of " + mUser.getScreenName());

            }
            catch (TwitterException te)
            {
                Log.e(TAG, "failed to verify credentials of" + mUser.getScreenName());
            }

            return mUser;
        }

        @Override
        protected void onPostExecute(User user)
        {
        }
    }
}
