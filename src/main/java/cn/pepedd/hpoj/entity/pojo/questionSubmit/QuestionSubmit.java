package cn.pepedd.hpoj.entity.pojo.questionSubmit;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import cn.pepedd.hpoj.judge.codesandbox.model.JudgeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 题目提交
 *
 * @TableName biz_question_submit
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionSubmit implements Serializable {
  private Long id;
  private String language;
  private String code;
  private JudgeInfo judgeInfo;
  private Integer status;
  private Long questionId;
  private Long userId;
  private Date createTime;
  private Date updateTime;
  private Integer deleted;
  private static final long serialVersionUID = 1L;

  /**
   * 使用了 列中的json字符串，需要单独的方法进行转换
   *
   * @param questionSubmit
   * @return
   */
  public static BizQuestionSubmit objToDb(QuestionSubmit questionSubmit) {
    BizQuestionSubmit bizQuestionSubmit = new BizQuestionSubmit();
    BeanUtils.copyProperties(questionSubmit, bizQuestionSubmit);
    // 对judgeInfo单独处理
    bizQuestionSubmit.setJudgeInfo(JSONUtil.toJsonStr(questionSubmit.getJudgeInfo()));
    return bizQuestionSubmit;
  }

  /**
   * 使用了 列中的json字符串，需要单独的方法进行转换
   *
   * @param bizQuestionSubmit
   * @return
   */
  public static QuestionSubmit dbToObj(BizQuestionSubmit bizQuestionSubmit) {
    QuestionSubmit questionSubmit = new QuestionSubmit();
    BeanUtils.copyProperties(bizQuestionSubmit, questionSubmit);
    // 对judgeInfo单独处理
    JSONConfig jsonConfig = JSONConfig.create();
    jsonConfig.setIgnoreNullValue(false);
    questionSubmit.setJudgeInfo(JSONUtil.parseObj(bizQuestionSubmit.getJudgeInfo(), jsonConfig).toBean(JudgeInfo.class));
    return questionSubmit;
  }
}
