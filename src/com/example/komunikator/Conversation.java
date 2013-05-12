package com.example.komunikator;

import java.util.ArrayList;

public class Conversation {
	 public static ArrayList<Message> details = new ArrayList<Message>();;
	
	 //TODO Change to non static after tests
	public static void add(Message m){
	public static void add(Message m){
		details.add(m);
		ConversationActivity.ad.notifyDataSetChanged();
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
