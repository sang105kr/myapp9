package com.kh.app3.web;

import com.kh.app3.domain.member.Member;
import com.kh.app3.domain.member.svc.MemberSVC;
import com.kh.app3.web.form.LoginForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

  private final MemberSVC memberSVC;

  //로그인
  @GetMapping("/login")
  public String loginForm(){

    return "login/loginForm";
  }

  //로그인처리
  @PostMapping("/login")
  public String login(
      @Valid @ModelAttribute LoginForm loginForm,
      BindingResult bindingResult,
      HttpServletRequest request
      ){

    //유효성 체크
    if(bindingResult.hasErrors()){
      log.info("loginError={}", bindingResult);
      return "login/loginForm";
    }

    //회원유무
    if(!memberSVC.isMember(loginForm.getEmail())) {
      bindingResult.reject("loginFail","아이디가 존재하지 않습니다!");
      return "login/loginForm";
    }

    //로그인
    Member loginMember = memberSVC.login(loginForm.getEmail(), loginForm.getPasswd());
    if(loginMember == null){
      bindingResult.reject("loginFail","비밀번호가 일치하지 않습니다.");
      return "login/loginForm";
    }

    //인증성공
    //세션이 있으면 세션 반환, 없으면 새로이 생성
    HttpSession session = request.getSession(true);
    session.setAttribute("loginMember", loginMember);

    return "redirect:/";
  }

  //로그아웃
  @GetMapping("/logout")
  public String logout(HttpServletRequest request){

    //세션이 있으면 세션을 반환하고 없으면 null반환
    HttpSession session = request.getSession(false);
    if(session != null){
      session.invalidate();  //세션 제거
    }

    return "redirect:/";
  }
}
