package com.example.komunikator;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

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
	
	private void showNotification(String msg){
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

		mBuilder.setContentTitle("My Application")
		        .setContentText(msg)
		        .setProgress(0, 0, true)
		        .setSmallIcon(R.drawable.ic_launcher);
		Intent intent = new Intent(this, ConversationActivity.class);
		PendingIntent in = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
		mBuilder.setContentIntent(in);
		mNotificationManager.notify(0, mBuilder.build());

	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		// naive implementation
		//TODO find more efficient solution(like AlarmManager)
		//TODO implement msgs handling
		long start = System.currentTimeMillis() + intervalInForeground;
		while(true){
			if(System.currentTimeMillis() > start){
				if ( connection.hasNewMessages("marcin") ){
					String messages = connection.getNewMessages("marcin");
					showNotification(messages);
				}
				start = System.currentTimeMillis() + intervalInForeground;				
			}			
		}
		
	}

}
