package com.kh.toy.member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kh.toy.common.exception.DataAccessException;
import com.kh.toy.common.db.JDBCTemplate;
import com.kh.toy.member.model.dto.Member;

// DAO(DATA ACCESS OBJECT)
// DBMS에 접근해 데이터의 조회, 수정, 삽입, 삭제 요청을 보내는 클래스
// DAO의 메서드는 하나의 메서드에서 하나의 쿼리만 처리하도록 작성
public class MemberDao {
	
	JDBCTemplate template = JDBCTemplate.getInstance();
	
	public MemberDao() {
		
	}
	
	// 사용자의 아이디와 password를 전달받아서
	// 해당 아이디와 password가 일치하는 회원을 조회하는 메서드
	public Member memberAuthenticate(String userId, String password, Connection conn) {
		Member member = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		try {
			String query = "select * from member where user_id = ? and password = ?";
			pstm = conn.prepareStatement(query);
			pstm.setString(1, userId);
			pstm.setString(2, password);
			rset = pstm.executeQuery(); // -- 쿼리 조회 결과를 참조할 주소값을 받음
			
			// 5. ResultSet에 저장된 데이터를 DTO에 옮겨닮기
			if(rset.next()) {
				member = convertRowToMember(rset);
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(rset,pstm);
		}
		return member;
	}
	
	public Member selectMemberById(String userId, Connection conn) {
		Member member = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;

		String query = "select * from member where user_id = ?";
		
		// String query = "select * from eeeee where user_id = ?";
		
		try {
			pstm = conn.prepareStatement(query);
			pstm.setString(1, userId);
			rset = pstm.executeQuery();
			
			if(rset.next()) {
				member = convertRowToMember(rset);
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(rset,pstm);
		}
		return member;
	}
	
	public List<Member> selectMemberList(Connection conn) {
		List<Member> memberList = new ArrayList<Member>();
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		String query = "select * from member";
		
		try {
			pstm = conn.prepareStatement(query);
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				memberList.add(convertRowToMember(rset));
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(rset,pstm);
		}
		return memberList;
	}
	
	public int insertMember(Member member, Connection conn) {
		int res = 0;
		PreparedStatement pstm = null;
		
		String query = "insert into member(user_id, password, email, tell) "
				+ " values(?,?,?,?)";
		
		try {
			pstm = conn.prepareStatement(query);
			pstm.setString(1, member.getUserId());
			pstm.setString(2, member.getPassword());
			pstm.setString(3, member.getEmail());
			pstm.setString(4, member.getTell());
			res = pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}
		return res;
	}
	
	
	// member.getUserId() => ' or 1=1 or user_id = '
	// SQL Injection 공격
	// SQL에 악의적인 구문을 주입해서 상대방의 DataBase를 공격하는 기법
		
	// SQL Injection 공격을 막기 위해 PreparedStatement 사용
	// 생성시에 등록된 쿼리 탬플릿이 변경되는 것을 방지
	// 문자열에 대해서 자동으로 escape처리를 해준다.
	public int updateMember(Member member, Connection conn) {
		int res = 0;
		PreparedStatement pstm = null;
		String query = "update member set password = ? where user_id = ?";
			
		try {
			pstm = conn.prepareStatement(query);
			pstm.setString(1, member.getPassword());
			pstm.setString(2, member.getUserId());
			res = pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}
		return res;
	}
		
	public int deleteMember(String userId, Connection conn) {
		int res = 0;
		PreparedStatement pstm = null;
		String query = "delete from member where user_id = ?";
			
		try {
			pstm = conn.prepareStatement(query);
			pstm.setString(1, userId);
			res = pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}
		return res;
	}
	
	private Member convertRowToMember(ResultSet rset) throws SQLException {
		Member member = new Member();
		member.setUserId(rset.getString("user_id"));
		member.setPassword(rset.getString("password"));
		member.setEmail(rset.getString("email"));
		member.setGrade(rset.getString("grade"));
		member.setTell(rset.getString("tell"));
		member.setRegDate(rset.getDate("reg_date"));
		member.setRentableDate(rset.getDate("rentable_date"));
		member.setIsLeave(rset.getInt("is_leave"));
		return member;
	}

	
	
	
}

	