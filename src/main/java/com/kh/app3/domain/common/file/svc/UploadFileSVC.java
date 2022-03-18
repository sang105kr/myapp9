package com.kh.app3.domain.common.file.svc;

import com.kh.app3.domain.common.file.UploadFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadFileSVC {

  /**
   * 업로드파일 처리-단건
   * @param code  분류코드(ex 계시판,상품등록..)
   * @param fid   참조ID(ex 게시글번호, 상품번호..)
   * @param file  첨부파일
   * @return 성공여부
   */
  boolean addFile(String code, Long fid, MultipartFile file);
  /**
   * 업로드파일 처리-여러건
   * @param code  분류코드(ex 계시판,상품등록..)
   * @param fid   참조ID(ex 게시글번호, 상품번호..)
   * @param files  첨부파일
   * @return 성공여부
   */
  boolean addFile(String code, Long fid, List<MultipartFile> files);

}