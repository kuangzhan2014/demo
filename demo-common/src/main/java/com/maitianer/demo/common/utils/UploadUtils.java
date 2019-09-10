package com.maitianer.demo.common.utils;


import com.maitianer.demo.common.model.FileInfo;
import com.maitianer.demo.common.model.ImageBean;
import com.maitianer.demo.common.spring.SpringContextHolder;
import com.maitianer.demo.core.filesystem.spring.FSProviderSpringFacade;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

/**
 * 上传辅助类
 *
 * @author Leo
 */
public final class UploadUtils {

    private UploadUtils() {}

    private static final Logger logger = LoggerFactory.getLogger(UploadUtils.class);

    /**
     * 上传文件缓存大小限制
     */
    private static int fileSizeThreshold = 1024 * 1024 * 1;
    /**
     * 上传文件临时目录
     */
    private static final String uploadFileDir = "/WEB-INF/upload/";

    /**
     * 获取所有文本域
     */
    public static final List<?> getFileItemList(HttpServletRequest request, File saveDir) throws FileUploadException {
        if (!saveDir.isDirectory()) {
            saveDir.mkdir();
        }
        List<?> fileItems = null;
        RequestContext requestContext = new ServletRequestContext(request);
        if (FileUpload.isMultipartContent(requestContext)) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(saveDir);
            factory.setSizeThreshold(fileSizeThreshold);
            ServletFileUpload upload = new ServletFileUpload(factory);
            fileItems = upload.parseRequest(request);
        }
        return fileItems;
    }

    /**
     * 获取文本域
     */
    public static final FileItem[] getFileItem(HttpServletRequest request, File saveDir, String... fieldName)
            throws FileUploadException {
        if (fieldName == null || saveDir == null) {
            return null;
        }
        List<?> fileItemList = getFileItemList(request, saveDir);
        FileItem fileItem = null;
        FileItem[] fileItems = new FileItem[fieldName.length];
        for (int i = 0; i < fieldName.length; i++) {
            for (Iterator<?> iterator = fileItemList.iterator(); iterator.hasNext(); ) {
                fileItem = (FileItem) iterator.next();
                // 根据名字获得文本域
                if (fieldName[i] != null && fieldName[i].equals(fileItem.getFieldName())) {
                    fileItems[i] = fileItem;
                    break;
                }
            }
        }
        return fileItems;
    }

    /**
     * 上传文件处理(支持批量)
     */
    public static List<String> uploadFile(HttpServletRequest request) {
        List<String> fileNames = new ArrayList<>();
        List<FileInfo> files = uploadFiles(request);
        for (FileInfo fileInfo : files) {
            fileNames.add(fileInfo.getFileName());
        }
        return fileNames;
    }

    /**
     * 上传文件处理(支持批量)
     */
    public static List<FileInfo> uploadFiles(HttpServletRequest request) {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        List<FileInfo> fileNames = new ArrayList<>();
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            String pathDir = getUploadDir(request);
            File dirFile = new File(pathDir);
            if (!dirFile.isDirectory()) {
                dirFile.mkdirs();
            }
            for (Iterator<String> iterator = multiRequest.getFileNames(); iterator.hasNext(); ) {
                String key = iterator.next();
                MultipartFile multipartFile = multiRequest.getFile(key);
                if (multipartFile != null) {
                    FileInfo fileInfo = new FileInfo();
                    String name = multipartFile.getOriginalFilename();
                    fileInfo.setOrgName(name);
                    if (name.indexOf(".") == -1 && "blob".equals(name)) {
                        name = name + ".png";
                    }
                    String uuid = UUID.randomUUID().toString();
                    String postFix = name.substring(name.lastIndexOf(".")).toLowerCase();
                    String fileName = uuid + postFix;
                    String filePath = pathDir + File.separator + fileName;
                    File file = new File(filePath);
                    file.setWritable(true, false);
                    fileInfo.setFileSize(multipartFile.getSize());
                    try {
                        multipartFile.transferTo(file);
                        fileInfo.setFileName(fileName);
                        fileNames.add(fileInfo);
                    } catch (Exception e) {
                        logger.error(name + "保存失败", e);
                    }
                }
            }
        }
        return fileNames;
    }

    /**
     * 上传文件处理(支持批量)
     */
    public static List<String> uploadImage(HttpServletRequest request, boolean lessen) {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        List<String> fileNames = new ArrayList<>();
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            String pathDir = getUploadDir(request);
            File dirFile = new File(pathDir);
            if (!dirFile.isDirectory()) {
                dirFile.mkdirs();
            }
            for (Iterator<String> iterator = multiRequest.getFileNames(); iterator.hasNext(); ) {
                String key = iterator.next();
                MultipartFile multipartFile = multiRequest.getFile(key);
                if (multipartFile != null) {
                    String name = multipartFile.getOriginalFilename();
                    if (name.indexOf(".") == -1 && "blob".equals(name)) {
                        name = name + ".png";
                    }
                    String uuid = UUID.randomUUID().toString();
                    String postFix = name.substring(name.lastIndexOf(".")).toLowerCase();
                    String fileName = uuid + postFix;
                    String filePath = pathDir + File.separator + fileName;
                    File file = new File(filePath);
                    file.setWritable(true, false);
                    try {
                        multipartFile.transferTo(file);
                        fileNames.add(fileName);
                    } catch (Exception e) {
                        logger.error(name + "保存失败", e);
                    }
                    if (lessen) {
                        try { // 缩放
                            BufferedImage bufferedImg = ImageIO.read(file);
                            int orgwidth = bufferedImg.getWidth();// 原始宽度
                            ImageUtils.scaleWidth(file, 100);
                            if (orgwidth > 300) {
                                ImageUtils.scaleWidth(file, 300);
                            }
                            if (orgwidth > 500) {
                                ImageUtils.scaleWidth(file, 500);
                            }
                        } catch (Exception e) {
                            logger.error(name + "缩放失败", e);
                        }
                    }
                }
            }
        }
        return fileNames;
    }

    /**
     * 上传文件处理(支持批量)--以流的形式
     */
    public static List<String> uploadImageByInputStream(HttpServletRequest request, boolean lessen) {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        List<String> fileNames = new ArrayList<>();
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            String pathDir = getUploadDir(request);
            File dirFile = new File(pathDir);
            if (!dirFile.isDirectory()) {
                dirFile.mkdirs();
            }
            for (Iterator<String> iterator = multiRequest.getFileNames(); iterator.hasNext(); ) {
                String key = iterator.next();
                MultipartFile multipartFile = multiRequest.getFile(key);
                if (multipartFile != null) {
                    String name = multipartFile.getOriginalFilename();
                    if (name.indexOf(".") == -1 && "blob".equals(name)) {
                        name = name + ".png";
                    }
                    String uuid = UUID.randomUUID().toString();
                    String postFix = name.substring(name.lastIndexOf(".")).toLowerCase();
                    String fileName = Uploader.getQuickPathname(Uploader.IMAGE, postFix);
                    try {
                        InputStream is = multipartFile.getInputStream();
                        remove2Aliyun(is,fileName);
                        fileNames.add(fileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return fileNames;
    }

    /**
     * 上传文件处理(支持批量)--以流的形式
     */
    public static String uploadImageOnApi(MultipartFile image) {
        if (image != null) {
            String name = image.getOriginalFilename();
            if (name.indexOf(".") == -1) {
                name = name + imgTypeValid(image.getContentType());
            }
            String postFix = name.substring(name.lastIndexOf(".")).toLowerCase();
            String fileName = Uploader.getQuickPathname(Uploader.IMAGE,postFix);
            try {
                InputStream is = image.getInputStream();
                remove2Aliyun(is, fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return fileName;
        }
        return null;
    }

    public static void downloadFile(String fileName, HttpServletResponse response){
        InputStream ins = downloadFileOnAliyun(fileName);
        BufferedOutputStream bos = null;
        try {
            byte[] buff = new byte[2048];
            int bytesRead;
            // 清空response
            response.reset();
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment;filename="+fileName);
            bos = new BufferedOutputStream(response.getOutputStream());
            while (-1 != (bytesRead = ins.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 上传富文本图片：返回一定格式
     * {
     *   "code": 0 //0表示成功，其它失败
     *   ,"msg": "" //提示信息 //一般上传失败后返回
     *   ,"data": {
     *     "src": "图片路径"
     *     ,"title": "图片名称" //可选
     *   }
     * }
     * 上传文件处理(支持批量)--以流的形式
     */
    public static ImageBean uploadRichTextImageByInputStream(HttpServletRequest request, boolean lessen) {
        ImageBean imageBean = new ImageBean();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            String pathDir = getUploadDir(request);
            File dirFile = new File(pathDir);
            if (!dirFile.isDirectory()) {
                dirFile.mkdirs();
            }
            for (Iterator<String> iterator = multiRequest.getFileNames(); iterator.hasNext(); ) {
                String key = iterator.next();
                MultipartFile multipartFile = multiRequest.getFile(key);
                if (multipartFile != null) {
                    String name = multipartFile.getOriginalFilename();
                    if (name.indexOf(".") == -1 && "blob".equals(name)) {
                        name = name + ".png";
                    }
                    String uuid = UUID.randomUUID().toString();
                    String postFix = name.substring(name.lastIndexOf(".")).toLowerCase();
                    String fileName = Uploader.getQuickPathname(Uploader.IMAGE, postFix);
                    try {
                        InputStream is = multipartFile.getInputStream();
                        remove2Aliyun(is,fileName);
                        imageBean.setTitle(fileName); // 设置图片名称
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return imageBean;
    }

    public static List<String> uploadImageData(HttpServletRequest request) {
        List<String> fileNames = new ArrayList<>();
        Enumeration<String> params = request.getParameterNames();
        String pathDir = getUploadDir(request);
        File dir = new File(pathDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        while (params.hasMoreElements()) {
            String key = params.nextElement();
            String fileStr = request.getParameter(key);
            if (fileStr != null && !"".equals(fileStr)) {
                int index = fileStr.indexOf("base64");
                if (index > 0) {
                    try {
                        String fileName = UUID.randomUUID().toString();
                        String preStr = fileStr.substring(0, index + 7);
                        String prefix = preStr.substring(preStr.indexOf("/") + 1, preStr.indexOf(";")).toLowerCase();
                        fileStr = fileStr.substring(fileStr.indexOf(",") + 1);
                        byte[] bb = Base64.getDecoder().decode(fileStr);
                        for (int j = 0; j < bb.length; ++j) {
                            if (bb[j] < 0) {// 调整异常数据
                                bb[j] += 256;
                            }
                        }
                        String distPath = pathDir + fileName + "." + prefix;
                        OutputStream out = new FileOutputStream(distPath);
                        out.write(bb);
                        out.flush();
                        out.close();
                        fileNames.add(fileName + "." + prefix);
                    } catch (Exception e) {
                        logger.error("上传文件异常：", e);
                    }
                }
            }
        }
        return fileNames;
    }

    /**
     * 获取上传文件临时目录
     */
    public static String getUploadDir(HttpServletRequest request) {
        return request.getServletContext().getRealPath(uploadFileDir) + File.separator;
    }

    /** 移动文件到fastDFS */
//    public static FileModel remove2DFS(String namespace, String objectId, String fileName) {
//        FileModel fastDFSFile = new FileModel(namespace, objectId, fileName);
//        if (fastDFSFile.getKey() != null) {
//            FileManager.getInstance().upload(fastDFSFile);
//        }
//        return fastDFSFile;
//    }

    /** 移动文件到SFTP */
//    public static String remove2Sftp(String filePath, String namespace) {
//        File file = new File(filePath);
//        if (!file.exists()) {
//            throw new RuntimeException("文件" + filePath + "不存在");
//        }
//        SftpClient client = SftpClient.connect();
//        String dir = PropertiesUtil.getString("sftp.baseDir");
//        String fileName = namespace + File.separator + file.getName();
//        client.put(filePath, dir + fileName);
//        client.disconnect();
//        return PropertiesUtil.getString("sftp.nginx.path") + fileName;
//    }

    /**
     * 迁移文件到阿里云
     * @param filePath
     * @param objectKey
     * @return
     */
    public static String remove2Aliyun(String filePath, String objectKey) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("文件" + filePath + "不存在");
        }
        FSProviderSpringFacade fsProviderSpringFacade = SpringContextHolder.getBean("aliyunFSProvider");
        if (fsProviderSpringFacade == null) {
            throw new RuntimeException("未找到aliyunFSProvider！");
        }

        return fsProviderSpringFacade.upload(objectKey, file);
    }


    /**
     * 已文件流的形式上传到阿里云
     * @param objectKey
     * @return
     */
    public static String remove2Aliyun(InputStream inputStream, String objectKey) {
        FSProviderSpringFacade fsProviderSpringFacade = SpringContextHolder.getBean("aliyunFSProvider");
        if (fsProviderSpringFacade == null) {
            throw new RuntimeException("未找到aliyunFSProvider！");
        }
        return fsProviderSpringFacade.upload(objectKey, inputStream,"image/png");
    }

    /**
     * 已文件流的形式从阿里云上下载
     * @return
     */
    public static InputStream downloadFileOnAliyun(String file) {
        FSProviderSpringFacade fsProviderSpringFacade = SpringContextHolder.getBean("aliyunFSProvider");
        if (fsProviderSpringFacade == null) {
            throw new RuntimeException("未找到aliyunFSProvider！");
        }
        return fsProviderSpringFacade.downloadFile(file);
    }


    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     *
     * @param contentType 文件contentType
     * @return String
     */
    public static String imgTypeValid(String contentType ) {
        if ("image/jpeg;".equalsIgnoreCase(contentType )) {
            return ".jpeg";
        } else if ("image/jpg;".equalsIgnoreCase(contentType )) {
            return ".jpg";
        } else if ("image/gif;".equalsIgnoreCase(contentType )) {
            return ".gif";
        } else if ("image/png;".equalsIgnoreCase(contentType )) {
            return ".png";
        } else if ("image/apng;".equalsIgnoreCase(contentType )) {
            return ".apng";
        } else if ("image/svg;".equalsIgnoreCase(contentType )) {
            return ".svg";
        } else if ("image/bmp;".equalsIgnoreCase(contentType )) {
            return ".bmp";
        }
        return ".png";
    }
}
