<%@ include file="header.jsp" %>
<h2>Student Registration</h2>
<form action="register" method="post">
  <input type="text" name="name" placeholder="Full name" required>
  <input type="email" name="email" placeholder="Email" required>
  <input type="password" name="password" placeholder="Password" required>
  <input type="text" name="department" placeholder="Department (e.g. Mechanical)" required>
  <button>Register</button>
</form>
<p>Already registered? <a href="index.jsp">Login</a></p>
</div>
