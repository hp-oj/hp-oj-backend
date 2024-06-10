package cn.pepedd.hpoj.judge.codesandbox.impl;

import cn.pepedd.hpoj.entity.enums.JudgeInfoMessageEnum;
import cn.pepedd.hpoj.entity.enums.QuestionSubmitStatusEnum;
import cn.pepedd.hpoj.judge.codesandbox.CodeSandbox;
import cn.pepedd.hpoj.judge.codesandbox.model.ExecuteCodeRequest;
import cn.pepedd.hpoj.judge.codesandbox.model.ExecuteCodeResponse;
import cn.pepedd.hpoj.judge.codesandbox.model.JudgeInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 示例代码沙箱（仅为了跑通业务流程）
 */
@Slf4j
public class ExampleCodeSandbox implements CodeSandbox {
  @Override
  public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
    List<String> inputList = executeCodeRequest.getInputList();
    ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
    executeCodeResponse.setOutputList(inputList);
    executeCodeResponse.setMessage("测试执行成功");
    executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
    JudgeInfo judgeInfo = JudgeInfo
        .builder()
        .memory(100L)
        .time(100L)
        .message(JudgeInfoMessageEnum.ACCEPTED.getValue())
        .build();
    executeCodeResponse.setJudgeInfo(judgeInfo);
    // 延迟5s模拟执行过程
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return executeCodeResponse;
  }
}
