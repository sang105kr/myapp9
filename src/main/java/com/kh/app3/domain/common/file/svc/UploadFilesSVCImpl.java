package com.kh.app3.domain.common.file.svc;

import com.kh.app3.domain.common.file.UploadFile;
import com.kh.app3.domain.common.file.dao.UploadFileDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UploadFilesSVCImpl implements UploadFileSVC{

  private final UploadFileDAO uploadFileDAO;

  //첨부파일 저장될 파일시스템의 경로 application.properties에 정의
  @Value("${attach.root_dir}")
  private String ROOT_DIR;  //첨부파일 루트경로

  @Override
  public boolean addFile(String code, Long fid, MultipartFile file) {
    try {
      UploadFile uploadFile = new UploadFile();
      uploadFile.setCode(code);
      uploadFile.setRid(fid);

      String originalFileName = file.getOriginalFilename();
      String storeFileName = createStoreFilename(originalFileName);
      uploadFile.setStore_finename(storeFileName);
      uploadFile.setUpload_filename(originalFileName);

      uploadFile.setFsize(String.valueOf(file.getSize()));
      uploadFile.setFtype(file.getContentType());

      //파일시스템에 물리적 파일 저장
      storeFile(code, file);

      //uploadfile 테이블에 첨부파일 메타정보 저장
      uploadFileDAO.addFile(uploadFile);

    }catch (Exception e){
      e.printStackTrace();
      return false;
    }
    return true;
  }

  @Override
  public boolean addFile(String code, Long fid, List<MultipartFile> files) {
    //1) uploadfile 테이블에 첨부파일 메타정보 저장
    //2) 파일시스템에 물리적 파일 저장
    try {
      List<UploadFile> uploadFiles = new ArrayList<>();

      for(MultipartFile file: files) {
        UploadFile uploadFile = new UploadFile();
        uploadFile.setCode(code);
        uploadFile.setRid(fid);

        String originalFileName = file.getOriginalFilename();
        String storeFileName = createStoreFilename(originalFileName);
        uploadFile.setStore_finename(storeFileName);
        uploadFile.setUpload_filename(originalFileName);

        uploadFile.setFsize(String.valueOf(file.getSize()));
        uploadFile.setFtype(file.getContentType());

        uploadFiles.add(uploadFile);
        storeFile(code, file);
      }
      uploadFileDAO.addFile(uploadFiles);
    }catch (Exception e){
      e.printStackTrace();
      return false;
    }
    return true;
  }

  //파일시스템에 물리적 파일 저장
  private void storeFile(String code, MultipartFile file) {
    try {
      String originalFileName = file.getOriginalFilename();
      String storeFileName = createStoreFilename(originalFileName);
      file.transferTo(Path.of(getFullPath(code), storeFileName));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  //파일저장경로
  private String getFullPath(String code) {
    StringBuffer path = new StringBuffer();
    path = path.append(ROOT_DIR).append(code).append("/");
    //경로가 없으면 생성
    createFolder(path.toString());
    return path.toString();
  }

  //폴더생성
  private void createFolder(String path) {
    File folder = new File(path);
    if(!folder.exists()){
      folder.mkdir();
    }
  }

  //임의파일명 생성
  private String createStoreFilename(String originalFile) {
    StringBuffer storeFileName = new StringBuffer();
    storeFileName.append(UUID.randomUUID().toString())
        .append(".")
        .append(extractExt(originalFile)); // xxx-yyy-zzz-ttt..
    return storeFileName.toString();
  }

  //확장자 추출
  private String extractExt(String originalFile) {
    int posOfExt =originalFile.lastIndexOf(".");
    String ext = originalFile.substring(posOfExt + 1);
    return ext;
  }

}
