package com.kh.app3.web;

import com.kh.app3.domain.member.Member;
import com.kh.app3.domain.member.svc.MemberSVC;
import com.kh.app3.web.api.ApiResult;
import com.kh.app3.web.form.member.DetailForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ApiMemberController {

  private final MemberSVC memberSVC;

  @ResponseBody //http응답 메세지 바디에 직접 쓰기
                //(반환타입이 객체이면 java객체=>json포맷 문자열로 변환후)
  @GetMapping("/api/members")
  public ApiResult<List<Member>> members(){
    List<Member> list = memberSVC.findAll();
    ApiResult<List<Member>> result = new ApiResult<>("00","success",list);
    return result;
  }

  @ResponseBody
  @GetMapping("/api/member")
  public ApiResult<Member> member(@RequestParam String email){
    Member member = memberSVC.findByEmail(email);
    ApiResult<Member> result = new ApiResult<>("00","success",member);
    return result;
  }

  @ResponseBody
  @GetMapping("/api/members/{email}/exist")
  public ApiResult<Member> existMember(@PathVariable String email){

    boolean existMember = memberSVC.existMember(email);

    if(existMember){
      return new ApiResult("00","success","OK");
    }else{
      return new ApiResult("99","fail","NOK");
    }
  }
}
