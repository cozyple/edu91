package com.lgcns.sample01;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpMethod;

//DateClient.java (GET, 날짜 요청)
public class DateClient {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
        HttpClient httpClient = new HttpClient();

        httpClient.start();

        ContentResponse contentRes = httpClient.newRequest("http://127.0.0.1:8080/requestDate").method(HttpMethod.GET).send();

        System.out.println(contentRes.getContentAsString());

        httpClient.stop();
	}

}
