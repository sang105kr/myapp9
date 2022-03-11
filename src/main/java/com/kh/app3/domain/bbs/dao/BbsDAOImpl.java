package com.kh.app3.domain.bbs.dao;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@ToString
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

  //목록
  @Override
  public List<Bbs> findAll() {
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT ");
    sql.append("  bbs_id, ");
    sql.append("  bcategory, ");
    sql.append("  title, ");
    sql.append("  email, ");
    sql.append("  nickname, ");
    sql.append("  hit, ");
    sql.append("  bcontent, ");
    sql.append("  pbbs_id, ");
    sql.append("  bgroup, ");
    sql.append("  step, ");
    sql.append("  bindent, ");
    sql.append("  status, ");
    sql.append("  cdate, ");
    sql.append("  udate ");
    sql.append("FROM ");
    sql.append("  bbs ");

    List<Bbs> list = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(Bbs.class));

    return list;
  }

  //조회
  @Override
  public Bbs findByBbsId(Long id) {
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT  ");
    sql.append("  bbs_id, ");
    sql.append("  bcategory,  ");
    sql.append("  title,  ");
    sql.append("  email,  ");
    sql.append("  nickname, ");
    sql.append("  hit,  ");
    sql.append("  bcontent, ");
    sql.append("  pbbs_id,  ");
    sql.append("  bgroup, ");
    sql.append("  step, ");
    sql.append("  bindent,  ");
    sql.append("  status, ");
    sql.append("  cdate,  ");
    sql.append("  udate ");
    sql.append("FROM  ");
    sql.append("  bbs ");
    sql.append("where bbs_id = ?  ");

    Bbs bbsItem = null;
    try {
      bbsItem = jdbcTemplate.queryForObject(
          sql.toString(),
          new BeanPropertyRowMapper<>(Bbs.class),
          id);
    }catch (Exception e){ // 1건을 못찾으면
      bbsItem = null;
    }
    
    return bbsItem;
  }

  //삭제
  @Override
  public int deleteByBbsId(Long id) {
    StringBuffer sql = new StringBuffer();
    sql.append("DELETE FROM bbs ");
    sql.append(" WHERE bbs_id = ? ");

    int updateItemCount = jdbcTemplate.update(sql.toString(), id);

    return updateItemCount;
  }

  //수정
  @Override
  public int updateByBbsId(Long id, Bbs bbs) {

    StringBuffer sql = new StringBuffer();
    sql.append("UPDATE bbs ");
    sql.append("   SET bcategory = ?, ");
    sql.append("       title = ?, ");
    sql.append("       bcontent = ?, ");
    sql.append("       udate = systimestamp ");
    sql.append(" WHERE bbs_id = ? ");

    int updatedItemCount = jdbcTemplate.update(
        sql.toString(),
        bbs.getBcategory(),
        bbs.getTitle(),
        bbs.getBcontent(),
        id
    );

    return updatedItemCount;
  }
}
