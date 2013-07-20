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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class ConversationActivity extends Activity {
	ListView msgList;
	EditText editText;

	public static ConversationAdapter ad;
	private Conversation connversation;
	private String recipient = "rafal";

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

		Intent recievedIntent = getIntent();
		recipient = recievedIntent
				.getStringExtra(ConversationsListActivity.EXTRA_RECIPIENT);

		this.createMessageList();

		broadcastReciever = new SampleReceiver();
		intentFilter = new IntentFilter(NewMessageListener.NEW_MESSAGES);

		EditText action = (EditText) findViewById(R.id.editText1);

		// TODO ogarnac co to jest, poprawic jesli potrzeba
		action.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				boolean handled = false;

				if (actionId == EditorInfo.IME_ACTION_SEND) {
					String content = editText.getText().toString();
					Message Detail = new Message("nadawca", "odbiorca",
							"Odwrocony ekran: " + content);
					Date currentDate = Calendar.getInstance().getTime();
					Detail.setDateSent(currentDate);
					conversation.details.add(Detail);
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

	private void createMessageList() {
		conversation = ConversationsList.getConversationByRecipient(recipient);
		msgList = (ListView) findViewById(R.id.listView1);
		msgList.setAdapter(ad = new ConversationAdapter(conversation.details,
				this));
		msgList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Message msg = conversation.getAt(arg2);
				if( !msg.isSent() ){
					conversation.sendMessage(msg);
					ad.notifyDataSetChanged();
				}	
			}
		});

	}

	public void sendMessage(View view) {
		EditText editText = this.editText;

		String msgContent = editText.getText().toString();
		Message msg = createNewMessage(msgContent);

		conversation.sendMessage(msg);
		conversation.add(msg);
		ad.notifyDataSetChanged();
		editText.setText("");
		editText.clearFocus();
		hideKeyboard();
	}

	private Message createNewMessage(String content) {
		Message newMessage = new Message(User.getUsername(), this.recipient,
				content);
		Date currentDate = Calendar.getInstance().getTime();
		newMessage.setDateSent(currentDate);
		return newMessage;
	}

	private void hideKeyboard() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}

	private class SampleReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			ad.notifyDataSetChanged();
		}
	}
}
