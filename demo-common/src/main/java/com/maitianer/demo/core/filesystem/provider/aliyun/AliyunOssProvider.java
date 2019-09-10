package com.maitianer.demo.core.filesystem.provider.aliyun;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.*;
import com.maitianer.demo.core.filesystem.UploadObject;
import com.maitianer.demo.core.filesystem.provider.AbstractProvider;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.time.DateUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 *
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2017年8月23日
 */
public class AliyunOssProvider extends AbstractProvider {

	public static final String NAME = "aliyun";

	private OSSClient ossClient;
	private String bucketName;
	private String urlPrefix = "/";
	private boolean isPrivate;
	private String accessKey;

	public AliyunOssProvider(String urlPrefix, String endpoint, String bucketName, String accessKey, String secretKey, boolean isPrivate) {

		Validate.notBlank(endpoint, "[endpoint] not defined");
		Validate.notBlank(bucketName, "[bucketName] not defined");
		Validate.notBlank(accessKey, "[accessKey] not defined");
		Validate.notBlank(secretKey, "[secretKey] not defined");
		Validate.notBlank(urlPrefix, "[urlPrefix] not defined");

		ossClient = new OSSClient(endpoint, new DefaultCredentialProvider(accessKey, secretKey), null);
		this.bucketName = bucketName;
		this.urlPrefix = urlPrefix.endsWith("/") ? urlPrefix : (urlPrefix + "/");
		this.isPrivate = isPrivate;
		this.accessKey = accessKey;
		if (!ossClient.doesBucketExist(bucketName)) {
			System.out.println("Creating bucket " + bucketName + "\n");
			ossClient.createBucket(bucketName);
			CreateBucketRequest createBucketRequest= new CreateBucketRequest(bucketName);
			createBucketRequest.setCannedACL(isPrivate ? CannedAccessControlList.Private : CannedAccessControlList.PublicRead);
			ossClient.createBucket(createBucketRequest);
		}
	}

	// TODO 异步上传
	@Override
	public String upload(UploadObject object) {
		try {
			PutObjectRequest request = null;
			if(object.getFile() != null){
				request = new PutObjectRequest(bucketName, object.getFileName(), object.getFile());
			}else if(object.getBytes() != null){
				request = new PutObjectRequest(bucketName, object.getFileName(), new ByteArrayInputStream(object.getBytes()));
			}else if(object.getInputStream() != null){
				request = new PutObjectRequest(bucketName, object.getFileName(), object.getInputStream());
			}else{
				throw new IllegalArgumentException("upload object is NULL");
			}

			PutObjectResult result = ossClient.putObject(request);
			if(result.getResponse() == null){
				return isPrivate ? object.getFileName() : urlPrefix + object.getFileName();
			}
			if(result.getResponse().isSuccessful()){
				return result.getResponse().getUri();
			}else{
				throw new RuntimeException(result.getResponse().getErrorResponseAsString());
			}
		} catch (OSSException e) {
			throw new RuntimeException(e.getErrorMessage());
		}
	}

	@Override
	public InputStream downloadFile(String fileName){
		InputStream ins=null;
		try{
		OSSObject ossObject = ossClient.getObject(new GetObjectRequest(bucketName, fileName));
		ins = ossObject.getObjectContent();
		}catch (OSSException oe){
			oe.printStackTrace();
			System.out.println("Caught an OSSException, which means your request made it to OSS, "
					+ "but was rejected with an error response for some reason.");
			System.out.println("Error Message: " + oe.getErrorMessage());
			System.out.println("Error Code:       " + oe.getErrorCode());
			System.out.println("Request ID:      " + oe.getRequestId());
			System.out.println("Host ID:           " + oe.getHostId());
			System.out.println("Raw Response Error: " + oe.getRawResponseError());
		} catch (ClientException ce) {
			ce.printStackTrace();
			System.out.println("Caught an ClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with OSS, "
					+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ce.getMessage());
		}
		// 读取文件内容。
		return ins;
	}

	@Override
	public Map<String, String> createHtmlUploadToken(Long expireTime){
		try {
			long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
			java.sql.Date expiration = new java.sql.Date(expireEndTime);
			PolicyConditions policyConds = new PolicyConditions();
			policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
			policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, "");

			String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
			byte[] binaryData = postPolicy.getBytes("utf-8");
			String encodedPolicy = BinaryUtil.toBase64String(binaryData);
			String postSignature = ossClient.calculatePostSignature(postPolicy);

			Map<String, String> respMap = new LinkedHashMap<String, String>();
			respMap.put("accessid", accessKey);
			respMap.put("policy", encodedPolicy);
			respMap.put("signature", postSignature);
			respMap.put("host", urlPrefix);
			respMap.put("expire", String.valueOf(expireEndTime / 1000));
			// respMap.put("expire", formatISO8601Date(expiration));
			return respMap;
		} catch (Exception e) {
			// Assert.fail(e.getMessage());
			System.out.println(e.getMessage());
		}
		return new HashMap<>();
	}


	@Override
	public String createUploadToken(Map<String, Object> metadata, long expires, String... fileNames) {
		return null;
	}

	@Override
	public boolean delete(String fileName) {
		ossClient.deleteObject(bucketName, fileName);
		return true;
	}

	@Override
	public String getDownloadUrl(String file) {
		//ObjectAcl objectAcl = ossClient.getObjectAcl(bucketName, key);
		if(isPrivate){
			URL url = ossClient.generatePresignedUrl(bucketName, file, DateUtils.addHours(new Date(), 1));
			return url.toString();
		}
		return urlPrefix + file;
	}


	@Override
	public void close() {
		ossClient.shutdown();
	}

	@Override
	public String name() {
		return NAME;
	}
}
