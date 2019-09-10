/**
 * 
 */
package com.maitianer.demo.core.filesystem.provider;

import com.maitianer.demo.common.utils.http.HttpUtils;
import com.maitianer.demo.core.filesystem.FSProvider;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2017年1月7日
 */
public abstract class AbstractProvider implements FSProvider {

	private static final String HTTP_PREFIX = "http://";
	private static final String HTTPS_PREFIX = "https://";

	protected static final String DIR_SPLITTER = "/";
	
	protected String urlPrefix;

	protected String bucketName;

	protected String getFullPath(String file) {
		if(file.startsWith(HTTP_PREFIX) || file.startsWith(HTTPS_PREFIX)){
			return file;
		}
		return urlPrefix + file;
	}
	

	@Override
	public String downloadAndSaveAs(String file, String localSaveDir) {
		return HttpUtils.downloadFile(getDownloadUrl(file), localSaveDir);
	}



	public abstract String name();
}
