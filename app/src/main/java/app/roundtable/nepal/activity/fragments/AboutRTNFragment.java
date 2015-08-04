package app.roundtable.nepal.activity.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.activity.AboutUsActivity;
import app.roundtable.nepal.activity.activity.AimsAndObjectiveActivity;
import app.roundtable.nepal.activity.activity.ContactDetailsActivity;
import app.roundtable.nepal.activity.activity.ConveyersActivity;
import app.roundtable.nepal.activity.activity.MeetingProcedureActivity;
import app.roundtable.nepal.activity.activity.RTNSongsActivity;

/**
 * Created by afif on 10/6/15.
 */
public class AboutRTNFragment extends Fragment implements View.OnClickListener{

    public static String tag = AboutRTNFragment.class.getSimpleName();
    private TextView mAboutUsTextView, mAimsObjectiveTextView, mContactsTextView, mBoardMembersTextView, RTNSongTextView, mMeetingProcedureTextView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_about_us,container,false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAboutUsTextView = (TextView) view.findViewById(R.id.aboutRTVTextView);
        mAimsObjectiveTextView = (TextView) view.findViewById(R.id.aimsAndObjectiveTextView);
        mContactsTextView = (TextView) view.findViewById(R.id.contactsTextView);
        mBoardMembersTextView = (TextView)view.findViewById(R.id.boardMembersTextView);
        RTNSongTextView = (TextView) view.findViewById(R.id.songTextView);
        mMeetingProcedureTextView = (TextView) view.findViewById(R.id.meetingProcedureTextView);

        mAboutUsTextView.setOnClickListener(this);
        mAimsObjectiveTextView.setOnClickListener(this);
        mContactsTextView.setOnClickListener(this);
        mBoardMembersTextView.setOnClickListener(this);
        RTNSongTextView.setOnClickListener(this);
        mMeetingProcedureTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {


        Intent intent ;

        switch (view.getId()){


            case R.id.aboutRTVTextView :

                intent = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(intent);
                break;


            case R.id.aimsAndObjectiveTextView :

                intent = new Intent(getActivity(), AimsAndObjectiveActivity.class);
                startActivity(intent);
                break;

            case R.id.contactsTextView :

                intent = new Intent(getActivity(), ContactDetailsActivity.class);
                startActivity(intent);
                break;


            case R.id.boardMembersTextView :

                intent = new Intent(getActivity(), ConveyersActivity.class);
                startActivity(intent);
                break;


            case R.id.songTextView :

                intent = new Intent(getActivity(), RTNSongsActivity.class);
                startActivity(intent);
                break;

            case R.id.meetingProcedureTextView :
                intent = new Intent(getActivity() , MeetingProcedureActivity.class);
                startActivity(intent);
                break;
        }

    }
}
