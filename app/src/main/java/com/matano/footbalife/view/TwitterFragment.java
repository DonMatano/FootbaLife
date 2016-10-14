package com.matano.footbalife.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matano.footbalife.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link TwitterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TwitterFragment extends Fragment
{

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
        return inflater.inflate(R.layout.fragment_twitter, container, false);
    }

}
