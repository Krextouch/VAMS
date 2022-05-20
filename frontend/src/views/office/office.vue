<template>
  <officeNav />
  <router-view @infoPopup="$emit('infoPopup', $event)"/>
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