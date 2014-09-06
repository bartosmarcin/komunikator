package com.example.komunikator;

import java.sql.Connection;
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
	public final static String NEW_MESSAGES = "com.example.komunikator.NewMessageListener"; 
	private long intervalInForeground;
	private long intervalInBackground;
	private long currentInterval;
//	private Connection connection;
		
	public NewMessageListener(){
		super("NewMessageListener");
//		connection = new Connection();
		intervalInForeground = 10000;
		intervalInBackground = 300000;
	}
	
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
	
	private void showNotification(String msg, int id){
		NotificationManager mNotificationManager = 
				(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

		mBuilder.setContentTitle(getText(R.string.app_name))
		        .setContentText(msg)
		        .setProgress(0, 0, true)
		        .setSmallIcon(R.drawable.ic_launcher)
		        .setOngoing(true);
		Intent intent = new Intent(this, ConversationActivity.class);
		PendingIntent in = PendingIntent.getActivity(getApplicationContext(), 
				0, intent, 0);
		mBuilder.setContentIntent(in);
		mNotificationManager.notify(id, mBuilder.build());
		
	}
	
	private void sendNewMessagesBroadcast(){
		Intent intent=new Intent("com.example.komunikator.ConversationActivity");
		intent.setAction(this.NEW_MESSAGES);
		sendBroadcast(intent);
	}
	
	private void putToApropriateConversation(Message message){
		String username = message.getSenderName();
		ConversationsList.getConversationByRecipient(username).add(message);;
		
	}
	
	private void handleNewMessages(String newMessagesJson){
		List<Message> messagesArray= this.jsonArrayToMessageList(newMessagesJson);
		Message message;
		for(int i=0; i < messagesArray.size(); i++){
			message = messagesArray.get(i);
			String senderName = message.getSenderName();
			this.showNotification(message.getMessageContent()+
					senderName, i);
			putToApropriateConversation(message);
			sendNewMessagesBroadcast();
			}
		
	}
	
	

	@Override
	protected void onHandleIntent(Intent arg0) {
		// naive implementation
		//TODO find more efficient solution(like AlarmManager)
		long start = System.currentTimeMillis() + intervalInForeground;
		while(true){
			if(System.currentTimeMillis() > start){
//				if ( connection.hasNewMessages(User.getUsername(), User.getPassword())){
//					String messages = connection.getNewMessages(User.getUsername(), User.getPassword());
//					handleNewMessages(messages);
				}
				//if( MyApp.isInForeground() )
					start = System.currentTimeMillis() + intervalInForeground;	
				//else
				//	start = System.currentTimeMillis() + intervalInBackground;	
			}			
		}
		
	}

//}
