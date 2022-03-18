package com.kh.app3.domain.common.file;

import java.util.List;

public interface UploadFileDAO {

  /**
   * 업로드 파일 등록 - 단건
   * @param uploadFile
   * @return 파일Id
   */
  Long addFile(UploadFile uploadFile);

  /**
   * 업로드 파일 등록 - 여러건
   * @param uploadFile
   */
  void addFile(List<UploadFile> uploadFile);

}
