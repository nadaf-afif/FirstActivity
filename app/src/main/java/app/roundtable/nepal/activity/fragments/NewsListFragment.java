package app.roundtable.nepal.activity.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.activity.AddNewNewsActivity;
import app.roundtable.nepal.activity.adapters.NewsListAdapter;

/**
 * Created by afif on 8/6/15.
 */
public class NewsListFragment extends Fragment implements View.OnClickListener{

    private RecyclerView mRecyclerView;
    private FloatingActionButton mAddNewsFloatingButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_news,container,false);

        return view;


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.newsRecyclerView);
        mAddNewsFloatingButton = (FloatingActionButton)view.findViewById(R.id.addNewsFloatingActionButton);
        mAddNewsFloatingButton.setOnClickListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        NewsListAdapter adapter = new NewsListAdapter(getActivity());
        mRecyclerView.setAdapter(adapter);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.addNewsFloatingActionButton :

                Intent intent = new Intent(getActivity(), AddNewNewsActivity.class);
                startActivity(intent);
                break;

        }

    }
}
