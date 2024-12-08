package com.Ebill;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/sq")
public class SquareServlet extends HttpServlet{
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		int i = (int) request.getAttribute("k");
		
//		int i = Integer.parseInt(request.getParameter("k"));
	
		HttpSession session = request.getSession();
		int i = (int) session.getAttribute("k");
		
		i =i*i;
		PrintWriter pw = response.getWriter();
		pw.println("Square is "+i);
	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
