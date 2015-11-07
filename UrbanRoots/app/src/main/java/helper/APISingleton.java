package helper;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by whdinata on 11/7/15.
 */
public class APISingleton {
    private static APISingleton mInstance;
    private RequestQueue requestQueue;
    private static Context context;

    private APISingleton(Context context){
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public static APISingleton getmInstance(Context context){
        if(mInstance == null){
            mInstance = new APISingleton(context);
        }

        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }

        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request){
        getRequestQueue().add(request);
    }
}
