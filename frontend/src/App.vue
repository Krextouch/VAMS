<template>
  <menu-bar/>
  <LoginMask/>
  <div style="height: 1000px; width: 50px; background-color:lightgoldenrodyellow;"></div>
</template>

<script>
import LoginMask from './components/login.vue'
import menuBar from './components/menuBar.vue'

export default {
  name: 'App',
  components: {
    LoginMask,
    menuBar
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
