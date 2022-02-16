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
  private String email;
  private String nickname;

//  public LoginMember(String email, String nickname) {
//    this.email = email;
//    this.nickname = nickname;
//  }
//
//  public String getEmail() {
//    return email;
//  }
//
//  public void setEmail(String email) {
//    this.email = email;
//  }
//
//  public String getNickname() {
//    return nickname;
//  }
//
//  public void setNickname(String nickname) {
//    this.nickname = nickname;
//  }
}
