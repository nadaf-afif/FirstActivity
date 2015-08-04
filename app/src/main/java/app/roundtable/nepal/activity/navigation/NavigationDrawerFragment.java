package app.roundtable.nepal.activity.navigation;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.activity.HomeActivity;
import app.roundtable.nepal.activity.activity.MyProfileViewActivity;
import app.roundtable.nepal.activity.activity.ProfileViewActivity;
import app.roundtable.nepal.activity.databeans.RecyclerData;
import app.roundtable.nepal.activity.network.ApiUrls;
import app.roundtable.nepal.activity.util.ApplicationPreferences;
import de.hdodenhof.circleimageview.CircleImageView;


public class NavigationDrawerFragment extends Fragment implements RoundTableMenuAdapter.ClickListener{

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerView;
    private RoundTableMenuAdapter mMenuAdapter;
    private CircleImageView mDrawerImage;
    private RelativeLayout mProfileLayout;
    private ApplicationPreferences mSharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSharedPreferences = new ApplicationPreferences(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_navigation_drawer,container);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycleListView);
        mDrawerImage = (CircleImageView)view.findViewById(R.id.drawerImage);
        mProfileLayout = (RelativeLayout) view.findViewById(R.id.profileRL);

        mMenuAdapter = new RoundTableMenuAdapter(getActivity(),getData());
        mRecyclerView.setAdapter(mMenuAdapter);

        mMenuAdapter.setClickListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        String imagePath = ApiUrls.BASE_URL_PATH + mSharedPreferences.getUserProfileImage();

        Picasso.with(getActivity()).load(imagePath).skipMemoryCache().placeholder(R.drawable.rtn_logo).into(mDrawerImage);

        mDrawerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyProfileViewActivity.class);
                startActivity(intent);
            }
        });

        mProfileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        if(mSharedPreferences.getProfileUpdated()) {
            String imagePath = ApiUrls.BASE_URL_PATH + mSharedPreferences.getUserProfileImage();

            Picasso.with(getActivity()).load(imagePath).skipMemoryCache().placeholder(R.drawable.rtn_logo).into(mDrawerImage);
            mSharedPreferences.setProfileUpdated(false);
        }
    }

    public List<RecyclerData> getData(){

        List<RecyclerData> data = new ArrayList<RecyclerData>();
        int[] icons = new int[]{R.drawable.ic_action_group_light, R.drawable.ic_action_event,R.drawable.ic_action_news,R.drawable.ic_action_favorite ,R.drawable.ic_action_favorite, R.drawable.ic_action_camera , R.drawable.conveners_icon , R.drawable.ic_action_about};
        String[] title = new String[]{"RTN Clubs", "Events", "News", "Favorites","Privileges","Submit Photos", "Conveners" ,"About RTN"};

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
