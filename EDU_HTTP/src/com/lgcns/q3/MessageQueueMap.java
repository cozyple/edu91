package com.lgcns.q3;

import java.util.HashMap;
import java.util.Map;

public class MessageQueueMap {
	private Map<String,MessageQueue> queueMap = new HashMap<>();
	
	private static final Object lock = new Object();
	
	private static MessageQueueMap instance;
	
	
	private MessageQueueMap() {
		
	}
	
	public static MessageQueueMap getInstance() {
		
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new MessageQueueMap();
				}
			}

		}
		return instance;
		
	}
	
	public int getSize() {
		return queueMap.size();
	}
	
	public boolean setMessageQueue(String key, int capacity) {
		if(queueMap.containsKey(key)) {
			return false;
		} else {
			queueMap.put(key, new MessageQueue(key, capacity));
			return true;
		}
	}
	
	public MessageQueue getMessageQueue(String key) {
		return queueMap.get(key);
	}
	
	public String setMessage(String key, String message) {
		return getMessageQueue(key).enqueue(message);
	}
	
	public Message getMessage(String key) {
		return getMessageQueue(key).dequeue();
	}
	
	public String completeMessageHandling(String key, String messageId) {
		return queueMap.get(key).delete(messageId);
	}
	
	public void failMessageHandling(String key, String messageId) {
		queueMap.get(key).recover(messageId);
	}
}
