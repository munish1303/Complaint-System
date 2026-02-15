package com.complaint.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Logged Out</title>");

        /* AUTO REDIRECT */
        out.println("<meta http-equiv='refresh' content='3;URL=login.html'>");

        /* GOOGLE FONT */
        out.println("<link href='https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap' rel='stylesheet'>");

        /* CSS */
        out.println("<style>");
        out.println("body{margin:0;font-family:Poppins;background:linear-gradient(135deg,#4facfe,#00f2fe);"
                + "height:100vh;display:flex;justify-content:center;align-items:center;}");
        out.println(".card{background:white;padding:45px;border-radius:18px;text-align:center;"
                + "box-shadow:0 12px 30px rgba(0,0,0,0.25);animation:fade 0.8s ease;}");
        out.println("@keyframes fade{from{opacity:0;transform:translateY(20px);}to{opacity:1;transform:translateY(0);}}");
        out.println("h2{color:#333;margin-bottom:15px;}");
        out.println(".msg{color:#27ae60;font-weight:600;font-size:18px;margin-bottom:15px;}");
        out.println("a{display:inline-block;margin-top:10px;text-decoration:none;"
                + "background:#4facfe;color:white;padding:10px 20px;border-radius:8px;}");
        out.println("a:hover{background:#3b82f6;}");
        out.println("</style>");

        out.println("</head><body>");

        out.println("<div class='card'>");
        out.println("<h2>Logged Out Successfully</h2>");
        out.println("<div class='msg'>Your session has been ended securely.</div>");
        out.println("<p>Redirecting to login page...</p>");
        out.println("<a href='login.html'>Login Again</a>");
        out.println("</div>");

        out.println("</body></html>");
    }
}
