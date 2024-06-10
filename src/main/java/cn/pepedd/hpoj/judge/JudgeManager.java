package cn.pepedd.hpoj.judge;

import cn.pepedd.hpoj.entity.pojo.questionSubmit.QuestionSubmit;
import cn.pepedd.hpoj.judge.codesandbox.model.JudgeInfo;
import cn.pepedd.hpoj.judge.strategy.DefaultJudgeStrategy;
import cn.pepedd.hpoj.judge.strategy.JavaJudgeStrategy;
import cn.pepedd.hpoj.judge.strategy.JudgeContext;
import cn.pepedd.hpoj.judge.strategy.JudgeStrategy;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用）
 *
 * @author pepedd864
 * @since 2024/6/10
 */
@Service
public class JudgeManager {

  /**
   * 执行判题
   *
   * @param judgeContext
   * @return
   */
  JudgeInfo doJudge(JudgeContext judgeContext) {
    QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
    String language = questionSubmit.getLanguage();
    JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
    if ("java".equals(language)) {
      judgeStrategy = new JavaJudgeStrategy();
    }
    return judgeStrategy.doJudge(judgeContext);
  }

}
