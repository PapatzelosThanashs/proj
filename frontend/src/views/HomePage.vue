<template>
  <div class="homepage">
    <section class="menu">
      <router-link to="/users" class="menu-item">
        <div class="icon">👥</div>
        <h2>Display Users.</h2>
        <p>View a list of all registered users.</p>
      </router-link>

      <router-link to="/register" class="menu-item">
        <div class="icon">📝</div>
        <h2>Register New User</h2>
        <p>Add a new user to the system with a simple form.</p>
      </router-link>
    </section>
  </div>
  
  </template>

<script>

export default {
  name: 'HomePage',
}
</script>

<style scoped>
.homepage {
  min-height: 100vh;
  padding: 2rem;
  background-color: rgb(255, 247, 237);
  display: flex;
  flex-direction: column;
  align-items: center;
  border-radius: 0px 0px 16px 16px;
}


.menu {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 2rem;
}

.menu-item {
  background-color: rgb(230, 182, 127);
  border-radius: 16px;
  padding: 4rem;
  width: 280px;
  text-align: center;
  text-decoration: none;
  color: inherit;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  margin: 10rem 4rem;;

}

.menu-item:hover {
  transform: translateY(-6px);
  box-shadow: 0 12px 24px rgba(0, 0, 0, 0.15);
  background-color: rgb(230, 196, 158);
}

.icon {
  font-size: 2rem;
  margin-bottom: 1rem;
}

.menu-item h2 {
  font-size: 1.4rem;
  color: #1e293b;
  margin-bottom: 0.5rem;
}

.menu-item p {
  color: #475569;
  font-size: 0.95rem;
}

</style>
