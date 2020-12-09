package weekree.home.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    WebView webmenu,webdemo,webtop10;
    private AppBarConfiguration mAppBarConfiguration;
    LinearLayout l;
    int ccnt = 0;
    ProgressDialog progress;
    Button btncart;
    String uid;
    EditText et_search;

    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
 /*       mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Sumit";
            String description = "Suradkar";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("users", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        FirebaseMessaging.getInstance().subscribeToTopic("user")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Success";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
                        Log.d(TAG, msg);
                        Toast.makeText(Home.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


        l=findViewById(R.id.ll);
        et_search=findViewById(R.id.et_search);
        webmenu=findViewById(R.id.webmenu);
        webdemo=findViewById(R.id.webdemo);
       // webtop10=findViewById(R.id.webtopten);
       //webmenu.loadUrl("http://www.krescendo.co.in/weekree/index.php");
    // ==================== Web Menu

        webmenu.getSettings().setJavaScriptEnabled(true);
        webmenu.setWebChromeClient(new WebChromeClient());
        webmenu.loadUrl(BaseUrl.HOSTNAME+"index.php");

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

                String s=url.substring(url.lastIndexOf("/")+1);
                String s1[]=s.split("-");
                if(s1[0].equals("c"))
                {

                    Intent intent=new Intent(Home.this,CategoryWiseProductView.class);
                    intent.putExtra("id",s1[1]);
                    startActivity(intent);
                }else if(s1[0].equals("p"))
                {
                    Intent intent=new Intent(Home.this,SingleProductView.class);
                    intent.putExtra("id",s1[1]);
                    startActivity(intent);
                }
            //    Snackbar.make(l,""+s+" ID : "+s1[1],Snackbar.LENGTH_LONG).show();

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


     //============================= Web Demo
        webdemo.getSettings().setJavaScriptEnabled(true);
        webdemo.setWebChromeClient(new WebChromeClient());
        webdemo.loadUrl(BaseUrl.HOSTNAME+"slide/index.php");

        webdemo.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                //Make the bar disappear after URL is loaded, and changes string to Loading...
                setTitle("Loading...");
                setProgress(progress * 100); //Make the bar disappear after URL is loaded

                // Return the app name after finish loading

            }
        });

        webdemo.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url);
                return false; // then it is not handled by default action
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                view.loadUrl("file:///android_asset/slideimage.html");
               // Toast.makeText(MainActivity.this, " Check Internet Connectivity . ", Toast.LENGTH_LONG).show();
            }
        });

        webdemo.setOnKeyListener(new View.OnKeyListener() {
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

        progress = new ProgressDialog(Home.this);
        progress.setMessage("Loading .........");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        uid = new MyDb(Home.this).getUserID().trim();
        btncart = findViewById(R.id.btncart);
        try {
            showcart(uid);
        } catch (Exception e) {
            Log.i("Error in showcart", e.getMessage());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
       // Toast.makeText(Home.this, "Menu Clicked", Toast.LENGTH_SHORT).show();
        int id=item.getItemId();
        if(id==R.id.nav_history)
        {
            Intent intent=new Intent(Home.this,OrderHistory.class);
            startActivity(intent);
        }else if(id==R.id.nav_contactus)
        {
            Intent intent=new Intent(Home.this,ContactUs.class);
            startActivity(intent);
        }else if(id==R.id.nav_personal)
        {
            Intent intent=new Intent(Home.this,PersonalInfo.class);
            startActivity(intent);
        }else if(id==R.id.nav_shareapp)
        {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Weekree");
                String shareMessage= "\nYour Shop  .. Let's Shopping with us.\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch(Exception e) {
                //e.toString();
            }
        }else if(id==R.id.nav_terms)
        {
            Intent intent=new Intent(Home.this,TearmsAndConditions.class);
            startActivity(intent);
        }
        else if(id==R.id.nav_abautus)
        {
            Intent intent=new Intent(Home.this,AboutUs.class);
            startActivity(intent);
        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Toast.makeText(this, "History 11", Toast.LENGTH_SHORT).show();
        if(item.getItemId()==R.id.nav_history)
        {
            Toast.makeText(this, "History", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void setbtntext(String ste) {
        try {
            ccnt = Integer.parseInt(ste.toString().trim());
            btncart.setText("(" + ste + ")");
        } catch (Exception e) {

        }
    }

    public void viewcart(View v) {
        try {
            if (ccnt < 1) {
                //Snackbar.make(SingleProductView.this,"",Snackbar.LENGTH_LONG);
                Toast.makeText(Home.this, "No Product in Cart", Toast.LENGTH_LONG).show();

            } else {
                Intent intent = new Intent(Home.this, ViewCart.class);
                startActivity(intent);
            }

        } catch (Exception e) {
            Log.i(" Error Details", e.getMessage());
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
                        try {

                            setbtntext(response);

                        } catch (Exception e) {
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
                    Toast.makeText(Home.this, "Error : Loading Event Details" + error, Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("uid", uid);


                    return params;
                }

                ;

            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    15000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getmInstance(Home.this).addToRequestque(stringRequest);

        } catch (Exception e) {
            progress.dismiss();
            Toast.makeText(Home.this, "Error is" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
        showcart(uid);
    }
    public  void search(View v)
    {
        try{
            String data=et_search.getText().toString().trim();
            if(data.equals(""))
            {
                et_search.setError("Enter What to find !");
            }
            else
            {
                  Intent intent=new Intent(Home.this,SearchActivity.class);
                  intent.putExtra("data",data);
                  startActivity(intent);
            }

        }catch(Exception e)
        {

        }
    }

    public void opendrawer(View v)
    {
        try{
            drawer.openDrawer(Gravity.LEFT);
        }catch(Exception e)
        {
            Log.i("Error is ",e.getMessage());
        }
    }

}