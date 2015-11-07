package helper;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import entity.RegistrationData;
import roots.urban.com.urbanroots.HomeActivity;

/**
 * Created by whdinata on 11/7/15.
 */
public final class API {
    public static final String DOMAIN = "https://codeforgood.herokuapp.com";
    public static final String LOGIN = DOMAIN + "/authenticate";
    public static final String CREATE_USER = DOMAIN + "/create_user";

    public static void login(final Context context, final String email, final String password){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch(Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, LOGIN, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Intent intent = new Intent(context, HomeActivity.class);
                context.startActivity(intent);

                System.out.println(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        APISingleton.getmInstance(context).addToRequestQueue(request);
    }

    public static void register(final Context context, final RegistrationData data){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("first_name", data.getFirstName());
            jsonObject.put("last_name", data.getLastName());
            jsonObject.put("email", data.getEmail());
            //jsonObject.put("password", data.getPhone());
            jsonObject.put("password", data.getPassword());
        } catch(Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, CREATE_USER, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Intent intent = new Intent(context, HomeActivity.class);
                context.startActivity(intent);

                System.out.println("Response: " + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        APISingleton.getmInstance(context).addToRequestQueue(request);
    }
}
