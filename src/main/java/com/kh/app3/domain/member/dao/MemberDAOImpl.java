package com.kh.app3.domain.member.dao;

import com.kh.app3.domain.member.Member;
import lombok.RequiredArgsConstructor;
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

@Slf4j
@Repository
@RequiredArgsConstructor // final 멤버필드를 매개값으로 하는 생성자를 자동 생성한다.
public class MemberDAOImpl implements MemberDAO{

  private final JdbcTemplate jdbcTemplate;

//  MemberDAOImpl(JdbcTemplate jdbcTemplate){
//    this.jdbcTemplate = jdbcTemplate;
//  }

  /**
   * 가입
   * @param member 회원이 입력한 정보
   * @return DB에 저장된 회원 정보
   */
  @Override
  public Member insertMember(Member member) {
    //SQL문작성
    StringBuffer sql = new StringBuffer();
    sql.append("insert into member ");
    sql.append("values(member_member_id_seq.nextval, ?, ?, ? )");

    //SQL실행
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {

        PreparedStatement pstmt = con.prepareStatement(
            sql.toString(),
            new String[] {"member_id"}    // keyHolder에 담을 테이블의 컬럼명을 지정
        );
        pstmt.setString(1,member.getEmail());
        pstmt.setString(2,member.getPasswd());
        pstmt.setString(3,member.getNickname());

        return pstmt;
      }
    },keyHolder);

    long member_id = keyHolder.getKey().longValue();
    log.info("신규회원등록={} 후 member_id반환값={}",member, keyHolder.getKey());
    return selectMemberByMemberId(member_id);
  }

  /**
   * 수정
   * @param member
   */
  @Override
  public void updateMember(Member member) {

    StringBuffer sql = new StringBuffer();
    sql.append("update member ");
    sql.append("   set nickname = ? ");
    sql.append(" where email = ? ");

    jdbcTemplate.update(sql.toString(),member.getNickname(),member.getEmail());

  }

  /**
   * 조회 by Email
   * @param email
   * @return
   */
  @Override
  public Member selectMemberByEmail(String email) {
    StringBuffer sql = new StringBuffer();
    sql.append("select member_id as memberId, email, passwd, nickname ");
    sql.append("  from member ");
    sql.append(" where email = ? ");

    Member member = jdbcTemplate.queryForObject(
        sql.toString(),
        new BeanPropertyRowMapper<>(Member.class),
        email
    );
    return member;
  }

  @Override
  public Member selectMemberByMemberId(Long memberId) {

    StringBuffer sql = new StringBuffer();
    sql.append("select member_id as memberId, email, passwd, nickname ");
    sql.append("  from member ");
    sql.append(" where member_id = ? ");

    Member member = jdbcTemplate.queryForObject(
                        sql.toString(),
                        new BeanPropertyRowMapper<>(Member.class),
                        memberId
                    );
    return member;
  }

  /**
   * 전체조회
   * @return
   */
  @Override
  public List<Member> selectAll() {
    StringBuffer sql = new StringBuffer();

    sql.append("select member_id as memberId, ");
    sql.append("       email, ");
    sql.append("       passwd, ");
    sql.append("       nickname ");
    sql.append("  from member ");
    sql.append(" order by member_id desc ");

    List<Member> list = jdbcTemplate.query(
        sql.toString(),
        new BeanPropertyRowMapper<>(Member.class)
    );

    return list;
  }

  /**
   * 탈퇴
   * @param email
   */
  @Override
  public void deleteMember(String email) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from member ");
    sql.append(" where email = ? ");

    jdbcTemplate.update(sql.toString(), email);
  }

  /**
   * 회원 유무 체크
   * @param email
   * @return 회원이면 true
   */
//  @Override
//  public boolean isMember(String email) {
//    boolean isMember = false;
//
//    String sql = "select count(email) from member where email = ? ";
//
//    Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
//
//    if(count == 1)  isMember = true;
//
//    return isMember;
//  }
  @Override
  public boolean isMember(String email) {

    String sql = "select count(email) from member where email = ? ";
    Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);

    return (count==1) ? true : false;
  }

  /**
   * 로그인
   * @param email
   * @param passwd
   * @return
   */
  @Override
  public Member login(String email, String passwd) {

    StringBuffer sql = new StringBuffer();
    sql.append("select member_id as member, email, nickname ");
    sql.append("  from member ");
    sql.append(" where email =? and passwd = ? ");

    Member member = jdbcTemplate.queryForObject(
        sql.toString(),
        new BeanPropertyRowMapper<>(Member.class),  //자바객체 <=> 테이블 레코드 자동 매핑
        email, passwd
    );

    return member;
  }
}
