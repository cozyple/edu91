package com.lgcns.q3;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class MyServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("Request : "+ req.getRequestURL());
		String requestURI = req.getRequestURI();
		
		Map<String,String> result = new HashMap<>();
			  	
	  	MessageQueueMap queueMap = MessageQueueMap.getInstance();
	  	
	  	String[] params = requestURI.substring(1).split("/");
		String contextPath = params[0];
		String queueName = params[1];
		
		System.out.println("doGet ");
		
		if("RECEIVE".equals(contextPath)){

			MessageQueue    messageQueue    = queueMap.getMessageQueue(queueName);
			Message message = messageQueue.get();
			
			if(message == null) {
			
				result = setResult("No Message");
			
			} else {
				
				result = setResult("Ok");
				result.put("MessageId", message.getMessageId());
				result.put("Message", message.getMessage());
				
			}
		}
		
		Gson gson2 = new Gson();
		String resultMessage = gson2.toJson(result);
		System.out.println(resultMessage);
		res.setStatus(200);
		res.getWriter().write(resultMessage);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			
			String requestURI = req.getRequestURI();
			System.out.println("Request : "+ requestURI);
			Map<String,String> result = new HashMap<>();
			
			// Json Body Parsing
			Map<String,Object> reqBody = new HashMap<>();
			Gson gson = new Gson();
			StringBuffer sb = new StringBuffer();
		  	String line = null;
		  	try {
		  		BufferedReader reader = req.getReader();
		  		while ((line = reader.readLine()) != null)
		  			sb.append(line);
		  	} catch (Exception e) {
		  		/*report an error*/ 
		  	}
		  	reqBody = (Map<String,Object>) gson.fromJson(sb.toString(), reqBody.getClass());
		  	
		  	MessageQueueMap queueMap = MessageQueueMap.getInstance();
		  	
		  	String[] params = requestURI.substring(1).split("/");
			String contextPath = params[0];
			String queueName = params[1];
			String message = "";
			
			if("CREATE".equals(contextPath)) {
				
				int capacity = 0; 
				
				if(reqBody != null && reqBody.containsKey("QueueSize")) {
					capacity = ((Double)reqBody.get("QueueSize")).intValue();
				}
				
				if(queueMap.getMessageQueue(queueName) == null) {
					queueMap.setMessageQueue(queueName, capacity);
					result = setResult("OK");
				}else {
					result = setResult("Queue Exist");
				}
				
			}
			else if("SEND".equals(contextPath)) {
				String rslt = "";
				message = (String) reqBody.get("Message");
				
				rslt = queueMap.setMessage(queueName, message);
				
				System.out.println("333" + rslt);
				result = setResult(rslt);
			}
			else if("FAIL".equals(contextPath)) {
				String rslt = "";
				String messageId = params[2];
				
				queueMap.failMessageHandling(queueName, messageId);
				
				System.out.println("444" + rslt);
				result = setResult("ok");
				
			}
			else if("ACK".equals(contextPath)) {
				String rslt = "";
				String messageId = params[2];
				
				rslt = queueMap.completeMessageHandling(queueName, messageId);
				
				System.out.println("555" + rslt);
				result = setResult(rslt);
			}
			
			Gson gson2 = new Gson();
			String resultMessage = gson2.toJson(result);
			System.out.println(resultMessage);
			res.setStatus(200);
			res.getWriter().write(resultMessage);
		}
	
		private Map<String,String> setResult(Map<String,String> result, String message) {
			
			if(result == null) {
				result = new HashMap<>();	
			}
			
			result.put("Result", message);
			
			return result;
		}
	
		private Map<String,String> setResult(String message) {
			
			return setResult(null, message);
		}
}
