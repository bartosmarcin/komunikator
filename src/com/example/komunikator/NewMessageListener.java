package com.example.komunikator;

import java.util.ArrayList;
import java.util.List;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import org.json.JSONArray;
import org.json.JSONException;

public class NewMessageListener extends IntentService{
	long intervalInForeground;
	long intervalInBackground;
	Connection connection;
		
	public NewMessageListener(){
		super("NewMessageListener");
		connection = new Connection();
		intervalInForeground = 10000;
		intervalInBackground = 300000;
	}
	
	
	// TODO test
	private List<Message> jsonArrayToMessageList(String json){
		List<Message> array = new ArrayList<Message>();
		Message message;
		try{
			JSONArray jarray = new JSONArray(json);
			for(int i=0; i < jarray.length(); i++){
				message = new Message(jarray.getString(i));
				array.add(message);
			}
		}catch(JSONException e){			
		}
		return array;
	}
	
	
	// TODO remove after tests
	private List<String> jsonArrayToStringList(String json){
		List<String> array = new ArrayList<String>();
		Message message;
		try{
			JSONArray jarray = new JSONArray(json);
			for(int i=0; i < jarray.length(); i++){
				array.add(jarray.getString(i));
			}
		}catch(JSONException e){			
		}
		return array;
	}	
	
	private void showNotification(String msg, int id){
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

		mBuilder.setContentTitle("My Application")
		        .setContentText(msg)
		        .setProgress(0, 0, true)
		        .setSmallIcon(R.drawable.ic_launcher);
		Intent intent = new Intent(this, ConversationActivity.class);
		PendingIntent in = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
		mBuilder.setContentIntent(in);
		mNotificationManager.notify(id, mBuilder.build());

	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		// naive implementation
		//TODO find more efficient solution(like AlarmManager)
		//TODO implement msgs handling
		long start = System.currentTimeMillis() + intervalInForeground;
		while(true){
			if(System.currentTimeMillis() > start){
				if ( connection.hasNewMessages("marcin", "temphash") ){
					String messages = connection.getNewMessages("marcin", "temphash");
					List<Message> messagesArray= this.jsonArrayToMessageList(messages);
					for(int i=0; i < messagesArray.size(); i++)
						this.showNotification(messagesArray.get(i).getMessageContent()+
								messagesArray.get(i).getSenderName(), i);
				}
				start = System.currentTimeMillis() + intervalInForeground;				
			}			
		}
		
	}

}
