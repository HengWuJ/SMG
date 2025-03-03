package com.smg.user.util;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.PutObjectResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class OssUtil {

    private static String endPoint = "";                          // oss域名
    private static String accessKeyId = "";                    // accessKeyId
    private static String accessKeySecret = "";            // accessKeySecret
    private static String bucketName = "";                      // 桶名称
    private static String fileDir = "/img";                                // 当前文件所属文件夹(就是你想给文件传到那个文件夹下面)

    //log日志对象
    static Log log = LogFactory.getLog(OssUtil.class);
    /**
     * oss 工具客户端
     */
    private static OSSClient ossClient = null;

    /**
     * 上传文件至阿里云 OSS
     * @param file 待上传文件
     * 返回访问的全路径
     */
    public static String uploadFileToOss(MultipartFile file) {
        // 初始化oss
        initOSS(endPoint, accessKeyId, accessKeySecret);
        String visitUrl = null;
        try {
            // 获取文件名
            String orgName = file.getOriginalFilename();
            if (StringUtils.isEmpty(orgName)) {
                orgName = file.getName();
            }
            orgName = getFileName(orgName);
            //上传后的文件名 进行修饰
            String fileRelName = fileDir + orgName.substring(0, orgName.lastIndexOf(".")) + "_" + System.currentTimeMillis() + orgName.substring(orgName.indexOf("."));
            // 上传至oss
            PutObjectResult result = ossClient.putObject(bucketName, fileRelName, file.getInputStream());
            if (result != null) {
                log.info("------OSS文件上传成功------");
            }
            // 拼接访问路径
            visitUrl = "https://" + bucketName + "." + endPoint + "/" + fileRelName;

            System.out.println("visit url : " + visitUrl);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
        return visitUrl;
    }

    /**
     * 删除文件
     * @param fileName
     */
    public static void deleteFileToOss(String fileName) {
        // 初始化oss
        initOSS(endPoint, accessKeyId, accessKeySecret);
        ossClient.deleteObject(bucketName, fileName);
    }

    /**
     * 初始化 oss 客户端
     * @return
     */
    private static OSSClient initOSS(String endpoint, String accessKeyId, String accessKeySecret) {
        if (ossClient == null) {
            synchronized (OSSClient.class) {
                if (ossClient == null) {
                    ossClient = new OSSClient(endpoint, new DefaultCredentialProvider(accessKeyId, accessKeySecret), new ClientConfiguration());
                    // 设置权限(公开读)
                    ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
                }
            }
        }
        return ossClient;
    }

    /**
     * 判断文件名是否带盘符，重新处理
     * @param fileName
     * @return
     */
    public static String getFileName(String fileName){
        //判断是否带有盘符信息
        // Check for Unix-style path
        int unixSep = fileName.lastIndexOf('/');
        // Check for Windows-style path
        int winSep = fileName.lastIndexOf('\\');
        // Cut off at latest possible point
        int pos = (winSep > unixSep ? winSep : unixSep);
        if (pos != -1) {
            // Any sort of path separator found...
            fileName = fileName.substring(pos + 1);
        }
        //替换上传文件名字的特殊字符
        fileName = fileName.replace("=", "").replace(",", "").replace("&", "").replace("#", "");
        return fileName;
    }

    public static String getEndPoint() {
        return endPoint;
    }

    public static void setEndPoint(String endPoint) {
        OssUtil.endPoint = endPoint;
    }

    public static String getAccessKeyId() {
        return accessKeyId;
    }

    public static void setAccessKeyId(String accessKeyId) {
        OssUtil.accessKeyId = accessKeyId;
    }

    public static String getAccessKeySecret() {
        return accessKeySecret;
    }

    public static void setAccessKeySecret(String accessKeySecret) {
        OssUtil.accessKeySecret = accessKeySecret;
    }

    public static String getBucketName() {
        return bucketName;
    }

    public static void setBucketName(String bucketName) {
        OssUtil.bucketName = bucketName;
    }

    public static String getFileDir() {
        return fileDir;
    }

    public static void setFileDir(String fileDir) {
        OssUtil.fileDir = fileDir;
    }

    public static OSSClient getOssClient() {
        return ossClient;
    }

    public static void setOssClient(OSSClient ossClient) {
        OssUtil.ossClient = ossClient;
    }
}