package com.example.komunikator;

import java.util.ArrayList;

public class Conversation {
	 public static ArrayList<Message> details = new ArrayList<Message>();;
	
	 //TODO Change to non static after tests
	public static void add(Message m){
		details.add(m);
	}
	
	public Message getMostRecent(){
		int last = details.size() - 1;
		return details.get(last);
	}
	
	public Message getMessage(int index){
		return details.get(index);
	}
	   
	public void deleteMessage(int index){
		details.remove(index);
	}
	
	public void deleteMessage(Message message){
		details.remove(message);
	}
	
	public void deleteAll(){
		details=null;
	}
}
