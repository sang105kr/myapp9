package com.kh.app3.domain.member;

import lombok.*;

@Getter              // 모든멤버필드의 getter 메소드를 자동 생성해준다.
@Setter              // 모든멤버필드의 setter 메소드를 자동 생성해준다.
@NoArgsConstructor   // 디폴트 생성자 자동 생성해준다.
@AllArgsConstructor  // 모든멤버필드를 매개값으로 받아 생성자를 자동 만들어준다.
@ToString
public class Member {
  private Long   memberId;
  private String email;
  private String passwd;
  private String nickname;
  private String gender;
  private String hobby;
  private String region;
  private String gubun;
  private byte[] pic;

  public Member(String email, String passwd, String nickname) {
    this.email = email;
    this.passwd = passwd;
    this.nickname = nickname;
  }
}
