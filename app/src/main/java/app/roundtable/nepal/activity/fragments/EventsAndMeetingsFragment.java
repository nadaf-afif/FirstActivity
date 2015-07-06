package app.roundtable.nepal.activity.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.activity.AddNewEventActivity;
import app.roundtable.nepal.activity.activity.AddNewMeetingActivity;
import app.roundtable.nepal.activity.adapters.PagerAdapter;

/**
 * Created by afif on 4/6/15.
 */
public class EventsAndMeetingsFragment extends Fragment {


    private ViewPager mViewPager;
    private FloatingActionButton mAddEventFloatingButton;
    private String mTitle[] = new String[]{"Events", "Meetings"};
    private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();
    public static final String tag = EventsAndMeetingsFragment.class.getSimpleName();


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
        mAddEventFloatingButton = (FloatingActionButton) view.findViewById(R.id.addEventFloatingActionButton);

        mAddEventFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShowDialogForEventAndMeeting();

            }
        });

        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager(),mFragments,mTitle);

        mViewPager.setAdapter(pagerAdapter);

        mViewPager.setOffscreenPageLimit(2);

    }

    private void ShowDialogForEventAndMeeting() {

        AlertDialog.Builder alertDBuilder = new AlertDialog.Builder(getActivity());
        alertDBuilder.setTitle("Choose options");
        alertDBuilder.setPositiveButton("Event", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(getActivity(), AddNewEventActivity.class);
                startActivity(intent);
            }
        });

        alertDBuilder.setNegativeButton("Meeting", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(getActivity(), AddNewMeetingActivity.class);
                startActivity(intent);
            }
        });

        alertDBuilder.show();

    }
}
