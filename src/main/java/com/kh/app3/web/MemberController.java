package com.kh.app3.web;

import com.kh.app3.domain.member.Member;
import com.kh.app3.domain.member.svc.MemberSVC;
import com.kh.app3.web.form.member.Gender;
import com.kh.app3.web.form.member.JoinForm;
import com.kh.app3.web.form.member.ModifyForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

  private final MemberSVC memberSVC;

  //성별
  @ModelAttribute("gender")
  public Gender[] genter(){

    return Gender.values();  //[MALE, FEMALE]
  }

  //취미
  @ModelAttribute("hobbys")
  public Map<String,String> hobbys(){
    Map<String,String> hobbys = new HashMap<>();
    hobbys.put("reading", "독서");
    hobbys.put("swim", "수영");
    hobbys.put("climing", "등산");
    hobbys.put("golf", "골프");
    return hobbys;
  }

  //지역
  @ModelAttribute("regions")
  public List<String> regions(){
    List<String> regions = new ArrayList<>();
    regions.add("서울");
    regions.add("부산");
    regions.add("울산");
    regions.add("대구");
    regions.add("제주");
    return regions;
  }

  //회원가입
  @GetMapping("/add")
  public String joinForm(@ModelAttribute JoinForm joinForm){
    log.info("joinForm() 호출됨!");
    return "member/joinForm";
  }

  //회원가입처리
  @PostMapping("/add")
  public String join(
      @Valid @ModelAttribute JoinForm joinForm,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes){

    log.info("join() 호출됨!");
    log.info("email={}, passwd={}, passwdChk={}, nickname={}",
        joinForm.getEmail(),joinForm.getPasswd(),
        joinForm.getPasswdChk(),joinForm.getNickname());
    
    //1)유효성체크 - 필드오류
    if(bindingResult.hasErrors()){
      log.info("error={}", bindingResult);
      return "member/joinForm";
    }
    //2)아이디 중복체크
    if(memberSVC.existMember(joinForm.getEmail())){
      bindingResult.rejectValue("email","joinForm.email.dup");
      log.info("error={}", bindingResult);
      return "member/joinForm";
    }
    //3)유효성체크 - global 오류 (2개이상의 필드체크, 백앤드로직 수행시 발생오류)
    //비밀번호 != 비빌번호체크
    if(!joinForm.getPasswd().equals(joinForm.getPasswdChk()))   {
      bindingResult.reject("member.passwdchk");
      return "member/joinForm";
    }

    //4)정상처리로직
    log.info("joinForm={}", joinForm);
    Member member = new Member( null,
                joinForm.getEmail(), joinForm.getPasswd(), joinForm.getNickname(),
                joinForm.getGender().getDescription(),
                makeListToString(joinForm.getHobby()),
                joinForm.getRegion());
    Member joinedMember = memberSVC.join(member);
    log.info("email={}, passwd={}, nickname={}",
        joinedMember.getEmail(),joinedMember.getPasswd(),joinedMember.getNickname());

    return "member/joinSuccess";
  }

  //문자열 리스트를 ','를 구분자로하는 문자열 변환
  private String makeListToString(List<String> hobby) {
    StringBuffer str = new StringBuffer();
    for (int i=0; i<hobby.size(); i++) {
      str.append(hobby.get(i));
      if(i == hobby.size()-1) break;
      str.append(",");
    }
    return str.toString();
  }

//  public String join(
//      @RequestParam("email") String email,
//      @RequestParam("passwd") String passwd,
//      @RequestParam("nickname") String nickname){
//
//    log.info("join() 호출됨!");
//    log.info("email={}, passwd={}, nickname={}",email,passwd,nickname);
//
//    //1)유효성체크
//
//    //2)정상처리로직
//    Member member = new Member(null, email, passwd, nickname);
//    Member joinedMember = memberSVC.join(member);
//    log.info("email={}, passwd={}, nickname={}",
//        joinedMember.getEmail(),joinedMember.getPasswd(),joinedMember.getNickname());
//
//    return "member/joinSuccess";
//  }

  //회원수정
  @GetMapping("/{email}/edit")
  public String editForm(
      @PathVariable("email") String email,
      Model model){

    Member member = memberSVC.findByEmail(email);

    ModifyForm modifyForm = new ModifyForm();
    modifyForm.setEmail(member.getEmail());
    modifyForm.setNickname(member.getNickname());
    modifyForm.setGender(getGender(member.getGender()));
    modifyForm.setHobby(stringToList(member.getHobby()));
    modifyForm.setRegion(member.getRegion());

    model.addAttribute("modifyForm", modifyForm);

    return "member/modifyForm";
  }

  //문자열로 Enum객체에서 상수요소 찾아오기
  private Gender getGender(String gender) {
    Gender finded = null;
    for(Gender g : Gender.values()){
      if(g.getDescription().equals(gender)){
        finded = Gender.valueOf(g.name());
        break;
      }
    }
    return finded;
  }

  //콤마를 구분자로하는 문자열을 문자열 요소로갖는 리스트로 변환
  private List<String> stringToList(String str) {
    String[] array = str.split(",");
    log.info("array={}", array.length);
    List<String> list = new ArrayList<>();
    for (int i = 0; i < array.length; i++) {
      list.add(array[i]);
    }
    return list;
  }

  //회원수정 처리
  @PostMapping("/edit")
  public String edit(
    @Valid @ModelAttribute ModifyForm modifyForm,
    BindingResult bindingResult,
    RedirectAttributes redirectAttributes){

    //1) 유효성 체크  - 필드오류
    if(bindingResult.hasErrors()){
      log.info("bindingResult={}", bindingResult);
      return "member/modifyForm";
    }

    //2) 비밀번호가 일치하는지 체크
    if(!memberSVC.isMember(modifyForm.getEmail(), modifyForm.getPasswd())){
      bindingResult.rejectValue("passwd",null,"비밀번호가 잘못되었습니다.");
      return "member/modifyForm";
    }
    
    //3) 회원정보 수정
    Member member = new Member( null,
        modifyForm.getEmail(), modifyForm.getPasswd(), modifyForm.getNickname(),
        modifyForm.getGender().getDescription(),
        makeListToString(modifyForm.getHobby()),
        modifyForm.getRegion());

    memberSVC.modify(member);
    redirectAttributes.addAttribute("email", member.getEmail());

    return "redirect:/members/{email}/detail";  //회원 상세화면 이동
  }

  //회원상세
  @GetMapping("/{email}/detail")
  public String detail(@PathVariable String email){

    Member member = memberSVC.findByEmail(email);


    return "member/detailForm";
  }


  //마이페이지
  @GetMapping("/mypage")
  public String mypage(){

    return "member/mypage";
  }

}
