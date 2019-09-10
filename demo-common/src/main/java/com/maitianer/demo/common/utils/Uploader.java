package com.maitianer.demo.common.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 上传工具类
 * 
 * @author Mr.Zhang
 * 
 */
public class Uploader {
	/**
	 * 文件
	 */
	public static final String FILE = "file";
	/**
	 * 图片
	 */
	public static final String IMAGE = "image";
	/**
	 * 视频
	 */
	public static final String VIDEO = "video";
	/**
	 * 音频
	 */
	public static final String AUDIO = "audio";

	/**
	 * 头像
	 */
	public static final String AVATAR = "avatar";

	/**
	 * 快速上传文件夹
	 */
	public static final String QUICK_UPLOAD = "";

	public static String randomPathname(String pattern, String extension) {
		StringBuilder filename = new StringBuilder();
		DateFormat df = new SimpleDateFormat(pattern);
		filename.append(df.format(new Date()));
		filename.append(RandomStringUtils.random(10, '0', 'Z', true, true)
				.toLowerCase());
		if (StringUtils.isNotBlank(extension)) {
			if (extension.startsWith(".")) {
				filename.append(extension.toLowerCase());
			}else {
				filename.append(".").append(extension.toLowerCase());
			}

		}
		return filename.toString();
	}

	public static String randomPathname(String extension) {
		return randomPathname("/yyyyMM/yyyyMMddHHmmss_", extension);
	}

	public static String getQuickPathname(String type, String extension) {
		StringBuilder name = new StringBuilder();
//		name.append('/').append(type);
		name.append(type);
		if (StringUtils.isNotBlank(QUICK_UPLOAD)) {
			name.append('/').append(QUICK_UPLOAD);
		}
		name.append(Uploader.randomPathname(extension));
		return name.toString();
	}
}
