package app.roundtable.nepal.activity.interfaces;

import android.database.Cursor;

/**
 * Created by afif on 23/6/15.
 */
public interface DataLoader {

    public abstract void getFirstPageData();
    public abstract void setFirstPageData(Cursor cursor);
    public abstract void onNoInternet();
    public abstract void onNoData();

}
