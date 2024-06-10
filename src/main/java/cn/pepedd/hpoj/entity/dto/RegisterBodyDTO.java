package cn.pepedd.hpoj.entity.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 注册信息
 *
 * @author pepedd864
 * @since 2023/12/4
 */
@Data
public class RegisterBodyDTO implements Serializable {
  @NotNull(message = "用户名不能为空")
  @Length(min = 4, max = 12, message = "用户名长度为4-12")
  private String username;
  @NotNull(message = "昵称不能为空")
  @Length(min = 4, max = 12, message = "昵称长度为4-12")
  private String nickname;
  @Length(min = 6, max = 15, message = "密码长度为6-15")
  private String password;
  @Pattern(regexp = "^1[3|4|5|7|8][0-9]\\d{8}$", message = "手机号格式不正确")
  private String phone;
  @Email(message = "邮箱格式不正确")
  private String email;
  private String code;
  private String uuid;
}
