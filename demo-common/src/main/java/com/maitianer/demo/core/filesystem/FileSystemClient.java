package com.maitianer.demo.core.filesystem;

import com.maitianer.demo.common.utils.ResourceUtils;
import com.maitianer.demo.core.filesystem.provider.aliyun.AliyunOssProvider;
import com.maitianer.demo.core.filesystem.provider.qiniu.QiniuProvider;
import org.apache.commons.lang3.Validate;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class FileSystemClient {
	
	private static Map<String, FileSystemClient> clients = new HashMap<>();
	private static final String PUBLIC_ID = ResourceUtils.getProperty("public.filesystem.id","public");
	private static final String PRIVATE_ID = ResourceUtils.getProperty("private.filesystem.id","private");

	private FSProvider fsProvider;
	
	private FileSystemClient(String id) {
		
		String provider = ResourceUtils.getProperty(id + ".filesystem.provider");
		Validate.notBlank(provider, "["+id+".filesystem.provider] not defined");
		boolean isPrivate = ResourceUtils.getBoolean(id + ".filesystem.private");
		String urlPrefix = ResourceUtils.getProperty(id + ".filesystem.urlPrefix");
		if(QiniuProvider.NAME.equals(provider)){
			String bucketName = ResourceUtils.getProperty(id + ".filesystem.bucketName");
			String accessKey = ResourceUtils.getProperty(id + ".filesystem.accessKey");
			String secretKey = ResourceUtils.getProperty(id + ".filesystem.secretKey");
			fsProvider = new QiniuProvider(urlPrefix, bucketName, accessKey, secretKey,isPrivate);
		}else if(AliyunOssProvider.NAME.equals(provider)){
			String endpoint = ResourceUtils.getProperty(id + ".filesystem.endpoint");
			String bucketName = ResourceUtils.getProperty(id + ".filesystem.bucketName");
			String accessKey = ResourceUtils.getProperty(id + ".filesystem.accessKey");
			String secretKey = ResourceUtils.getProperty(id + ".filesystem.secretKey");
			fsProvider = new AliyunOssProvider(urlPrefix,endpoint, bucketName, accessKey, secretKey,isPrivate);
		}else{
			throw new IllegalArgumentException("file provider ID:" + id + " not support");
		}
	}
	
	public static FileSystemClient getClient(String id){
		return createClient(id);
	}
	
	public static FileSystemClient getPublicClient(){
		return createClient(PUBLIC_ID);
	}
	
	public static FileSystemClient getPrivateClient(){
		return createClient(PRIVATE_ID);
	}
	
	private static FileSystemClient createClient(String id){

		FileSystemClient client = clients.get(id);
		if(client != null)return client;
		
		synchronized (clients) {
			client = clients.get(id);
			if(client != null)return client;
			client = new FileSystemClient(id);
			clients.put(id, client);
		}
		
		return client;
	}
	
	
	public String upload(File file) {
		return fsProvider.upload(new UploadObject(file));
	}
	
	public String upload(String fileName, File file) {
		return fsProvider.upload(new UploadObject(fileName, file));
	}
	
	public String upload(String fileName, File file,String catalog) {
		return fsProvider.upload(new UploadObject(fileName, file).toCatalog(catalog));
	}

	public String upload(String fileName,byte[] contents){
		return fsProvider.upload(new UploadObject(fileName, contents));
	}
	
	public String upload(String fileName,byte[] contents,String catalog){
		return fsProvider.upload(new UploadObject(fileName, contents).toCatalog(catalog));
	}

	public String upload(String fileName, InputStream in, String mimeType) {
		return fsProvider.upload(new UploadObject(fileName, in, mimeType));
	}
	
	public String upload(String fileName, InputStream in, String mimeType,String catalog) {
		return fsProvider.upload(new UploadObject(fileName, in, mimeType).toCatalog(catalog));
	}

	public boolean delete(String fileName) {
		return fsProvider.delete(fileName);
	}
	
	public String getDownloadUrl(String file) {
		return fsProvider.getDownloadUrl(file);
	}
	
	public String createUploadToken() {
		return fsProvider.createUploadToken(null, 300);
	}
	
	/**
	 * 
	 * @param metadata 自定义信息
	 * @param expires token过期时间 （秒）
	 * @return
	 */
	public String createUploadToken(Map<String, Object> metadata, long expires) {
		return fsProvider.createUploadToken(metadata, expires);
	}
	
	public String createUploadToken(Map<String, Object> metadata, long expires,String fileKey) {
		return fsProvider.createUploadToken(metadata, expires,fileKey);
	}
	
	public static void main(String[] args) throws Exception {
		FileInputStream inputStream = new FileInputStream("/Users/jiangwei/qiniu.properties");
		Properties properties = new Properties();
		properties.load(inputStream);
		ResourceUtils.merge(properties);
		String url = getPrivateClient().upload("test1.txt", new File("/Users/jiangwei/Desktop/homepage.txt"),"testdir");
		String downloadUrl = getPrivateClient().getDownloadUrl(url);
		System.out.println(downloadUrl);
		
	}
}
