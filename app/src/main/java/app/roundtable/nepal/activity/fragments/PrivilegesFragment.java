package app.roundtable.nepal.activity.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import app.roundtable.nepal.R;

/**
 * Created by afif on 4/7/15.
 */
public class PrivilegesFragment extends Fragment {

    public static String tag = PrivilegesFragment.class.getSimpleName();
    private WebView mWebView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_privileges,container,false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mWebView = (WebView) view.findViewById(R.id.webview);
        mWebView.loadUrl("http://www.uno.com.np/unocard");
        mWebView.getSettings().setJavaScriptEnabled(true);

    }

}
