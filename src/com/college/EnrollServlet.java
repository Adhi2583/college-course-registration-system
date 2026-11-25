package com.college;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/enroll")
public class EnrollServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession s = req.getSession();
        Integer sid = (Integer) s.getAttribute("sid");
        if (sid == null) { res.sendRedirect("index.jsp"); return; }

        int cid = Integer.parseInt(req.getParameter("course_id"));
        String action = req.getParameter("action");
        String back = "mycourses".equals(req.getParameter("back")) ? "mycourses" : "courses";
        String msg;

        try (Connection c = DBUtil.getConnection()) {
            c.setAutoCommit(false);

            PreparedStatement chk = c.prepareStatement(
                "SELECT status FROM registrations WHERE student_id=? AND course_id=?");   // SELECT
            chk.setInt(1, sid); chk.setInt(2, cid);
            ResultSet rs = chk.executeQuery();
            String status = rs.next() ? rs.getString(1) : null;

            if ("enroll".equals(action)) {
                if ("ENROLLED".equals(status)) {
                    msg = "Already enrolled in this course.";
                } else {
                    PreparedStatement seat = c.prepareStatement(
                        "UPDATE courses SET seats = seats - 1 WHERE course_id=? AND seats > 0");   // UPDATE
                    seat.setInt(1, cid);
                    if (seat.executeUpdate() == 0) {
                        msg = "No seats available.";
                    } else {
                        if (status == null) {
                            PreparedStatement ins = c.prepareStatement(
                                "INSERT INTO registrations (student_id, course_id, status) VALUES (?,?, 'ENROLLED')");   // INSERT
                            ins.setInt(1, sid); ins.setInt(2, cid);
                            ins.executeUpdate();
                        } else {
                            PreparedStatement upd = c.prepareStatement(
                                "UPDATE registrations SET status='ENROLLED', reg_date=NOW() WHERE student_id=? AND course_id=?");   // UPDATE
                            upd.setInt(1, sid); upd.setInt(2, cid);
                            upd.executeUpdate();
                        }
                        msg = "Enrolled successfully.";
                    }
                }
            } else {   // drop
                if ("ENROLLED".equals(status)) {
                    PreparedStatement upd = c.prepareStatement(
                        "UPDATE registrations SET status='DROPPED' WHERE student_id=? AND course_id=?");   // UPDATE
                    upd.setInt(1, sid); upd.setInt(2, cid);
                    upd.executeUpdate();
                    PreparedStatement seat = c.prepareStatement(
                        "UPDATE courses SET seats = seats + 1 WHERE course_id=?");   // UPDATE
                    seat.setInt(1, cid);
                    seat.executeUpdate();
                    msg = "Course dropped.";
                } else {
                    msg = "You are not enrolled in this course.";
                }
            }
            c.commit();
        } catch (Exception e) { throw new ServletException(e); }

        s.setAttribute("msg", msg);
        res.sendRedirect(back);
    }
}
