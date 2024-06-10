package cn.pepedd.hpoj.common.satoken;

import cn.dev33.satoken.stp.StpInterface;
import cn.pepedd.hpoj.entity.pojo.SysUser;
import cn.pepedd.hpoj.service.IUserService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 自定义权限加载接口实现类
 */
@Component
public class StpInterfaceImpl implements StpInterface {
  @Resource
  private IUserService userService;

  /**
   * 返回一个账号所拥有的权限码集合
   */
  @Override
  public List<String> getPermissionList(Object loginId, String loginType) {
    // TODO 可自定义权限规则 通过loginId获取用户权限
    return null;
  }

  /**
   * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
   */
  @Override
  public List<String> getRoleList(Object loginId, String loginType) {
    // 1. 查询用户
    SysUser user = userService.getOne(Wrappers.<SysUser>lambdaQuery()
        .eq(SysUser::getId, loginId)
    );
    // 2. 查询用户角色
    String role = user.getRole();
    if (role.equals("admin")) {
      return Arrays.asList("admin", "user");
    }
    if (role.equals("user")) {
      return Arrays.asList("user");
    }
    return null;
  }

}
