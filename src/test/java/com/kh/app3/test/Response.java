package com.kh.app3.test;

import lombok.Data;
import java.util.List;

@Data
public class Response {
  private Header header;

  @Data
  static class Header {
    private String resultCode;           //결과코드
    private String resultMsg;            //결과메세지
  }
  private Body body;

  @Data
  static class Body{
    List<Item> items;
    private int numOfRows;              //한 페이지 결과 수
    private int pageNo;                 //페이지 번호
    private int totalCount;             //전체 결과 수

    @Data
    static class Item{
      private long confCase;               //확진자수
      private double confCaseRate;         //확진률
      private String createDt;             //생성일시
      private double criticalRate;         //치명률
      private long death;                  //사망자수
      private double deathRate;            //사망률
      private String gubun;                //구분(0~9)
      private long seq;                    //게시글번호
      private String updateDt;             //수정일시
    }
  }
}