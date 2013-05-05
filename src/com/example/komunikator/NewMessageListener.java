package com.example.komunikator;

import android.app.IntentService;
import android.content.Intent;

public class NewMessageListener extends IntentService{
	int intervalInForeground;
	int intervalInBackground;
		
	public NewMessageListener(){
		super("NewMessageListener");
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		//TODO add code
		
	}

}
