package cn.pepedd.hpoj.controller.api;

import cn.pepedd.hpoj.common.upload.FileUploadFactory;
import cn.pepedd.hpoj.common.upload.FileUploadProxy;
import cn.pepedd.hpoj.entity.result.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 公共接口
 *
 * @author pepedd864
 * @since 2024/5/31
 */
@RestController
@RequestMapping("/common")
public class CommonController {
  @Resource
  private FileUploadFactory fileUploadFactory;

  /**
   * 文件上传
   *
   * @param file 文件
   * @return 文件路径
   */
  @ApiOperation("文件上传")
  @PostMapping("/upload")
  public R<String> upload(@RequestParam("file") MultipartFile file) {
    FileUploadProxy fileUploadProxy = fileUploadFactory.newInstance("aliyun");
    String[] upload = fileUploadProxy.upload(file);
    return R.success(upload[0]);
  }
}
