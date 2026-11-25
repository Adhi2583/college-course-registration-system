package com.college;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String sql = "SELECT student_id, name FROM students WHERE email=? AND password=?";   // SELECT
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, req.getParameter("email"));
            ps.setString(2, DBUtil.hash(req.getParameter("password")));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                HttpSession s = req.getSession();
                s.setAttribute("sid", rs.getInt("student_id"));
                s.setAttribute("sname", rs.getString("name"));
                res.sendRedirect("courses");
            } else {
                req.setAttribute("error", "Invalid email or password.");
                req.getRequestDispatcher("index.jsp").forward(req, res);
            }
        } catch (Exception e) { throw new ServletException(e); }
    }
}
