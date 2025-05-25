<template>
   <div id="app">
    <header class="hero">
    <h1>Project: Simple Web application</h1>
    </header>
    <NavBar />
   <router-view/>
  </div>
  <footer class="app-footer">
    <p>© {{ new Date().getFullYear() }} <strong>Thanasis Papatzelos</strong></p>
  </footer>
</template>

<script>
import NavBar from './components/navBar.vue'; 

export default {
  name: 'App',
  components: {
    NavBar,
  },

}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 0px;
  
}
body{
  background-color: rgb(230, 182, 127);
  background: url('../public/pexels.jpg') no-repeat center center fixed;
  background-size: cover;
}
.app-footer {
  text-align: center;
  padding: 0.2rem;
  color: #334155;
  font-size: 0.9rem;
}
.hero {
  text-align: center;
  margin-bottom: 1rem;
  padding: 1rem;
 
}

.hero h1 {
  font-size: 2.5rem;
  color: #2c3e50;
  margin-bottom: 0.5rem;
}

</style>
