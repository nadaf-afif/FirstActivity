package app.roundtable.nepal.activity.util;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.util.Patterns;

/**
 * Created by afif on 9/6/15.
 */
public class Utils {



    public static boolean emailValid(String email) {

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
