package me.pedrazas.heartbeat.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import me.pedrazas.heartbeat.om.Envelope;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AsyncClient {
	
	private static CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();

	public static void main(final String[] args) throws Exception {
        
		Envelope e = doGetJson("http://localhost:8080/ping");
        System.out.println(e.toString());
    }
	
	public static Envelope doGetJson(String url) throws InterruptedException, ExecutionException, IllegalStateException, IOException{
		try {
            httpclient.start();
            HttpGet request = new HttpGet(url);
            request.addHeader("accept", "application/json");            
            Future<HttpResponse> future = httpclient.execute(request, null);
            HttpResponse response = future.get();
            InputStream input = response.getEntity().getContent();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.fromJson(IOUtils.toString(input, "UTF-8"), Envelope.class);
            
        } finally {
            httpclient.close();
        }
	} 
	
	
	public static void doPost(String url, boolean json){
		
	}
}
