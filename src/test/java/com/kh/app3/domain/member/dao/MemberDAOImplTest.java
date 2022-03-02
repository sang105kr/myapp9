package com.kh.app3.domain.member.dao;

import com.kh.app3.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest  // springboot 환경에서 테스트 진행
public class MemberDAOImplTest {

  @Autowired  // SC(스프링컨테이너)에서 동일타입의 객체를 찾아서 주입시켜준다.
  private MemberDAO memberDAO;

  @Test       // 테스트 대상
  @DisplayName("등록") //테스트케이스 이름
  @Disabled  //테스트 대상에서 제외
  void insert(){
    Member member = new Member("test3@kh.com","1234","테스터abc");
    Member savedMember = memberDAO.insertMember(member);
//    log.info("savedMember.getEmail={}",savedMember.getEmail());
//    log.info("savedMember.getPasswd={}",savedMember.getPasswd());
//    log.info("savedMember.getNickName={}",savedMember.getNickname());
    savedMember.setMemberId(null);
    Assertions.assertThat(savedMember).usingRecursiveComparison().isEqualTo(member);
  };

  @Test
  @DisplayName("조회")
  @Disabled
  void selectByMemberId(){
    Long memberId = 106L;
    Member member = memberDAO.selectMemberByMemberId(memberId);
    log.info("member={}", member);
    Assertions.assertThat(member.getMemberId()).isEqualTo(memberId);
  }

  @Test
  @DisplayName("수정")
  void updateMember(){

    String email = "test3@kh.com";
    String nickname = "테스터3";

    Member member = new Member(email, null, nickname);
    memberDAO.updateMember(member);

    Member updatedMember = memberDAO.selectMemberByEmail(email);

    log.info("updatedMember.nickname={}",nickname);
    Assertions.assertThat(updatedMember.getNickname()).isEqualTo(nickname);

  }
  @Test
  @DisplayName("탈퇴")
  void outMember(){
    String email = "test2@kh.com";
    memberDAO.deleteMember(email);

    boolean isMember = memberDAO.exitMember(email);
    Assertions.assertThat(isMember).isFalse();
  }

  @Test
  @DisplayName("전체조회")
  void selectAll(){

    List<Member> members = memberDAO.selectAll();
    //log.info("members={}", members);
    Assertions.assertThat(members.size()).isEqualTo(2);
  }

  @Test
  @DisplayName("로그인")
  void login(){

    String email = "test1@kh.com";
    String passwd = "1234";

    Member member = memberDAO.login(email, passwd);

    Assertions.assertThat(member).isNotNull();

  }

  @Test
  @DisplayName("이메일찾기by별칭")
  void findEmailByNickname(){
    String nickname = "테스터3";
    String findedEmail = memberDAO.findEmailByNickname(nickname);
    Assertions.assertThat(findedEmail).isEqualTo("test3@kh.com");
  }
}
