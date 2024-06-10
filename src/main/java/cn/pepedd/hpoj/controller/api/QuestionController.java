package cn.pepedd.hpoj.controller.api;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.pepedd.hpoj.common.enums.ErrorCode;
import cn.pepedd.hpoj.common.enums.UserRoleEnum;
import cn.pepedd.hpoj.entity.dto.question.QuestionDTO;
import cn.pepedd.hpoj.entity.dto.questionSubmit.QuestionSubmitDTO;
import cn.pepedd.hpoj.entity.pojo.question.BizQuestion;
import cn.pepedd.hpoj.entity.pojo.question.Question;
import cn.pepedd.hpoj.entity.pojo.questionSubmit.BizQuestionSubmit;
import cn.pepedd.hpoj.entity.pojo.questionSubmit.QuestionSubmit;
import cn.pepedd.hpoj.entity.result.R;
import cn.pepedd.hpoj.entity.vo.QuestionSubmitVO;
import cn.pepedd.hpoj.entity.vo.QuestionVO;
import cn.pepedd.hpoj.exception.ThrowUtils;
import cn.pepedd.hpoj.service.IBizQuestionService;
import cn.pepedd.hpoj.service.IBizQuestionSubmitService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author pepedd864
 * @since 2024/6/8
 */
@RestController
@RequestMapping("/question")
public class QuestionController {
  @Resource
  private IBizQuestionService bizQuestionService;
  @Resource
  private IBizQuestionSubmitService bizQuestionSubmitService;

  /**
   * 添加题目
   */
  @SaCheckLogin
  @SaCheckRole(UserRoleEnum.ADMIN_ROLE)
  @PostMapping("/add")
  @ApiOperation("添加题目")
  public R<Boolean> addQuestion(@RequestBody @Valid QuestionDTO questionDTO) {
    return R.success(bizQuestionService.addQuestion(questionDTO));
  }

  /**
   * 删除题目
   */
  @SaCheckLogin
  @SaCheckRole(UserRoleEnum.ADMIN_ROLE)
  @PostMapping("/delete")
  @ApiOperation("删除题目")
  public R<Boolean> deleteQuestion(@RequestParam("id") Long id) {
    return R.success(bizQuestionService.removeById(id));
  }

  /**
   * 更新题目
   */
  @SaCheckLogin
  @SaCheckRole(UserRoleEnum.ADMIN_ROLE)
  @PostMapping("/update")
  @ApiOperation("更新题目")
  public R<Boolean> updateQuestion(@RequestBody @Valid QuestionDTO questionDTO) {
    return R.success(bizQuestionService.updateQuestion(questionDTO));
  }

  /**
   * 根据id查询题目VO
   */
  @GetMapping("/getVo")
  @ApiOperation("根据id查询题目VO")
  public R<QuestionVO> getQuestionVO(@RequestParam("id") Long id) {
    return R.success(bizQuestionService.getQuestionVO(id));
  }

  /**
   * 随机获得一题目VO
   */
  @GetMapping("/getRand")
  @ApiOperation("随机获得一题目VO")
  public R<QuestionVO> getRandQuestionVO() {
    return R.success(bizQuestionService.getRandQuestionVO());
  }

  /**
   * 根据id查询题目
   */
  @SaCheckLogin
  @SaCheckRole(UserRoleEnum.ADMIN_ROLE)
  @GetMapping("/get")
  @ApiOperation("根据id查询题目")
  public R<Question> getQuestion(@RequestParam("id") Long id) {
    BizQuestion bizQuestion = bizQuestionService.getById(id);
    ThrowUtils.throwIf(bizQuestion == null, ErrorCode.NOT_FOUND_ERROR);
    return R.success(Question.dbToObj(bizQuestion));
  }

  /**
   * 分页查询VO
   */
  @PostMapping("/listVo")
  @ApiOperation("分页查询题目VO")
  public R<Page<QuestionVO>> listQuestionVO(@RequestBody @Valid Page param) {
    return R.success(bizQuestionService.listQuestionVO(param));
  }

  /**
   * 分页查询
   */
  @SaCheckLogin
  @SaCheckRole(UserRoleEnum.ADMIN_ROLE)
  @PostMapping("/list")
  @ApiOperation("分页查询题目")
  public R<Page<Question>> listQuestion(@RequestBody @Valid Page param) {
    return R.success(bizQuestionService.listQuestion(param));
  }

  /**
   * 提交题目
   */
  @SaCheckLogin
  @PostMapping("/submit/do")
  @ApiOperation("提交题目")
  public R<Boolean> submitQuestion(@RequestBody @Valid QuestionSubmitDTO questionSubmitDTO) {
    return R.success(bizQuestionSubmitService.submitQuestion(questionSubmitDTO));
  }

  /**
   * 查看题目代码
   */
  @SaCheckLogin
  @GetMapping("/getSubmitAnswer")
  @ApiOperation("查看题目代码")
  public R<Map<String, String>> getSubmitAnswer(@RequestParam("id") Long id) {
    BizQuestionSubmit bizQuestionSubmit = bizQuestionSubmitService.getById(id);
    ThrowUtils.throwIf(bizQuestionSubmit == null, ErrorCode.NOT_FOUND_ERROR);
    Map<String, String> res = new HashMap<>();
    res.put("code", bizQuestionSubmit.getCode());
    res.put("language", bizQuestionSubmit.getLanguage());
    return R.success(res);
  }

  /**
   * 分页查询提交记录VO
   */
  @PostMapping("/submit/listVo")
  @ApiOperation("分页查询提交记录VO")
  public R<Page<QuestionSubmitVO>> listQuestionSubmitVO(@RequestBody @Valid Page param) {
    return R.success(bizQuestionSubmitService.listQuestionSubmitVO(param));
  }

  /**
   * 分页查询提交记录
   */
  @SaCheckLogin
  @SaCheckRole(UserRoleEnum.ADMIN_ROLE)
  @PostMapping("/submit/list")
  @ApiOperation("分页查询提交记录")
  public R<Page<QuestionSubmit>> listQuestionSubmit(@RequestBody @Valid Page param) {
    return R.success(bizQuestionSubmitService.listQuestionSubmit(param));
  }
}
