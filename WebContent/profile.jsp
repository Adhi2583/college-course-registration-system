<%@ include file="header.jsp" %>
<h2>My Profile</h2>
<form action="profile" method="post">
  <input type="text" name="name" value="<%= request.getAttribute("name") %>" required>
  <input type="email" value="<%= request.getAttribute("email") %>" disabled>
  <input type="text" name="department" value="<%= request.getAttribute("department") %>" required>
  <button>Update Profile</button>
</form>
</div>
