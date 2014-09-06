//package komunikator;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class ConversationsList {
//	static Map<String,Conversation> conversations = new HashMap<String,Conversation>();
//	static List<String> keys = new ArrayList<String>();
//	
//	public static boolean addNewConversation(String username){
//		if( conversations.containsKey(username) )
//			return false;
//		Conversation conv = new Conversation(username);
//		conversations.put(username, conv);
//		keys.add(username);
//		return true;		
//	}
//	
//	public static Conversation getConversationByRecipient(String recipient){
//		return conversations.get(recipient);
//	}
//	
//	public static int getSize(){
//		return conversations.size();
//	}
//	
//	public static Conversation getAt(int index){
//		return conversations.get(keys.get(index));
//	}
//}
