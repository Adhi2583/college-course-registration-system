package com.college;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// Shows all courses (/courses) and the logged-in student's courses (/mycourses)
@WebServlet(urlPatterns = {"/courses", "/mycourses"})
public class CourseServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Integer sid = (Integer) req.getSession().getAttribute("sid");
        if (sid == null) { res.sendRedirect("index.jsp"); return; }

        boolean mine = req.getServletPath().equals("/mycourses");
        String sql = mine
            ? "SELECT c.course_id, c.code, c.name, c.credits, c.seats, r.status FROM courses c " +
              "JOIN registrations r ON r.course_id=c.course_id WHERE r.student_id=? AND r.status='ENROLLED'"
            : "SELECT c.course_id, c.code, c.name, c.credits, c.seats, " +
              "(SELECT r.status FROM registrations r WHERE r.student_id=? AND r.course_id=c.course_id) AS status " +
              "FROM courses c";   // SELECT

        List<String[]> list = new ArrayList<>();
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, sid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new String[]{ rs.getString(1), rs.getString(2), rs.getString(3),
                                       rs.getString(4), rs.getString(5), rs.getString(6) });
            }
        } catch (Exception e) { throw new ServletException(e); }

        req.setAttribute("courses", list);
        req.getRequestDispatcher(mine ? "mycourses.jsp" : "courses.jsp").forward(req, res);
    }
}
