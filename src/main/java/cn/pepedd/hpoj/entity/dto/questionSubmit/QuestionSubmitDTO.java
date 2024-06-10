package cn.pepedd.hpoj.entity.dto.questionSubmit;

import cn.pepedd.hpoj.judge.codesandbox.model.JudgeInfo;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 题目提交
 */
@Data
@Builder
public class QuestionSubmitDTO implements Serializable {
    private Long id;
    @NotBlank
    private String language;
    @NotBlank
    @Length(max = 2048)
    private String code;
    private JudgeInfo judgeInfo;
    private Integer status;
    @NotNull
    private Long questionId;
    private static final long serialVersionUID = 1L;
}
