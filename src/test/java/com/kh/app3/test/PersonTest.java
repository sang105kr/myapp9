package com.kh.app3.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@RequiredArgsConstructor
class PersonTest {
// case1) 필드주입
  @Autowired
  Person p1;

// case2) 생성자주입
//  Person p1;
//  @Autowired
//  PersonTest(Person person){
//    this.person = person;
//  }

// case3) 생성자주입 - lombock @RequiredArgsConstructor
//   private final Person p1;

  @Test
  @DisplayName("DI 컨테이너 미사용")
  void person(){
    Person p1 = new Person("홍길동", 30);
    log.info(p1.getName());
    p1.study();
    p1.smile();
  }
  
  @Test
  @DisplayName("DI 컨테이너 사용")
  void person2(){
    log.info(p1.getName());
    p1.study();
    p1.smile();
  }
  
}