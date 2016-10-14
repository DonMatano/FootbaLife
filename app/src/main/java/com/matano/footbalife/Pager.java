package com.matano.footbalife;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.matano.footbalife.view.LeagueTableFragment;
import com.matano.footbalife.view.TwitterFragment;

/**
 * Created by M.Matano on 14-Oct-16.
 */

public class Pager extends FragmentPagerAdapter
{
    //integer to count number of tabs
    int tabCount;

    //Constructor to the class


    public Pager(FragmentManager fm, int tabCount)
    {
        super(fm);
        //Initializing tab count
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position)
    {
        //Returning the current tabs
        switch (position)
        {
            case 0:
                TwitterFragment twitterFragment = TwitterFragment.newInstance();
                return twitterFragment;

            case 1:
                LeagueTableFragment leagueTableFragment = LeagueTableFragment.newInstance();
                return leagueTableFragment;

            default:
                return null;
        }
    }

    //method getCount to get the number of tabs
    @Override
    public int getCount()
    {
        return tabCount;
    }
}
