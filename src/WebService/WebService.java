package WebService;

import java.util.HashMap;
import java.util.Map;

import komunikator.WebServiceActivity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.komunikator.R;

public class WebService {
   private final String SIGNUP_URL = "http://192.168.0.15:8000/signup";
	private final String SIGNIN_URL = "http://192.168.0.15:8000/signin";
	private final String SEND_MESSAGE_URL = "http://192.168.0.15:8000/new_message";
	private static String authToken;

    private static RequestQueue requestQueue;
    private Context context;

    public WebService(Context context) {
    	this.context = context;
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(context
                    .getApplicationContext());
        authToken = getAuthTokenFromSharedPref();        
    }

    public void setAuthToken(String token) {
        authToken = token;
        SharedPreferences pref = context.getSharedPreferences(
        		context.getResources().getString(R.string.shared_pref_webservice), 
        		context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(context.getString(R.string.shared_pref_webservice_token_fld), token);
        editor.commit();
    }
    
    public String getAuthTokenFromSharedPref(){
    	SharedPreferences pref = context.getSharedPreferences(
        		context.getResources().getString(R.string.shared_pref_webservice), 
        		context.MODE_PRIVATE);
    	return pref.getString(context.getResources()
    			.getString(R.string.shared_pref_webservice_token_fld), null);
    }
    
    public boolean isSignedIn(){
    	// TODO zastanowic sie nad jakims zapytaniem do serwera
    	return authToken != null;
    }

    public WebServiceRequest signUp(final String email, final String password,
                                    final WebServiceActivity activity) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);
        StringRequest request = getPostRequest(SIGNUP_URL, params, activity);
        requestQueue.add(request);
        return new WebServiceRequest(request);
    }

    public WebServiceRequest signIn(final String email, final String password,
                                    final WebServiceActivity activity) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);
        StringRequest request = getPostRequest(SIGNIN_URL, params, activity);
        requestQueue.add(request);
        return new WebServiceRequest(request);
    }

    public WebServiceRequest sendMessage(String message,
                                         final WebServiceActivity activity) {
        JSONObject obj = new JSONObject();
        // TODO posprzatac
        try {
            obj.put("test", message);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//		JsonObjectRequest request = getJsonRequest(SEND_MESSAGE_URL, obj,
//				activity);
        Map<String, String> par = new HashMap<String, String>();
        par.put("tets", "val");
        StringRequest request = getPostRequest(SEND_MESSAGE_URL, par, activity);
        requestQueue.add(request);
        return new WebServiceRequest(request);
    }

    private StringRequest getPostRequest(String url,
                                         final Map<String, String> params, final WebServiceActivity activity) {
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    public void onResponse(String response) {
                        WebServiceResponse resp = WebServiceResponse
                                .getFromJson(response);
                        if (resp == null)
                            activity.onRequestFailure();
                        activity.onRequestSuccess(resp);
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                activity.onRequestFailure();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Token " + authToken);
                return headers;
            }
        };
        return request;
    }

    private JsonObjectRequest getJsonRequest(String url, JSONObject json,
                                             final WebServiceActivity activity) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                url, json, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                WebServiceResponse resp = WebServiceResponse
                        .getFromJson(response);
                if (resp == null)
                    activity.onRequestFailure();
                activity.onRequestSuccess(resp);
            }

        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                activity.onRequestFailure();
            }
        }) {

        };
        return request;
    }
}
