package cn.pepedd.hpoj.judge;

import cn.pepedd.hpoj.entity.pojo.questionSubmit.QuestionSubmit;

/**
 * TODO
 *
 * @author pepedd864
 * @since 2024/6/10
 */
public interface JudgeService {
  /**
   * 判题
   *
   * @param questionSubmitId
   * @return
   */
  QuestionSubmit doJudge(long questionSubmitId);
}
