package com.example.komunikator;

import java.util.Date;

public interface IMessage {

	public Date getDateSent();

	public Date getDateRecieved();

	public void setDateSent();

	public void setDateRecieved();

	public boolean belongsToUser();

	public boolean isSent();

	public void markAsSent();

	public String getSenderName();

	public String getRecipientName();

	public String getMessageContent();

	public void setMessageContent(String content);

	public String toJson();
}
