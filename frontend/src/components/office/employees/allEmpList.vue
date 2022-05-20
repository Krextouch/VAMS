<template>
  <div class="card-wrapper">
    <table v-if="allEmployeesList.length !== 0">
        <tr class="table-head">
          <th>ID</th>
          <th>Vorname</th>
          <th>Nachname</th>
          <th>Kürzel</th>
          <th>Führerschein</th>
          <th>Admin</th>
          <th>Reservierungen</th>
        </tr>
        <tr class="emp-wrapper" v-for="emp in allEmployeesList" :key="emp.employeeId" :ref="'ref-'+emp.employeeId" @click="updateEmployee(emp)">
          <td class="param-wrapper">
            <span class="emp-param">{{ emp.employeeId }}</span>
          </td>
          <td class="param-wrapper">
            <span class="emp-param">{{ emp.firstname }}</span>
          </td>
          <td class="param-wrapper">
            <span class="emp-param">{{ emp.lastname }}</span>
          </td>
          <td class="param-wrapper">
            <span class="emp-param">{{ emp.nameTag }}</span>
          </td>
          <td class="param-wrapper">
            <span class="emp-param">{{ emp.hasDrivingLicense ? 'Ja' : 'Nein' }}</span>
          </td>
          <td class="param-wrapper">
            <span class="emp-param">{{ emp.hasOfficeRights ? 'Ja' : 'Nein' }}</span>
          </td>
          <td class="param-wrapper">
            <span class="emp-param">{{ emp.reservation.length }}</span>
          </td>
        </tr>
    </table>
    <div class="btn-wrapper">
      <router-link :to="{ name: 'newEmployee' }">Mitarbeiter anlegen</router-link>
    </div>
    <div class="info" id="empty-list" v-if="allEmployeesList.length === 0">
      <span>Keine Mitarbeiter verfügbar</span>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "allRsvtnList",
  emits: ['infoPopup', 'empClicked'],
  props: [
      'showAllEmployees'
  ],
  created() {
    let data = {
      firstName: null,
      lastName: null,
      email: null,
      nameTag: null
    }
    axios.post('office/api/v1/allEmployee', data, {
      headers: {
        "Authorization": "Bearer " + localStorage.token
      }
      }).then(
        res => {
          this.handleResponseData(res.data)
        }).catch(
        err => {
          console.log("err: ", err)
    })
  },
  data() {
    return {
      allEmployeesList: []
    }
  },
  methods: {
    updateEmployee(empToUpdate) {
      const item = document.getElementsByClassName('active')
      if (item.length > 0) {
        item[0].classList.remove('active')
      }
      this.$refs['ref-'+empToUpdate.employeeId][0].classList.add('active')
      this.$emit('empClicked', empToUpdate)
    },
    handleResponseData(data) {
      let resList = data.employeeList
      resList.forEach(emp => {
        let tempEmpObj = {
          employeeId: emp.employeeId,
          firstname: emp.firstName,
          lastname: emp.lastName,
          email: emp.email,
          nameTag: emp.nameTag,
          password: emp.password,
          workCard: emp.workCard,
          birthday: emp.birthday,
          birthplace: emp.birthplace,
          hasDrivingLicense: emp.hasDrivingLicense,
          hasOfficeRights: emp.hasOfficeRights,
          reservation: emp.reservation
        }
        this.allEmployeesList.push(tempEmpObj)
      })
    }
  }
}
</script>

<style scoped>
.card-wrapper {
  position: relative;
  width: 43vw;
  height: calc(94vh - 96px - 2px);
  background: dimgray;
  padding: 0;
  margin: 3vh 3vw;
  border: 2px solid gray;
  border-radius: 5px;
  box-shadow: inset 0 0 1em black;
  overflow-y: auto;
}

table {
  width: 100%;
  border-collapse: collapse;
}

.table-head {
  background-color: rgba(105, 105, 105);
  color: whitesmoke;
  position: sticky;
  top: 0;
}

.table-head th {
  padding: 8px 0 5px 0;
}

.emp-wrapper {
  width: calc(100% - 50px - 1px);
  background-color: #ababab;
  cursor: pointer;
  user-select: none;
}

.emp-wrapper:hover {
  background-color: #868686;
}

.param-wrapper {
  padding: 15px 5px;
  border-bottom: 1px solid black;
}

.emp-wrapper:first-of-type .param-wrapper {
  border-top: 1px solid black;
}

.active {
  font-weight: bold;
  background-color: #aaaaaa;
}

.info {
  color: lightgray;
  font-weight: bold;
}

.btn-wrapper {
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  margin-bottom: 20px;
  padding: 12px 25px;
  cursor: pointer;
  text-align: center;
  text-decoration: none;
  outline: none;
  border: none;
  border-radius: 15px;
  background-image: linear-gradient(to right, orange 0%, orangered 100%);
  font-size: 22px;
}

.btn-wrapper:hover {
  background-color: #3c5a8f;
}

.btn-wrapper a {
  color: #fff;
  text-decoration: none;
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