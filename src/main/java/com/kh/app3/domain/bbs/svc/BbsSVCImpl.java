package com.kh.app3.domain.bbs.svc;

import com.kh.app3.domain.bbs.dao.Bbs;
import com.kh.app3.domain.bbs.dao.BbsDAO;
import com.kh.app3.domain.common.file.UploadFile;
import com.kh.app3.domain.common.file.dao.UploadFileDAO;
import com.kh.app3.domain.common.file.svc.UploadFileSVC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BbsSVCImpl implements BbsSVC{

  private final BbsDAO bbsDAO;
  private final UploadFileSVC uploadFileSVC;

  private String CODE = "F0101";

  //원글
  @Override
  public Long saveOrigin(Bbs bbs) {
    return bbsDAO.saveOrigin(bbs);
  }

  //원글-첨부파일
  @Override
  public Long saveOrigin(Bbs bbs, List<MultipartFile> files) {

    //1)원글 저장
    Long bbsId = saveOrigin(bbs);

    //2)첨부 저장
    uploadFileSVC.addFile(CODE,bbsId,files);

    return bbsId;
  }

  //목록
  @Override
  public List<Bbs> findAll() {
    return bbsDAO.findAll();
  }

  //상세조회
  @Override
  public Bbs findByBbsId(Long id) {
    Bbs findedItem = bbsDAO.findByBbsId(id);
    bbsDAO.increaseHitCount(id);
    return findedItem;
  }

  //삭제
  @Override
  public int deleteByBbsId(Long id) {
    return bbsDAO.deleteByBbsId(id);
  }

  //수정
  @Override
  public int updateByBbsId(Long id, Bbs bbs) {
    return bbsDAO.updateByBbsId(id, bbs);
  }

  //답글
  @Override
  public Long saveReply(Long pbbsId, Bbs replyBbs) {
    return bbsDAO.saveReply(pbbsId, replyBbs);
  }

  //전체건수
  @Override
  public int totalCount() {
    return bbsDAO.totalCount();
  }
}
