<template>
  <officeNav />
  <router-view />
</template>

<script>
import officeNav from "@/components/office/officeNav";

export default {
  name: "officeView",
  emits: ['infoPopup'],
  components: {
    officeNav
  }
}
</script>

<style scoped>

</style>