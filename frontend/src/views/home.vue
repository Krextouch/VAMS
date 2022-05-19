<template>
  <div class="content-wrapper">
    <allRsvtnList @infoPopup="$emit('infoPopup', $event)" @rsvtnClicked="updateRsvtn($event)" hasOfficeRights="false"/>
    <updateRsvtn @infoPopup="$emit('infoPopup', $event)" :rsvtnToUpdate="rsvtnToUpdate"/>
  </div>
</template>

<script>
import allRsvtnList from '@/components/allRsvtnList.vue'
import updateRsvtn from '@/components/updateRsvtn.vue'

export default {
  name: "HomePage",
  emits: ['infoPopup'],
  components: {
    allRsvtnList,
    updateRsvtn
  },
  data() {
    return {
      rsvtnToUpdate: null,
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
</style>