package ServletsPages;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;


@WebServlet("/DeleteTaskServlet")
public class DeleteTaskServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int taskId = Integer.parseInt(request.getParameter("task_id"));
        HttpSession session = request.getSession();

        if (session.getAttribute("email") == null) {
            response.sendRedirect("login.html");
            return;
        }

        Connection conn = null;

        try {
            conn = DatabaseConnection.getConnection();

            String deleteSql = "DELETE FROM tasks WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(deleteSql);
            stmt.setInt(1, taskId);

            stmt.executeUpdate();

            response.sendRedirect("DashboardServlet");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h3>Error: Unable to delete task.</h3>");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

