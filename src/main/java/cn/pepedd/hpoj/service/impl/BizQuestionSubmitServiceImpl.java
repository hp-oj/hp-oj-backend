package cn.pepedd.hpoj.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.pepedd.hpoj.common.enums.ErrorCode;
import cn.pepedd.hpoj.entity.dto.questionSubmit.QuestionSubmitDTO;
import cn.pepedd.hpoj.entity.pojo.question.BizQuestion;
import cn.pepedd.hpoj.entity.pojo.questionSubmit.BizQuestionSubmit;
import cn.pepedd.hpoj.entity.pojo.questionSubmit.QuestionSubmit;
import cn.pepedd.hpoj.entity.vo.QuestionSubmitVO;
import cn.pepedd.hpoj.exception.ThrowUtils;
import cn.pepedd.hpoj.judge.JudgeService;
import cn.pepedd.hpoj.mapper.BizQuestionSubmitMapper;
import cn.pepedd.hpoj.service.IBizQuestionService;
import cn.pepedd.hpoj.service.IBizQuestionSubmitService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author pepedd864
 * @description 针对表【biz_question_submit(题目提交)】的数据库操作Service实现
 * @createDate 2024-06-08 17:38:48
 */
@Service
public class BizQuestionSubmitServiceImpl extends ServiceImpl<BizQuestionSubmitMapper, BizQuestionSubmit>
    implements IBizQuestionSubmitService {
  @Resource
  private IBizQuestionService bizQuestionService;
  @Resource
  @Lazy
  private JudgeService judgeService;

  /**
   * 提交题目
   *
   * @param questionSubmitDTO
   * @return
   */
  @Override
  public Boolean submitQuestion(QuestionSubmitDTO questionSubmitDTO) {
    // 如果id不为null说明是非法请求
    ThrowUtils.throwIf(questionSubmitDTO.getId() != null, ErrorCode.PARAMS_ERROR);
    // 查询是否有该题
    BizQuestion bizQuestion = bizQuestionService.getById(questionSubmitDTO.getQuestionId());
    ThrowUtils.throwIf(bizQuestion == null, ErrorCode.PARAMS_ERROR);
    // 先拷贝属性
    BizQuestionSubmit bizQuestionSubmit = BizQuestionSubmit.dtoToPojo(questionSubmitDTO);
    // 获取登录用户id
    Object loginId = StpUtil.getLoginId();
    bizQuestionSubmit.setUserId(Long.valueOf((String) loginId));
    // 插入
    save(bizQuestionSubmit);
    // 执行判题服务 TODO
    CompletableFuture.runAsync(() -> {
      judgeService.doJudge(bizQuestionSubmit.getId());
    });
    return true;
  }

  /**
   * 分页获取题目提交详情VO
   *
   * @param param
   * @return
   */
  @Override
  public Page<QuestionSubmitVO> listQuestionSubmitVO(Page param) {
    LambdaQueryWrapper<BizQuestionSubmit> queryWrapper = new LambdaQueryWrapper<>();
    // 按时间倒序排列
    queryWrapper.orderByDesc(BizQuestionSubmit::getCreateTime);
    param.setCurrent(param.getCurrent());
    Page<BizQuestionSubmit> page = page(param, queryWrapper);
    Page<QuestionSubmitVO> questionSubmitVOPage = new Page<>();
    // 拷贝属性
    BeanUtils.copyProperties(page, questionSubmitVOPage);
    if (page.getRecords().isEmpty()) {
      return questionSubmitVOPage;
    }
    List<QuestionSubmitVO> records = new ArrayList<>();
    page.getRecords().forEach(
        questionSubmit -> {
          QuestionSubmitVO questionSubmitVO = BizQuestionSubmit.pojoToVo(questionSubmit);
          records.add(questionSubmitVO);
        });
    questionSubmitVOPage.setRecords(records);
    return questionSubmitVOPage;
  }

  /**
   * 分页获取题目提交详情
   *
   * @param param
   * @return
   */
  @Override
  public Page<QuestionSubmit> listQuestionSubmit(Page param) {
    LambdaQueryWrapper<BizQuestionSubmit> queryWrapper = new LambdaQueryWrapper<>();
    // 按时间倒序排列
    queryWrapper.orderByDesc(BizQuestionSubmit::getCreateTime);
    param.setCurrent(param.getCurrent());
    Page<BizQuestionSubmit> page = page(param, queryWrapper);
    Page<QuestionSubmit> questionSubmitPage = new Page<>();
    // 拷贝属性
    List<QuestionSubmit> records = new ArrayList<>();
    BeanUtils.copyProperties(page, questionSubmitPage);
    if (page.getRecords().isEmpty()) {
      return questionSubmitPage;
    }
    page.getRecords().forEach(
        bizQuestionSubmit -> {
          QuestionSubmit questionSubmit = QuestionSubmit.dbToObj(bizQuestionSubmit);
          records.add(questionSubmit);
        });
    questionSubmitPage.setRecords(records);
    return questionSubmitPage;
  }
}




