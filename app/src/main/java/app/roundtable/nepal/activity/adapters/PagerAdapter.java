package app.roundtable.nepal.activity.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by afif on 4/6/15.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragments;
    private Fragment mFragment = null;
    private final String[] TAB_TITLES;

    public PagerAdapter(android.support.v4.app.FragmentManager fm,
                        ArrayList<Fragment> fragments, String[] tabTitles) {
        super(fm);
        mFragments = fragments;
        TAB_TITLES = tabTitles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }

    @Override
    public Fragment getItem(int position) {
        mFragment = mFragments.get(position);
        return mFragment;
    }
}
