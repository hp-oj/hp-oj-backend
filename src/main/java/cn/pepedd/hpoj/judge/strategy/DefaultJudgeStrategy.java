package cn.pepedd.hpoj.judge.strategy;

import cn.pepedd.hpoj.entity.dto.question.JudgeCase;
import cn.pepedd.hpoj.entity.dto.question.JudgeConfig;
import cn.pepedd.hpoj.entity.enums.JudgeInfoMessageEnum;
import cn.pepedd.hpoj.entity.pojo.question.Question;
import cn.pepedd.hpoj.entity.pojo.questionSubmit.QuestionSubmit;
import cn.pepedd.hpoj.judge.codesandbox.model.JudgeInfo;

import java.util.List;

/**
 * 默认判题策略
 *
 * @author pepedd864
 * @since 2024/6/10
 */
public class DefaultJudgeStrategy implements JudgeStrategy {
  /**
   * 执行判题
   *
   * @param judgeContext
   * @return
   */
  @Override
  public JudgeInfo doJudge(JudgeContext judgeContext) {
    // 拿到judgeContext的所有参数 使用插件 allget生成
    JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
    List<String> inputList = judgeContext.getInputList();
    List<String> outputList = judgeContext.getOutputList();
    List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
    Question question = judgeContext.getQuestion();
    QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
    // 返回信息
    JudgeInfo judgeInfoResponse = JudgeInfo.builder()
        .time(judgeInfo.getTime())
        .memory(judgeInfo.getMemory())
        .message(JudgeInfoMessageEnum.ACCEPTED.getValue())
        .build();
    // 判断预期输出和实际输出是否相等
    if (outputList.size() != inputList.size()) {
      judgeInfoResponse.setMessage(JudgeInfoMessageEnum.WRONG_ANSWER.getValue());
      return judgeInfoResponse;
    }
    // 依次判断是否相等
    for (int i = 0; i < judgeCaseList.size(); i++) {
      JudgeCase judgeCase = judgeCaseList.get(i);
      if (!judgeCase.getOutput().equals(outputList.get(i))) {
        judgeInfoResponse.setMessage(JudgeInfoMessageEnum.WRONG_ANSWER.getValue());
        return judgeInfoResponse;
      }
    }
    // 判断是否超出限制
    JudgeConfig judgeConfig = question.getJudgeConfig();
    if (judgeInfo.getTime() > judgeConfig.getTimeLimit()) {
      judgeInfoResponse.setMessage(JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED.getValue());
      return judgeInfoResponse;
    }
    if (judgeInfo.getMemory() > judgeConfig.getMemoryLimit()) {
      judgeInfoResponse.setMessage(JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED.getValue());
      return judgeInfoResponse;
    }
    return judgeInfoResponse;
  }
}
