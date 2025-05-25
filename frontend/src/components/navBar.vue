<template>
  <nav class="navbar">
    <div class="navbar-logo">Project: Simple Web application</div>
    <ul class="navbar-links">
      <li><router-link to="/" >Home</router-link></li>
      <li><router-link to="/Users" >Users</router-link></li>
      <li><router-link to="/register" >Register</router-link></li>
    </ul>
  </nav>
</template>

<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: linear-gradient(135deg, #007bff, #0056b3);
  padding: 15px 30px;
  color: white;
  font-family: 'Segoe UI', sans-serif;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  border-radius: 12px 12px 0px 0px;
}

.navbar-logo {
  font-size: 24px;
  font-weight: bold;
  letter-spacing: 1px;
}

.navbar-links {
  list-style: none;
  display: flex;
  gap: 20px;
  margin: 0;
  padding: 0;
}

.navbar-links li a {
  color: white;
  text-decoration: none;
  font-size: 20px;
  position: relative;
  transition: color 0.3s ease;
}

.navbar-links li a::after {
  content: '';
  position: absolute;
  width: 0%;
  height: 2px;
  bottom: -3px;
  left: 0;
  background-color: white;
  transition: width 0.3s ease-in-out;
}

.navbar-links li a:hover::after {
  width: 100%;
}

.navbar-links li a:hover {
  color: #d1eaff;
}
</style>
