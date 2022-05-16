<template>

  <navBar v-if="this.$route.fullPath !== '/login' && this.$route.fullPath.includes('/office') <= 0" />
  <router-view @infoPopup="handleInfo($event)"/>
  <errorMsg v-if="this.infoMsg[1] !== ''" v-show="showInfo" :info="this.infoMsg" />

</template>

<script>
import errorMsg from "@/components/errorMsg";
import navBar from "@/components/navBar";

export default {
  name: 'App',
  components: {
    errorMsg,
    navBar
  },
  data() {
    return {
      infoMsg: ["", ""],
      showInfo: false
    }
  },
  methods: {
    handleInfo(err) {
      this.infoMsg = err
      this.showInfo = true
      setTimeout(() => {
        this.showInfo = false
      }, 2500)
    }
  }
}
</script>

<style>
body {
  margin: 0;
  padding: 0;
  overflow: hidden;
  background-color: whitesmoke;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin: 0;
  padding: 0;
}
</style>
