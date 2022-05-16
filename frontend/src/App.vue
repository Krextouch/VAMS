<template>

  <router-view @errThrown="handleError($event)"/>
  <errorMsg v-show="showErr" :errmsg="this.errMsg" />

</template>

<script>
import errorMsg from "@/components/errorMsg";

export default {
  name: 'App',
  components: {
    errorMsg
  },
  data() {
    return {
      errMsg: null,
      showErr: false
    }
  },
  methods: {
    handleError(err) {
      this.errMsg = err
      this.showErr = true
      setTimeout(() => {
        this.showErr = false
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
