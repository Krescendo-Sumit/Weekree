package weekree.home.service;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class OrderHistory extends AppCompatActivity {
    WebView webmenu;
    String id;
    static String pid, uid;  int ccnt=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        uid=new MyDb(OrderHistory.this).getUserID().trim();;

        webmenu = findViewById(R.id.webmenu);
        webmenu.getSettings().setJavaScriptEnabled(true);
        webmenu.getSettings().setDomStorageEnabled(true);



        webmenu.setWebChromeClient(new WebChromeClient());
        webmenu.loadUrl(BaseUrl.HOSTNAME + "OrderHistory.php?uid=" + uid);
     //   Toast.makeText(OrderHistory.this, "Id id " + uid, Toast.LENGTH_LONG).show();
        webmenu.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                //Make the bar disappear after URL is loaded, and changes string to Loading...
                // setTitle("Loading...");
                setProgress(progress * 100); //Make the bar disappear after URL is loaded

                // Return the app name after finish loading

            }
        });

        webmenu.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {


                view.loadUrl(url);

                return true; // then it is not handled by default action
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                view.loadUrl("file:///android_asset/slideimage.html");
                // Toast.makeText(MainActivity.this, " Check Internet Connectivity . ", Toast.LENGTH_LONG).show();
            }
        });

        webmenu.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    WebView webView = (WebView) v;

                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            if (webView.canGoBack()) {
                                webView.goBack();
                                return true;
                            }
                            break;
                    }
                }

                return false;
            }
        });

    }
}