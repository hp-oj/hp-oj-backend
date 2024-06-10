package cn.pepedd.hpoj.entity.vo;

import cn.pepedd.hpoj.entity.dto.question.JudgeCase;
import cn.pepedd.hpoj.entity.dto.question.JudgeConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 题目
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionVO implements Serializable {
  private Long id;
  private String title;
  private String content;
  private List<String> tags;
  private Integer submitNum;
  private Integer acceptedNum;
  private List<JudgeCase> judgeCase;
  private JudgeConfig judgeConfig;
  private Integer thumbNum;
  private Integer favourNum;
  private Long userId;
  private Date createTime;
  private Date updateTime;
  private static final long serialVersionUID = 1L;
}
