package helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Project;
import entity.RegistrationData;
import entity.ReportData;
import listener.ProjectDataListener;
import roots.urban.com.urbanroots.HomeActivity;

/**
 * Created by whdinata on 11/7/15.
 */
public final class API {
    public static final String DOMAIN = "https://5812d998.ngrok.com";
    public static final String LOGIN = DOMAIN + "/authenticate";
    public static final String CREATE_USER = DOMAIN + "/create_user";
    public static final String ISSUE = DOMAIN + "/issue";
    public static final String PROJECTS = DOMAIN + "/projects";

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

    public static void report(final Context context, final ReportData data){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("image", data.getEncodedImage());
            jsonObject.put("description", data.getDescription());
            jsonObject.put("lat", data.getLatitude());
            jsonObject.put("lng", data.getLongitude());
            jsonObject.put("kind", data.getKind());
        } catch(Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ISSUE, jsonObject, new Response.Listener<JSONObject>() {
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

    public static void getProjects(final Context context, final ProjectDataListener listener) {
        final ProgressDialog dialog = showDialog(context);

        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, PROJECTS, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        List<Project> projects = getProjectAsList(response);

                        listener.onSuccess(projects);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

        APISingleton.getmInstance(context).addToRequestQueue(request);
    }

    private static ProgressDialog showDialog(Context context){
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading. Please wait...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();

        return dialog;
    }

    private static List<Project> getProjectAsList(JSONObject projects){
        List<Project> projectList = new ArrayList<>();

        try {
            JSONArray data = projects.getJSONArray("data");
            for(int i = 0; i < data.length(); i++){
                JSONObject object = data.getJSONObject(i);
                projectList.add(new Project(object));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return projectList;
    }
}
