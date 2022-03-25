package com.kh.app3;

import com.kh.app3.domain.common.paging.PageCriteria;
import com.kh.app3.domain.common.paging.RecordCriteria;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class PagingConfigTest {

  @Autowired  // SC 에서 동일한 타입의 객체주소를 주입받아온다.
  @Qualifier("rc10")   // SC 동일한 타입이 2개이상 존재하는경우 bean이름으로 구부해서 주소값을 받아온다.
  RecordCriteria recordCriteria_10;

  @Autowired
  @Qualifier("rc5")
  RecordCriteria recordCriteria_5;

  @Autowired
  @Qualifier("pc10")
  PageCriteria pageCriteria_10;

  @Autowired
  @Qualifier("pc5")
  PageCriteria pageCriteria_5;

  @Test
  @DisplayName("레코드 계산")
  void calRec(){
    recordCriteria_10.setReqPage(2);
    log.info("시작레코드={}",recordCriteria_10.getStartRec());
    log.info("종료레코드={}",recordCriteria_10.getEndRec());

//    log.info("------------------------------------------");
    recordCriteria_5.setReqPage(2);
    log.info("시작레코드={}",recordCriteria_5.getStartRec());
    log.info("종료레코드={}",recordCriteria_5.getEndRec());
  }

  @Test
  @DisplayName("페이징 계산")
  void calPage(){
    pageCriteria_10.getRc().setReqPage(33);    // 사용자 1page 요청
    pageCriteria_10.setTotalRec(350);         // 총레코드 건수 350개

    log.info("시작레코드={}",pageCriteria_10.getRc().getStartRec());
    log.info("종료레코드={}",pageCriteria_10.getRc().getEndRec());

    log.info("시작페이지={}",pageCriteria_10.getStartPage());
    log.info("종료페이지={}",pageCriteria_10.getEndPage());
  }
}