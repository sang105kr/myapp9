package com.kh.app3.domain.notice.dao;

import com.kh.app3.domain.notice.Notice;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
class NoticeDAOImplTest {

  @Autowired  //SC(스프링컨테이너)에서 동일타입의 객체를 주입받는다.
  private NoticeDAO noticeDAO;

  @Test
  @DisplayName("공지사항 등록")
  void create() {

    Notice notice = new Notice();
    notice.setSubject("제목3");
    notice.setContent("본문3");
    notice.setAuthor("홍길동3");
    Notice savedNotice = noticeDAO.create(notice);
    Assertions.assertThat(notice.getSubject()).isEqualTo(savedNotice.getSubject());
    log.info("savedNoticdId={}",savedNotice.getNoticeId());
  }

  @Test
  @DisplayName("공지사항 조회 1건")
  void selectOne() {
    Long noticdId = 2L;
    Notice notice = noticeDAO.selectOne(noticdId);
    Assertions.assertThat(notice).isNotNull();
    log.info("notice={}",notice);
  }
}





