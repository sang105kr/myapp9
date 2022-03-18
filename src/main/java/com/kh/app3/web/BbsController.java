package com.kh.app3.web;

import com.kh.app3.domain.bbs.dao.Bbs;
import com.kh.app3.domain.bbs.svc.BbsSVC;
import com.kh.app3.domain.common.code.CodeDAO;
import com.kh.app3.web.form.bbs.*;
import com.kh.app3.web.form.login.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/bbs")
@RequiredArgsConstructor
public class BbsController {
  private final BbsSVC bbsSvc;
  private final CodeDAO codeDAO;

  //게시판 코드,디코드 가져오기
  @ModelAttribute("classifier")
  public List<Code> classifier(){
    return codeDAO.code("B01");
  }


  //작성양식
  @GetMapping("/add")
//  public String addForm(Model model) {
//    model.addAttribute("addForm", new AddForm());
//    return "bbs/addForm";
//  }
  public String addForm(
      Model model,
      HttpSession session) {

    LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);

    AddForm addForm = new AddForm();
    addForm.setEmail(loginMember.getEmail());
    addForm.setNickname(loginMember.getNickname());
    model.addAttribute("addForm", addForm);

    return "bbs/addForm";
  }

  //작성처리
  @PostMapping("/add")
  public String add(
      @Valid @ModelAttribute AddForm addForm,
      BindingResult bindingResult,      // 폼객체에 바인딩될때 오류내용이 저장되는 객체
      HttpSession session,
      RedirectAttributes redirectAttributes) {
    log.info("addForm={}",addForm);

    if(bindingResult.hasErrors()){
      log.info("add/bindingResult={}",bindingResult);
      return "bbs/addForm";
    }

    Bbs bbs = new Bbs();
//    bbs.setBcategory(addForm.getBcategory());
//    bbs.setTitle(addForm.getTitle());
//    bbs.setEmail(addForm.getEmail());
//    bbs.setNickname(addForm.getNickname());
//    bbs.setBcontent(addForm.getBcontent());
    BeanUtils.copyProperties(addForm, bbs);

    //세션 가져오기
    LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);
    //세션 정보가 없으면 로그인페이지로 이동
    if(loginMember == null){
      return "redirect:/login";
    }
    
    //세션에서 이메일,별칭가져오기
    bbs.setEmail(loginMember.getEmail());
    bbs.setNickname(loginMember.getNickname());

    Long originId = bbsSvc.saveOrigin(bbs);
    redirectAttributes.addAttribute("id", originId);
    // <=서버응답 302 get http://서버:port/bbs/10
    // =>클라이언트요청 get http://서버:port/bbs/10
    return "redirect:/bbs/{id}";
  }

  //목록
  @GetMapping
  public String list(Model model) {

    List<Bbs> list = bbsSvc.findAll();

    List<ListForm> partOfList = new ArrayList<>();
    for (Bbs bbs : list) {
      ListForm listForm = new ListForm();
      BeanUtils.copyProperties(bbs, listForm);
      partOfList.add(listForm);
    }

    model.addAttribute("list", partOfList);

    return "bbs/list";
  }

  //조회
    @GetMapping("/{id}")
    public String detail(
        @PathVariable Long id,
        Model model) {

      Bbs detailBbs = bbsSvc.findByBbsId(id);

      DetailForm detailForm = new DetailForm();
//    detailForm.setBcategory(detailBbs.getBcategory());
//    detailForm.setTitle(detailBbs.getTitle());
//    detailForm.setBcontent(detailBbs.getBcontent());
//    detailForm.setEmail(detailBbs.getEmail());
//    detailForm.setNickname(detailBbs.getNickname());
//    detailForm.setHit(detailBbs.getHit());
      BeanUtils.copyProperties(detailBbs, detailForm);
      model.addAttribute("detailForm", detailForm);

    return "bbs/detailForm";
  }

  //삭제
  @GetMapping("/{id}/del")
  public String del(@PathVariable Long id) {

    bbsSvc.deleteByBbsId(id);

    return "redirect:/bbs";
  }

  //수정양식
  @GetMapping("/{id}/edit")
  public String editForm(@PathVariable Long id,Model model){

    Bbs bbs = bbsSvc.findByBbsId(id);

    EditForm editForm = new EditForm();
    BeanUtils.copyProperties(bbs,editForm);
    model.addAttribute("editForm", editForm);

    log.info("editForm={}",editForm);

    return "bbs/editForm";
  }

  //수정처리
  @PostMapping("/{id}/edit")
  public String edit(@PathVariable Long id,
    @Valid @ModelAttribute EditForm editForm,
    BindingResult bindingResult,
    RedirectAttributes redirectAttributes
      ) {

      if(bindingResult.hasErrors()){
        return "bbs/editForm";
      }

      Bbs bbs = new Bbs();
      BeanUtils.copyProperties(editForm, bbs);
      bbsSvc.updateByBbsId(id,bbs);

      redirectAttributes.addAttribute("id",id);
    return "redirect:/bbs/{id}";
  }

  //답글작성양식
  @GetMapping("/{id}/reply")
  public String replyForm(@PathVariable Long id,
                          Model model,HttpSession session) {
    Bbs parentBbs = bbsSvc.findByBbsId(id);
    ReplyForm replyForm = new ReplyForm();
    replyForm.setBcategory(parentBbs.getBcategory());
    replyForm.setTitle("답글:"+parentBbs.getTitle());

    //세션에서 로그인정보 가져오기
    LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);
    replyForm.setEmail(loginMember.getEmail());
    replyForm.setNickname(loginMember.getNickname());

    model.addAttribute("replyForm", replyForm);
    return "bbs/replyForm";
  }

  //답글작성처리
  @PostMapping("/{id}/reply")
  public String reply(
    @PathVariable Long id,      //부모글의 bbsId
    @Valid ReplyForm replyForm,
    BindingResult bindingResult,
    RedirectAttributes redirectAttributes
                      ){
    if(bindingResult.hasErrors()){
      return "bbs/replyForm";
    }

    Bbs replyBbs = new Bbs();
    BeanUtils.copyProperties(replyForm, replyBbs);

    //부모글의 bcategory,bbsId,bgroup,step,bindent 참조
    appendInfoOfParentBbs(id, replyBbs);

    //답글저장(return 답글번호)
    Long replyBbsId = bbsSvc.saveReply(id, replyBbs);

    redirectAttributes.addAttribute("id",replyBbsId);
    return "redirect:/bbs/{id}";
  }

  //부모글의 bbsId,bgroup,step,bindent
  private void appendInfoOfParentBbs(Long parentBbsId, Bbs replyBbs) {
    Bbs parentBbs = bbsSvc.findByBbsId(parentBbsId);
    replyBbs.setBcategory(parentBbs.getBcategory());
    replyBbs.setPbbsId(parentBbs.getBbsId());
    replyBbs.setBgroup(parentBbs.getBgroup());
    replyBbs.setStep(parentBbs.getStep());
    replyBbs.setBindent(parentBbs.getBindent());
  }
}




