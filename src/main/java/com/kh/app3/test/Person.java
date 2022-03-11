package com.kh.app3.test;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
@Slf4j
public class Person {
  private String name;
  private int age;

  public void smile(){
    log.info(name + "가 웃다");
  }

  public void study(){
    log.info(name + "가 공부하다");
  }
//  public void smile(){
//    log.info("웃다");
//  }
}
