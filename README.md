<h1>TO-DO LIST (USING SERVLET CONCEPTS)</h1>
<hr>
<h2>A Basic understanding Concept on Servlet</h2>
<p>Servlet is a <b>Java program</b> that runs on a web server or on an application server such as <b>Apache Tomcat.</b>
  The working Mechanism of the Servlet Using the MVC which is a controller component (kind of software) which handle the
  logic of Model view controller which manages user input, request processing, and communication between the Model and View,
  is commonly implemented using Servlets and rendering the java code can be handle by the JSP
</p>
<p><h2>Servlet Lifecycle</h2>
A servlet has four stages and let’s try to understand what happens in each stage.

Loading a Servlet: When the server starts up, the servlet container deploys and loads all the servlets.​
Initializing the Servlet: Servlet is initialized by calling the init() method. It is only called once when the servlet is created .
Request handling: To handle requests from browsers and return responses to them, the servlet container invokes the service() method​. This method is actually the main method that is expected to perform the actual task.
Destroying the servlet: At the end of a servlet’s life cycle, the destroy() function is only invoked once.​</p>

<p> <h1>since I am using Tomcat Apache v7.0 fOR THE v10+
  We can shifted to <b>>Jakarta EE</b from <b>Java EE </b> which occurred after Oracle donated Java EE to the Eclipse Foundation,
  which renamed it to Jakarta EE. This transition has implications for how you develop <b>Java-based web applications</b> 
</h1>
<table border="1" style="border-collapse: collapse; width: 100%; text-align: left;">
    <thead>
        <tr>
            <th>Feature</th>
            <th>Java EE (javax)</th>
            <th>Jakarta EE (jakarta)</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>Namespace</td>
            <td><code>javax.servlet.*</code>, <code>javax.persistence.*</code></td>
            <td><code>jakarta.servlet.*</code>, <code>jakarta.persistence.*</code></td>
        </tr>
        <tr>
            <td>Maintenance</td>
            <td>No longer actively updated</td>
            <td>Actively maintained by the Eclipse Foundation</td>
        </tr>
        <tr>
            <td>Server Support</td>
            <td>Older application servers like Tomcat 9, WildFly 20</td>
            <td>Newer servers like Tomcat 10, WildFly 26</td>
        </tr>
        <tr>
            <td>Migration</td>
            <td>No migration required for older apps</td>
            <td>Requires namespace updates if migrating from Java EE</td>
        </tr>
    </tbody>
</table>

<code>import java.io.*:</code>
<p>
This imports the java.io package for handling input and output operations in Java.
For example, reading data from the request or writing a response back to the client.
</p>
<code>import javax.servlet.*:</code>
<p>This imports the Servlet API, which provides the classes and interfaces needed
  to create servlets. Servlets are server-side Java programs that handle client 
  requests and generate responses.
</p>
<hr>
<code>import javax.servlet.annotation.WebServlet:</code>
<p>
  This annotation is used to define a servlet without the need to configure it in the web.xml. It specifies the URL pattern for accessing the servlet.
  So no need to declare the servlet name and mapping
</p>
<hr>
<code>import javax.servlet.http.*:</code>
<p>
  This imports the HTTP-specific servlet classes, such as HttpServlet, HttpServletRequest, and HttpServletResponse.
@WebServlet Annotation:It is an alternative to configuring the servlet in web.xml.
  The below give detail explain of the importance of the java  HTTP-specici servlet classes
</p>

<code>
@WebServlet("/example")
public class ExampleServlet extends HttpServlet { ... }
The URL pattern /example will map client requests to this servlet.
Using web.xml for Servlet Declaration:
<code>
<h2>If you don't use @WebServlet, you need to declare the servlet and its URL mapping in web.xml like this:</h2>
<code>
  xml
Copy code
<servlet>
    <servlet-name>ExampleServlet</servlet-name>
    <servlet-class>com.example.ExampleServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>ExampleServlet</servlet-name>
    <url-pattern>/example</url-pattern>
</servlet-mapping>
</code>
  <br><br>
<hr>
<h1>SQL Concepts in the Code:</h1>

<code>import java.sql.*:</code>
<p>
This imports the java.sql package, 
which is used for database connectivity and operations in Java.
It includes classes like Connection, Statement, PreparedStatement, and ResultSet.</p>
Key Steps in SQL Integration:
Loading the Driver:
<code>
  Class.forName("com.mysql.cj.jdbc.Driver");
  Loads the JDBC driver for MySQL (or another database).
</code>

<code>
//Establishing a Connection:
Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourdb", "username", "password");
Connects to the database using the connection URL, username, and password.
</code>

<code>
Executing Queries:
Using Statement:
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery("SELECT * FROM table_name");
//Using PreparedStatement (preferred for parameterized queries):

PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM table_name WHERE id = ?");
pstmt.setInt(1, 1);
ResultSet rs = pstmt.executeQuery();
 //Processing Results:

while (rs.next()) {
    System.out.println(rs.getString("column_name"));
}

<b>Closing Resources: Always close ResultSet, Statement, and Connection to free resources:</b>

rs.close();
stmt.close();
conn.close();

</code>

try (Connection conn = DriverManager.getConnection(...);
     PreparedStatement pstmt = conn.prepareStatement(...);
     ResultSet rs = pstmt.executeQuery()) {
    while (rs.next()) {
        // Process results
    }
}
Security Considerations:

Avoid hardcoding database credentials. Use environment variables or configuration files.
Use PreparedStatement to prevent SQL injection.

</p>
