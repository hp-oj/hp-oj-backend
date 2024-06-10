package cn.pepedd.hpoj.service;

import cn.pepedd.hpoj.entity.dto.RegisterBodyDTO;
import cn.pepedd.hpoj.entity.dto.UserUpdateDTO;
import cn.pepedd.hpoj.entity.pojo.SysUser;
import cn.pepedd.hpoj.entity.result.R;
import cn.pepedd.hpoj.entity.vo.UserVO;

/**
 * 用户相关接口
 *
 * @author pepedd864
 * @since 2023/11/24
 */
public interface IUserService extends IAuthService<SysUser> {
  /**
   * 注册
   *
   * @param registerBodyDTO
   * @return
   */
  R<Boolean> register(RegisterBodyDTO registerBodyDTO);

  /**
   * 更新用户信息
   * @param userUpdateDTO
   * @return
   */
  R<Boolean> updateInfo(UserUpdateDTO userUpdateDTO);

  /**
   * 获取用户信息
   *
   * @return
   */
  R<UserVO> getInfo();
}
