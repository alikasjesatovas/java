
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Out;

import java.sql.*;

/**
 * Servlet implementation class E39_09
 */
@WebServlet("/E39_09")
public class E39_09 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		java.io.PrintWriter out = response.getWriter();
		String driver = request.getParameter("driver");
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			out.println(e.toString());
			e.printStackTrace();
			return;
		}
		String url = request.getParameter("url");
		Connection connection;
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
			out.println(e.toString());
			return;
		}
		Statement statement;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			out.println(e.toString());
			e.printStackTrace();
			return;
		}
		String table = request.getParameter("table");
		try {
			ResultSet result = statement.executeQuery("SELECT * FROM " + table);
			ResultSetMetaData meta = result.getMetaData();
			int columnCount = meta.getColumnCount();
			out.println("<table align = \"left\" border = 1>");
			out.println("<tr>");
			for (int i = 1; i <= columnCount; i++) {
				out.println("<th>" + meta.getColumnLabel(i) + "</th>");
			}
			out.println("</tr>");
			while (result.next()) {
				out.println("<tr>");
				for (int i = 1; i <= columnCount; i++) {
					System.out.println(result.getString(i));
					out.println("<td>" + result.getString(i) + "</td>");
				}
				out.println("</tr>");
			}
			out.println("</table>");
		} catch (SQLException e) {
			out.println(e.toString());
			e.printStackTrace();
		}
	}

}
