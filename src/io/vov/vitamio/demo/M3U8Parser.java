package io.vov.vitamio.demo;

import java.io.BufferedReader; 
import java.io.InputStream; 
import java.io.InputStreamReader; 
import java.util.ArrayList; 
import java.util.HashMap;
import java.util.List; 
import java.util.Map;

import org.apache.http.HttpResponse; 

public final  class M3U8Parser {
	/** 
	* ��ָ����Url���н���,����һ������FilePath������б� 
	* FilePath��װÿһ��Audio·���� 
	* @param url 
	* @return 
	*/ 
	/*
	public static List<FilePath> parseFromUrl(String url){ 
	List<FilePath> resultList = null; 
	HttpResponse res = HttpConnect.getResponseFromUrl(url); 
	try { 
		if(res != null){ 
			resultList = new ArrayList<M3U8Parser.FilePath>(); 
			InputStream in = res.getEntity().getContent(); 
			BufferedReader reader = new BufferedReader(new InputStreamReader(in)); 
			String line = ""; 
			while((line = reader.readLine()) != null){ 
				if(line.startsWith("#")){ 
					//������Metadata��Ϣ 
				}else if(line.length() > 0 && line.startsWith("http://")){ 
					//������һ��ָ�����Ƶ��·�� 
					FilePath filePath = new FilePath(line); 
					resultList.add(filePath); 
				} 
			} 
			in.close(); 
		} 
	} catch (Exception e) { 
		e.printStackTrace(); 
	} 
		return resultList; 
	} 
*/
	/** 
	* ����List<String>���� 
	* @param url 
	* @return 
	*/ 
	public static Map<String,String> parseStringFromUrl(String url){ 
		Map<String,String>  resultMap = null;
		String urlparse = null;
		String timeparse = null;
		HttpResponse res = HttpConnect.getResponseFromUrl(url); 
		try { 
			if(res != null){ 
				resultMap = new HashMap<String,String>();
				InputStream in = res.getEntity().getContent(); 
				BufferedReader reader = new BufferedReader(new InputStreamReader(in)); 
				String line = ""; 
				while((line = reader.readLine()) != null){ 
					if(line.startsWith("#EXTINF:")){
						//������Metadata��Ϣ  ʱ��
						timeparse = line;
					}else if(line.length() > 0 && line.startsWith("http://")){ 
						//������һ��ָ�����Ƶ��·��
						urlparse = line; 
					}
					if(timeparse != null && urlparse != null){
						resultMap.put(timeparse, urlparse);
					}
					
				} 
				in.close(); 
			} 
		} catch (Exception e) { 
			e.printStackTrace(); 
		} 
			return resultMap; 
	} 
/*
	public static List<String> parseStringFromUrl(String url){ 
		List<String> urlList = null; 
		List<String> timeList = null; 
		HttpResponse res = HttpConnect.getResponseFromUrl(url); 
		try { 
			if(res != null){ 
				urlList = new ArrayList<String>(); 
				timeList = new ArrayList<String>();
				InputStream in = res.getEntity().getContent(); 
				BufferedReader reader = new BufferedReader(new InputStreamReader(in)); 
				String line = ""; 
				while((line = reader.readLine()) != null){ 
					if(line.startsWith("#EXTINF:")){
						//������Metadata��Ϣ 
						timeList.add(line);
					}else if(line.length() > 0 && line.startsWith("http://")){ 
						//������һ��ָ�����Ƶ��·�� 
						urlList.add(line); 
					} 
				} 
				in.close(); 
			} 
		} catch (Exception e) { 
			e.printStackTrace(); 
		} 
			return urlList; 
	} 
	
*/
/*
	//�������ʵ����� 
	static class FilePath{
		
		private  String filePath;
		
		{
			this.filePath = filePath; 
		}
	
		public String getFilePath() { 
			return filePath; 
		}

		public void setFilePath(String filePath) { 
			this.filePath = filePath; 
		} 
	}
	*/
}


