package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDAO;
import com.javaex.vo.GuestVO;


@WebServlet("/gbc")
public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
  

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//로직 잘 작동했는지 확인
		System.out.println("gbc");
		
		//action 파라미터 값 뭔지 알아야 함
		String action = request.getParameter("action");
		System.out.println(action);  // 업무 구분
		
		if("list".equals(action)) {
			System.out.println("리스트");
			
			GuestbookDAO guestbookDAO = new GuestbookDAO();
			List<GuestVO> guestList = guestbookDAO.guestSelect();
			
			System.out.println(guestList);
			
			request.setAttribute("gList", guestList);
			
			RequestDispatcher rd = request.getRequestDispatcher("/list.jsp");
			rd.forward(request, response);
			
		} else if("wform".equals(action)) {
			System.out.println("등록폼");
			
			RequestDispatcher rd = request.getRequestDispatcher("/writeForm.jsp");
			rd.forward(request, response);
			
		} else if("write".equals(action)) {
			System.out.println("등록");
			
			//파라미터 4개 꺼내기
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");
			String redDate = request.getParameter("red_date");
			
			GuestVO guestVO = new GuestVO(name, password, content, redDate);
			System.out.println(guestVO);
			
			GuestbookDAO guestbookDAO = new GuestbookDAO();
			guestbookDAO.guestInsert(guestVO);
			
			response.sendRedirect("http://localhost:8080/guestbook/gbc?action=list");
			
		} else if("delete".equals(action)) {
			System.out.println("삭제");
			
			int no = Integer.parseInt(request.getParameter("no"));
			
			//DAO를 통해 no을 주고 삭제
			GuestbookDAO guestbookDAO = new GuestbookDAO();
			guestbookDAO.guestDelete(no, password);
			
			response.sendRedirect("http://localhost:8080/guestbook/gbc?action=list");
			
		}
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
	}

}
