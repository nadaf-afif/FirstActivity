package app.roundtable.nepal.activity.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import app.roundtable.nepal.activity.activity.ProfileViewActivity;

/**
 * Created by afif on 7/6/15.
 */
public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {


    private int mToolbarOffset = 30;
    private int mToolbarHeight;

    public HidingScrollListener(Context context) {
        mToolbarHeight = ProfileViewActivity.getToolBarHeight();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        clipToolbarOffset();
        onMoved(mToolbarOffset);

        if((mToolbarOffset <mToolbarHeight && dy>0) || (mToolbarOffset >30 && dy<0)) {
            mToolbarOffset += dy;
        }
    }

    private void clipToolbarOffset() {
        if(mToolbarOffset > mToolbarHeight) {
            mToolbarOffset = mToolbarHeight;
        } else if(mToolbarOffset < 0) {
            mToolbarOffset = 0;
        }
    }

    public abstract void onMoved(int distance);


}
