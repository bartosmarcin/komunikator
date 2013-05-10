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
	Connection connect=new Connection(); //a to w sumie jest w klasie NewMessageListener
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conversation);
		
		EditText action = (EditText) findViewById(R.id.editText1); 
		msgList = (ListView) findViewById(R.id.listView1);
        
		//pobieranie nowych - na razie tylko przy ladowaniu aktywnosci poxniej sie doda ten modul dzialajacy w tle
//		if(connect.hasNewMessages()){
//			String mes=connect.getNewMessages();
			//w stringu jest masa wiadomosci w JSON... jak je oddzieliæ i przekonwertowaæ, aby mieæ obiekty message.
			//przekonwetowane mozna juz dodac do Conversation :)
//			for(int i=0;i<properMes.length();i++){
//				Conversation.add(properMes[i]);
//			}
//		}
		
		
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
		        // klopot z wroceniem do aktywnosci po wprowadzeniu tekstu + z zaprogrmowaniem przycisku newline
		        
//		        if (!event.isShiftPressed()) {
//		        	return true;
//		        } //przymiarka do obslugi przycisku powrotu
		        
		        if (actionId == EditorInfo.IME_ACTION_SEND) {
		        	String content = editText.getText().toString();
		        	Message Detail = new Message("ja","odbiorca","Z nowego przycisku: "+content);
		    		Date currentDate = Calendar.getInstance().getTime();
		            Detail.setDateSent(currentDate);
		    		Conversation.details.add(Detail);
		    		editText.setText("");
		    		ad.notifyDataSetChanged();
		            handled = true;
		            //na sztywno wyslanie wiadomosci
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
		Message Detail = new Message("ja","odbiorca",message);
		Date currentDate = Calendar.getInstance().getTime();
        Detail.setDateSent(currentDate);
		//this.connect.sendMessage("odbiorca",Detail.toJson());
		Conversation.details.add(Detail); //na ekran
		
		ad.notifyDataSetChanged();  //how to refresh listview
		editText.setText("");
		
		//refresh aktywnosci (tak na przyszlosc... jakos mozna to przechwycic... nie wczytuje sie metoda onCreate) 
		//finish();
		//startActivity(getIntent());
	}
		
}
