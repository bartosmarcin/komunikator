package com.example.komunikator;

import java.util.Date;

public class Message implements IMessage {
	
	private Date dateSent, dateRecieved;
	private String sender, recipent, content;
	private boolean sent, belongsToUser;
	
	public Message(String sender, String recipent, String content) {
		this.sender = sender;
		this.recipent = recipent;
		setMessageContent(content);
		sent = false;
	}
	
	public Message(String json) {
		JsonObject obj = new JsonObject(json);
		sender = obj.getSender();
		recipent = obj.getRecipent();
		content = obj.getContent();
		dateSent = obj.getDateSent();
	}

	public Date getDateSent() {
		return dateSent;
	}

	public Date getDateRecieved() {
		return dateRecieved;
	}
	
	public void setDateSent() {
		dateSent = new Date();
	}

	public void setDateRecieved() {
		dateRecieved = new Date();
	}
	
	public boolean belongsToUser() {
		return belongsToUser;
	}

	public boolean isSent() {
		return sent;
	}
	
	public void markAsSent() {
		sent = true;
	}

	public String getSenderName() {
		return sender;
	}

	public String getRecipientName() {
		return recipent;
	}

	public String getMessageContent() {
		return content;
	}

	public void setMessageContent(String content) {
		this.content = content;
	}

	public String toJson() {
		JsonObject obj = new JsonObject(sender, recipent, content, dateSent);
		return obj.toJson();
	}

}
