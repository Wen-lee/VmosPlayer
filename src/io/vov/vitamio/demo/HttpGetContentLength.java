package io.vov.vitamio.demo;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpGetContentLength {
	
	private HttpEntity mHttpEntity = null;
	private HttpResponse mHttpResponse = null;
	private Long  mContenLength;
	private String mPath;


	public HttpGetContentLength() {  
	       // Exists only to defeat instantiation.  
	}  

	public HttpGetContentLength( String path) {  
		mPath = path;
	}  
	public Long getHttpContentLength(){
	// http://blog.csdn.net/bhq2010/article/details/9210007
	HttpGet httpGet = new HttpGet(mPath);
	httpGet.addHeader("Accept-Encoding","identity");
	//HttpGet httpGet = new HttpGet("http://www.baidu.com");
	HttpClient httpClient = new DefaultHttpClient();
	try{
		mHttpResponse = httpClient.execute(httpGet);
		
		if(mHttpResponse.getStatusLine().getStatusCode() == 200)
		 {
			mHttpEntity = mHttpResponse.getEntity();
			mContenLength = mHttpEntity.getContentLength();
		 }else{
			 mContenLength = -2L;
		 }

	}catch (ClientProtocolException e) {  
	    // TODO Auto-generated catch block  
	    e.printStackTrace(); 
	}catch (IOException e) {
		// TODO Auto-generated catch block  
		e.printStackTrace();
	}
	finally{
		httpClient.getConnectionManager().shutdown();
	}

	return mContenLength;
	}
	

}
