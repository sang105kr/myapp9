package com.kh.app3.domain.notice;

import lombok.*;

import java.time.LocalDateTime;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Notice {
  private Long noticeId;        //공지 아이디  NOTICE_ID	NUMBER(8,0)
  private String subject;       //제목 SUBJECT	VARCHAR2(100 BYTE)
  private String content;       //본문 CONTENT	CLOB
  private String author;        //작성자 AUTHOR	VARCHAR2(12 BYTE)
  private Long  hit;            //조회수 HIT	NUMBER(5,0)
  private LocalDateTime cdate;  //생성일시 CDATE	TIMESTAMP(6)
  private LocalDateTime udate;  //수정일시 UDATE	TIMESTAMP(6)
}
