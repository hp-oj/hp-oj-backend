package cn.pepedd.hpoj.entity.dto.question;

import lombok.Data;

/**
 * TODO
 *
 * @author pepedd864
 * @since 2024/6/8
 */
@Data
public class JudgeConfig {
  /**
   * 时间限制（ms）
   */
  private Long timeLimit;

  /**
   * 内存限制（KB）
   */
  private Long memoryLimit;

  /**
   * 堆栈限制（KB）
   */
  private Long stackLimit;
}
