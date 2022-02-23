package com.kh.app3.web.form.notice;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter@Getter
@ToString
public class DetailForm {
  private String subject;
  private String content;
  private String author;
}
