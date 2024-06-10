package cn.pepedd.hpoj.common.upload;

import org.springframework.web.multipart.MultipartFile;

/**
 * OSS文件上传接口
 *
 * @author pepedd864
 * @since 2024/5/31
 */
public interface FileUpload {
  /**
   * 上传文件
   *
   * @param file
   * @return
   */
  String[] upload(MultipartFile... file);

  /**
   * 删除文件
   *
   * @param fileName
   * @return
   */
  Boolean delete(String... fileName);
}
