package com.kh.app3.web.form.bbs;

import lombok.Data;

@Data
public class DetailForm {
  private Long bbsId;           //  게시글번호
  private String bcategory;     //  분류 BCATEGORY	VARCHAR2(11 BYTE)
  private String title;         //  제목 TITLE	VARCHAR2(150 BYTE)
  private String email;         //  EMAIL	VARCHAR2(50 BYTE)
  private String nickname;      //  별칭 NICKNAME	VARCHAR2(30 BYTE)
  private String bcontent;      //  내용 BCONTENT	CLOB
  private int hit;              //  조회수
}
