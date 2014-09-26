package komunikator;

import java.util.Date;

public class Message {
	private Long from;
	private Long to;
	private Date dateSent;
	private Date dateRecieved;
	private String content;
	
	private Long webServiceId;
	public Long getWebServiceId() {
		return webServiceId;
	}
	public void setWebServiceId(Long webServiceId) {
		this.webServiceId = webServiceId;
	}
	public Long getFrom() {
		return from;
	}
	public void setFrom(Long from) {
		this.from = from;
	}
	public Long getTo() {
		return to;
	}
	public void setTo(Long to) {
		this.to = to;
	}
	public Date getDateSent() {
		return dateSent;
	}
	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}
	public Date getDateRecieved() {
		return dateRecieved;
	}
	public void setDateRecieved(Date dateRecieved) {
		this.dateRecieved = dateRecieved;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
