package com.example.komunikator;

public class Conversation {
      
//	public void addSent(){
//		
//	}
//	    
//	public void addRecieved(){
//		
//	}
	
	public void add(Message m){
		Conversation_activity.details.add(m);
	}
	
	public Message getMostRecent(){
		int last = Conversation_activity.details.size()-1;
		return Conversation_activity.details.get(last);
	}
	
	public Message getMessage(int index){
		return Conversation_activity.details.get(index);
	}
	   
	public void deleteMessage(int index){
		Conversation_activity.details.remove(index);
	}
	
	public void deleteMessage(Message message){
		Conversation_activity.details.remove(message);
	}
	
	public void deleteAll(){
		Conversation_activity.details=null;
	}
	
}
