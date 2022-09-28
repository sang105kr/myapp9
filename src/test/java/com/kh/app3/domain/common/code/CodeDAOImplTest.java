package com.kh.app3.domain.common.code;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class CodeDAOImplTest {

  @Autowired
  private CodeDAO codeDAO;
  
  @Test
  void code() {
    List<Code> codes = codeDAO.code("B01");
    log.info(codes.toString());

    String bbsTitle = codes.stream()
        .filter(ele -> ele.getCode().equals("B0104"))
        .findFirst().orElse(new Code("","전체")).getDecode();

    log.info("decode={}", bbsTitle);
  }









}