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
  margin-top: 60px;
}

#view {
  text-align: left;
  width: 700px;
  margin: 0 auto;
}

#logo {
  width: 200px;
}
</style>
