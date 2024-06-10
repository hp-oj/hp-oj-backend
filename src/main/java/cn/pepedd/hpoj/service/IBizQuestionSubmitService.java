package cn.pepedd.hpoj.service;

import cn.pepedd.hpoj.entity.dto.questionSubmit.QuestionSubmitDTO;
import cn.pepedd.hpoj.entity.pojo.questionSubmit.BizQuestionSubmit;
import cn.pepedd.hpoj.entity.pojo.questionSubmit.QuestionSubmit;
import cn.pepedd.hpoj.entity.vo.QuestionSubmitVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author pepedd864
* @description 针对表【biz_question_submit(题目提交)】的数据库操作Service
* @createDate 2024-06-08 17:38:48
*/
public interface IBizQuestionSubmitService extends IService<BizQuestionSubmit> {
  /**
   * 提交题目
   * @param questionSubmitDTO
   * @return
   */
  Boolean submitQuestion(QuestionSubmitDTO questionSubmitDTO);

  /**
   * 分页获取题目提交详情VO
   *
   * @param param
   * @return
   */
  Page<QuestionSubmitVO> listQuestionSubmitVO(Page param);

  /**
   * 分页获取题目提交详情
   *
   * @param param
   * @return
   */
  Page<QuestionSubmit> listQuestionSubmit(Page param);
}
