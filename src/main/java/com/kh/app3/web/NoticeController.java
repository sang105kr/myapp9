package com.kh.app3.web;

import com.kh.app3.domain.notice.Notice;
import com.kh.app3.domain.notice.svc.NoticeSVC;
import com.kh.app3.web.form.notice.AddForm;
import com.kh.app3.web.form.notice.DetailForm;
import com.kh.app3.web.form.notice.EditForm;
import com.kh.app3.web.form.notice.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/notices")
public class NoticeController {

  private final NoticeSVC noticeSVC;

  //  등록화면
  @GetMapping("")
  public String addForm(@ModelAttribute AddForm addFrom) {
    log.info("NoticeController.addForm() 호출됨!");
    return "notice/addForm";
  }

//  public String addForm(Model model) {
//    log.info("NoticeController.addForm() 호출됨!");
//    model.addAttribute("addForm",new AddForm());
//    return "notice/addForm";
//  }
  //  등록처리
  @PostMapping("")
  public String add(
      @ModelAttribute AddForm addForm,
      RedirectAttributes redirectAttributes,
      Model model){

    log.info("NoticeController.add() 호출됨!");
    log.info("Adform={}",addForm);

    Notice notice = new Notice();
    notice.setSubject(addForm.getSubject());
    notice.setContent(addForm.getContent());
    notice.setAuthor(addForm.getAuthor());

    Notice writedNotice = noticeSVC.write(notice);
    redirectAttributes.addAttribute("noticeId",writedNotice.getNoticeId());

    return "redirect:/notices/{noticeId}/detail";  //http://서버:9080/notices/공지사항번호
  }
  //  상세화면
  @GetMapping("/{noticeId}/detail")
  public String detailForm(@PathVariable Long noticeId, Model model){

    Notice notice = noticeSVC.findByNoticeId(noticeId);

    DetailForm detailForm = new DetailForm();
    detailForm.setNoticeId(notice.getNoticeId());
    detailForm.setSubject(notice.getSubject());
    detailForm.setContent(notice.getContent());
    detailForm.setAuthor(notice.getAuthor());

    model.addAttribute("detailForm",detailForm);

    return "notice/detailForm";
  }
  //  수정화면
  @GetMapping("/{noticeId}")
  public String editForm(@PathVariable Long noticeId, Model model){

    Notice notice = noticeSVC.findByNoticeId(noticeId);

    EditForm editForm = new EditForm();
    editForm.setNoticeId(notice.getNoticeId());
    editForm.setSubject(notice.getSubject());
    editForm.setContent(notice.getContent());
    editForm.setAuthor(notice.getAuthor());

    model.addAttribute("editForm", editForm);

    return "notice/editForm";
  }
  //  수정처리
  @PatchMapping("/{noticeId}")
  public String edit(
      @ModelAttribute EditForm editForm,
      @PathVariable Long noticeId,
      RedirectAttributes redirectAttributes
  ){

    Notice notice = new Notice();
    notice.setNoticeId(noticeId);
    notice.setSubject(editForm.getSubject());
    notice.setContent(editForm.getContent());
    Notice modifiedNotice = noticeSVC.modify(notice);

    log.info("notice={}", notice);

    redirectAttributes.addAttribute("noticeId", modifiedNotice.getNoticeId());

    return "redirect:/notices/{noticeId}/detail";
  }
  //  삭제처리
  @DeleteMapping("{noticeId}")
  public String del(@PathVariable Long noticeId){

    noticeSVC.remove(noticeId);

    return "redirect:/notices/all";
  }
  //  전체목록
  @GetMapping("/all")
  public String list(Model model){

    List<Notice> list = noticeSVC.findAll();

    List<Item> notices = new ArrayList<>();
    for (Notice notice : list) {
      Item item = new Item();
      item.setNoticeId(notice.getNoticeId());
      item.setSubject(notice.getSubject());
      item.setCdate(notice.getCdate().toLocalDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))); //LocalDateTime => LocalDate
      item.setCtime(notice.getCdate().toLocalTime().format(DateTimeFormatter.ofPattern("H:m:s")));
      item.setHit(notice.getHit());
      notices.add(item);
    }

    model.addAttribute("notices", notices);
    return "notice/list";
  }

}
