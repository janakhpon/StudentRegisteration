package org.greentech.simplewebapp.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.greentech.simplewebapp.beans.Product;
import org.greentech.simplewebapp.utils.DBUtils;
import org.greentech.simplewebapp.utils.MyUtils;

@WebServlet(urlPatterns = { "/editProduct" })
public class EditProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditProductServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);

		String code = (String) request.getParameter("CODE");

		Product product = null;

		String errorString = null;

		try {
			product = DBUtils.findProduct(conn, code);
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}

		// If no error.
		// The product does not exist to edit.
		// Redirect to productList page.
		if (errorString != null && product == null) {
			response.sendRedirect(request.getServletPath() + "/productList");
			return;
		}

		// Store errorString in request attribute, before forward to views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("product", product);

		RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/editProductView.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);

		String code = (String) request.getParameter("CODE");
		String name = (String) request.getParameter("NAME");
		String priceStr = (String) request.getParameter("PRICE");
		float price = 0;
		try {
			price = Float.parseFloat(priceStr);
		} catch (Exception e) {
		}
		Product product = new Product(code, name, price);

		String errorString = null;

		if (code == null) {
			errorString = "Product Code invalid!";
		}

		if (errorString == null) {
			try {
				DBUtils.updateProduct(conn, product);
			} catch (SQLException e) {
				e.printStackTrace();
				errorString = e.getMessage();
			}
		}

		// Store infomation to request attribute, before forward to views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("product", product);

		// If error, forward to Edit page.
		if (errorString != null) {
			RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/editProductView.jsp");
			dispatcher.forward(request, response);
		}
		// If everything nice.
		// Redirect to the product listing page.
		else {
			response.sendRedirect(request.getContextPath() + "/productList");
		}
	}

}
