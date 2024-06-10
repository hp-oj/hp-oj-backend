package cn.pepedd.hpoj.entity.pojo.questionSubmit;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import cn.pepedd.hpoj.judge.codesandbox.model.JudgeInfo;
import cn.pepedd.hpoj.entity.dto.questionSubmit.QuestionSubmitDTO;
import cn.pepedd.hpoj.entity.vo.QuestionSubmitVO;
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
 * 题目提交
 *
 * @TableName biz_question_submit
 */
@TableName(value = "biz_question_submit")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BizQuestionSubmit implements Serializable {
  /**
   * id
   */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private Long id;

  /**
   * 编程语言
   */
  @TableField(value = "language")
  private String language;

  /**
   * 用户代码
   */
  @TableField(value = "code")
  private String code;

  /**
   * 判题信息（json 对象）
   */
  @TableField(value = "judge_info")
  private String judgeInfo;

  /**
   * 判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）
   */
  @TableField(value = "status")
  private Integer status;

  /**
   * 题目 id
   */
  @TableField(value = "question_id")
  private Long questionId;

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
   * @param questionSubmitDTO
   * @return
   */
  public static BizQuestionSubmit dtoToPojo(QuestionSubmitDTO questionSubmitDTO) {
    BizQuestionSubmit bizQuestionSubmit = new BizQuestionSubmit();
    BeanUtils.copyProperties(questionSubmitDTO, bizQuestionSubmit);
    // 对judgeInfo单独处理
    bizQuestionSubmit.setJudgeInfo(JSONUtil.toJsonStr(questionSubmitDTO.getJudgeInfo()));
    return bizQuestionSubmit;
  }

  /**
   * 使用了 列中的json字符串，需要单独的方法进行dto->pojo->vo的转换
   *
   * @param bizQuestionSubmit
   * @return
   */
  public static QuestionSubmitVO pojoToVo(BizQuestionSubmit bizQuestionSubmit) {
    QuestionSubmitVO questionSubmitVO = new QuestionSubmitVO();
    BeanUtils.copyProperties(bizQuestionSubmit, questionSubmitVO);
    // 对judgeInfo单独处理
    JSONConfig jsonConfig = JSONConfig.create();
    jsonConfig.setIgnoreNullValue(false);
    questionSubmitVO.setJudgeInfo(JSONUtil.parseObj(bizQuestionSubmit.getJudgeInfo(), jsonConfig).toBean(JudgeInfo.class));
    return questionSubmitVO;
  }
}
