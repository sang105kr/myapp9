package com.kh.app3.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
public class Test2 {

  @Test
  void test(){
    String startDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    log.info(startDate.toString());
  }

  @Test
  void randomCode(){
    for(int i=0; i<20; i++) {
      UUID uuid = UUID.randomUUID();
      log.info("uuid={}", uuid);
    }
  }
}
