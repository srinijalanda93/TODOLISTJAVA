package ServletsPages;


import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import ServletsPages.DatabaseConnection;
//@WebServlet("/UpdateTaskServlet")
//public class UpdateTaskServlet extends HttpServlet {
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int taskId = Integer.parseInt(request.getParameter("task_id"));
//        String taskName = request.getParameter("task_name");
//        int hoursNeeded = Integer.parseInt(request.getParameter("hours_needed"));
//        String dueDate = request.getParameter("due_date");
//        String description = request.getParameter("description");
//
//        Connection conn = null;
//
//        try {
//            conn = DatabaseConnection.getConnection();
//
//            String updateSql = "UPDATE tasks SET task_name = ?, hours_needed = ?, due_date = ?, description = ? WHERE id = ?";
//            PreparedStatement stmt = conn.prepareStatement(updateSql);
//            stmt.setString(1, taskName);
//            stmt.setInt(2, hoursNeeded);
//            stmt.setString(3, dueDate);
//            stmt.setString(4, description);
//            stmt.setInt(5, taskId);
//
//            stmt.executeUpdate();
//
//            response.sendRedirect("DashboardServlet");
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.getWriter().println("<h3>Error: Unable to update task.</h3>");
//        } finally {
//            try {
//                if (conn != null) conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}

@WebServlet("/UpdateTaskServlet")
public class UpdateTaskServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Parse task_id from the form
            int taskId = Integer.parseInt(request.getParameter("task_id"));
            String taskName = request.getParameter("task_name");
            int hoursNeeded = Integer.parseInt(request.getParameter("hours_needed"));
            String dueDate = request.getParameter("due_date");
            String description = request.getParameter("description");

            Connection conn = null;

            try {
                conn = DatabaseConnection.getConnection();

                String updateSql = "UPDATE tasks SET task_name = ?, hours_needed = ?, due_date = ?, description = ? WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(updateSql);
                stmt.setString(1, taskName);
                stmt.setInt(2, hoursNeeded);
                stmt.setString(3, dueDate);
                stmt.setString(4, description);
                stmt.setInt(5, taskId);

                stmt.executeUpdate();

                // Redirect back to Dashboard
                response.sendRedirect("DashboardServlet");
            } catch (Exception e) {
                e.printStackTrace();
                response.getWriter().println("<h3>Error: Unable to update task.</h3>");
            } finally {
            	try {
                  if (conn != null) conn.close();
              } catch (SQLException e) {
                  e.printStackTrace();
              }
            }
        } catch (NumberFormatException e) {
            response.getWriter().println("<h3>Error: Invalid task ID format.</h3>");
            e.printStackTrace();
        }
    }
}

