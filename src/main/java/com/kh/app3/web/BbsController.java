package com.kh.app3.web;

import com.kh.app3.domain.bbs.svc.BbsSVC;
import com.kh.app3.domain.common.CodeDAO;
import com.kh.app3.web.form.bbs.AddForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
  public String addForm(@ModelAttribute AddForm addForm) {
    return "bbs/addForm";
  }

  //작성처리
  @PostMapping("/add")
  public String add(@ModelAttribute AddForm addForm) {

    return "redirect:/bbs/{id}";
  }

  //목록
  @GetMapping
  public String list() {

    return "bbs/list";
  }

  //조회
  @GetMapping("/{id}")
  public String detail() {

    return "bbs/detail";
  }

  //삭제
  @GetMapping("/{id}/del")
  public String del() {
    return "redirect:/bbs";
  }

  //수정양식
  @GetMapping("/{id}/edit")
  public String editForm() {
    return "bbs/editForm";
  }

  //수정처리
  @PostMapping("/{id}/edit")
  public String edit() {
    return "redirect:/bbs/{id}";
  }

  //답글작성양식
  @GetMapping("/{id}/reply")
  public String replyForm() {
    return "bbs/replyForm";
  }

  //답글작성처리
  @PostMapping("/{id}/reply")
  public String reply(){

    return "redirect:/bbs/{id}";
  }
}
