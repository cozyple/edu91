package com.lgcns.q2;

import java.util.HashMap;
import java.util.Scanner;

public class RunManager {
	
	private static HashMap<String, MsgQueue> queueMap;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		queueMap = new HashMap<String, MsgQueue>();
		
			while(true) {
				
				try {
					String line = sc.nextLine();			
					String[] data = line.split(" ");
					
					String command = data[0];
					String qname = data[1];
					
					switch(command) {
						
						case "C":

							System.out.println(createQueue(qname, Integer.parseInt(data[2])));
							break;
							
						case "S":
							System.out.println(sendQueue(qname, data[2]));
							break;
							
						case "R":
							System.out.println(recvQueue(qname));
							break;					
						
						
					}
				
				}catch (Exception e) {
					// TODO: handle exception
				}
		}


	}

	private static String recvQueue(String qname) {
		
		return queueMap.get(qname).rcvQueue();
	}

	private static String sendQueue(String qname, String message) {
		
		return queueMap.get(qname).sendQueue(message);

	}

	private static String createQueue(String qname, int size) {
		
		
		
		if(queueMap.containsKey(qname)) {
			return "Queue Exist";
		}
		
		MsgQueue msgQ = new MsgQueue(size);
		
		queueMap.put(qname, msgQ);
		
		return "";
	}

}
