package com.complaint.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.complaint.util.DBConnection;

public class AdminDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect("login.html");
            return;
        }

        response.setContentType("text/html;charset=UTF-8");

        try (
            PrintWriter out = response.getWriter();
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM complaints");
        ) {

            /* ================= HTML START ================= */
            out.println("<html><head><title>Admin Dashboard</title>");

            /* GOOGLE FONT */
            out.println("<link href='https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap' rel='stylesheet'>");

            /* ================= CSS ================= */
            out.println("<style>");

            out.println("body{margin:0;font-family:Poppins;background:#eef2f7;}");

            /* HEADER */
            out.println(".header{background:linear-gradient(45deg,#4facfe,#00f2fe);color:white;padding:18px;font-size:22px;font-weight:600;text-align:center;}");

            /* LAYOUT */
            out.println(".container{display:flex;}");

            /* SIDEBAR */
            out.println(".sidebar{width:220px;background:#1f2937;height:100vh;color:white;padding-top:20px;}");
            out.println(".sidebar h3{text-align:center;margin-bottom:30px;}");
            out.println(".sidebar a{display:block;color:white;padding:12px 20px;text-decoration:none;}");
            out.println(".sidebar a:hover{background:#374151;}");

            /* CONTENT */
            out.println(".content{flex:1;padding:30px;}");

            /* CARDS */
            out.println(".cards{display:flex;gap:20px;margin-bottom:25px;}");
            out.println(".card{flex:1;background:white;padding:20px;border-radius:12px;box-shadow:0 4px 12px rgba(0,0,0,0.15);}");
            out.println(".card h4{margin:0;color:#555;}");
            out.println(".card p{font-size:26px;margin:8px 0;color:#111;font-weight:bold;}");

            /* TABLE */
            out.println("table{width:100%;border-collapse:collapse;background:white;border-radius:10px;overflow:hidden;box-shadow:0 4px 12px rgba(0,0,0,0.15);}");
            out.println("th{background:#4facfe;color:white;padding:12px;}");
            out.println("td{padding:10px;text-align:center;border-bottom:1px solid #eee;}");
            out.println("tr:hover{background:#f9fafb;}");

            /* BUTTON */
            out.println("button{background:#4facfe;border:none;color:white;padding:7px 14px;border-radius:6px;cursor:pointer;}");
            out.println("button:hover{background:#3b82f6;}");

            /* STATUS */
            out.println(".resolved{color:green;font-weight:600;}");

            /* INPUTS */
            out.println("select,input{padding:6px;border-radius:5px;border:1px solid #ccc;}");

            out.println("</style></head><body>");

            /* HEADER */
            out.println("<div class='header'>Complaint Management Dashboard</div>");

            out.println("<div class='container'>");

            /* SIDEBAR */
            out.println("<div class='sidebar'>");
            out.println("<h3>Admin Panel</h3>");
            out.println("<a href='#'>Dashboard</a>");
            out.println("<a href='LogoutServlet'>Logout</a>");
            out.println("</div>");

            /* CONTENT */
            out.println("<div class='content'>");

            /* ===== STAT CARDS ===== */
            Statement stats = con.createStatement();

            ResultSet total = stats.executeQuery("SELECT COUNT(*) FROM complaints");
            total.next();
            int totalCount = total.getInt(1);

            ResultSet pending = stats.executeQuery("SELECT COUNT(*) FROM complaints WHERE status='Pending'");
            pending.next();
            int pendingCount = pending.getInt(1);

            ResultSet progress = stats.executeQuery("SELECT COUNT(*) FROM complaints WHERE status='In Progress'");
            progress.next();
            int progressCount = progress.getInt(1);

            ResultSet resolved = stats.executeQuery("SELECT COUNT(*) FROM complaints WHERE status='Resolved'");
            resolved.next();
            int resolvedCount = resolved.getInt(1);

            out.println("<div class='cards'>");
            out.println("<div class='card'><h4>Total Complaints</h4><p>"+totalCount+"</p></div>");
            out.println("<div class='card'><h4>Pending</h4><p>"+pendingCount+"</p></div>");
            out.println("<div class='card'><h4>In Progress</h4><p>"+progressCount+"</p></div>");
            out.println("<div class='card'><h4>Resolved</h4><p>"+resolvedCount+"</p></div>");
            out.println("</div>");

            /* ===== TABLE ===== */
            out.println("<table>");
            out.println("<tr><th>ID</th><th>Name</th><th>Category</th><th>Description</th><th>Status</th><th>Update</th></tr>");

            while (rs.next()) {

                String id = rs.getString("complaint_id");
                String name = rs.getString("name");
                String category = rs.getString("category");
                String description = rs.getString("description");
                String status = rs.getString("status");

                out.println("<tr>");
                out.println("<td>"+id+"</td>");
                out.println("<td>"+name+"</td>");
                out.println("<td>"+category+"</td>");
                out.println("<td>"+description+"</td>");
                out.println("<td>"+status+"</td>");
                out.println("<td>");

                if ("Resolved".equalsIgnoreCase(status)) {
                    out.println("<span class='resolved'>Resolved ✔</span>");
                } else {

                    out.println("<form action='UpdateStatusServlet' method='post'>");
                    out.println("<input type='hidden' name='cid' value='"+id+"'>");

                    out.println("<select name='status'>");
                    out.println("<option "+("Pending".equals(status)?"selected":"")+">Pending</option>");
                    out.println("<option "+("In Progress".equals(status)?"selected":"")+">In Progress</option>");
                    out.println("<option>Resolved</option>");
                    out.println("</select>");

                    out.println("<input name='remarks' placeholder='remarks'>");
                    out.println("<button>Update</button>");
                    out.println("</form>");
                }

                out.println("</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</div></div></body></html>");

        } catch (SQLException e) {
            throw new ServletException("Database error while loading dashboard", e);
        }
    }
}
