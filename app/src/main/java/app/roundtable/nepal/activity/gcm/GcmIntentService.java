package app.roundtable.nepal.activity.gcm;



import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;


import com.google.android.gms.gcm.GcmListenerService;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.activity.HomeActivity;
import app.roundtable.nepal.activity.util.Constants;

/**
 * Created by afif on 4/7/15.
 */
public class GcmIntentService  extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);

        String title = data.getString("msg");
        String description = data.getString("description");
        String type = data.getString("type");

        if(type != null) {

            if (type.equals("event")) {

                showNotification(title, description, HomeActivity.NAVIGATION_TAB_EVENTS, Constants.EVENT_NOTIFICATION);

            } else if (type.equals("meeting")) {

                showNotification(title, description, HomeActivity.NAVIGATION_TAB_EVENTS, Constants.MEETING_NOTIFICATION);

            } else if (type.equals("favorites")) {

                showNotification(title, description, HomeActivity.NAVIGATION_TAB_FAVORITES, Constants.FAVOURITES_NOTIFICATION);

            } else if (type.equals("news")) {

                showNotification(title, description, HomeActivity.NAVIGATION_TAB_NEWS, Constants.NEWS_NOTIFICATION);

            } else if (type.equals("table")) {

                showNotification(title, description, HomeActivity.NAVIGATION_TAB_TABLES, Constants.TABLE_NOTIFICATION);

            }
        }


    }

    private void showNotification(String title, String description, int tabIndex, int requestCode) {




        Intent notificationIntent = null;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationIntent = new Intent(this,HomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.selectedTab, tabIndex);
        bundle.putInt(Constants.REQUEST_CODE, requestCode);
        notificationIntent.putExtras(bundle);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = null;

        pendingIntent = PendingIntent.getActivity(this, requestCode, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentText(description);
        builder.setContentIntent(pendingIntent);
        builder.setContentTitle(title);
        builder.setSmallIcon(R.drawable.app_logo);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.app_logo));


        Notification notification = builder.build();
        notification.when = when;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(requestCode ,notification);



    }

}
