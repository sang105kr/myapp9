package com.kh.app3.domain.notice.dao;

import com.kh.app3.domain.notice.Notice;
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
public class NoticeDAOImpl implements NoticeDAO{

    private final JdbcTemplate jdbcTemplate;
//  private JdbcTemplate jdbcTemplate;
//  public NoticeDAOImpl(JdbcTemplate jdbcTemplate){
//    this.jdbcTemplate = jdbcTemplate;
//  }

  /**
   * 등록
   * @param notice
   * @return
   */
  @Override
  public Notice create(Notice notice) {
    //SQL작성
    StringBuffer sql = new StringBuffer();
    sql.append("insert into notice (notice_id,subject,content,author) ");
    sql.append("values(notice_notice_id_seq.nextval, ?, ? ,?) ");

    //SQL실행
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(
            sql.toString(),
            new String[]{"notice_id"}  // insert 후 insert 레코드중 반환할 컬럼명
        );

        pstmt.setString(1, notice.getSubject());
        pstmt.setString(2, notice.getContent());
        pstmt.setString(3, notice.getAuthor());

        return pstmt;
      }
    },keyHolder);

    long notice_id = Long.valueOf(keyHolder.getKeys().get("notice_id").toString());
    return selectOne(notice_id);
  }

  /**
   * 전제조회
   * @return
   */
  @Override
  public List<Notice> selectAll() {
    return null;
  }

  /**
   * 상세조회
   * @param noticeId
   * @return
   */
  @Override
  public Notice selectOne(Long noticeId) {
    return null;
  }

  /**
   * 수정
   * @param notice
   * @return
   */
  @Override
  public Notice update(Notice notice) {
    return null;
  }

  /**
   * 삭제
   * @param noticeId
   * @return
   */
  @Override
  public int delete(Long noticeId) {
    return 0;
  }

  /**
   * 조회수 증가
   * @param noticeId
   * @return
   */
  @Override
  public int updateHit(Long noticeId) {
    return 0;
  }
}
