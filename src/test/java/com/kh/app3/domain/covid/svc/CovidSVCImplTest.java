package com.kh.app3.domain.covid.svc;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CovidSVCImplTest {

  @Autowired
  private CovidSVC covidSVC;

  @Test
  void getCovidInfo() {
    String sdate = "20220308";
    String edate = "20220308";
    Response res = covidSVC.getCovidInfo(sdate, edate);
    log.info("totalCount={}",res.getBody().getTotalCount());
    Assertions.assertThat(res.getBody().getTotalCount()).isEqualTo(11);
  }
}