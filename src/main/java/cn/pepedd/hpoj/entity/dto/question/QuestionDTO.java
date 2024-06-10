package cn.pepedd.hpoj.entity.dto.question;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 题目
 */
@Data
@Builder
public class QuestionDTO implements Serializable {
  private Long id;
  @NotBlank
  private String title;
  @NotNull
  @Length(max = 2048)
  private String content;
  @NotNull
  private List<String> tags;
  @NotBlank
  private String answer;
  @NotNull
  private List<JudgeCase> judgeCase;
  @NotNull
  private JudgeConfig judgeConfig;
  private static final long serialVersionUID = 1L;

}
