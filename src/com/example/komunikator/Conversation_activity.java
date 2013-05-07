package com.example.komunikatorr;

import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

public class Conversation_activity extends Activity {
  ListView msgList;
    public static ArrayList<Message> details;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conversation);
		
		details = new ArrayList<Message>();
		msgList = (ListView) findViewById(R.id.listView1);
        
		//polacznie z klasa connection i pobranie wiadomosci... + ewentualnie wczytanie historii
        // potrzebna klasa createMessage
		Message Detail = new Message("nadwca","odbiorca","tresc hej");
        details.add(Detail);
        
        Detail = new Message("nadawca","odbiorca","tresc hej co tam");
        details.add(Detail);
        
        //trzeba po all wiadomosciach wywolac
        msgList.setAdapter(new ConversationAdapter(details , this));
	}
	
	public void sendMessage(){
		//reakcja na guzik, tworzy wiadomosc i wysyla, nie wiem skad brac nadawce i odbiorce
		
		//EditText editText = (EditText) findViewById(R.id.editText1);
		//String message = editText.getText().toString();
		
		//potrzebna klasa createMessage tutaj na sztywno:
		//Message Detail = new Message("nadwca","odbiorca",message);
		
		//uzycie klasy connection na razie dopisze na ekran
		//details.add(Detail);
		
		//refresh aktywnosci bo musi sie wczytac nowa tablica :(
//		finish();
//		startActivity(getIntent());
	}
}
