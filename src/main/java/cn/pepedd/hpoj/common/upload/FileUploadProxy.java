package cn.pepedd.hpoj.common.upload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传代理类，用于记录日志
 *
 * @author pepedd864
 * @since 2024/5/31
 */
@Slf4j
public class FileUploadProxy implements FileUpload {
  private FileUpload fileUpload;

  public FileUploadProxy(FileUpload fileUpload) {
    this.fileUpload = fileUpload;
  }

  /**
   * 上传文件
   *
   * @param file
   * @return
   */
  @Override
  public String[] upload(MultipartFile... file) {
    log.info("上传文件...");
    String[] upload = fileUpload.upload(file);
    for (String s : upload) {
      log.info("上传文件成功，文件名：{}", s);
    }
    return upload;
  }

  /**
   * 删除文件
   *
   * @param fileName
   * @return
   */
  @Override
  public Boolean delete(String... fileName) {
    log.info("删除文件...");
    Boolean delete = fileUpload.delete(fileName);
    if (delete) {
      log.info("删除文件成功，文件名：{}", fileName);
    }
    return delete;
  }
}
