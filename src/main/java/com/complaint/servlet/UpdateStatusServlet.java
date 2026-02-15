package com.complaint.servlet;

import com.complaint.util.DBConnection;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class UpdateStatusServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String cid = request.getParameter("cid");
        String status = request.getParameter("status");
        String remarks = request.getParameter("remarks");

        response.setContentType("text/html;charset=UTF-8");

        try (
            PrintWriter out = response.getWriter();
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE complaints SET status=?, remarks=? WHERE complaint_id=?")
        ) {

            ps.setString(1, status);
            ps.setString(2, remarks);
            ps.setString(3, cid);

            ps.executeUpdate();

            /* ================= SUCCESS PAGE ================= */

            out.println("<html><head><title>Status Updated</title>");

            /* AUTO REDIRECT */
            out.println("<meta http-equiv='refresh' content='2;URL=AdminDashboardServlet'>");

            /* FONT */
            out.println("<link href='https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap' rel='stylesheet'>");

            /* CSS */
            out.println("<style>");
            out.println("body{margin:0;font-family:Poppins;background:linear-gradient(135deg,#4facfe,#00f2fe);"
                    + "height:100vh;display:flex;justify-content:center;align-items:center;}");

            out.println(".card{background:white;padding:45px;border-radius:18px;text-align:center;"
                    + "box-shadow:0 12px 30px rgba(0,0,0,0.25);animation:fade 0.7s ease;}");

            out.println("@keyframes fade{from{opacity:0;transform:translateY(20px);}to{opacity:1;transform:translateY(0);}}");

            out.println("h2{color:#333;margin-bottom:10px;}");
            out.println(".msg{color:#27ae60;font-weight:600;margin-bottom:15px;}");

            out.println(".badge{display:inline-block;background:#4facfe;color:white;"
                    + "padding:6px 14px;border-radius:20px;margin:10px 0;font-weight:600;}");

            out.println(".btn{display:inline-block;margin-top:15px;text-decoration:none;"
                    + "background:#4facfe;color:white;padding:10px 20px;border-radius:8px;}");

            out.println(".btn:hover{background:#3b82f6;}");

            out.println("</style></head><body>");

            out.println("<div class='card'>");
            out.println("<h2>Status Updated</h2>");
            out.println("<div class='msg'>Complaint updated successfully</div>");
            out.println("<div>Complaint ID: <b>" + cid + "</b></div>");
            out.println("<div class='badge'>" + status + "</div>");
            out.println("<p>Redirecting to dashboard...</p>");
            out.println("<a class='btn' href='AdminDashboardServlet'>Back Now</a>");
            out.println("</div>");

            out.println("</body></html>");

        } catch (SQLException e) {
            throw new ServletException("Error updating complaint status", e);
        }
    }
}
