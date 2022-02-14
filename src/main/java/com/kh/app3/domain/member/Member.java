package com.kh.app3.domain.member;

import lombok.*;

@Getter              // 모든멤버필드의 getter 메소드를 자동 생성해준다.
@Setter              // 모든멤버필드의 setter 메소드를 자동 생성해준다.
@NoArgsConstructor   // 디폴트 생성자 자동 생성해준다.
@AllArgsConstructor  // 모든멤버필드를 매개값으로 받아 생성자를 자동 만들어준다.
@ToString
public class Member {
  private Long memberId;
  private String email;
  private String passwd;
  private String nickname;
}
//  public Member(Long memberId, String email, String passwd, String nickname) {
//    this.member_Id = memberId;
//    this.email = email;
//    this.passwd = passwd;
//    this.nickname = nickname;
//  }


//  public Long getMember_Id() {
//    return member_Id;
//  }
//
//  public String getEmail() {
//    return email;
//  }
//
//  public String getPasswd() {
//    return passwd;
//  }
//
//  public String getNickname() {
//    return nickname;
//  }
//}
