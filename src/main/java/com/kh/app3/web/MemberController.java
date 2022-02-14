package com.kh.app3.web;

import com.kh.app3.domain.member.Member;
import com.kh.app3.domain.member.svc.MemberSVC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

  private final MemberSVC memberSVC;

  //회원가입
  @GetMapping("/add")
  public String joinForm(){
    log.info("joinForm() 호출됨!");
    return "member/joinForm";
  }

  //회원가입처리
  @PostMapping("/add")
  public String join(
      @RequestParam("email") String email,
      @RequestParam("passwd") String passwd,
      @RequestParam("nickname") String nickname){

    log.info("join() 호출됨!");
    log.info("email={}, passwd={}, nickname={}",email,passwd,nickname);
    
    //1)유효성체크
    
    //2)정상처리로직
    Member member = new Member(null, email, passwd, nickname);
    Member joinedMember = memberSVC.join(member);
    log.info("email={}, passwd={}, nickname={}",
        joinedMember.getEmail(),joinedMember.getPasswd(),joinedMember.getNickname());

    return "member/joinSuccess";
  }

}
