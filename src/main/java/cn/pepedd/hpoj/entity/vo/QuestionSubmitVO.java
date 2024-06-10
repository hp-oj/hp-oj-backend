package cn.pepedd.hpoj.entity.vo;

import cn.pepedd.hpoj.judge.codesandbox.model.JudgeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 题目提交
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionSubmitVO implements Serializable {
  private Long id;
  private String language;
//  private String code;
  private JudgeInfo judgeInfo;
  private Integer status; // 判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）
  private Long questionId;
  private Long userId;
  private Date createTime;
  private Date updateTime;
  private static final long serialVersionUID = 1L;
}
