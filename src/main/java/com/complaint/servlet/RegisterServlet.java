package com.complaint.servlet;

import com.complaint.util.DBConnection;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String category = request.getParameter("category");
        String description = request.getParameter("description");

        response.setContentType("text/html;charset=UTF-8");

        try (
            PrintWriter out = response.getWriter();
            Connection con = DBConnection.getConnection();
            PreparedStatement check = con.prepareStatement(
                    "SELECT 1 FROM complaints WHERE email=? AND description=?")
        ) {

            check.setString(1, email);
            check.setString(2, description);

            try (ResultSet rs = check.executeQuery()) {

                /* ================= DUPLICATE CASE ================= */
                if (rs.next()) {

                    out.println("<html><head><title>Duplicate Complaint</title>");

                    out.println("<link href='https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap' rel='stylesheet'>");

                    out.println("<style>");
                    out.println("body{margin:0;font-family:Poppins;background:linear-gradient(135deg,#ff6a6a,#ff9966);"
                            + "height:100vh;display:flex;justify-content:center;align-items:center;}");
                    out.println(".card{background:white;padding:40px;border-radius:15px;text-align:center;"
                            + "box-shadow:0 12px 30px rgba(0,0,0,0.25);}");
                    out.println("h2{color:#e74c3c;margin-bottom:15px;}");
                    out.println("a{display:inline-block;margin-top:15px;text-decoration:none;"
                            + "background:#e74c3c;color:white;padding:10px 18px;border-radius:8px;}");
                    out.println("a:hover{background:#c0392b;}");
                    out.println("</style>");

                    out.println("</head><body>");

                    out.println("<div class='card'>");
                    out.println("<h2>Complaint Already Submitted</h2>");
                    out.println("<p>You have already registered this complaint.</p>");
                    out.println("<a href='index.html'>Go Back</a>");
                    out.println("</div>");

                    out.println("</body></html>");
                    return;
                }
            }

            /* ================= INSERT ================= */

            String cid = "CMP" + System.currentTimeMillis();

            try (PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO complaints(complaint_id,name,email,category,description) VALUES(?,?,?,?,?)")) {

                ps.setString(1, cid);
                ps.setString(2, name);
                ps.setString(3, email);
                ps.setString(4, category);
                ps.setString(5, description);

                ps.executeUpdate();
            }

            /* ================= SUCCESS PAGE ================= */

            out.println("<html><head><title>Complaint Registered</title>");

            out.println("<link href='https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap' rel='stylesheet'>");

            out.println("<style>");
            out.println("body{margin:0;font-family:Poppins;background:linear-gradient(135deg,#4facfe,#00f2fe);"
                    + "height:100vh;display:flex;justify-content:center;align-items:center;}");
            out.println(".card{background:white;padding:45px;border-radius:18px;text-align:center;"
                    + "box-shadow:0 12px 30px rgba(0,0,0,0.25);animation:fade 0.8s ease;}");
            out.println("@keyframes fade{from{opacity:0;transform:translateY(20px);}to{opacity:1;transform:translateY(0);}}");
            out.println("h2{color:#333;margin-bottom:15px;}");
            out.println(".idbox{background:#f1f5f9;padding:12px;border-radius:10px;margin:15px 0;"
                    + "font-size:18px;font-weight:600;color:#2563eb;}");
            out.println(".btn{display:inline-block;margin-top:12px;text-decoration:none;"
                    + "background:#4facfe;color:white;padding:10px 20px;border-radius:8px;}");
            out.println(".btn:hover{background:#3b82f6;}");
            out.println("</style>");

            out.println("</head><body>");

            out.println("<div class='card'>");
            out.println("<h2>Complaint Registered Successfully</h2>");
            out.println("<p>Your complaint has been recorded.</p>");
            out.println("<div class='idbox'>Complaint ID: " + cid + "</div>");
            out.println("<a class='btn' href='index.html'>Submit Another</a>");
            out.println("</div>");

            out.println("</body></html>");

        } catch (SQLException e) {
            throw new ServletException("Database error during complaint registration", e);
        }
    }
}
