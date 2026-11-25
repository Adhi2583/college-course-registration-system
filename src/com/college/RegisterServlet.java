package com.college;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String sql = "INSERT INTO students (name, email, password, department) VALUES (?,?,?,?)";   // INSERT
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, req.getParameter("name"));
            ps.setString(2, req.getParameter("email"));
            ps.setString(3, DBUtil.hash(req.getParameter("password")));
            ps.setString(4, req.getParameter("department"));
            ps.executeUpdate();
            req.setAttribute("success", "Registered successfully. Please login.");
            req.getRequestDispatcher("index.jsp").forward(req, res);
        } catch (SQLIntegrityConstraintViolationException e) {
            req.setAttribute("error", "Email already registered.");
            req.getRequestDispatcher("register.jsp").forward(req, res);
        } catch (Exception e) { throw new ServletException(e); }
    }
}
