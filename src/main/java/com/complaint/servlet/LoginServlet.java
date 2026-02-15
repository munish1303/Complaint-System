package com.complaint.servlet;

import com.complaint.util.DBConnection;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM admin WHERE username=? AND password=?")
        ) {

            ps.setString(1, user);
            ps.setString(2, pass);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    HttpSession session = request.getSession();
                    session.setAttribute("admin", user);
                    response.sendRedirect("AdminDashboardServlet");
                } else {

                    response.setContentType("text/html;charset=UTF-8");
                    PrintWriter out = response.getWriter();

                    out.println("<html><head><title>Login Failed</title>");

                    /* GOOGLE FONT */
                    out.println("<link href='https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap' rel='stylesheet'>");

                    /* CSS */
                    out.println("<style>");
                    out.println("body{margin:0;font-family:Poppins;background:linear-gradient(135deg,#4facfe,#00f2fe);"
                            + "height:100vh;display:flex;justify-content:center;align-items:center;}");
                    out.println(".card{background:white;padding:40px;border-radius:15px;text-align:center;"
                            + "box-shadow:0 10px 25px rgba(0,0,0,0.2);width:320px;}");
                    out.println("h2{margin-bottom:15px;color:#333;}");
                    out.println(".error{color:#e74c3c;font-weight:600;margin:15px 0;}");
                    out.println("a{display:inline-block;margin-top:15px;text-decoration:none;"
                            + "background:#4facfe;color:white;padding:10px 18px;border-radius:8px;}");
                    out.println("a:hover{background:#3b82f6;}");
                    out.println("</style>");

                    out.println("</head><body>");

                    out.println("<div class='card'>");
                    out.println("<h2>Login Failed</h2>");
                    out.println("<div class='error'>Invalid username or password</div>");
                    out.println("<a href='login.html'>Try Again</a>");
                    out.println("</div>");

                    out.println("</body></html>");
                }
            }

        } catch (SQLException e) {
            throw new ServletException("Login database error", e);
        }
    }
}
