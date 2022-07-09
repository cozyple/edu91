package com.lgcns.q3;

import java.util.LinkedHashMap;

public class MessageQueue {
	private String queueName;
	private int  size;
    private int  seqNo;
    private LinkedHashMap<String, Message> hashMsg;
    
    public MessageQueue(String queueName, int size) {
    	this.queueName = queueName;
    	this.size = size;
        this.seqNo = 0;		
        hashMsg = new LinkedHashMap<String, Message>();
    }

	// Queue Name의 Queue 에 Message 저장 , 저장 시 고유 Id 값을 생성하여 함께 저장
    public String enqueue(String msg) {
        if (hashMsg.size() == size) {
        	return "Queue Full";
        }   

        String messageId = this.queueName + "_" + Thread.currentThread().getId() + seqNo++;
        
        Message message = new Message(messageId, msg);
        hashMsg.put(messageId, message);
        

        return "Ok";
    }

	// Queue Name의 Queue 에 가장 먼저 저장된 Message 와 Message Id 를 출력하고 , 해당 메시지 삭제
    public Message dequeue() {
        if (hashMsg.size() == 0) {
        	return null;
        }
            

        String key = (String)hashMsg.keySet().iterator().next();
        
        Message res = hashMsg.get(key);

        hashMsg.remove(key);

        return res;
    }


	/*
	- Queue Name 의 Queue 에 가장 먼저 저장된 Message 와 Message Id 를 출력
	- 해당 Message 는 Queue 에서 삭제되지 않지만 , 다시 GET 할 수는 없음
	*/
    public Message get() {
        if (hashMsg.size() > 0)
            for(String key : hashMsg.keySet()) {
                if (hashMsg.get(key).getStatus().equals("A")) {
                	Message val = hashMsg.get(key);
                	val.setStatus("U");
                    hashMsg.put(key, val); 
                    return val;
                }
            }

        return null;
    }

	// Queue Name과 Message Id 에 해당하는 Message 를 다시 GET 할 수 있게 세팅
    public Message recover(String id) {
        if (hashMsg.size() > 0) {
            if (hashMsg.containsKey(id)) {
//                hashMsg.get(id).set(0, "A");  
            	Message val = hashMsg.get(id);
            	val.addFailCnt();
            	val.setStatus("A");
                return val;
            }
        }

        return null;
    }

    // Queue에서 Message Id 에 해당하는 Message 삭제 , 삭제 성공 시 “Deleted” 출력
    public String delete(String id) {
        if (hashMsg.size() > 0) {
            if (hashMsg.containsKey(id)) {
                hashMsg.remove(id);
                return "Ok";
            }
        }

        return "Not Deleted";
    }
    
    @Override 
    public String toString() {
    	return new StringBuilder()
    			.append("queueName=").append(queueName)
    			.append("&size=").append(size)
    			.append("&").append(hashMsg)
    			.toString();
    }
}
