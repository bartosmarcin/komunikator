package com.example.komunikator;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


public class Connection {
	private final String SEND_MSG_URL="http://iem.pw.edu.pl/~bartosm4/service/testsendmsg.php";
    private final String GET_MSGS_URL="http://iem.pw.edu.pl/~bartosm4/service/getmsg.php";
    private final String HAS_NEW_MSGS_URL="http://iem.pw.edu.pl/~bartosm4/service/hasnew.php";
    
    private final String POST_RECIPIENT_FIELD_NAME="recipient";
    private final String POST_MESSAGE_FIELD_NAME="message";    
    
    private final String CORRECT_SERVER_RESPONSE="ok";
    private final String HAS_NEW_MSGS_RESPONSE="y";  
    
    public boolean hasNewMessages(){
    	return hasNewMessages(User.getUsername(), User.getPassword());
    }
    
    public boolean hasNewMessages(String username, String passHash){
    	List<NameValuePair> params = new ArrayList<NameValuePair>(1);
    	params.add(0, new BasicNameValuePair("username", username) );
    	HttpResponse resp = makePOSTrequest(params, HAS_NEW_MSGS_URL);
    	String response = responseToString(resp);
    	if( response != null )
    		return response.equals(HAS_NEW_MSGS_RESPONSE);
    	return false;
    }
    
    public boolean sendMessage(String recipient, String messageJson){
    	List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair(POST_RECIPIENT_FIELD_NAME, recipient));
        params.add(new BasicNameValuePair(POST_MESSAGE_FIELD_NAME, messageJson));
        return isResponseCorrect(makePOSTrequest(params, SEND_MSG_URL));
    }
	
	public boolean sendMessage(Message message){
		String recipientName = message.getRecipientName();
		String messageJson = message.toJson();
		return sendMessage(recipientName, messageJson);
	}
	
	public String getNewMessages(){
			return getNewMessages(User.getUsername(), User.getPassword());
	}
    
    public String getNewMessages(String username, String passHash){
    	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair(POST_RECIPIENT_FIELD_NAME, username));
        HttpResponse response = makePOSTrequest(nameValuePairs, GET_MSGS_URL);
        return responseToString(response);
    }
    
	private HttpResponse makePOSTrequest(List<NameValuePair> params, String url){		
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		try {
	        httppost.setEntity(new UrlEncodedFormEntity(params));
	        return httpclient.execute(httppost); 	
	    } catch (ClientProtocolException e) {
	        return null;
	    } catch (IOException e) {
	        return null;
	    }
	}
	
	private boolean isResponseCorrect(HttpResponse response){
		String responseStr = responseToString(response);
		if(responseStr == null)
			return false;
		if(!responseStr.equals(CORRECT_SERVER_RESPONSE))
			return false;
		return true;
	}
		
	private String responseToString(HttpResponse response){
		if(response == null)
			return null;
		HttpEntity entity = response.getEntity();
        {
        try {
				InputStream instream = entity.getContent();
				return streamToString(instream);
			} catch (IOException e) {
				return null;
			} catch (NullPointerException e){
				return null;
			}
  		}
	}
	
	private String streamToString(InputStream inputStream) {
		java.util.Scanner s = new java.util.Scanner(inputStream,"UTF-8").useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
}
