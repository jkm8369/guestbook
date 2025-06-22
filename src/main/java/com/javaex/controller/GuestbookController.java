package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDAO;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestbookVO;

@WebServlet("/gbc")
public class GuestbookController extends HttpServlet {
	//필드
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GuestbookController");
		
		
		String action = request.getParameter("action");
		System.out.println(action);
		
		if("addlist".equals(action)) {
			System.out.println("등록폼 + 리스트");
			
			GuestbookDAO guestbookDAO = new GuestbookDAO();
			List<GuestbookVO> guestbookList = guestbookDAO.guestbookSelect();
			
			request.setAttribute("guestbookList", guestbookList);
			
			WebUtil.forward(request, response, "/WEB-INF/addList.jsp");

		}else if("delform".equals(action)) {
			System.out.println("삭제폼");
			
			WebUtil.forward(request, response, "/WEB-INF/deleteForm.jsp");
			
		}else if("delete".equals(action)) {
			System.out.println("delete(삭제)");
			
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("password");
			
			GuestbookVO guestbookVO = new GuestbookVO();
			guestbookVO.setNo(no);
			guestbookVO.setPassword(password);
			
			GuestbookDAO guestbookDAO = new GuestbookDAO();
			guestbookDAO.guestbookDelete(guestbookVO);
			
			WebUtil.redirect(request, response, "/guestbook2/gbc?action=addlist");
			
		}else if("write".equals(action)) {
			System.out.println("write(등록)");
			
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");
			String regDate = request.getParameter("reg_date");
			
			GuestbookVO guestbookVO = new GuestbookVO(name, password, content, regDate);
			
			GuestbookDAO guestbookDAO = new GuestbookDAO();
			guestbookDAO.guestbookInsert(guestbookVO);
			
			WebUtil.redirect(request, response, "/guestbook2/gbc?action=addlist");
		
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
