package app.roundtable.nepal.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.fragments.AboutRTNFragment;
import app.roundtable.nepal.activity.fragments.EventsAndMeetingsFragment;
import app.roundtable.nepal.activity.fragments.FavoritesFragemet;
import app.roundtable.nepal.activity.fragments.NewsListFragment;
import app.roundtable.nepal.activity.fragments.RTNClubsFragment;
import app.roundtable.nepal.activity.navigation.NavigationDrawerFragment;


/**
 * Created by afif on 12/5/15.
 */
public class HomeActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mToolBar = (Toolbar) findViewById(R.id.action_bar);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentNavigationDrawer);
        drawerFragment.setDrawerLayout(drawerLayout, mToolBar);

        displayTab(0);

    }



    public void displayTab(int position){




        switch (position)
        {
            case  0 :
                mFragment = new RTNClubsFragment();
                setTitle("RTN Clubs");
                break;

            case 1 :

                mFragment = new EventsAndMeetingsFragment();
                setTitle("Events");

                break;


            case 2 :
                mFragment = new NewsListFragment();
                setTitle("News");
                break;



            case 3 :
                mFragment = new FavoritesFragemet();
                setTitle("Favorites");

                break;


            case 4 :

                Intent intent = new Intent(this, SubmitPhotosActivity.class);
                startActivity(intent);
                break;


            case 5 :

                mFragment = new AboutRTNFragment();
                setTitle("About RTN");
                break;

        }


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, mFragment);
        ft.commit();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
        {
            mFragment.onActivityResult(requestCode,resultCode,data);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.my,menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){

            case R.id.action_search :

                Intent intent = new Intent(this, SearchMembersActivity.class);
                startActivity(intent);

        }
        return true;
    }



}
