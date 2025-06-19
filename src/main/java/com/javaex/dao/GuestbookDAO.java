package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestVO;

public class GuestbookDAO {

	//필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/guestbook_db";
	private String id = "guestbook";
	private String pw = "guestbook";

    //생성자
	public void phonebookDAO() {
		
	}

    //메소드gs
    //자원정리 메소드 - 공통
	// DB연결 메소드
	private void connect() { // 메인에서는 사용하지 못함

		try {
			// 1. JDBC 드라이버 (MySQL) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			this.conn = DriverManager.getConnection(url, id, pw);

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
	
	public List<GuestVO> guestSelect() {
		List<GuestVO> guestList = new ArrayList<GuestVO>();
		
		this.connect();
		
		try {
			
			String query = "";
			query += " select no, ";
			query += " 		  name, ";
			query += " 		  password, ";
			query += " 		  content, ";
			query += " 		  reg_date ";
			query += " from guestbook ";
			
			pstmt = conn.prepareStatement(query);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("reg_date");
				
				GuestVO guestVO = new GuestVO(no, name, password, content, regDate);
				guestList.add(guestVO);
				
			}
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		
		this.close();
		return guestList;
	}
	
	
	public int guestInsert(GuestVO guestVO) {
		int count = -1;
		
		System.out.println("guestInsert()");
		this.connect();
		
		try {
			
			String query = "";
			query += " insert into guestbook ";
			query += " values(null, ?, ?, ?, ?) ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, guestVO.getName());
			pstmt.setString(2, guestVO.getPassword());
			pstmt.setString(3, guestVO.getContent());
			pstmt.setString(4, guestVO.getRegDate());
			
			count = pstmt.executeUpdate();
			
			System.out.println(count + "건이 등록되었습니다.");
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		
		this.close();
		return count;
	}
	
	public int guestDelete(int no, String password) {
		int count = -1;
		
		this.connect();
		
		try {
			
			String query = "";
			query += " delete from guestbook ";
			query += " where no = ? ";
			query += " and password = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			pstmt.setString(2, password);
			
			count = pstmt.executeUpdate();
			
			System.out.println(count + "건 삭제 되었습니다.");
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		
		this.close();
		return count;
	}
	
	
	
}
