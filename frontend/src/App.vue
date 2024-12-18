<script>
export default {};
</script>

<template>
  <title>Atexo - Générateur de numéro</title>
  <img id="logo" alt="Atexo logo" src="./assets/atexo-logo.png">
  <div id="app">
    <nav>
      <router-link to="/config">Configuration</router-link>
      ,
      <router-link to="/">Génération</router-link>
    </nav>
    <div id="view">
      <router-view/>
    </div>
  </div>
</template>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#view {
  text-align: left;
  width: 800px;
  margin: 0 auto;
}

#logo {
  width: 200px;
}

nav {
  margin-bottom: 20px;
}

nav a {
  margin: 0 10px;
  text-decoration: none;
  color: #034ba2;
  transition: color 0.3s;
}

nav a:hover {
  color: #0a315b;
}
</style>
