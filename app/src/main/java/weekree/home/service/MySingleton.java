package weekree.home.service;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Nihar on 20-07-2018.
 */

public class MySingleton {
    private static MySingleton mInstance;
    private static Context mctx;
    private RequestQueue requestQueue;

    private MySingleton(Context context)
    {
          mctx=context;
        requestQueue=getRequestQueue();
    }

    private RequestQueue getRequestQueue()
    {
        if(requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(mctx.getApplicationContext());

        }
        return requestQueue;
    }

    public static synchronized MySingleton getmInstance(Context mctx)
    {
        if(mInstance==null)
        {
            mInstance=new MySingleton(mctx);
        }
        return mInstance;
    }

    public<T> void addToRequestque(Request<T> request)
    {
        getRequestQueue().add(request);
    }
}
