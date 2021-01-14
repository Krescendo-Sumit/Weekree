package weekree.home.service;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

public class SingleProductView extends AppCompatActivity {
    WebView webmenu;
    String id;
    static String pid, uid;  int ccnt=0;
    ProgressDialog progress;
    Button btncart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product_view);
        id = getIntent().getExtras().getString("id");
        pid = id;
        uid = new MyDb(SingleProductView.this).getUserID().trim();;
        btncart=findViewById(R.id.btncart);
        progress = new ProgressDialog(SingleProductView.this);
        progress.setMessage("Loading ...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        setTitle("Weekree");
        try {
            showcart(uid);
        }catch(Exception e)
        {
             Log.i("Error in showcart",e.getMessage());
        }
        webmenu = findViewById(R.id.webmenu);
        webmenu.getSettings().setJavaScriptEnabled(true);
        webmenu.setWebChromeClient(new WebChromeClient());
        webmenu.loadUrl(BaseUrl.HOSTNAME + "SingleProductView.php?id=" + id);
      //  Toast.makeText(SingleProductView.this, "Id id " + id, Toast.LENGTH_LONG).show();
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
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:

               /* String s=url.substring(url.lastIndexOf("/")+1);
                String s1[]=s.split("-");
                if(s1[0].equals("c"))
                {
                    Toast.makeText(SingleProductView.this,"Category",Toast.LENGTH_LONG).show();
                }
                if(s1[0].equals("i"))
                {
                    Toast.makeText(SingleProductView.this,"Image Details",Toast.LENGTH_LONG).show();
                }*/

                //view.loadUrl(url);

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


    public void addtocart(View v) {
        try {

          //  Toast.makeText(SingleProductView.this, "Product id is " + id, Toast.LENGTH_SHORT).show();
             addtocartproduct(uid,pid);
        } catch (Exception e) {
            Log.i(" Error Details", e.getMessage());
        }
    }

    public void viewcart(View v) {
        try {
            if(ccnt<1)
            {
                //Snackbar.make(SingleProductView.this,"",Snackbar.LENGTH_LONG);
                Toast.makeText(SingleProductView.this,"No Product in Cart",Toast.LENGTH_LONG).show();

            }else {
                Intent intent = new Intent(SingleProductView.this, ViewCart.class);
                startActivity(intent);
            }

        } catch (Exception e) {
            Log.i(" Error Details", e.getMessage());
        }
    }

    public void buynow(View v) {
        try {

            addtocartproduct(uid,pid);
            Intent intent=new Intent(SingleProductView.this,ViewCart.class);
            startActivity(intent);
        } catch (Exception e) {

        }
    }

    public void addtocartproduct(final String uid, final String pid) {
        try {

            String appURL = BaseUrl.HOSTNAME + "addtocart.php";
        //   Toast.makeText(this, ""+appURL, Toast.LENGTH_SHORT).show();

            progress.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, appURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Toast.makeText(Home.this, "" + response, Toast.LENGTH_SHORT).show();
                    try {
                        progress.dismiss();
                       // Toast.makeText(SingleProductView.this, "" + response, Toast.LENGTH_SHORT).show();
                        Log.i("Details", response);
                        try{

                            String ss[]=response.split("-");
                            Toast.makeText(SingleProductView.this, ""+ss[0], Toast.LENGTH_SHORT).show();
                             setbtntext(""+ss[1]);

                        }catch(Exception e)
                        {
                            Log.i("Details Inner", e.getMessage());
                        }


                        //loadData(response);

                    } catch (Exception e) {

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progress.dismiss();
                    Toast.makeText(SingleProductView.this, "Error : Loading Event Details" + error, Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("uid", uid);
                    params.put("pid", pid);


                    return params;
                } ;

            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    15000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getmInstance(SingleProductView.this).addToRequestque(stringRequest);

        } catch (Exception e) {
            progress.dismiss();
            Toast.makeText(SingleProductView.this, "Error is" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void showcart(final String uid) {
        try {

            String appURL = BaseUrl.HOSTNAME + "getcartcount.php";
            //   Toast.makeText(this, ""+appURL, Toast.LENGTH_SHORT).show();

            progress.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, appURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Toast.makeText(Home.this, "" + response, Toast.LENGTH_SHORT).show();
                    try {
                        progress.dismiss();
                        // Toast.makeText(SingleProductView.this, "" + response, Toast.LENGTH_SHORT).show();
                        Log.i("Details", response);
                        try{

                           setbtntext(response);

                        }catch(Exception e)
                        {
                            Log.i("Details Inner", e.getMessage());
                        }


                        //loadData(response);

                    } catch (Exception e) {

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progress.dismiss();
                    Toast.makeText(SingleProductView.this, "Error : Loading Event Details" + error, Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("uid", uid);



                    return params;
                } ;

            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    15000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getmInstance(SingleProductView.this).addToRequestque(stringRequest);

        } catch (Exception e) {
            progress.dismiss();
            Toast.makeText(SingleProductView.this, "Error is" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void setbtntext(String ste)
    {
       try{
           ccnt=Integer.parseInt(ste.toString().trim());
           btncart.setText("("+ste+")");
       }catch(Exception e)
       {

       }
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
        showcart(uid);
    }

}