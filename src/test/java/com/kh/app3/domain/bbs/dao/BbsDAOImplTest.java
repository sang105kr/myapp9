package com.kh.app3.domain.bbs.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class BbsDAOImplTest {

  @Autowired
  private BbsDAO bbsDAO;

  @Test
  @DisplayName("원글 작성")
  void saveOrigin() {
    Bbs bbs = new Bbs();

    bbs.setBcategory("B0101");
    bbs.setTitle("제목1");
    bbs.setEmail("test1@kh.com");
    bbs.setNickname("테스터1");
    bbs.setBcontent("본문1");

    Long saveOriginId = bbsDAO.saveOrigin(bbs);
    Assertions.assertThat(saveOriginId).isEqualTo(4);
    log.info("saveOriginId={}",saveOriginId);
  }
}