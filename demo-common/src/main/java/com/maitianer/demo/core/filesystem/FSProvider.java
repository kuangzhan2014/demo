/**
 * 
 */
package com.maitianer.demo.core.filesystem;

import java.io.Closeable;
import java.io.InputStream;
import java.util.Map;

/**
 * 上传接口
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2017年1月5日
 */
public interface FSProvider extends Closeable {

	/**
	 * 文件上传
	 * @param object
	 * @return
	 */
	String upload(UploadObject object);
	/**
	 * 获取文件下载地址
	 * @param file 文件（全路径或者fileKey）
	 * @return
	 */
	String getDownloadUrl(String file);
	
	/**
	 * 删除图片
	 * @return
	 */
	boolean delete(String fileName);
	
	
	String createUploadToken(Map<String, Object> metadata, long expires, String... fileNames);
	
	String downloadAndSaveAs(String file, String localSaveDir);

	InputStream downloadFile(String file);

	/**
	 * 用于网页上传 发放临时token
	 * @param expireTime  token超时时间 （单位：S）
	 * @return
	 */
	Map<String, String> createHtmlUploadToken(Long expireTime);
}
