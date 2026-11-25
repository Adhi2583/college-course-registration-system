package com.college;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Integer sid = (Integer) req.getSession().getAttribute("sid");
        if (sid == null) { res.sendRedirect("index.jsp"); return; }
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT name, email, department FROM students WHERE student_id=?")) {   // SELECT
            ps.setInt(1, sid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                req.setAttribute("name", rs.getString(1));
                req.setAttribute("email", rs.getString(2));
                req.setAttribute("department", rs.getString(3));
            }
        } catch (Exception e) { throw new ServletException(e); }
        req.getRequestDispatcher("profile.jsp").forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession s = req.getSession();
        Integer sid = (Integer) s.getAttribute("sid");
        if (sid == null) { res.sendRedirect("index.jsp"); return; }
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement("UPDATE students SET name=?, department=? WHERE student_id=?")) {   // UPDATE
            ps.setString(1, req.getParameter("name"));
            ps.setString(2, req.getParameter("department"));
            ps.setInt(3, sid);
            ps.executeUpdate();
            s.setAttribute("sname", req.getParameter("name"));
            s.setAttribute("msg", "Profile updated.");
        } catch (Exception e) { throw new ServletException(e); }
        res.sendRedirect("profile");
    }
}
