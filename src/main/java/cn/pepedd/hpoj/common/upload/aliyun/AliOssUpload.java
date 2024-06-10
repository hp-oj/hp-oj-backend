package cn.pepedd.hpoj.common.upload.aliyun;

import cn.pepedd.hpoj.common.enums.ErrorCode;
import cn.pepedd.hpoj.common.upload.FileUpload;
import cn.pepedd.hpoj.exception.BusinessException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GenericRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.VoidResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.InputStream;

/**
 * 阿里云上传文件接口
 *
 * @author pepedd864
 * @since 2024/5/31
 */
@Component
public class AliOssUpload implements FileUpload {
  @Value("${oss.aliyun.project-name}")
  private String projectName;
  @Value("${oss.aliyun.endpoint}")
  private String endpoint;
  @Value("${oss.aliyun.access-key-id}")
  private String accessKeyId;
  @Value("${oss.aliyun.access-key-secret}")
  private String accessKeySecret;
  @Value("${oss.aliyun.bucket-name}")
  private String bucketName;
  @Value("${oss.aliyun.domain}")
  private String domain;
  private OSS oss;

  /**
   * TODO
   * Spring在AliOssUpload构造器中注入相关属性，而OSS依赖于AliOssUpload的属性
   * 因此需要在AliOssUpload构造完成成为Bean之后再初始化OSS
   * 所以使用@PostConstruct注解
   * 又因为OSS只是AliOssUpload内部需要使用，不需要暴露到Bean池中，所以不需要使用@Bean注解
   */
  @PostConstruct
  public void init() {
    oss = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
  }

  /**
   * 上传文件
   *
   * @param file
   * @return
   */
  @Override
  public String[] upload(MultipartFile... file) {
    // 初始化 urls 数组以存储每个文件的 URL
    String[] urls = new String[file.length];
    for (int i = 0; i < file.length; i++) {
      MultipartFile f = file[i];
      try {
        InputStream inputStream = f.getInputStream();
        String filename = f.getOriginalFilename();
        // 取后缀名
        String suffix = filename.substring(filename.lastIndexOf("."));
        // 使用 时间 生成文件名
        String newFileName = System.currentTimeMillis() + suffix;
        String filePath = projectName + "/" + newFileName;
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filePath, inputStream);
        oss.putObject(putObjectRequest);
        // 将生成的 URL 存储在 urls 数组中
        urls[i] = domain + "/" + projectName + "/" + newFileName;
      } catch (Exception e) {
        throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传文件失败" + e.getMessage());
      }
    }
    // 返回 urls 数组
    return urls;
  }

  /**
   * 删除文件
   *
   * @param fileName
   * @return
   */
  @Override
  public Boolean delete(String... fileName) {
    for (String f : fileName) {
      GenericRequest genericRequest = new GenericRequest(bucketName, projectName + f);
      VoidResult voidResult = oss.deleteObject(genericRequest);
    }
    return true;
  }
}
