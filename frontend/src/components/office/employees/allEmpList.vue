<template>
  <div class="card-wrapper">
    <ul>
      <li class="emp-wrapper" v-for="emp in allEmployeesList" :key="emp.id" v-on:click="updateEmployee(emp)">
        <div class="param-wrapper">
          <span class="emp-param">{{  }}</span>
          <span class="emp-param">{{  }}</span>
        </div>
      </li>
    </ul>
    <div class="info" id="empty-list" v-if="allEmployeesList.length == 0">
      <span>Keine Mitarbeiter verfügbar</span>
    </div>
  </div>
</template>

<script>
// import axios from "axios";

export default {
  name: "allRsvtnList",
  props: [
      'showAllEmployees'
  ],
  created() {
    // let data = {
    //   firstName: null
    //   lastName: null
    //   email: null
    //   nameTag: null
    // }
    // axios.post('office/api/v1/allEmployees', data, {
    //   headers: {
    //     "Authorization": "Bearer " + localStorage.token
    //   }
    //   }).then(
    //     res => {
    //       this.handleResponseData(res.data)
    //     }).catch(
    //     err => {
    //       console.log("err: ", err)
    // })
  },
  data() {
    return {
      allEmployeesList: []
    }
  },
  methods: {
    updateEmployee(empToUpdate) {
      this.$emit('empClicked', empToUpdate)
    },
    handleResponseData(data) {
      let resList = data.employeeList
      resList.forEach(emp => {
        let tempEmpObj = {
          id: emp.id,
        }
        this.allEmployeesList.push(tempEmpObj)
      })
    }
  }
}
</script>

<style scoped>
.card-wrapper {
  width: 40vw;
  height: calc(94vh - 96px - 2px);
  background: dimgray;
  padding: 0;
  margin: 3vh 5vw;
  border: 2px solid gray;
  border-radius: 5px;
  box-shadow: inset 0 0 1em black;
  overflow-y: scroll;
}

ul {
  padding: 0;
  margin: 0;
  list-style: none;
}

.emp-wrapper {
  width: calc(100% - 50px - 1px);
  padding: 20px 25px;
  margin: 2px 1px;
  background-color: #ababab;
  border-radius: 2px;
  cursor: pointer;
  user-select: none;
}

.emp-wrapper:first-of-type {
  margin-top: 1px;
}

.emp-wrapper:last-of-type {
  margin-bottom: 1px;
}

.param-wrapper img {
  height: 18px;
  width: 18px;
  padding: 2px;
}

.emp-wrapper:hover {
  background-color: #868686;
}

.param-wrapper {
  display: flex;
  justify-content: space-between;
}

.info {
  color: lightgray;
  font-weight: bold;
}

#empty-list {
  position: relative;
  top: 50%;
}

/* --- Scrollbar ---
styles imported from:
  https://css-tricks.com/the-current-state-of-styling-scrollbars-in-css/
*/

.card-wrapper::-webkit-scrollbar-track
{
  -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
  background-color: #F5F5F5;
}

.card-wrapper::-webkit-scrollbar
{
  width: 10px;
  background-color: #F5F5F5;
}

.card-wrapper::-webkit-scrollbar-thumb
{
  background-color: rgb(18, 18, 18);
  border: 2px solid dimgray;
}
/* --- Scrollbar ---
*/
</style>