package com.lgcns.sample01;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.Map;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpMethod;

//BinaryFile2.java (GET, Serialize된 MAP을 받아 Key는 파일이름, Value는 파일내용으로 처리하여 저장)
public class BinaryFile2 {
    public static void main(String[] args) throws Exception {

        HttpClient httpClient = new HttpClient();
        httpClient.start();

        ContentResponse contentRes = httpClient.newRequest("http://127.0.0.1:8080/requestFile2").method(HttpMethod.GET).send();

        byte [] myByteArray = contentRes.getContent();

        ByteArrayInputStream byteIn = new ByteArrayInputStream(myByteArray);
        ObjectInputStream in = new ObjectInputStream(byteIn);

        Map<String, byte[]> data2 = (Map<String, byte[]>) in.readObject();

        try (FileOutputStream fos = new FileOutputStream("C:\\temp\\test.jpg")) {
            fos.write(data2.get("file"));
        }

        httpClient.stop();
    }
}
