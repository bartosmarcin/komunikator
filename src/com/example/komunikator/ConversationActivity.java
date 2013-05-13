package com.example.komunikator;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.KeyEvent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class ConversationActivity extends Activity {
	ListView msgList;
	public static ConversationAdapter ad;
	EditText editText;

	private SampleReceiver broadcastReciever;
	private IntentFilter intentFilter;

	@Override
	protected void onResume() {
		super.onResume();
		MyApp.activityResumed();
		registerReceiver(broadcastReciever, intentFilter);
		ad.notifyDataSetChanged();
	}

	@Override
	protected void onPause() {
		super.onPause();
		MyApp.activityPaused();
		unregisterReceiver(broadcastReciever);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conversation);

		// TODO przeniesc, gdy to nie bedzie glowna aktywnosc!
		broadcastReciever = new SampleReceiver();
		intentFilter = new IntentFilter("newmsgs");
		Intent intent = new Intent(this, NewMessageListener.class);
		startService(intent);

		EditText action = (EditText) findViewById(R.id.editText1);
		msgList = (ListView) findViewById(R.id.listView1);
		msgList.setAdapter(ad = new ConversationAdapter(Conversation.details,
				this));

		//TODO ogarnac co to jest, poprawic jesli potrzeba
		action.setOnEditorActionListener(new OnEditorActionListener() { 
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				boolean handled = false;
				
				if (actionId == EditorInfo.IME_ACTION_SEND) {
					String content = editText.getText().toString();
					Message Detail = new Message("ja", "odbiorca",
							"Z nowego przycisku: " + content);
					Date currentDate = Calendar.getInstance().getTime();
					Detail.setDateSent(currentDate);
					Conversation.details.add(Detail);
					editText.setText("");
					ad.notifyDataSetChanged();
					handled = true;
					// na sztywno wyslanie wiadomosci
				}
				return handled;
			}
		});
		this.editText = action;
	}

	public void sendMessage(View view) {
		EditText editText = this.editText;

		String message = editText.getText().toString();
		Message Detail = new Message("marcin", "marcin", message);
		Date currentDate = Calendar.getInstance().getTime();
		Detail.setDateSent(currentDate);

		Conversation.add(Detail);
		Connection connection = new Connection();
		connection.sendMessage(Detail);

		ad.notifyDataSetChanged();
		editText.setText("");
		editText.clearFocus();
		InputMethodManager imm = (InputMethodManager)getSystemService(
			      Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

		
	}

	private class SampleReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			ad.notifyDataSetChanged();
		}
	}
}
