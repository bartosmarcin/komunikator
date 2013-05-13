package com.example.komunikator;

import java.util.ArrayList;

public class Conversation {
	public ArrayList<Message> details = new ArrayList<Message>();
	private String recipient;

	public Conversation(String recipient) {
		this.recipient = recipient;
	}
	
	public String getRecipient(){
		return recipient;
	}

	public void add(Message m) {
		details.add(m);
	}

	public Message getMostRecent() {
		int last = details.size() - 1;
		return details.get(last);
	}

	public Message getMessage(int index) {
		return details.get(index);
	}

	public void deleteMessage(int index) {
		details.remove(index);
	}

	public void deleteMessage(Message message) {
		details.remove(message);
	}

	public void deleteAll() {
		details = null;
	}

	public boolean sendMessageSuccessful(Message message) {
		if (new Connection().sendMessage(message)) {
			this.add(message);
			return true;
		}
		return false;
	}
}
