<template>
  <div class="list-wrapper">
    <ul>
      <li class="rsvtn-wrapper" v-for="rsvtn in allReservationList" :key="rsvtn.id" v-on:click="updateReservation(rsvtn)">
        <div class="param-wrapper">
<!--          <span class="rsvtn-param" v-for="(value, name) in rsvtn" :key="name" :class="name">{{ value }}</span>-->
          <span class="rsvtn-param startTime">{{ rsvtn.startTimeOfReservation }}</span>
          <span class="rsvtn-param endTime">{{ rsvtn.endTimeOfReservation }}</span>
          <span v-if="rsvtn.isVerified" class="tooltip">
              <img src="../assets/verified.png" alt="Verified">
              <span class="tooltiptext">Verifiziert</span>
          </span>
          <span v-if="!rsvtn.isVerified" class="tooltip">
              <img src="../assets/waiting.png" alt="Waiting for verification">
              <span class="tooltiptext">Wartet auf<br>Verifizierung</span>
            </span>
        </div>
      </li>
    </ul>
    <div class="info" id="empty-list" v-if="allReservationList.length == 0">
      <span>Zurzeit keine Reservierungen verfügbar</span>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "allRsvtnList",
  created () {
    axios.post('employee/api/v1/allReservations', {
        "startTimeFrame": null,
        "endTimeFrame": null,
        "isVerified": null,
        "vehicleVin": null,
        "employeeId": 2,
        "showAllEmployees": null
      }, {
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
      allReservationList: []
    }
  },
  methods: {
    updateReservation(rsvtnToUpdate) {
      console.log("clicked reservation of id:", rsvtnToUpdate.id)
      this.$emit('rsvtnClicked', rsvtnToUpdate);
    },
    formatDate(_dateStr) {
      const date = new Date(_dateStr)
      return date.getDay() + "." + date.getMonth() + "." + date.getFullYear() + " - " + date.getHours() + ":" + date.getMinutes()
    },
    handleResponseData(data) {
      let resList = data.reservationParamList
      resList.forEach(rsvtn => {
        let formattedStartTime = this.formatDate(rsvtn.startTimeOfReservation)
        let formattedEndTime = this.formatDate(rsvtn.endTimeOfReservation)
        let tempRsvtnObj = {
          id: rsvtn.id,
          startTimeOfReservation: formattedStartTime,
          endTimeOfReservation: formattedEndTime,
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
.list-wrapper {
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

.rsvtn-wrapper {
  width: calc(100% - 50px - 1px);
  padding: 20px 25px;
  margin: 2px 1px;
  background-color: #ababab;
  border-radius: 2px;
  cursor: pointer;
  user-select: none;
}

.rsvtn-wrapper:first-of-type {
  margin-top: 1px;
}

.rsvtn-wrapper:last-of-type {
  margin-bottom: 1px;
}

.param-wrapper img {
  height: 18px;
  width: 18px;
  padding: 2px;
}

.rsvtn-wrapper:hover {
  background-color: #868686;
}

.rsvtn-param.isVerified{
  display: none;
}

.param-wrapper {
  display: flex;
  justify-content: space-between;
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
  left: -300%;
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

.list-wrapper::-webkit-scrollbar-track
{
  -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
  background-color: #F5F5F5;
}

.list-wrapper::-webkit-scrollbar
{
  width: 10px;
  background-color: #F5F5F5;
}

.list-wrapper::-webkit-scrollbar-thumb
{
  background-color: rgb(18, 18, 18);
  border: 2px solid dimgray;
}
/* --- Scrollbar ---
*/
</style>