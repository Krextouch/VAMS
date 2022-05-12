<template>
  <navBar />
  <div class="content-wrapper">
    <allRsvtnList />
    <updateRsvtn />
  </div>
</template>

<script>
import navBar from '../components/navBar.vue'
import allRsvtnList from '../components/allRsvtnList.vue'
import updateRsvtn from '../components/updateRsvtn.vue'

export default {
  name: "HomePage",
  components: {
    navBar,
    allRsvtnList,
    updateRsvtn
  },

  data() {
    return {

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

</style>