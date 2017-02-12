package io.github.adraw.framework.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import com.alibaba.fastjson.JSONObject;

public class HttpUtil {

	public static void main(String[] args) throws IOException {
		String token = login();
		test(token);
	}

	private static String login() throws IOException {
		BufferedReader buffer=null;  
        String result="";  
        try {  
              CloseableHttpClient httpclient = HttpClients.createDefault();  
                  RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();//设置请求和传输超时时间  
              HttpPost httpPost = new HttpPost("http://localhost:8080/login");  
              httpPost.setConfig(requestConfig);  
                      httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");  
              httpPost.addHeader(HTTP.CONTENT_TYPE,  "text/json");  
              JSONObject obj= new JSONObject();
              obj.put("userId", "jack");
              obj.put("password", "123456");
              StringEntity se = new StringEntity(obj.toString());  
              se.setContentType("text/json");  
              se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));  
              httpPost.setEntity(se);  
              CloseableHttpResponse  response= httpclient.execute(httpPost);  
              buffer=new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"utf-8"));  
              result =buffer.readLine();
              System.out.println(result);
              return result;
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{  
            buffer.close();  
        }
        
        return null;
	}
	private static void test(String token) throws IOException {
		BufferedReader buffer=null;  
		String result="";  
		try {  
			CloseableHttpClient httpclient = HttpClients.createDefault();  
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();//设置请求和传输超时时间  
			HttpPost httpPost = new HttpPost("http://localhost:8080/test");  
			httpPost.setConfig(requestConfig);  
			httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");  
			httpPost.addHeader("Authorization", token);  
//			JSONObject obj= new JSONObject();
//			obj.put("userId", "jack");
//			obj.put("password", "123456");
//			StringEntity se = new StringEntity(obj.toString());  
//			se.setContentType("text/json");  
//			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));  
//			httpPost.setEntity(se);  
			CloseableHttpResponse  response= httpclient.execute(httpPost);  
			buffer=new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"utf-8"));  
			result =buffer.readLine();
			System.out.println(result);
		} catch (Exception e) {  
			e.printStackTrace();  
		}finally{  
			buffer.close();  
		}
	}

}
