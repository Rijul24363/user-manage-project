package com.usermanagement;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/")
public class UserServlet extends HttpServlet {
	private UserDao userDao;
	
	public void init(ServletConfig config) throws ServletException {
		
		userDao = new UserDao();
		
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		String action = request.getServletPath();
		switch(action) 
		{
		case "/new":
			showNewForm(request, response);
			break;
		
		case "/insert":
			try {
				insertUser(request, response);
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case "/delete":
			try {
				deleteUser(request, response);
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case "/edit":
			try {
				showEditForm(request, response);
			} catch (SQLException | IOException | ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case "/update":
			try {
				updateUser(request, response);
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		default :
			try {
				listUser(request, response);
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		}
		
		private void showNewForm(HttpServletRequest request , HttpServletResponse response ) throws ServletException, IOException{
			
			RequestDispatcher rd = request.getRequestDispatcher("user-form.jsp");
			rd.forward(request, response);
			
		}
		
		
		// insert user 
		private void insertUser(HttpServletRequest request , HttpServletResponse response)throws SQLException, IOException, ClassNotFoundException{
			int id=Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			User newUser = new User(id,name, email, phone);
			
				userDao.insertUser(newUser);
				response.sendRedirect("list");
		}
		
		
		// delete user 
		
		private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
			
			int id = Integer.parseInt(request.getParameter("id"));
			try {
				userDao.deleteUser(id);
				
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			response.sendRedirect("list");
		}
		
		
		// edit
		
		private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
			
			int id = Integer.parseInt(request.getParameter("id"));
			
				User existingUser;
				try {
					existingUser = userDao.selectUser(id);
					RequestDispatcher rd = request.getRequestDispatcher("user-form.jsp"); 
					request.setAttribute("user", existingUser);
					rd.forward(request, response);
				} catch (Exception e )
				{
					e.printStackTrace();
				}
			
		}
		
		// update 
		
		private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ClassNotFoundException {
			
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			
			User user = new User(id, name, email, phone);
				userDao.updateUser(user);
			response.sendRedirect("list");
		
		}
		
		
		// default 
		
		private void listUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
			
			try {
				List<User>listUser = userDao.selectAllUsers();
				request.setAttribute("listUser", listUser);
				RequestDispatcher rd = request.getRequestDispatcher("user-list.jsp");
				rd.forward(request, response);
				} catch (Exception e) 
			{
					e.printStackTrace();
			}
		}
		
	
	
	

}
