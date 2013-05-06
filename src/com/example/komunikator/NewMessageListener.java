package com.example.komunikator;

import android.app.IntentService;
import android.content.Intent;

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

				}
				start = System.currentTimeMillis() + intervalInForeground;				
			}			
		}
		
	}

}
