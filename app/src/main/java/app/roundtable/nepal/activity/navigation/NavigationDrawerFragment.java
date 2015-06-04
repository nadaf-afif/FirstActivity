package app.roundtable.nepal.activity.navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.activity.HomeActivity;
import app.roundtable.nepal.activity.databeans.RecyclerData;


public class NavigationDrawerFragment extends Fragment implements RoundTableMenuAdapter.ClickListener{

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerView;
    private RoundTableMenuAdapter mMenuAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_navigation_drawer,container);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycleListView);

        mMenuAdapter = new RoundTableMenuAdapter(getActivity(),getData());
        mRecyclerView.setAdapter(mMenuAdapter);

        mMenuAdapter.setClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }



    public List<RecyclerData> getData(){

        List<RecyclerData> data = new ArrayList<RecyclerData>();
        int[] icons = new int[]{R.drawable.ic_users, R.drawable.ic_events,R.drawable.ic_news,R.drawable.ic_favorites,R.drawable.ic_action_about, R.drawable.ic_action_about};
        String[] title = new String[]{"Search Members", "Events", "News", "Favorites", "RTM", "About RTN"};

        for (int i = 0;i<title.length ;i++){

            RecyclerData currentData = new RecyclerData();
            currentData.setIconId(icons[i]);
            currentData.setItemLabel(title[i]);
            data.add(currentData);
        }

        return data;
    }



    public void setDrawerLayout(DrawerLayout drawerLayout, Toolbar mToolBar) {

        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),mDrawerLayout,mToolBar, R.string.open,R.string.close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {

                mDrawerToggle.syncState();
            }
        });

    }

    @Override
    public void itemClick(View view, int position) {


           HomeActivity activity = (HomeActivity) getActivity();
           mDrawerLayout.closeDrawer(Gravity.LEFT);
           activity.displayTab(position);

    }
}
