<%@ page import="java.util.*" %>
<%@ include file="header.jsp" %>
<h2>Available Courses</h2>
<table>
  <tr><th>Code</th><th>Course</th><th>Credits</th><th>Seats Left</th><th>Action</th></tr>
<%
  List<String[]> list = (List<String[]>) request.getAttribute("courses");
  for (String[] r : list) {
    boolean enrolled = "ENROLLED".equals(r[5]);
%>
  <tr>
    <td><%= r[1] %></td><td><%= r[2] %></td><td><%= r[3] %></td><td><%= r[4] %></td>
    <td>
      <form action="enroll" method="post">
        <input type="hidden" name="course_id" value="<%= r[0] %>">
        <% if (enrolled) { %>
          <input type="hidden" name="action" value="drop"><button class="red">Drop</button>
        <% } else { %>
          <input type="hidden" name="action" value="enroll"><button>Enroll</button>
        <% } %>
      </form>
    </td>
  </tr>
<% } %>
</table>
</div>
