package ServletsPages;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet("/AddTaskServlet")
public class AddTaskServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        if (email == null) {
            // Redirect to login if not logged in
            response.sendRedirect("login.html");
            return;
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Connection conn = null;

        try {
            conn = DatabaseConnection.getConnection();

            // Fetch categories for the dropdown
            String categorySql = "SELECT id, name FROM categories";
            PreparedStatement categoryStmt = conn.prepareStatement(categorySql);
            ResultSet categoryRs = categoryStmt.executeQuery();

            // Build the HTML response
            out.println("<!DOCTYPE html>");
            out.println("<html lang='en'>");
            out.println("<head>");
            out.println("<title>Add Task</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }");
            out.println("header { background-color: #333; color: white; padding: 15px; text-align: center; }");
            out.println(".container { margin: 20px auto; padding: 20px; background: white; width: 80%; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1); }");
            out.println("form { display: flex; flex-direction: column; }");
            out.println("label { margin-top: 10px; }");
            out.println("input, select, textarea { margin-top: 5px; padding: 10px; font-size: 16px; }");
            out.println("button { margin-top: 20px; padding: 10px; background-color: blue; color: white; border: none; cursor: pointer; }");
            out.println("button:hover { background-color: darkblue; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<header>");
            out.println("<h2>Add New Task</h2>");
            out.println("</header>");
            out.println("<div class='container'>");
            out.println("<form method='POST' action='AddTaskServlet'>");

            // Task Name
            out.println("<label for='taskName'>Task Name:</label>");
            out.println("<input type='text' id='taskName' name='taskName' required>");

            // Hours Needed
            out.println("<label for='hoursNeeded'>Hours Needed:</label>");
            out.println("<input type='number' id='hoursNeeded' name='hoursNeeded' required>");

            // Due Date
            out.println("<label for='dueDate'>Due Date:</label>");
            out.println("<input type='date' id='dueDate' name='dueDate' required>");

            // Description
            out.println("<label for='description'>Description:</label>");
            out.println("<textarea id='description' name='description' rows='4'></textarea>");

            // Category Dropdown
            out.println("<label for='category'>Category:</label>");
            out.println("<select id='category' name='category' required>");
            while (categoryRs.next()) {
                int categoryId = categoryRs.getInt("id");
                String categoryName = categoryRs.getString("name");
                out.println("<option value='" + categoryId + "'>" + categoryName + "</option>");
            }
            out.println("</select>");

            // Submit Button
            out.println("<button type='submit'>Add Task</button>");

            out.println("</form>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3>Error: Unable to load Add Task page.</h3>");
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user_id");

        if (userId == null) {
            // Redirect to login if not logged in
            response.sendRedirect("login.html");
            return;
        }

        String taskName = request.getParameter("taskName");
        int hoursNeeded = Integer.parseInt(request.getParameter("hoursNeeded"));
        String dueDate = request.getParameter("dueDate");
        String description = request.getParameter("description");
        int categoryId = Integer.parseInt(request.getParameter("category"));

        Connection conn = null;

        try {
            conn = DatabaseConnection.getConnection();

            // Insert task into the database
            String insertSql = "INSERT INTO tasks (user_id, category_id, task_name, hours_needed, due_date, description) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setInt(1, userId);
            insertStmt.setInt(2, categoryId);
            insertStmt.setString(3, taskName);
            insertStmt.setInt(4, hoursNeeded);
            insertStmt.setString(5, dueDate);
            insertStmt.setString(6, description);
            insertStmt.executeUpdate();

            // Redirect back to the dashboard
            response.sendRedirect("DashboardServlet");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h3>Error: Unable to add task.</h3>");
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
