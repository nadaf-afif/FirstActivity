package app.roundtable.nepal.activity.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.activity.EventDetailsActivity;
import app.roundtable.nepal.activity.adapters.EventsListAdapter;
import app.roundtable.nepal.activity.asynktasks.GetEventsAsyncTasks;
import app.roundtable.nepal.activity.interfaces.DataLoader;
import app.roundtable.nepal.activity.network.NetworkManager;

/**
 * Created by afif on 4/6/15.
 */
public class EventsFragment extends Fragment implements DataLoader{


    private RecyclerView mRecyclerView;
    private GetEventsAsyncTasks mAsyncTask;
    private EventsListAdapter mAdapter;
    public static final String tag = EventsFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_events_list,container,false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.eventsRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getFirstPageData();

    }

    @Override
    public void getFirstPageData() {

        if(NetworkManager.isConnectedToInternet(getActivity())){

            executeAsyncTask();
        }

    }

    private void executeAsyncTask() {

        if(NetworkManager.isConnectedToInternet(getActivity())) {

            mAsyncTask = new GetEventsAsyncTasks(getActivity(), this);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                mAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            else
                mAsyncTask.execute(tag);

        }else
            onNoInternet();
    }

    @Override
    public void setFirstPageData(Cursor cursor) {

        if(isAdded()) {
            if (cursor.getCount() > 0) {
                mAdapter = new EventsListAdapter(getActivity(), cursor);
                mRecyclerView.setAdapter(mAdapter);
            } else {
                onNoData();
            }
        }
    }

    @Override
    public void onNoInternet() {

    }

    @Override
    public void onNoData() {

    }
}
