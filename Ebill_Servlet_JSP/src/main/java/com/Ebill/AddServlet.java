package com.Ebill;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/add")
public class AddServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int i =Integer.parseInt(request.getParameter("num1"));
		int j =Integer.parseInt(request.getParameter("num2"));
		
		int k = i+j;
		
//		PrintWriter pw = response.getWriter();
//		pw.println("Sum is "+k);
		
//		//Session management
//		request.setAttribute("k", k);
//		
//		//Request Dispatcher
//		RequestDispatcher rd = request.getRequestDispatcher("sq");
//		rd.forward(request, response);
			
//		response.sendRedirect("sq?k="+k); //URL Rewriting
		
		
		HttpSession session = request.getSession();
		session.setAttribute("k", k);
		
		response.sendRedirect("sq");
		
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
