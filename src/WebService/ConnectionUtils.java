package WebService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

class ConnectionUtils {
	private HttpClient httpclient;
	private final String CORRECT_SERVER_RESPONSE = "ok";

	public ConnectionUtils(){
		httpclient = new DefaultHttpClient();
	}
	
	protected HttpResponse makePOSTrequest(List<NameValuePair> params, String url) {
		HttpPost postRequest = new HttpPost(url);
		try {
			postRequest.setEntity(new UrlEncodedFormEntity(params));
			return httpclient.execute(postRequest);
		} catch (ClientProtocolException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	protected String responseToString(HttpResponse response) {
		if (response == null)
			return null;
		HttpEntity entity = response.getEntity();
		{
			try {
				InputStream instream = entity.getContent();
				return streamToString(instream);
			} catch (IOException e) {
				return null;
			} catch (NullPointerException e) {
				return null;
			}
		}
	}

	private String streamToString(InputStream inputStream) {
		java.util.Scanner s = new java.util.Scanner(inputStream, "UTF-8")
				.useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

	protected boolean isResponseCorrect(HttpResponse response) {
		String responseStr = responseToString(response);
		if (responseStr == null)
			return false;
		if (!responseStr.equals(CORRECT_SERVER_RESPONSE))
			return false;
		return true;
	}

}
