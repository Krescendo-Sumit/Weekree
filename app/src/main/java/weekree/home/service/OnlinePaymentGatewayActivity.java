package weekree.home.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class OnlinePaymentGatewayActivity extends AppCompatActivity {
    WebView webmenu;
    static String pid, uid;
    String mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_payment_gateway);
        uid = new MyDb(OnlinePaymentGatewayActivity.this).getUserID().trim();
        pid = getIntent().getExtras().getString("oid");
        mobile=getIntent().getExtras().getString("mobile");;
        webmenu = findViewById(R.id.webmenu);

        webmenu.getSettings().setJavaScriptEnabled(true);
        webmenu.setWebChromeClient(new WebChromeClient());
        webmenu.loadUrl(BaseUrl.HOSTNAME + "OnlinePayment.php?oid=" + pid+"&uid="+uid+"&mobile="+mobile);
        webmenu.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                //Make the bar disappear after URL is loaded, and changes string to Loading...
                setTitle("Loading...");
                setProgress(progress * 100); //Make the bar disappear after URL is loaded
                // Return the app name after finish loading
            }
        });

        webmenu.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:

              if(url.contains("PaymentSuccess"))
              {
                  Toast.makeText(OnlinePaymentGatewayActivity.this, "Order Placed Successfully.", Toast.LENGTH_SHORT).show();
                  Intent intent = new Intent(getApplicationContext(), Home.class);
                  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                  startActivity(intent);
              }else
              {
                  view.loadUrl(url);
              }

                //

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