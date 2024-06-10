package cn.pepedd.hpoj.controller.api;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.pepedd.hpoj.entity.dto.RegisterBodyDTO;
import cn.pepedd.hpoj.entity.dto.UserUpdateDTO;
import cn.pepedd.hpoj.entity.result.R;
import cn.pepedd.hpoj.entity.vo.UserVO;
import cn.pepedd.hpoj.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 用户接口
 *
 * @author pepedd864
 * @since 2023/12/4
 */
@RestController
@RequestMapping("/user")
public class UserController {
  @Resource
  private IUserService userService;

  /**
   * 注册
   *
   * @param registerBodyDTO 注册信息
   * @return 登录消息
   */
  @PostMapping("/register")
  @ApiOperation("注册")
  public R<Boolean> register(@RequestBody @Valid RegisterBodyDTO registerBodyDTO) {
    return userService.register(registerBodyDTO);
  }

  /**
   * 获取用户信息
   *
   * @return
   */
  @SaCheckLogin
  @GetMapping("/getInfo")
  @ApiOperation("获取用户信息")
  public R<UserVO> getInfo() {
    return userService.getInfo();
  }

  /**
   * 更新用户信息
   *
   * @param userUpdateDTO
   * @return
   */
  @SaCheckLogin
  @PostMapping("/updateInfo")
  @ApiOperation("更新用户信息")
  public R<Boolean> updateInfo(@RequestBody @Valid UserUpdateDTO userUpdateDTO) {
    return userService.updateInfo(userUpdateDTO);
  }

  //  @SaCheckRole(UserRoleEnum.ADMIN_ROLE)
  @GetMapping("/test")
  @ApiOperation("测试")
  public R test() {
    return R.success(1L);
  }
}
