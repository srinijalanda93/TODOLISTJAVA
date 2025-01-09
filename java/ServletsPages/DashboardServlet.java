package ServletsPages;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        if (email == null) {
            response.sendRedirect("login.html");
            return;
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Connection conn = null;

        try {
            conn = DatabaseConnection.getConnection();

            String userSql = "SELECT id, username, email FROM users WHERE email = ?";
            PreparedStatement userStmt = conn.prepareStatement(userSql);
            userStmt.setString(1, email);
            ResultSet userRs = userStmt.executeQuery();

            if (userRs.next()) {
                int userId = userRs.getInt("id");
                String username = userRs.getString("username");
                String userEmail = userRs.getString("email");

                session.setAttribute("username", username);
                session.setAttribute("user_id", userId);

                out.println("<!DOCTYPE html>");
                out.println("<html lang='en'>");
                out.println("<head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                out.println("<title>Dashboard</title>");
                out.println("<style>");
                out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }");
                out.println("header { background-color: #333; color: white; padding: 15px; text-align: center; }");
                out.println(".container { margin: 20px auto; padding: 20px; background: white; width: 80%; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1); }");
                out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
                out.println("table, th, td { border: 1px solid #ddd; padding: 10px; }");
                out.println("th { background-color: #f2f2f2; }");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("<header>");
                out.println("<h2>Welcome to Your Dashboard, " + username + "!</h2>");
                out.println("</header>");
                out.println("<div class='container'>");

                String taskSql = "SELECT t.id, t.task_name, t.hours_needed, t.due_date, t.description, c.name AS category_name " +
                        "FROM tasks t " +
                        "JOIN categories c ON t.category_id = c.id " +
                        "WHERE t.user_id = ?";
                PreparedStatement taskStmt = conn.prepareStatement(taskSql);
                taskStmt.setInt(1, userId);
                ResultSet taskRs = taskStmt.executeQuery();

                if (taskRs.next()) {
                    out.println("<h3>Your Tasks</h3>");
                    out.println("<table>");
                    out.println("<tr><th>Task Name</th><th>Hours Needed</th><th>Due Date</th><th>Description</th><th>Category</th><th>Actions</th></tr>");
                    do {
                        int taskId = taskRs.getInt("id");
                        String taskName = taskRs.getString("task_name");
                        int hoursNeeded = taskRs.getInt("hours_needed");
                        String dueDate = taskRs.getString("due_date");
                        String description = taskRs.getString("description");
                        String categoryName = taskRs.getString("category_name");

                        out.println("<tr>");
                        out.println("<td>" + taskName + "</td>");
                        out.println("<td>" + hoursNeeded + "</td>");
                        out.println("<td>" + dueDate + "</td>");
                        out.println("<td>" + description + "</td>");
                        out.println("<td>" + categoryName + "</td>");
                        out.println("<td>");
                        out.println("<form action='DeleteTaskServlet' method='post' style='display:inline;'>");
                        out.println("<input type='hidden' name='task_id' value='" + taskId + "'>");
                        out.println("<button type='submit'>Delete</button>");
                        out.println("</form>");
                        out.println("<form action='Update.jsp' method='get' style='display:inline;'>");
                        out.println("<input type='hidden' name='task_id' value='" + taskId + "'>");
                        out.println("<button type='submit'>Update</button>");
                        out.println("</form>");
                        out.println("</td>");
                        out.println("</tr>");
                    } while (taskRs.next());
                    out.println("</table>");
                } else {
                    out.println("<h3>You have no tasks yet!</h3>");
                    out.println("<p>Add a new task to get started.</p>");
                }

                out.println("<a href='AddTaskServlet' style='text-decoration:none; color:white; background-color:blue; padding:10px 20px; border-radius:5px;'>Add Task</a>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            } else {
                out.println("<h3>User not found. Please log in again.</h3>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3>Error: Unable to load dashboard.</h3>");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

