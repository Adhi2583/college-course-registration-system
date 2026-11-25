<%@ page contentType="text/html;charset=UTF-8" %>
<link rel="stylesheet" href="css/style.css">
<div class="nav">
  <b>Course Registration System</b>
  <% if (session.getAttribute("sid") != null) { %>
    <span>
      <a href="courses">All Courses</a>
      <a href="mycourses">My Courses</a>
      <a href="profile">Profile</a>
      <a href="logout">Logout (<%= session.getAttribute("sname") %>)</a>
    </span>
  <% } %>
</div>
<div class="box">
<%
  String flash = (String) session.getAttribute("msg");
  if (flash != null) { out.print("<p class='ok'>" + flash + "</p>"); session.removeAttribute("msg"); }
  if (request.getAttribute("error") != null) out.print("<p class='err'>" + request.getAttribute("error") + "</p>");
  if (request.getAttribute("success") != null) out.print("<p class='ok'>" + request.getAttribute("success") + "</p>");
%>
