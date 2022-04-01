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
import org.thymeleaf.util.StringUtils;

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
    sql.append("Order by bgroup desc, step asc ");

    List<Bbs> list = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(Bbs.class));

    return list;
  }

  //카테고리별 목록
  @Override
  public List<Bbs> findAll(String category) {
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
    sql.append("WHERE bcategory = ? ");
    sql.append("Order by bgroup desc, step asc ");

    List<Bbs> list = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(Bbs.class),category);

    return list;
  }

  @Override
  public List<Bbs> findAll(int startRec, int endRec) {
    StringBuffer sql = new StringBuffer();
    sql.append("select t1.* ");
    sql.append("from( ");
    sql.append("    SELECT ");
    sql.append("    ROW_NUMBER() OVER (ORDER BY bgroup DESC, step ASC) no, ");
    sql.append("    bbs_id, ");
    sql.append("    bcategory, ");
    sql.append("    title, ");
    sql.append("    email, ");
    sql.append("    nickname, ");
    sql.append("    hit, ");
    sql.append("    bcontent, ");
    sql.append("    pbbs_id, ");
    sql.append("    bgroup, ");
    sql.append("    step, ");
    sql.append("    bindent, ");
    sql.append("    status, ");
    sql.append("    cdate, ");
    sql.append("    udate ");
    sql.append("    FROM bbs) t1 ");
    sql.append("where t1.no between ? and ? ");

    List<Bbs> list = jdbcTemplate.query(
        sql.toString(),
        new BeanPropertyRowMapper<>(Bbs.class),
        startRec, endRec
    );
    return list;
  }

  @Override
  public List<Bbs> findAll(String category, int startRec, int endRec) {
    StringBuffer sql = new StringBuffer();
    sql.append("select t1.* ");
    sql.append("from( ");
    sql.append("    SELECT ");
    sql.append("      ROW_NUMBER() OVER (ORDER BY bgroup DESC, step ASC) no, ");
    sql.append("      bbs_id, ");
    sql.append("      bcategory, ");
    sql.append("      title, ");
    sql.append("      email, ");
    sql.append("      nickname, ");
    sql.append("      hit, ");
    sql.append("      bcontent, ");
    sql.append("      pbbs_id, ");
    sql.append("      bgroup, ");
    sql.append("      step, ");
    sql.append("      bindent, ");
    sql.append("      status, ");
    sql.append("      cdate, ");
    sql.append("      udate ");
    sql.append("    FROM bbs ");
    sql.append("   where bcategory = ? ) t1 ");
    sql.append("where t1.no between ? and ? ");

    List<Bbs> list = jdbcTemplate.query(
        sql.toString(),
        new BeanPropertyRowMapper<>(Bbs.class),
        category, startRec, endRec
    );
    return list;
  }

  //검색
  @Override
  public List<Bbs> findAll(BbsFilterCondition filterCondition) {
    StringBuffer sql = new StringBuffer();
    sql.append("select t1.* ");
    sql.append("from( ");
    sql.append("    SELECT  ROW_NUMBER() OVER (ORDER BY bgroup DESC, step ASC) no, ");
    sql.append("            bbs_id, ");
    sql.append("            bcategory, ");
    sql.append("            title, ");
    sql.append("            email, ");
    sql.append("            nickname, ");
    sql.append("            hit, ");
    sql.append("            bcontent, ");
    sql.append("            pbbs_id, ");
    sql.append("            bgroup, ");
    sql.append("            step, ");
    sql.append("            bindent, ");
    sql.append("            status, ");
    sql.append("            cdate, ");
    sql.append("            udate ");
    sql.append("      FROM bbs ");
    sql.append("     WHERE ");

    //분류
    sql = dynamicQuery(filterCondition, sql);

    sql.append(") t1 ");
    sql.append("where t1.no between ? and ? ");


    List<Bbs> list = null;

    //게시판 전체
    if(StringUtils.isEmpty(filterCondition.getCategory())){
      list = jdbcTemplate.query(
          sql.toString(),
          new BeanPropertyRowMapper<>(Bbs.class),
          filterCondition.getStartRec(),
          filterCondition.getEndRec()
      );
    //게시판 분류
    }else{
      list = jdbcTemplate.query(
          sql.toString(),
          new BeanPropertyRowMapper<>(Bbs.class),
          filterCondition.getCategory(),
          filterCondition.getStartRec(),
          filterCondition.getEndRec()
      );
    }

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

  //답글
  @Override
  public Long saveReply(Long pbbsId, Bbs replyBbs) {

    //부모글 참조반영
    Bbs bbs = addInfoOfParentToChild(pbbsId,replyBbs);

    StringBuffer sql = new StringBuffer();
    sql.append("insert into bbs (bbs_id,bcategory,title,email,nickname,bcontent,pbbs_id,bgroup,step,bindent) ");
    sql.append("values(bbs_bbs_id_seq.nextval,?,?,?,?,?,?,?,?,?) ");

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(sql.toString(), new String[]{"bbs_id"});
        pstmt.setString(1, bbs.getBcategory());
        pstmt.setString(2, bbs.getTitle());
        pstmt.setString(3, bbs.getEmail());
        pstmt.setString(4, bbs.getNickname());
        pstmt.setString(5, bbs.getBcontent());
        pstmt.setLong(6, bbs.getPbbsId());
        pstmt.setLong(7, bbs.getBgroup());
        pstmt.setInt(8, bbs.getStep());
        pstmt.setInt(9, bbs.getBindent());
        return pstmt;
      }
    },keyHolder);

    return Long.valueOf(keyHolder.getKeys().get("bbs_id").toString());
  }

  //답글에 부모정보 반영하기
  private Bbs addInfoOfParentToChild(Long pbbsId, Bbs replyBbs) {
    //부모글
    Bbs bbs = findByBbsId(pbbsId);

    //부모글의 카테고리 가져오기
    replyBbs.setBcategory(bbs.getBcategory());

    //bgroup 로직
    // 답글의 bgroup = 부모글의 bgroup
    replyBbs.setBgroup(bbs.getBgroup());

    //step 로직
    //1) 부모글의 bgroup값과 동일한 게시글중 부모글의 step보다큰 게시글의 bstep을 1씩 증가
    int affectedRows = updateBstep(bbs);
    //2) 답글의 bstep값은 부모글의 bstep값 + 1
    replyBbs.setStep(bbs.getStep()+1);

    //bindent 로직
    // 답글의 bindent = 부모글의 bindent + 1
    replyBbs.setBindent(bbs.getBindent()+1);

    replyBbs.setPbbsId(pbbsId);
    return replyBbs;
  }

  //부모글과 동일한그룹 bstep반영
  private int updateBstep(Bbs bbs) {
    StringBuffer sql = new StringBuffer();

    sql.append("update bbs ");
    sql.append("   set step = step + 1 ");
    sql.append(" where bgroup =  ? ");
    sql.append("   and step  >  ? ");

    int affectedRows = jdbcTemplate.update(sql.toString(), bbs.getBgroup(), bbs.getStep());

    return affectedRows;
  }

  //조회수증가
  @Override
  public int increaseHitCount(Long id) {
    StringBuffer sql = new StringBuffer();
    sql.append("update bbs  ");
    sql.append("set hit = hit + 1 ");
    sql.append("where bbs_id = ? ");

    int affectedRows = jdbcTemplate.update(sql.toString(), id);

    return affectedRows;
  }

  //전체건수
  @Override
  public int totalCount() {

    String sql = "select count(*) from bbs";

    Integer cnt = jdbcTemplate.queryForObject(sql, Integer.class);

    return cnt;
  }

  @Override
  public int totalCount(String bcategory) {

    String sql = "select count(*) from bbs where bcategory = ? ";

    Integer cnt = jdbcTemplate.queryForObject(sql, Integer.class, bcategory);

    return cnt;
  }

  @Override
  public int totalCount(BbsFilterCondition filterCondition) {

    StringBuffer sql = new StringBuffer();

    sql.append("select count(*) ");
    sql.append("  from bbs  ");
    sql.append(" where  ");

    sql = dynamicQuery(filterCondition, sql);

    Integer cnt = 0;
    //게시판 전체 검색 건수
    if(StringUtils.isEmpty(filterCondition.getCategory())) {
      cnt = jdbcTemplate.queryForObject(
              sql.toString(), Integer.class
            );
    //게시판 분류별 검색 건수
    }else{
      cnt = jdbcTemplate.queryForObject(
              sql.toString(), Integer.class,
              filterCondition.getCategory()
            );
    }

    return cnt;
  }

  private StringBuffer dynamicQuery(BbsFilterCondition filterCondition, StringBuffer sql) {
    //분류
    if(StringUtils.isEmpty(filterCondition.getCategory())){

    }else{
      sql.append("       bcategory = ? ");
    }

    //분류,검색유형,검색어 존재
    if(!StringUtils.isEmpty(filterCondition.getCategory()) &&
        !StringUtils.isEmpty(filterCondition.getSearchType()) &&
        !StringUtils.isEmpty(filterCondition.getKeyword())){

      sql.append(" AND ");
    }

    //검색유형
    switch (filterCondition.getSearchType()){
      case "TC":  //제목 + 내용
        sql.append("    (  title    like '%"+ filterCondition.getKeyword()+"%' ");
        sql.append("    or bcontent like '%"+ filterCondition.getKeyword()+"%' )");
        break;
      case "T":   //제목
        sql.append("       title    like '%"+ filterCondition.getKeyword()+"%' ");
        break;
      case "C":   //내용
        sql.append("       bcontent like '%"+ filterCondition.getKeyword()+"%' ");
        break;
      case "E":   //아이디(이메일)
        sql.append("       email    like '%"+ filterCondition.getKeyword()+"%' ");
        break;
      case "N":   //별칭
        sql.append("       nickname like '%"+ filterCondition.getKeyword()+"%' ");
        break;
      default:
    }
    return sql;
  }

}
