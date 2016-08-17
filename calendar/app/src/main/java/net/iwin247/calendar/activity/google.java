package net.iwin247.calendar.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.iwin247.calendar.R;

public class google extends AppCompatActivity {
    private WebView mWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);

        setLayout();

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("https://accounts.google.com/o/oauth2/auth?access_type=offline&scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fcalendar&response_type=code&client_id=392747140214-1j6f0uip9ek2p8js6ge184nepn780q7h.apps.googleusercontent.com&redirect_uri=http%3A%2F%2Fiwin247.net%2Fgoogle%2Foauth2callback");
        mWebView.setWebViewClient(new WebViewClientClass());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private void setLayout(){
        mWebView = (WebView) findViewById(R.id.webView);
    }

}
