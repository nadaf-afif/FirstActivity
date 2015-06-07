package app.roundtable.nepal.activity.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.adapters.PagerAdapter;

/**
 * Created by afif on 4/6/15.
 */
public class EventsAndMeetingsFragment extends Fragment {


    private ViewPager mViewPager;
    private String mTitle[] = new String[]{"Events", "Meetings"};
    private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_events,container,false);

        mFragments.add(new EventsFragment());
        mFragments.add(new MeetingsFragment());

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpViewPager(view);

    }

    private void setUpViewPager(View view) {

        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);

        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager(),mFragments,mTitle);

        mViewPager.setAdapter(pagerAdapter);

        mViewPager.setOffscreenPageLimit(2);

    }
}
