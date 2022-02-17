package com.kh.app3.test;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class Test1 {

  @Test
  void test1(){
    List<String> hobby = new ArrayList<>();
    hobby.add("a");
    hobby.add("b");
    hobby.add("c");
    hobby.add("d");
    String s = makeListToString(hobby);
    Assertions.assertThat("a,b,c,d").isEqualTo(s);
  }

  private String makeListToString(List<String> hobby) {
    StringBuffer str = new StringBuffer();
    for (int i=0; i<hobby.size(); i++) {
      str.append(hobby.get(i));
      if(i == hobby.size()-1) break;
      str.append(",");
    }
    return str.toString();
  }
}
