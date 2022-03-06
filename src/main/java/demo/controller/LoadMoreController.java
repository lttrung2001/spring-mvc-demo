package demo.controller;

import java.io.IOException;


import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import demo.model.Product;

/**
 * Servlet implementation class LoadMoreController
 */
@WebServlet("/LoadMoreController")
public class LoadMoreController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoadMoreController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		DAO dao = new DAO();
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<h1>Hello world</h1>");
		for (Product product : dao.getProducts()) {
			out.println("<div class=\"product-item\">\r\n"
					+ "												<div class=\"product product_filter\">\r\n"
					+ "													<div class=\"product_image\">\r\n"
					+ "														<img src=\"" + product.getImage()
					+ "\" alt=\"Product image\">\r\n" + "													</div>\r\n"
					+ "													<div class=\"favorite\"></div>\r\n"
					+ "													<div class=\"product_info\">\r\n"
					+ "														<h6 class=\"product_name\">\r\n"
					+ "															<a href=\"#\">" + product.getName()
					+ "</a>\r\n" + "														</h6>\r\n"
					+ "														<div class=\"product_price\">$"
					+ String.valueOf(product.getPrice()) + "</div>\r\n"
					+ "													</div>\r\n"
					+ "												</div>\r\n"
					+ "												<div class=\"red_button add_to_cart_button\">\r\n"
					+ "													<a href=\"#\">add to cart</a>\r\n"
					+ "												</div>\r\n"
					+ "											</div>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
