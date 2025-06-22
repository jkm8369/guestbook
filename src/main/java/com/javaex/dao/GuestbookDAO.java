package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestbookVO;

public class GuestbookDAO {

	// 필드
	// 0. import java.sql.*;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/guestbook_db";
	private String id = "guestbook";
	private String pw = "guestbook";


	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
			// System.out.println("접속성공");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	private void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public List<GuestbookVO> guestbookSelect() {
		List<GuestbookVO> guestbookList = new ArrayList<GuestbookVO>();

		this.getConnection();
		
		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			
			//--SQL문 준비
			String query ="";
			query += " select no, ";
			query += "        name, ";
			query += "        password, ";
			query += "        content, ";
			query += "        reg_date ";
			query += " from guestbook ";
			query += " order by reg_date desc  ";
			
			//--바인딩
			pstmt = conn.prepareStatement(query);
			
			//--실행
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("reg_date");

				GuestbookVO guestbookVO = new GuestbookVO(no, name, password, content, regDate);
				guestbookList.add(guestbookVO);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 

		this.close();
		
		return guestbookList;
	}

	
	public int guestbookInsert(GuestbookVO guestbookVO) {
		int count = -1;
		
		this.getConnection();
		
		try {
			
			// 3. SQL문 준비 / 바인딩 / 실행
			//SQL문 준비 
			String query ="";
			query += " insert into guestbook ";
			query += " values (null, ?, ?, ?, now()) ";

			//바인딩
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, guestbookVO.getName());
			pstmt.setString(2, guestbookVO.getPassword());
			pstmt.setString(3, guestbookVO.getContent());

			//실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 등록");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();
		
		return count;
	}

	
	public int guestbookDelete(GuestbookVO guestbookVO) {
		int count = -1;

		this.getConnection();
		
		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			//SQL문 준비
			String query = "";
			query += " delete from guestbook ";
			query += " where no= ?  ";
			query += " and password= ?  ";		
					
			//바인딩
			pstmt = conn.prepareStatement(query);

			//실행
			pstmt.setInt(1, guestbookVO.getNo());
			pstmt.setString(2, guestbookVO.getPassword());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 삭제");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		this.close();
		
		return count;
	}

}