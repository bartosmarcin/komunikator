package com.example.komunikator;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class Connection {
	private final String SEND_MSG_URL="http://iem.pw.edu.pl/~bartosm4/sendmsg.php";
    private final String GET_MSGS_URL="http://iem.pw.edu.pl/~bartosm4/getmsg.php";
    private final String hasNewMessegesURL="some string";
    
    private final String POST_RECIPIENT_FIELD_NAME="recipient";
    private final String POST_MESSAGE_FIELD_NAME="message";    
    private final String CORRECT_SERVER_RESPONSE="ok";
    

    public boolean sendMessage(String recipient, String messageJson){
    	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair(POST_RECIPIENT_FIELD_NAME, recipient));
        nameValuePairs.add(new BasicNameValuePair(POST_MESSAGE_FIELD_NAME, messageJson));
        return makePOSTrequest(nameValuePairs);
    }
	
/*	public boolean sendMessage(Message message){
		String recipientName = message.getRecipientName();
		String messageJson = Message.toJson();
		return sendMessage(recipientName, messageJson);
	}*/
	
	private boolean makePOSTrequest(List<NameValuePair> params){		
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(SEND_MSG_URL);
		try {
	        httppost.setEntity(new UrlEncodedFormEntity(params));
	        HttpResponse response = httpclient.execute(httppost);
	        if (isResponseCorrect(response))
	        	return true;	    	        	
	    } catch (ClientProtocolException e) {
	        return false;
	    } catch (IOException e) {
	        return false;
	    }
		return false;
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
		HttpEntity entity = response.getEntity();
        {
        try {
				InputStream instream = entity.getContent();
				return streamToString(instream);
			} catch (IOException e) {
				return null;
			}  		    
  		}
	}
	
	private String streamToString(InputStream inputStream) {
		java.util.Scanner s = new java.util.Scanner(inputStream,"UTF-8").useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
}
