<template>
  <div class="content-wrapper">
    <allEmpList @empClicked="updateEmp($event)" />
    <updateEmp :empToUpdate="empToUpdate"/>
  </div>
</template>

<script>
import allEmpList from "@/components/office/employees/allEmpList";
import updateEmp from "@/components/office/employees/updateEmp";

export default {
  name: "allEmployees",
  emits: ['infoPopup'],
  components: {
    allEmpList,
    updateEmp
  },
  data() {
    return {
      empToUpdate: null
    }
  },
  methods: {
    updateEmp(empToUpdate) {
      this.empToUpdate = empToUpdate
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