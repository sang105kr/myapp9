package com.kh.app3.web;

import com.kh.app3.domain.notice.svc.NoticeSVC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/notices")
public class NoticeController {

  private final NoticeSVC noticeSVC;

  //  등록화면
  @GetMapping("/add")
  public String addForm() {
    log.info("NoticeController.add() 호출됨!");
    return "notice/addForm";
  }
  //  등록처리
  @PostMapping("/add")
  public String add(){

    return "redirect:/notices/{noticeId}";
  }
  //  상세화면
  @GetMapping("/{noticeId}")
  public String detailForm(){

    return "notice/detailForm";
  }
  //  수정화면
  @GetMapping("/{noticeId}/edit")
  public String editForm(){
    return "notice/editForm";
  }
  //  수정처리
  @PostMapping("/{noticeId}/edit")
  public String edit(){

    return "redirect:/notices/{noticeId}";
  }
  //  삭제처리
  @GetMapping("{noticeId}/del")
  public String del(){

    return "redirect:/notices";
  }
  //  전체목록
  @GetMapping("")
  public String list(){

    return "notice/list";
  }

}
