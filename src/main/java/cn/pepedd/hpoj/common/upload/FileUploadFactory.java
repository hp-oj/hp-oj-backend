package cn.pepedd.hpoj.common.upload;

import cn.pepedd.hpoj.common.upload.aliyun.AliOssUpload;
import cn.pepedd.hpoj.common.upload.tencent.TencentOssUpload;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 文件上传工厂类，获取各种文件上传实现类
 *
 * @author pepedd864
 * @since 2024/5/31
 */
@Component
public class FileUploadFactory {
  @Resource
  private AliOssUpload aliOssUpload;
  @Resource
  private TencentOssUpload tencentOssUpload;

  public FileUploadProxy newInstance(String type) {
    switch (type) {
      case "tencent":
        return new FileUploadProxy(tencentOssUpload);
      case "aliyun":
      default:
        return new FileUploadProxy(aliOssUpload);
    }
  }
}
