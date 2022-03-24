package com.kh.app3.domain.common.file;

import com.kh.app3.domain.common.file.dao.UploadFileDAO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
class UploadFileDAOImplTest {

  @Autowired
  private UploadFileDAO uploadFileDAO;

  @Test
  @DisplayName("업로드-단건")
  void addFile() {
    UploadFile uploadFile = new UploadFile();

    uploadFile.setCode("F0101");
    uploadFile.setRid(88l);
    uploadFile.setStore_filename("xxx-yyy-zz2.png");
    uploadFile.setUpload_filename("커피2.png");
    uploadFile.setFsize("200");
    uploadFile.setFtype("image/png");

    Long fid = uploadFileDAO.addFile(uploadFile);
    log.info("fid={}",fid);
  }

  @Test
  @DisplayName("업로드-여러건")
  void testAddFile() {
    List<UploadFile> list = new ArrayList<>();
    for(int i=0; i<10; i++){
      UploadFile uploadFile = new UploadFile();
      uploadFile.setCode("F0101");
      uploadFile.setRid(88l);
      uploadFile.setStore_filename("xxx-yyy-zz2.png");
      uploadFile.setUpload_filename("커피2.png");
      uploadFile.setFsize("200");
      uploadFile.setFtype("image/png");
      list.add(uploadFile);
    }
    uploadFileDAO.addFile(list);
  }
}