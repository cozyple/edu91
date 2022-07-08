package com.lgcns.q1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class RunManager {

	private static Queue<String> queue = new LinkedList<String>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			String line = sc.nextLine();
			
			String[] data = line.split(" ");
			
			if(data[0].equals("SEND")) {
				try {
					queue.add(data[1]);
				}catch (Exception e) {
					// TODO: handle exception
				}
				
			}else if(data[0].equals("RECEIVE")) {
				try {
					String msg = queue.poll();
					
					if(!msg.isEmpty()) {
						System.out.println(msg);
					}
				}catch (Exception e) {
					// TODO: handle exception
				}
				
			}else {
				continue;
			}
			
		}
	}

}
