<template>
  <div class="content-wrapper">
    <updateVhcl />
  </div>
</template>

<script>
import updateVhcl from "@/components/office/vehicles/updateVhcl";

export default {
  name: "allVehicles",
  components: {
    updateVhcl
  },
  data() {
    return {
      vhclToUpdate: null,
    }
  },
  methods: {
    updateEmp(vhclToUpdate) {
      this.vhclToUpdate = vhclToUpdate
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