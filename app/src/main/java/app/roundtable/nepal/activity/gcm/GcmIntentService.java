package app.roundtable.nepal.activity.gcm;

import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by afif on 4/7/15.
 */
public class GcmIntentService  extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);


    }
}
