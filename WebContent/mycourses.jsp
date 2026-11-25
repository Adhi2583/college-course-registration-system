<%@ page import="java.util.*" %>
<%@ include file="header.jsp" %>
<h2>My Courses</h2>
<table>
  <tr><th>Code</th><th>Course</th><th>Credits</th><th>Action</th></tr>
<%
  List<String[]> list = (List<String[]>) request.getAttribute("courses");
  int total = 0;
  for (String[] r : list) { total += Integer.parseInt(r[3]); %>
  <tr>
    <td><%= r[1] %></td><td><%= r[2] %></td><td><%= r[3] %></td>
    <td>
      <form action="enroll" method="post">
        <input type="hidden" name="course_id" value="<%= r[0] %>">
        <input type="hidden" name="action" value="drop">
        <input type="hidden" name="back" value="mycourses">
        <button class="red">Drop</button>
      </form>
    </td>
  </tr>
<% } %>
</table>
<p><b>Total credits: <%= total %></b></p>
</div>
