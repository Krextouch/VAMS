<template>
  <div class="card-wrapper">
    <table v-if="allReservationList.length !== 0">
      <tr class="table-head">
        <th>Beginn</th>
        <th>Ende</th>
        <th>Von [ID]</th>
        <th>Verifiziert</th>
      </tr>
      <tr class="rsvtn-wrapper" v-for="rsvtn in allReservationList" :key="rsvtn.id" :ref="'ref-'+rsvtn.id" v-on:click="updateReservation(rsvtn)">
        <td class="param-wrapper">
          <span class="rsvtn-param">{{ rsvtn.startTimeOfReservation }}</span>
        </td>
        <td class="param-wrapper">
          <span class="rsvtn-param">{{ rsvtn.endTimeOfReservation }}</span>
        </td>
        <td class="param-wrapper">
          <span class="rsvtn-param">{{ rsvtn.employeeId }}</span>
        </td>
        <td class="param-wrapper tooltip" v-if="rsvtn.isVerified" >
          <img src="../assets/verified.png" alt="Verified">
          <span class="tooltiptext">Verifiziert</span>
        </td>
        <td class="param-wrapper tooltip" v-if="!rsvtn.isVerified">
          <img src="../assets/waiting.png" alt="Waiting for verification">
          <span class="tooltiptext">Wartet auf<br>Verifizierung</span>
        </td>
      </tr>
    </table>
    <div class="info" id="empty-list" v-if="allReservationList.length === 0">
      <span>Zurzeit keine Reservierungen verfügbar</span>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "allRsvtnList",
  emits: ['rsvtnClicked', 'infoPopup'],
  props: ['showAllEmployees'],
  created() {
    const data = {
      "startTimeFrame": null,
      "endTimeFrame": null,
      "isVerified": null,
      "vehicleVin": null,
      "employeeId": this.showAllEmployees === 'true' ? null : 2,//parseInt(localStorage.getItem('employeeId')),
      "showAllEmployees": this.showAllEmployees === 'true'
    }
    axios.post('employee/api/v1/allReservations', data, {
      headers: {
        "Authorization": "Bearer " + localStorage.token
      }
      }).then(res => {
      console.log(res)
          this.handleResponseData(res.data)
        }).catch(err => {
          console.log("err: ", err)
    })
  },
  data() {
    return {
      allReservationList: []
    }
  },
  methods: {
    updateReservation(rsvtnToUpdate) {
      const items = document.getElementsByClassName('active')
      if (items.length > 0) {
        items[0].classList.remove('active')
      }
      this.$refs['ref-'+rsvtnToUpdate.id][0].classList.add('active')
      this.$emit('rsvtnClicked', rsvtnToUpdate)
    },
    formatForDatetimeLocal(_date) {
      const date = new Date(_date)
      let datestr = date.getFullYear()+"-"+`${(date.getMonth()+1)<10?'0':''}`+(date.getMonth()+1)+"-"+date.getDate()+"T"+(date.getHours()<10?'0':'')+date.getHours()+":"+(date.getMinutes()<10?'0':'')+date.getMinutes()
      return datestr
    },
    formatClearDate(_dateStr) {
      const date = new Date(_dateStr)
      return date.getDate()+"."+`${(date.getMonth()+1<10?'0':'')+date.getMonth()}`+"."+date.getFullYear()+" - "+(date.getHours()<10?'0':'')+date.getHours()+":"+(date.getMinutes()<10?'0':'')+date.getMinutes()
    },
    handleResponseData(data) {
      let resList = data.reservationParamList
      resList.forEach(rsvtn => {
        let clearStartTime = this.formatClearDate(rsvtn.startTimeOfReservation)
        let strStartTime = this.formatForDatetimeLocal(rsvtn.startTimeOfReservation)
        let clearEndTime = this.formatClearDate(rsvtn.endTimeOfReservation)
        let strEndTime = this.formatForDatetimeLocal(rsvtn.endTimeOfReservation)
        let tempRsvtnObj = {
          id: rsvtn.id,
          startTimeOfReservation: clearStartTime,
          startTimeString: strStartTime,
          endTimeOfReservation: clearEndTime,
          endTimeString: strEndTime,
          isVerified: rsvtn.isVerified,
          vehicleVin: rsvtn.vehicleVin,
          employeeId: rsvtn.employeeId
        }
        this.allReservationList.push(tempRsvtnObj)
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

.rsvtn-wrapper {
  width: calc(100% - 50px - 1px);
  background-color: #ababab;
  cursor: pointer;
  user-select: none;
}

.rsvtn-wrapper:hover {
  background-color: #868686;
}

.param-wrapper {
  padding: 15px 5px;
  border-bottom: 1px solid black;
}

.rsvtn-wrapper:first-of-type .param-wrapper {
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

.param-wrapper img {
  height: 18px;
  width: 18px;
  padding: 2px;
}

.rsvtn-wrapper:hover {
  background-color: #868686;
}

/* --- Tooltip ---
styles imported from:
  https://www.w3schools.com/css/css_tooltip.asp
*/

.tooltip {
  position: relative;
  display: inline-block;
}

.tooltip .tooltiptext {
  visibility: hidden;
  background-color: #ababab;
  color: #fff;
  text-align: center;
  font-size: smaller;
  border-radius: 1px;
  padding: 2px 4px;

  /* Position the tooltip */
  position: absolute;
  left: -200%;
  z-index: 1;
}

.tooltip:hover .tooltiptext {
  visibility: visible;
}
/* --- Tooltip ---
 */

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