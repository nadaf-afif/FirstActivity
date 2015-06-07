package app.roundtable.nepal.activity.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.activity.ProfileViewActivity;
import app.roundtable.nepal.activity.view.CircularImageView;

/**
 * Created by afif on 3/6/15.
 */
public class SearchMembersFragment extends Fragment {


    public static final String TAG = SearchMembersFragment.class.getSimpleName();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_search_members,container,false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView userImage = (ImageView) view.findViewById(R.id.userImageView);
        ImageView userImage2 = (ImageView) view.findViewById(R.id.userImageView2);
        LinearLayout layout = (LinearLayout)view.findViewById(R.id.firstdata);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ProfileViewActivity.class);
                startActivity(intent);
            }
        });

        Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.drawable.events_1);
        Bitmap bmp2 = BitmapFactory.decodeResource(getResources(),R.drawable.events_2);

        userImage.setImageBitmap(getCircleBitmap(bmp));
        userImage2.setImageBitmap(getCircleBitmap(bmp2));

    }


    private Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }
}
