package com.example.komunikator;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

public class ConversationActivity extends Activity {
	ListView msgList;
	ConversationAdapter ad;
	
	@Override
	protected void onResume(){
		super.onResume();
		MyApp.activityResumed();
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		MyApp.activityPaused();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conversation);
		
		//Startowanie MessageListenera - Marcin :)
		//TODO przeniesc, gdy to nie bedzie glowna aktywnosc!
		Intent intent = new Intent(this, NewMessageListener.class);
		startService(intent);	
		
		//Conversation.details = new ArrayList<Message>();
		msgList = (ListView) findViewById(R.id.listView1);
        
		//polacznie z klasa connection i pobranie wiadomosci... + ewentualnie wczytanie historii
        // potrzebna klasa createMessage
		Message Detail = new Message("nadwca","odbiorca","tresc hej");
        Date currentDate = Calendar.getInstance().getTime();
		Detail.setDateSent(currentDate);
		Conversation.details.add(Detail);
        
        Detail = new Message("nadawca","odbiorca","tresc hej co tam");
        currentDate = Calendar.getInstance().getTime();
        Detail.setDateSent(currentDate);
        Conversation.details.add(Detail);
        
        //trzeba po all wiadomosciach wywolac
        msgList.setAdapter(ad = new ConversationAdapter(Conversation.details , this));
	}
	
	public void sendMessage(View view){
		//reakcja na guzik, tworzy wiadomosc i wysyla, nie wiem skad brac nadawce i odbiorce
		
		EditText editText = (EditText) findViewById(R.id.editText1);
		String message = editText.getText().toString();
		
		//potrzebna klasa createMessage tutaj na sztywno:
		Message Detail = new Message("marcin","marcin",message);
		Date currentDate = Calendar.getInstance().getTime();
        Detail.setDateSent(currentDate);
		//uzycie klasy connection na razie dopisze na ekran
		Conversation.details.add(Detail);
		Connection connection = new Connection();
		connection.sendMessage(Detail);
		
		ad.notifyDataSetChanged();  //how to refresh listview
		
		//refresh aktywnosci (tak na przyszlosc... jakos mozna to przechwycic... nie wczytuje sie metoda onCreate) 
		//finish();
		//startActivity(getIntent());
	}
}
