//package komunikator;
//
//import com.example.komunikator.R;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ListView;
//
//public class ConversationsListActivity extends Activity {
//	public final static String EXTRA_RECIPIENT = "com.example.komunikator.ConversationsListActivity";
//
//	public static ConversationsListAdapter ad;
//	public ListView contacts;
//	
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//	    super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_conversation);
//		
//		this.startNewMessageListener();
//		this.createListView();
//
//		
//		
//		ConversationsList.addNewConversation("marcin");
//		ConversationsList.addNewConversation("michal");
//		ConversationsList.addNewConversation("rafal");
//		ad.notifyDataSetChanged();
//		
//
//	}
//	
//	private void startNewMessageListener(){
//		Intent intent = new Intent(this, NewMessageListener.class);
//		startService(intent);
//	}
//	
//	private void createListView(){
//		contacts = (ListView) findViewById(R.id.listView1);
//		contacts.setAdapter(ad = new ConversationsListAdapter(this.getApplicationContext()));
//		contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//		    	Conversation c = ConversationsList.getAt(arg2);
//		    	String recipient = c.getRecipient();
//		    	Intent intent = new Intent(getApplicationContext(), ConversationActivity.class);
//		    	intent.putExtra(ConversationsListActivity.EXTRA_RECIPIENT, recipient);
//		    	startActivity(intent);
//				
//			}
//		});
//	}
//
//}
