/**
 * 
 */
package com.maitianer.demo.core.filesystem.utils;

import com.maitianer.demo.core.filesystem.UploadObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;


public class HttpDownloader {

	private static OkHttpClient httpClient = new OkHttpClient();
	
	public static UploadObject read(String url) throws IOException{
		
		Request.Builder requestBuilder = new Request.Builder().url(url);
		Response response = httpClient.newCall(requestBuilder.build()).execute();
		
		UploadObject item = new UploadObject(FilePathHelper.parseFileName(url), response.body().bytes());
		return item;
	}
	
	
}
