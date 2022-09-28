package com.kh.app3.web;

import com.kh.app3.domain.bbs.dao.Bbs;
import com.kh.app3.domain.bbs.dao.BbsFilterCondition;
import com.kh.app3.domain.bbs.svc.BbsSVC;
import com.kh.app3.domain.common.code.Code;
import com.kh.app3.domain.common.code.CodeDAO;
import com.kh.app3.domain.common.file.UploadFile;
import com.kh.app3.domain.common.file.svc.UploadFileSVC;
import com.kh.app3.domain.common.paging.FindCriteria;
import com.kh.app3.web.form.bbs.*;
import com.kh.app3.web.form.login.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/bbs")
@RequiredArgsConstructor
public class BbsController {
  private final BbsSVC bbsSvc;
  private final CodeDAO codeDAO;
  private final UploadFileSVC uploadFileSVC;

  @Autowired
  @Qualifier("fc10") //동일한 타입의 객체가 여러개있을때 빈이름을 명시적으로 지정해서 주입받을때
  private FindCriteria fc;

  //게시판 코드,디코드 가져오기
  @ModelAttribute("classifier")
  public List<Code> classifier(){
    return codeDAO.code("B01");
  }

  @ModelAttribute("bbsTitle")
  public Map<String,String> bbsTitle(){
    List<Code> codes = codeDAO.code("B01");
    Map<String,String> btitle = new HashMap<>();
    for (Code code : codes) {
      btitle.put(code.getCode(), code.getDecode());
    }
    return btitle;
  }

  //작성양식
  @GetMapping("/add")
//  public String addForm(Model model) {
//    model.addAttribute("addForm", new AddForm());
//    return "bbs/addForm";
//  }
  public String addForm(
      Model model,
      @RequestParam(required = false) Optional<String> category,
      HttpSession session) {

    String cate = getCategory(category);

    LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);

    AddForm addForm = new AddForm();
    addForm.setEmail(loginMember.getEmail());
    addForm.setNickname(loginMember.getNickname());
    model.addAttribute("addForm", addForm);
    model.addAttribute("category", cate);

    return "bbs/addForm";
  }

  //작성처리
  @PostMapping("/add")
  public String add(
      //@Valid
      @ModelAttribute AddForm addForm,
      @RequestParam(required = false) Optional<String> category,
      BindingResult bindingResult,      // 폼객체에 바인딩될때 오류내용이 저장되는 객체
      HttpSession session,
      RedirectAttributes redirectAttributes) throws IOException {
    log.info("addForm={}",addForm);

    if(bindingResult.hasErrors()){
      log.info("add/bindingResult={}",bindingResult);
      return "bbs/addForm";
    }

    String cate = getCategory(category);

    Bbs bbs = new Bbs();
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

    
    Long originId = 0l;
    //파일첨부유무
    if(addForm.getFiles().size() == 0) {
      originId = bbsSvc.saveOrigin(bbs);
    }else{
      originId = bbsSvc.saveOrigin(bbs, addForm.getFiles());
    }
    redirectAttributes.addAttribute("id", originId);
    redirectAttributes.addAttribute("category",cate);
    // <=서버응답 302 get http://서버:port/bbs/10
    // =>클라이언트요청 get http://서버:port/bbs/10
    return "redirect:/bbs/{id}";
  }

  //목록
//  @GetMapping("/list")
//  public String list(
//      Model model) {
//
//    List<Bbs> list = bbsSvc.findAll();
//
//    List<ListForm> partOfList = new ArrayList<>();
//    for (Bbs bbs : list) {
//      ListForm listForm = new ListForm();
//      BeanUtils.copyProperties(bbs, listForm);
//      partOfList.add(listForm);
//    }
//
//    model.addAttribute("list", partOfList);
//
//    return "bbs/list";
//  }

  @GetMapping({"/list",
               "/list/{reqPage}",
               "/list/{reqPage}//",
               "/list/{reqPage}/{searchType}/{keyword}"})
  public String listAndReqPage(
      @PathVariable(required = false) Optional<Integer> reqPage,
      @PathVariable(required = false) Optional<String> searchType,
      @PathVariable(required = false) Optional<String> keyword,
      @RequestParam(required = false) Optional<String> category,
      Model model) {
    log.info("/list 요청됨{},{},{},{}",reqPage,searchType,keyword,category);

    String cate = getCategory(category);

    //FindCriteria 값 설정
    fc.getRc().setReqPage(reqPage.orElse(1)); //요청페이지, 요청없으면 1
    fc.setSearchType(searchType.orElse(""));  //검색유형
    fc.setKeyword(keyword.orElse(""));        //검색어

    List<Bbs> list = null;
    //게시물 목록 전체
    if(category == null || StringUtils.isEmpty(cate)) {

      //검색어 있음
      if(searchType.isPresent() && keyword.isPresent()){
        BbsFilterCondition filterCondition = new BbsFilterCondition(
            "",fc.getRc().getStartRec(), fc.getRc().getEndRec(),
            searchType.get(),
            keyword.get());
        fc.setTotalRec(bbsSvc.totalCount(filterCondition));
        fc.setSearchType(searchType.get());
        fc.setKeyword(keyword.get());
        list = bbsSvc.findAll(filterCondition);

      //검색어 없음  
      }else {
        //총레코드수
        fc.setTotalRec(bbsSvc.totalCount());
        list = bbsSvc.findAll(fc.getRc().getStartRec(), fc.getRc().getEndRec());
      }

    //카테고리별 목록
    }else{
      //검색어 있음
      if(searchType.isPresent() && keyword.isPresent()){
        BbsFilterCondition filterCondition = new BbsFilterCondition(
            category.get(),fc.getRc().getStartRec(), fc.getRc().getEndRec(),
            searchType.get(),
            keyword.get());
        fc.setTotalRec(bbsSvc.totalCount(filterCondition));
        fc.setSearchType(searchType.get());
        fc.setKeyword(keyword.get());
        list = bbsSvc.findAll(filterCondition);
      //검색어 없음
      }else {
        fc.setTotalRec(bbsSvc.totalCount(cate));
        list = bbsSvc.findAll(cate, fc.getRc().getStartRec(), fc.getRc().getEndRec());
      }
    }

    List<ListForm> partOfList = new ArrayList<>();
    for (Bbs bbs : list) {
      ListForm listForm = new ListForm();
      BeanUtils.copyProperties(bbs, listForm);
      partOfList.add(listForm);
    }

    model.addAttribute("list", partOfList);
    model.addAttribute("fc",fc);
    model.addAttribute("category", cate);

    return "bbs/list";
  }

  //조회
    @GetMapping("/{id}")
    public String detail(
        @PathVariable Long id,
        @RequestParam(required = false) Optional<String> category,
        Model model) {

      String cate = getCategory(category);

      Bbs detailBbs = bbsSvc.findByBbsId(id);
      DetailForm detailForm = new DetailForm();
      BeanUtils.copyProperties(detailBbs, detailForm);
      model.addAttribute("detailForm", detailForm);
      model.addAttribute("category", cate);

      //첨부조회
      List<UploadFile> attachFiles = uploadFileSVC.getFilesByCodeWithRid(detailBbs.getBcategory(), detailBbs.getBbsId());
      if(attachFiles.size() > 0){
        log.info("attachFiles={}",attachFiles);
        model.addAttribute("attachFiles", attachFiles);
      }

      return "bbs/detailForm";
  }
  
  //삭제
  @GetMapping("/{id}/del")
  public String del(
      @PathVariable Long id,
      @RequestParam(required = false) Optional<String> category) {

    bbsSvc.deleteByBbsId(id);
    String cate = getCategory(category);
    return "redirect:/bbs/list?category="+cate;
  }

  //수정양식
  @GetMapping("/{id}/edit")
  public String editForm(
      @PathVariable Long id,
      @RequestParam(required = false) Optional<String> category,
      Model model){
    String cate = getCategory(category);
    Bbs bbs = bbsSvc.findByBbsId(id);

    EditForm editForm = new EditForm();
    BeanUtils.copyProperties(bbs,editForm);
    model.addAttribute("editForm", editForm);
    model.addAttribute("category",cate);

    //첨부조회
    List<UploadFile> attachFiles = uploadFileSVC.getFilesByCodeWithRid(bbs.getBcategory(), bbs.getBbsId());
    if(attachFiles.size() > 0){
      log.info("attachFiles={}",attachFiles);
      model.addAttribute("attachFiles", attachFiles);
    }

    return "bbs/editForm";
  }

  //수정처리
  @PostMapping("/{id}/edit")
  public String edit(
    @PathVariable Long id,
    @RequestParam(required = false) Optional<String> category,
    @Valid @ModelAttribute EditForm editForm,
    BindingResult bindingResult,
    RedirectAttributes redirectAttributes
      ) {

    if(bindingResult.hasErrors()){
      return "bbs/editForm";
    }

    String cate = getCategory(category);
    Bbs bbs = new Bbs();
    BeanUtils.copyProperties(editForm, bbs);
    bbsSvc.updateByBbsId(id,bbs);

    if(editForm.getFiles().size() == 0) {
      bbsSvc.updateByBbsId(id, bbs);
    }else{
      bbsSvc.updateByBbsId(id, bbs, editForm.getFiles());
    }
    redirectAttributes.addAttribute("id",id);
    redirectAttributes.addAttribute("category", cate);

    return "redirect:/bbs/{id}";
  }

  //답글작성양식
  @GetMapping("/{id}/reply")
  public String replyForm(@PathVariable Long id,
                          @RequestParam(required = false) Optional<String> category,
                          Model model,HttpSession session) {

    String cate = getCategory(category);

    Bbs parentBbs = bbsSvc.findByBbsId(id);
    ReplyForm replyForm = new ReplyForm();
    replyForm.setBcategory(parentBbs.getBcategory());
    replyForm.setTitle("답글:"+parentBbs.getTitle());

    //세션에서 로그인정보 가져오기
    LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);
    replyForm.setEmail(loginMember.getEmail());
    replyForm.setNickname(loginMember.getNickname());

    model.addAttribute("replyForm", replyForm);
    model.addAttribute("category", cate);
    return "bbs/replyForm";
  }

  //답글작성처리
  @PostMapping("/{id}/reply")
  public String reply(
    @PathVariable Long id,      //부모글의 bbsId
    @RequestParam(required = false) Optional<String> category,
    @Valid @ModelAttribute ReplyForm replyForm,
    BindingResult bindingResult,
    RedirectAttributes redirectAttributes
                      ){
    if(bindingResult.hasErrors()){
      return "bbs/replyForm";
    }
    String cate = getCategory(category);
    Bbs replyBbs = new Bbs();
    BeanUtils.copyProperties(replyForm, replyBbs);

    //부모글의 bcategory,bbsId,bgroup,step,bindent 참조
    appendInfoOfParentBbs(id, replyBbs);

    //답글저장(return 답글번호)
    Long replyBbsId = 0L;

    if(replyForm.getFiles().size() == 0) {
      replyBbsId = bbsSvc.saveReply(id, replyBbs);

    }else{
      replyBbsId = bbsSvc.saveReply(id, replyBbs, replyForm.getFiles());
    }
    redirectAttributes.addAttribute("id",replyBbsId);
    redirectAttributes.addAttribute("category", cate);
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

  //쿼리스트링 카테고리 읽기, 없으면 ""반환
  private String getCategory(Optional<String> category) {
    String cate = category.isPresent()? category.get():"";
    log.info("category={}", cate);
    return cate;
  }
}




