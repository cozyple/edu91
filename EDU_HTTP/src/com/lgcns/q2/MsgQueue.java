package com.lgcns.q2;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MsgQueue {
	
	private int size;
	private int seq;
	
	//List => msg
	private LinkedHashMap<Integer, List<String>> map;
	
	public MsgQueue(int size) {
		this.size = size;
		seq = 0;
		map = new LinkedHashMap<Integer, List<String>>();
	}
	
	public String sendQueue(String message) {
		if(map.size() == size) {
			return "Queue Full";
		}
		
		List<String> list = new ArrayList<String>();
		
		list.add(message);
		
		map.put(seq++, list);		
		
		return null;
	}
	
	public String rcvQueue() {
		
		if(map.size() == 0) {
			System.out.println("Queue Empty");
		}
		
		int key = map.keySet().iterator().next();
		
		String result = map.get(key).get(0);
		
		map.remove(key);
		
		return result;
		
	}
	
}
