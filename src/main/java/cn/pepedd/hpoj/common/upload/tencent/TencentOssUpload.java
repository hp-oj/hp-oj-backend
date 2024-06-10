package cn.pepedd.hpoj.common.upload.tencent;

import cn.pepedd.hpoj.common.upload.FileUpload;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * TODO
 *
 * @author pepedd864
 * @since 2024/5/31
 */
@Component
public class TencentOssUpload implements FileUpload {
  /**
   * 上传文件
   *
   * @param file
   * @return
   */
  @Override
  public String[] upload(MultipartFile... file) {
    return null;
  }

  /**
   * 删除文件
   *
   * @param fileName
   * @return
   */
  @Override
  public Boolean delete(String... fileName) {
    return null;
  }
}
