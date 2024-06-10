package cn.pepedd.hpoj.judge.codesandbox.model;

import lombok.Builder;
import lombok.Data;

/**
 * TODO
 *
 * @author pepedd864
 * @since 2024/6/8
 */
@Data
@Builder
public class JudgeInfo {
  /**
   * 执行消息
   */
  private String message;

  /**
   * 消耗内存
   */
  private Long memory;

  /**
   * 消耗时间
   */
  private Long time;
}
