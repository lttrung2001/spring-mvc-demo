package demo;

import java.io.IOException;




import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import demo.model.Product;

/**
 * Servlet implementation class LoadMoreController
 */
@WebServlet("/LoadMoreController")
public class LoadMoreController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @throws ServletException 
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
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		
		String dbURL = "jdbc:sqlserver://localhost:1433; Database=TestDatabase";
        String user = "sa";
        String pass = "tt";
        List<Product> myList = new ArrayList<Product>();
        try {
			Connection conn = DriverManager.getConnection(dbURL, user, pass);
			String sqlString = "SELECT TOP(4) * FROM products";
            Statement statement = conn.createStatement();
            ResultSet list = statement.executeQuery(sqlString);
            while(list.next()) {
            	myList.add(new Product(list.getInt(1), list.getString(2), list.getDouble(3), list.getString(4)));
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		for (Product product : myList) {
			out.println("<div class=\"product-item men\" style='display: inline-block;' >\r\n"
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
