package cn.pepedd.hpoj.service;

import cn.pepedd.hpoj.entity.dto.question.QuestionDTO;
import cn.pepedd.hpoj.entity.pojo.question.BizQuestion;
import cn.pepedd.hpoj.entity.pojo.question.Question;
import cn.pepedd.hpoj.entity.vo.QuestionVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author pepedd864
 * @description 针对表【biz_question(题目)】的数据库操作Service
 * @createDate 2024-06-08 17:38:48
 */
public interface IBizQuestionService extends IService<BizQuestion> {

  /**
   * 添加题目
   *
   * @param questionDTO
   * @return
   */
  Boolean addQuestion(QuestionDTO questionDTO);

  /**
   * 更新题目
   *
   * @param questionDTO
   * @return
   */
  Boolean updateQuestion(QuestionDTO questionDTO);

  /**
   * 获取题目详情VO
   *
   * @param id
   * @return
   */
  QuestionVO getQuestionVO(Long id);

  /**
   * 分页获取题目详情VO
   *
   * @return
   */
  Page<QuestionVO> listQuestionVO(Page param);

  /**
   * 分页获取题目
   *
   * @param param
   * @return
   */
  Page<Question> listQuestion(Page param);

  /**
   * 随机获得一题
   *
   * @return
   */
  QuestionVO getRandQuestionVO();
}
