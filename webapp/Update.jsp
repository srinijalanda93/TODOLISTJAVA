<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
 <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<h2>Update Task</h2>
    <form action="UpdateTaskServlet" method="post">
       <input type="hidden" name="task_id" value="<%= request.getParameter("task_id") %>">
        <label>Task Name:</label>
        <input type="text" name="task_name" required><br>
        <label>Hours Needed:</label>
        <input type="number" name="hours_needed" required><br>
        <label>Due Date:</label>
        <input type="date" name="due_date" required><br>
        <label>Description:</label>
        <textarea name="description"></textarea><br>
        <button type="submit">Update</button>
    </form>
</body>
</html>