package cn.pepedd.hpoj.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.pepedd.hpoj.common.enums.ErrorCode;
import cn.pepedd.hpoj.entity.dto.question.QuestionDTO;
import cn.pepedd.hpoj.entity.pojo.question.BizQuestion;
import cn.pepedd.hpoj.entity.pojo.question.Question;
import cn.pepedd.hpoj.entity.vo.QuestionVO;
import cn.pepedd.hpoj.exception.ThrowUtils;
import cn.pepedd.hpoj.mapper.BizQuestionMapper;
import cn.pepedd.hpoj.service.IBizQuestionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pepedd864
 * @description 针对表【biz_question(题目)】的数据库操作Service实现
 * @createDate 2024-06-08 17:38:48
 */
@Service
public class BizQuestionServiceImpl extends ServiceImpl<BizQuestionMapper, BizQuestion>
    implements IBizQuestionService {

  /**
   * 添加题目
   *
   * @param questionDTO
   * @return
   */
  @Override
  public Boolean addQuestion(QuestionDTO questionDTO) {
    // 如果id不为null说明是非法请求
    ThrowUtils.throwIf(questionDTO.getId() != null, ErrorCode.PARAMS_ERROR);
    // 先拷贝属性
    BizQuestion bizQuestion = BizQuestion.dtoToPojo(questionDTO);
    // 获取登录用户id
    Object loginId = StpUtil.getLoginId();
    bizQuestion.setUserId(Long.valueOf((String) loginId));
    // 插入
    save(bizQuestion);
    return true;
  }

  /**
   * 更新题目
   *
   * @param questionDTO
   * @return
   */
  @Override
  public Boolean updateQuestion(QuestionDTO questionDTO) {
    // 如果id为null说明是非法请求
    ThrowUtils.throwIf(questionDTO.getId() == null, ErrorCode.PARAMS_ERROR);
    // 查询数据库
    BizQuestion updateQuestion = getById(questionDTO.getId());
    // 获取登录用户id 如果不是自己的题目则无法更新
    long loginId = Long.parseLong((String) (StpUtil.getLoginId()));
    ThrowUtils.throwIf(loginId != updateQuestion.getUserId(), ErrorCode.FORBIDDEN_ERROR);
    // 拷贝
    BizQuestion bizQuestion = BizQuestion.dtoToPojo(questionDTO);
    BeanUtils.copyProperties(bizQuestion, updateQuestion);
    // 更新
    updateById(updateQuestion);
    return true;
  }

  /**
   * 获取题目详情VO
   *
   * @param id
   * @return
   */
  @Override
  public QuestionVO getQuestionVO(Long id) {
    BizQuestion question = getById(id);
    ThrowUtils.throwIf(question == null, ErrorCode.NOT_FOUND_ERROR);
    return BizQuestion.pojoToVo(question);
  }

  /**
   * 分页获取题目详情VO
   *
   * @param param
   * @return
   */
  @Override
  public Page<QuestionVO> listQuestionVO(Page param) {
    LambdaQueryWrapper<BizQuestion> queryWrapper = new LambdaQueryWrapper<>();
    // 按时间倒序排列
    queryWrapper.orderByDesc(BizQuestion::getUpdateTime);
    param.setCurrent(param.getCurrent());
    Page<BizQuestion> page = page(param, queryWrapper);
    Page<QuestionVO> questionVOPage = new Page<>();
    // 拷贝属性
    BeanUtils.copyProperties(page, questionVOPage);
    if (page.getRecords().isEmpty()) {
      return questionVOPage;
    }
    List<QuestionVO> records = new ArrayList<>();
    page.getRecords().forEach(
        question -> {
          QuestionVO questionVO = BizQuestion.pojoToVo(question);
          records.add(questionVO);
        });
    questionVOPage.setRecords(records);
    return questionVOPage;
  }

  /**
   * 分页获取题目
   *
   * @param param
   * @return
   */
  @Override
  public Page<Question> listQuestion(Page param) {
    LambdaQueryWrapper<BizQuestion> queryWrapper = new LambdaQueryWrapper<>();
    // 按时间倒序排列
    queryWrapper.orderByDesc(BizQuestion::getUpdateTime);
    param.setCurrent(param.getCurrent());
    Page<BizQuestion> page = page(param, queryWrapper);
    Page<Question> questionPage = new Page<>();
    // 拷贝属性
    BeanUtils.copyProperties(page, questionPage);
    if (page.getRecords().isEmpty()) {
      return questionPage;
    }
    List<Question> records = new ArrayList<>();
    page.getRecords().forEach(
        bizQuestion -> {
          Question question = Question.dbToObj(bizQuestion);
          records.add(question);
        });
    questionPage.setRecords(records);
    return questionPage;
  }

  /**
   * 随机获得一题
   *
   * @return
   */
  @Override
  public QuestionVO getRandQuestionVO() {
    // 随机获取一条记录
    // 使用 MyBatis 的 ORDER BY RAND() 语句随机获取一条记录
    LambdaQueryWrapper<BizQuestion> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.last("ORDER BY RAND() LIMIT 1");
    BizQuestion question = getOne(queryWrapper);
    ThrowUtils.throwIf(question == null, ErrorCode.SYSTEM_ERROR);
    return BizQuestion.pojoToVo(question);
  }
}




