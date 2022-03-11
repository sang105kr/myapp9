package com.kh.app3.domain.bbs.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BbsDAOImpl implements BbsDAO{

  private final JdbcTemplate jdbcTemplate;

  //원글작성
  @Override
  public Long saveOrigin(Bbs bbs) {
    StringBuffer sql = new StringBuffer();
    sql.append("insert into bbs (bbs_id,bcategory,title,email,nickname,bcontent,bgroup) ");
    sql.append("values(bbs_bbs_id_seq.nextval,?,?,?,?,?,bbs_bbs_id_seq.currval) ");


    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(sql.toString(), new String[]{"bbs_id"});
        pstmt.setString(1,bbs.getBcategory());
        pstmt.setString(2, bbs.getTitle());
        pstmt.setString(3, bbs.getEmail());
        pstmt.setString(4, bbs.getNickname());
        pstmt.setString(5, bbs.getBcontent());
        return pstmt;
      }
    },keyHolder);

    return Long.valueOf(keyHolder.getKeys().get("bbs_id").toString());
  }

  @Override
  public List<Bbs> findAll() {
    return null;
  }

  @Override
  public Bbs findByBbsId(Long id) {
    return null;
  }

  @Override
  public int deleteByBbsId(Long id) {
    return 0;
  }

  @Override
  public int updateByBbsId(Long id, Bbs bbs) {
    return 0;
  }
}
