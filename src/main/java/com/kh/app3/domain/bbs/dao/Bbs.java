package com.kh.app3.domain.bbs.dao;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Bbs {
  private Long bbsId;          //  게시글 번호 BBS_ID	NUMBER(10,0)
  private String bcategory;     //  분류 BCATEGORY	VARCHAR2(11 BYTE)
  private String title;         //  제목 TITLE	VARCHAR2(150 BYTE)
  private String email;         //  EMAIL	VARCHAR2(50 BYTE)
  private String nickname;      //  별칭 NICKNAME	VARCHAR2(30 BYTE)
  private int hit;              //  조회수 HIT	NUMBER(5,0)
  private String bcontent;      //  내용 BCONTENT	CLOB
  private Long  pbbsId;         //  부모 게시글 번호 PBBS_ID	NUMBER(10,0)
  private Long bgroup;          //  답글그룹 BGROUP	NUMBER(10,0)
  private int step;             //  답글단계 STEP	NUMBER(3,0)
  private int bindent;           //  답글 들여쓰기 BINDENT	NUMBER(3,0)
  private BbsStatus status;     //  게시글 상태 STATUS	CHAR(1 BYTE) (D:삭제, I:임시저장, W:경고)
  private LocalDateTime cdate;  //  생성일 CDATE	TIMESTAMP(6)
  private LocalDateTime udate;  //  수정일 UDATE	TIMESTAMP(6)
}
