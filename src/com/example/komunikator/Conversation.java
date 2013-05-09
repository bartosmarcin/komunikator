package com.example.komunikator;

import java.util.ArrayList;

public class Conversation {
	 public static ArrayList<Message> details = new ArrayList<Message>();;
	
	public static void add(Message m){
		details.add(m);
	}
	
	public static Message getMostRecent(){
		int last = details.size() - 1;
		return details.get(last);
	}
	
	public static Message getMessage(int index){
		return details.get(index);
	}
	   
	public static void deleteMessage(int index){
		details.remove(index);
	}
	
	public static void deleteMessage(Message message){
		details.remove(message);
	}
	
	public static void deleteAll(){
		details=null;
	}
}
