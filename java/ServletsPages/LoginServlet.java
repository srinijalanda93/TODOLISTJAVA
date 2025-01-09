package ServletsPages;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import ServletsPages.DatabaseConnection;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Connection conn = null;

        try {
            // Open a new connection
            conn = DatabaseConnection.getConnection();

            // Check email and password and retrieve user_id and username
            String sql = "SELECT id, username FROM users WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Login successful
                int userId = rs.getInt("id");
                String username = rs.getString("username");

                // Store user details in the session
                HttpSession session = request.getSession();
                session.setAttribute("email", email);
                session.setAttribute("username", username);
                session.setAttribute("user_id", userId); // Store user_id in the session

                // Redirect to the dashboard
                response.sendRedirect("DashboardServlet");
            } else {
                // Login failed
                out.println("<h3>Invalid email or password.</h3>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3>Error: Unable to process login.</h3>");
        } finally {
            try {
                if (conn != null) {
                    conn.close(); // Always close the connection
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
