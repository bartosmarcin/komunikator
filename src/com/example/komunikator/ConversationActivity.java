package com.example.komunikator;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.view.KeyEvent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class ConversationActivity extends Activity {
	ListView msgList;
	public ConversationAdapter ad;
	EditText editText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conversation);
		
		EditText action = (EditText) findViewById(R.id.editText1); 

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
        
        
		action.setOnEditorActionListener(new OnEditorActionListener() { //setOnEditorActionListener(new OnEditorActionListener() {
		    @Override
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        boolean handled = false;
		        // klopot z wroceniem do aktywnosci + z zaprogrmowaniem przycisku new line
		        
//		        if (!event.isShiftPressed()) {
//		        	return true;
//		        }
		        
		        if (actionId == EditorInfo.IME_ACTION_SEND) {
		        	Message Detail = new Message("ja","odbiorca","Z nowego przycisku");
		    		Date currentDate = Calendar.getInstance().getTime();
		        	Detail = new Message("ja","odbiorca","Z nowego przycisku");
		    		currentDate = Calendar.getInstance().getTime();
		            Detail.setDateSent(currentDate);
		    		Conversation.details.add(Detail);
		    		editText.setText("");
		    		ad.notifyDataSetChanged();
		            handled = true;
		            
		        }
		        return handled;
		    }
		});
		this.editText=action;
	}
	
	public void sendMessage(View view){
		//reakcja na guzik, tworzy wiadomosc i wysyla, nie wiem skad brac nadawce i odbiorce
		
		//EditText editText = (EditText) findViewById(R.id.editText1);
		EditText editText = this.editText;
		String message = editText.getText().toString();
		
		//potrzebna klasa createMessage tutaj na sztywno:
		Message Detail = new Message("ja","odbiorca",message);
		Date currentDate = Calendar.getInstance().getTime();
        Detail.setDateSent(currentDate);
		//uzycie klasy connection na razie dopisze na ekran
		Conversation.details.add(Detail);
		
		ad.notifyDataSetChanged();  //how to refresh listview
		editText.setText("");
		
		
		//refresh aktywnosci (tak na przyszlosc... jakos mozna to przechwycic... nie wczytuje sie metoda onCreate) 
		//finish();
		//startActivity(getIntent());
	}
		
}
