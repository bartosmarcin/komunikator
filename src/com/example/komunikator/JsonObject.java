package com.example.komunikator;

import java.util.Date;

import com.google.gson.Gson;

public class JsonObject {
	private String sender;
	private String recipent;
	private String content;
	private Date dateSent;
	
	public JsonObject(String sender, String recipent, String content, Date dateSent) {
		this.sender = sender;
		this.recipent = recipent;
		this.content = content;
		this.dateSent = dateSent;
	}
	
	public JsonObject(String json) {
		Gson gson = new Gson();
		JsonObject o = gson.fromJson(json, JsonObject.class);
		sender = o.getSender();
		recipent = o.getRecipent();
		content = o.getContent();
		dateSent = o.getDateSent();
	}
	
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
	public String getSender() {
		return sender;
	}
	
	public String getRecipent() {
		return recipent;
	}
	
	public String getContent() {
		return content;
	}
	
	public Date getDateSent() {
		return dateSent;
	}

}
