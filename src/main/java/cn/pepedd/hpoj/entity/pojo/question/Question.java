package cn.pepedd.hpoj.entity.pojo.question;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import cn.pepedd.hpoj.entity.dto.question.JudgeCase;
import cn.pepedd.hpoj.entity.dto.question.JudgeConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 题目
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Question implements Serializable {
  private Long id;
  private String title;
  private String content;
  private List<String> tags;
  private String answer;
  private Integer submitNum;
  private Integer acceptedNum;
  private List<JudgeCase> judgeCase;
  private JudgeConfig judgeConfig;
  private Integer thumbNum;
  private Integer favourNum;
  private Long userId;
  private Date createTime;
  private Date updateTime;
  private Integer deleted;

  private static final long serialVersionUID = 1L;

  /**
   * 使用了 列中的json字符串，需要单独的方法进行转换
   *
   * @param question
   * @return
   */
  public static BizQuestion objToDb(Question question) {
    BizQuestion bizQuestion = new BizQuestion();
    BeanUtils.copyProperties(question, bizQuestion);
    // 对tags、judgeCase、judgeConfig单独处理
    bizQuestion.setTags(JSONUtil.toJsonStr(question.getTags()));
    bizQuestion.setJudgeCase(JSONUtil.toJsonStr(question.getJudgeCase()));
    bizQuestion.setJudgeConfig(JSONUtil.toJsonStr(question.getJudgeConfig()));
    return bizQuestion;
  }

  /**
   * 使用了 列中的json字符串，需要单独的方法进行转换
   *
   * @param bizQuestion
   * @return
   */
  public static Question dbToObj(BizQuestion bizQuestion) {
    Question question = new Question();
    BeanUtils.copyProperties(bizQuestion, question);
    // 对tags、judgeCase、judgeConfig单独处理
    question.setTags(JSONUtil.parseArray(bizQuestion.getTags()).toList(String.class));
    question.setJudgeCase(JSONUtil.parseArray(bizQuestion.getJudgeCase()).toList(JudgeCase.class));
    JSONConfig jsonConfig = JSONConfig.create();
    jsonConfig.setIgnoreNullValue(false);
    question.setJudgeConfig(JSONUtil.parseObj(bizQuestion.getJudgeConfig(), jsonConfig).toBean(JudgeConfig.class));
    return question;
  }
}
