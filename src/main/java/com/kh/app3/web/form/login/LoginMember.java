package com.kh.app3.web.form.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원 세션 정보
 */
@Getter @Setter
@AllArgsConstructor
public class LoginMember {
  private Long memberId;
  private String email;
  private String nickname;
  private String gubun;
}
