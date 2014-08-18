package WebService;

import java.util.HashMap;
import java.util.Map;

import komunikator.WebServiceActivity;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class WebService {
	private final String SIGNUP_URL = "http://192.168.1.100:8000/signup";

	private RequestQueue requestQueue;

	public WebService(Context context) {
		if (requestQueue == null)
			requestQueue = Volley.newRequestQueue(context.getApplicationContext());
	}

	public WebServiceRequest signUp(final String email, final String password,
			final WebServiceActivity activity) {
			Map<String,String> params = new HashMap<String, String>();
			params.put("email", email);
			params.put("password", password);
			StringRequest request = getPostRequest(SIGNUP_URL, params, activity);
			requestQueue.add(request);
			return new WebServiceRequest(request);
	}
		

	private StringRequest getPostRequest(String url, final Map<String, String> params,
			final WebServiceActivity activity) {
		StringRequest request = new StringRequest(Request.Method.POST, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						WebServiceResponse resp = WebServiceResponse
								.getFromJson(response);
						if (resp == null)
							activity.onRequestFailure();
						activity.onRequestSuccess(resp);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						activity.onRequestFailure();
					}
				})
		{
			@Override
			 public Map<String, String> getParams() {
					 return params;
			}
		};

		return request;
	}
}

