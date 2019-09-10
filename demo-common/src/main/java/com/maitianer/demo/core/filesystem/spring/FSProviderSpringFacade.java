/**
 *
 */
package com.maitianer.demo.core.filesystem.spring;

import com.maitianer.demo.core.filesystem.FSProvider;
import com.maitianer.demo.core.filesystem.UploadObject;
import com.maitianer.demo.core.filesystem.provider.aliyun.AliyunOssProvider;
import com.maitianer.demo.core.filesystem.provider.qiniu.QiniuProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

/**
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @description <br>
 * @date 2017年1月7日
 */
public class FSProviderSpringFacade implements InitializingBean, DisposableBean {

    private FSProvider fsProvider;
    String endpoint;
    String provider;
    String groupName;
    String accessKey;
    String secretKey;
    String urlPrefix;
    String servers;
    long connectTimeout = 3000;
    int maxThreads = 50;
    boolean privated;


    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    public void setServers(String servers) {
        this.servers = servers;
    }

    public void setPrivated(boolean privated) {
        this.privated = privated;
    }

    @Override
    public void destroy() throws Exception {
        fsProvider.close();
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        if (QiniuProvider.NAME.equals(provider)) {
            Validate.notBlank(accessKey, "[accessKey] not defined");
            Validate.notBlank(secretKey, "[secretKey] not defined");
            fsProvider = new QiniuProvider(urlPrefix, groupName, accessKey, secretKey, privated);
        } else if (AliyunOssProvider.NAME.equals(provider)) {
            Validate.notBlank(endpoint, "[endpoint] not defined");
            fsProvider = new AliyunOssProvider(urlPrefix, endpoint, groupName, accessKey, secretKey, privated);
        } else {
            throw new RuntimeException("Provider[" + provider + "] not support");
        }
    }

    public String upload(String fileName, File file) {
        return fsProvider.upload(new UploadObject(fileName, file));
    }

    public String upload(String fileName, InputStream in, String mimeType) {
        return fsProvider.upload(new UploadObject(fileName, in, mimeType));
    }

    public String getDownloadUrl(String file) {
        if (StringUtils.isNotBlank(file) && !file.startsWith("http")) {
            return fsProvider.getDownloadUrl(file);
        } else {
            return file;
        }
    }

    public InputStream downloadFile(String file){
        return fsProvider.downloadFile(file);
    }

    public boolean delete(String fileName) {
        return fsProvider.delete(fileName);
    }

    public String createUploadToken(Map<String, Object> metadata, long expires, String... fileNames) {
        return fsProvider.createUploadToken(metadata, expires, fileNames);
    }

    public void setConnectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
    public Map<String, String> createHtmlUploadToken(Long expireTime){
        return fsProvider.createHtmlUploadToken(expireTime);
    }

}
