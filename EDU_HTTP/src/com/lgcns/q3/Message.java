package com.lgcns.q3;

import java.io.Serializable;

public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5211952159902695678L;
	
	private String messageId;
	private String message;
	private String status;
	private int failCnt;
	
	public Message(String messageId, String message) {
		this.messageId = messageId;
		this.message = message;
		this.status = "A";
		this.failCnt = 0;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getMessageId() {
		return this.messageId;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void addFailCnt() {
		this.failCnt++;
	}
	
	public int getFailCnt() {
		return this.failCnt;
	}
	
	
	@Override
	public String toString() {
		return "messageId="+messageId+"&message="+message+"&failCnt="+failCnt+"&status="+status;
	}
}