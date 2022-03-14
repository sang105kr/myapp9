package com.kh.app3.domain.bbs.svc;

import com.kh.app3.domain.bbs.dao.Bbs;

import java.util.List;

public interface BbsSVC {

  /**
   * 원글작성
   * @param bbs
   * @return 게시글 번호
   */
  Long saveOrigin(Bbs bbs);

  /**
   * 목록
   * @return
   */
  List<Bbs> findAll();

  /**
   * 상세 조회
   * @param id 게시글번호
   * @return
   */
  Bbs findByBbsId(Long id);

  /**
   * 삭제
   * @param id 게시글번호
   * @return 삭제건수
   */
  int deleteByBbsId(Long id);

  /**
   * 수정
   * @param id 게시글 번호
   * @param bbs 수정내용
   * @return 수정건수
   */
  int updateByBbsId(Long id,Bbs bbs);


  /**
   * 답글작성
   * @param pbbsId 부모글번호
   * @param replyBbs 답글
   * @return 답글번호
   */
  Long saveReply(Long pbbsId,Bbs replyBbs);

  /**
   * 전체건수
   * @return 게시글 전체건수
   */
  int totalCount();

}
