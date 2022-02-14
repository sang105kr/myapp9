package com.kh.app3.domain.member.svc;

import com.kh.app3.domain.member.Member;
import com.kh.app3.domain.member.dao.MemberDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberSVCImpl implements MemberSVC{

  private final MemberDAO memberDAO;

//  @Autowired
//  MemberSVCImpl(MemberDAO memberDAO){
//    this.memberDAO = memberDAO;
//  }

  /**
   * 가입
   * @param member
   * @return
   */
  @Override
  public Member join(Member member) {
    return memberDAO.insertMember(member);
  }

  /**
   * 수정
   * @param member
   */
  @Override
  public void modify(Member member) {
    memberDAO.updateMember(member);
  }

  /**
   * 조회 by email
   * @param email
   * @return
   */
  @Override
  public Member findByEmail(String email) {
    return memberDAO.selectMemberByEmail(email);
  }

  /**
   * 조회 by memberId
   * @param memberId
   * @return
   */
  @Override
  public Member findByMemberId(Long memberId) {
    return memberDAO.selectMemberByMemberId(memberId);
  }

  /**
   * 전체조회
   * @return
   */
  @Override
  public List<Member> findAll() {
    return memberDAO.selectAll();
  }

  /**
   * 탈퇴
   * @param email
   */
  @Override
  public void out(String email) {
    memberDAO.deleteMember(email);
  }

  /**
   * 회원유무 체크
   * @param email
   * @return
   */
  @Override
  public boolean isMember(String email) {
    return memberDAO.isMember(email);
  }

  /**
   * 로그인
   * @param email
   * @param passwd
   * @return
   */
  @Override
  public Member login(String email, String passwd) {

    return memberDAO.login(email, passwd);
  }
}
