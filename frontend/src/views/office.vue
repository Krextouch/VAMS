<template>
  <div class="content-wrapper">
    <allRsvtnList @rsvtnClicked="updateRsvtn($event)"/>
    <updateRsvtn :rsvtnToUpdate="rsvtnToUpdate"/>
  </div>
</template>

<script>
import allRsvtnList from '../components/allRsvtnList.vue'
import updateRsvtn from '../components/updateRsvtn.vue'

export default {
  name: "officePortal",
  components: {
    allRsvtnList,
    updateRsvtn
  },
  data() {
    return {
      rsvtnToUpdate: null
    }
  },
  methods: {
    updateRsvtn(rsvtnToUpdate) {
      this.rsvtnToUpdate = rsvtnToUpdate
    }
  }
}
</script>

<style scoped>
.content-wrapper {
  height: fit-content;
  display: flex;
  justify-content: space-between;
}

nav-bar {
  background-color: red;
}
</style>
