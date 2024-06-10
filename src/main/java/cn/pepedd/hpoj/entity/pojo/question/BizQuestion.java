package cn.pepedd.hpoj.entity.pojo.question;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import cn.pepedd.hpoj.entity.dto.question.JudgeCase;
import cn.pepedd.hpoj.entity.dto.question.JudgeConfig;
import cn.pepedd.hpoj.entity.dto.question.QuestionDTO;
import cn.pepedd.hpoj.entity.vo.QuestionVO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 题目
 *
 * @TableName biz_question
 */
@TableName(value = "biz_question")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BizQuestion implements Serializable {
  /**
   * id
   */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private Long id;

  /**
   * 标题
   */
  @TableField(value = "title")
  private String title;

  /**
   * 内容
   */
  @TableField(value = "content")
  private String content;

  /**
   * 标签列表（json 数组）
   * 在实体类上还是要用string类型，否则报错 TODO
   */
  @TableField(value = "tags")
  private String tags;

  /**
   * 题目答案
   */
  @TableField(value = "answer")
  private String answer;

  /**
   * 题目提交数
   */
  @TableField(value = "submit_num")
  private Integer submitNum;

  /**
   * 题目通过数
   */
  @TableField(value = "accepted_num")
  private Integer acceptedNum;

  /**
   * 判题用例（json 数组）
   * 在实体类上还是要用string类型，否则报错 TODO
   */
  @TableField(value = "judge_case")
  private String judgeCase;

  /**
   * 判题配置（json 对象）
   * 在实体类上还是要用string类型，否则报错 TODO
   */
  @TableField(value = "judge_config")
  private String judgeConfig;

  /**
   * 点赞数
   */
  @TableField(value = "thumb_num")
  private Integer thumbNum;

  /**
   * 收藏数
   */
  @TableField(value = "favour_num")
  private Integer favourNum;

  /**
   * 创建用户 id
   */
  @TableField(value = "user_id")
  private Long userId;

  /**
   * 创建时间
   */
  @TableField(value = "create_time")
  private Date createTime;

  /**
   * 更新时间
   */
  @TableField(value = "update_time")
  private Date updateTime;

  /**
   * 是否删除
   */
  @TableField(value = "deleted")
  private Integer deleted;

  private static final long serialVersionUID = 1L;

  /**
   * 使用了 列中的json字符串，需要单独的方法进行dto->pojo->vo的转换
   *
   * @param questionDTO
   * @return
   */
  public static BizQuestion dtoToPojo(QuestionDTO questionDTO) {
    BizQuestion bizQuestion = new BizQuestion();
    BeanUtils.copyProperties(questionDTO, bizQuestion);
    // 对tags、judgeCase、judgeConfig单独处理
    bizQuestion.setTags(JSONUtil.toJsonStr(questionDTO.getTags()));
    bizQuestion.setJudgeCase(JSONUtil.toJsonStr(questionDTO.getJudgeCase()));
    bizQuestion.setJudgeConfig(JSONUtil.toJsonStr(questionDTO.getJudgeConfig()));
    return bizQuestion;
  }
  /**
   * 使用了 列中的json字符串，需要单独的方法进行dto->pojo->vo的转换
   *
   * @param bizQuestion
   * @return
   */
  public static QuestionVO pojoToVo(BizQuestion bizQuestion) {
    QuestionVO questionVO = new QuestionVO();
    BeanUtils.copyProperties(bizQuestion, questionVO);
    // 对tags、judgeCase、judgeConfig单独处理
    questionVO.setTags(JSONUtil.parseArray(bizQuestion.getTags()).toList(String.class));
    questionVO.setJudgeCase(JSONUtil.parseArray(bizQuestion.getJudgeCase()).toList(JudgeCase.class));
    JSONConfig jsonConfig = JSONConfig.create();
    jsonConfig.setIgnoreNullValue(false);
    questionVO.setJudgeConfig(JSONUtil.parseObj(bizQuestion.getJudgeConfig(), jsonConfig).toBean(JudgeConfig.class));
    return questionVO;
  }
}
