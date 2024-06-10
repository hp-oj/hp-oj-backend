package cn.pepedd.hpoj.judge.codesandbox;

import cn.pepedd.hpoj.judge.codesandbox.model.ExecuteCodeRequest;
import cn.pepedd.hpoj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 */
public interface CodeSandbox {

    /**
     * 执行代码
     *
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
