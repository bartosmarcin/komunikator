package com.example.komunikator;

import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class ConversationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conversation);
		startService(new Intent(this,NewMessageListener.class));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		Message msg = new Message("marcin","marcin","testmsg");
		msg.setDateSent(new Date());
		getMenuInflater().inflate(R.menu.conversation, menu);
		Connection c = new Connection();
		c.sendMessage(msg);
		return true;
	}

}
