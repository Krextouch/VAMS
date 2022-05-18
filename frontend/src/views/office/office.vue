<template>
  <officeNav />
  <router-view @infoPopup="handleInfo($event)"/>
  <errorMsg v-if="this.infoMsg.msg !== ''" v-show="showInfo" :info="this.infoMsg" />
</template>

<script>
import officeNav from "@/components/office/officeNav";
import errorMsg from "@/components/errorMsg";

export default {
  name: "officeView",
  emits: ['infoPopup'],
  components: {
    officeNav,
    errorMsg
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
      }, 2500)
    }
  }
}
</script>

<style scoped>

</style>