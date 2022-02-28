package com.kh.app3.domain.notice.svc;

import com.kh.app3.domain.notice.Notice;
import com.kh.app3.domain.notice.dao.NoticeDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NoticeSVCImpl implements NoticeSVC{

  private final NoticeDAO noticeDAO;

  /**
   * 등록
   * @param notice
   * @return
   */
  @Override
  public Notice write(Notice notice) {

    return noticeDAO.create(notice);
  }

  /**
   * 전체조회
   * @return
   */
  @Override
  public List<Notice> findAll() {
    return noticeDAO.selectAll();
  }

  /**
   * 상세조회
   * @param noticeId 공지사항 번호
   * @return 공지사항 상세
   */
  @Override
  public Notice findByNoticeId(Long noticeId) {
    Notice notice = noticeDAO.selectOne(noticeId);
    noticeDAO.updateHit(noticeId);
    return notice;
  }

  /**
   * 수정
   * @param notice
   * @return
   */
  @Override
  public Notice modify(Notice notice) {
    return noticeDAO.update(notice);
  }

  /**
   * 삭제
   * @param noticeId
   * @return
   */
  @Override
  public int remove(Long noticeId) {
    return noticeDAO.delete(noticeId);
  }

  /**
   * 조회수 증가
   * @param noticeId
   * @return
   */
  @Override
  public int increaseHit(Long noticeId) {
    return noticeDAO.updateHit(noticeId);
  }
}
