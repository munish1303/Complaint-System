package com.complaint.servlet;

import com.complaint.util.DBConnection;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class TrackServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String cid = request.getParameter("cid");

        response.setContentType("text/html;charset=UTF-8");

        try (
            PrintWriter out = response.getWriter();
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT complaint_id,name,description,status,remarks FROM complaints WHERE complaint_id=?")
        ) {

            ps.setString(1, cid);

            try (ResultSet rs = ps.executeQuery()) {

                out.println("<html><head><title>Track Complaint</title>");

                /* FONT */
                out.println("<link href='https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap' rel='stylesheet'>");

                /* CSS */
                out.println("<style>");
                out.println("body{margin:0;font-family:Poppins;background:linear-gradient(135deg,#4facfe,#00f2fe);"
                        + "height:100vh;display:flex;justify-content:center;align-items:center;}");

                out.println(".card{background:white;padding:40px;border-radius:18px;width:420px;"
                        + "box-shadow:0 12px 30px rgba(0,0,0,0.25);animation:fade 0.7s ease;}");

                out.println("@keyframes fade{from{opacity:0;transform:translateY(20px);}to{opacity:1;transform:translateY(0);}}");

                out.println("h2{text-align:center;color:#333;margin-bottom:20px;}");

                out.println(".row{margin:10px 0;font-size:15px;}");
                out.println(".label{font-weight:600;color:#555;}");

                out.println(".status{display:inline-block;padding:6px 12px;border-radius:20px;color:white;font-size:14px;}");

                out.println(".Pending{background:#f39c12;}");
                out.println(".InProgress{background:#3498db;}");
                out.println(".Resolved{background:#2ecc71;}");

                out.println(".btn{display:block;text-align:center;margin-top:20px;text-decoration:none;"
                        + "background:#4facfe;color:white;padding:10px;border-radius:8px;}");

                out.println(".btn:hover{background:#3b82f6;}");

                out.println("</style></head><body>");

                if (rs.next()) {

                    String id = rs.getString("complaint_id");
                    String name = rs.getString("name");
                    String desc = rs.getString("description");
                    String status = rs.getString("status");
                    String remarks = rs.getString("remarks");

                    String cssStatus = status.replace(" ", "");

                    out.println("<div class='card'>");
                    out.println("<h2>Complaint Details</h2>");

                    out.println("<div class='row'><span class='label'>Complaint ID:</span> " + id + "</div>");
                    out.println("<div class='row'><span class='label'>Name:</span> " + name + "</div>");
                    out.println("<div class='row'><span class='label'>Description:</span> " + desc + "</div>");

                    out.println("<div class='row'><span class='label'>Status:</span> "
                            + "<span class='status " + cssStatus + "'>" + status + "</span></div>");

                    out.println("<div class='row'><span class='label'>Remarks:</span> "
                            + (remarks == null ? "Not updated yet" : remarks) + "</div>");

                    out.println("<a class='btn' href='track.html'>Track Another</a>");
                    out.println("</div>");

                } else {

                    /* INVALID ID PAGE */

                    out.println("<div class='card'>");
                    out.println("<h2>Invalid Complaint ID</h2>");
                    out.println("<p>Please check your ID and try again.</p>");
                    out.println("<a class='btn' href='track.html'>Try Again</a>");
                    out.println("</div>");
                }

                out.println("</body></html>");
            }

        } catch (SQLException e) {
            throw new ServletException("Error tracking complaint", e);
        }
    }
}
