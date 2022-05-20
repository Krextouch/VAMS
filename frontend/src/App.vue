<template>

  <navBar v-if="this.$route.fullPath !== '/login' && this.$route.fullPath.includes('/office') <= 0" />
  <router-view @infoPopup="handleInfo($event)"/>
  <errorMsg v-if="this.infoMsg.msg !== ''" v-show="showInfo" :info="this.infoMsg" />

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
      infoMsg: {status: "", msg: ""},
      showInfo: false
    }
  },
  methods: {
    handleInfo(_info) {
      console.log(_info)
      this.infoMsg = _info
      this.showInfo = true
      setTimeout(() => {
        this.showInfo = false
        this.infoMsg.msg = ''
      }, 2500)
    }
  }
}
</script>

<style>
body {
  margin: 0;
  padding: 0;
  overflow: auto;
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

/* --- Scrollbar ---
styles imported from:
  https://css-tricks.com/the-current-state-of-styling-scrollbars-in-css/
*/

::-webkit-scrollbar-track
{
  -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
  background-color: #F5F5F5;
}

::-webkit-scrollbar
{
  width: 10px;
  background-color: #F5F5F5;
}

::-webkit-scrollbar-thumb
{
  background-color: rgb(18, 18, 18);
  border: 2px solid dimgray;
}
/* --- Scrollbar ---
*/
</style>
