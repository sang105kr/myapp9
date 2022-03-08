package com.kh.app3.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
public class Test2 {

  @Test
  void test(){
    String startDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    log.info(startDate.toString());
  }
}
