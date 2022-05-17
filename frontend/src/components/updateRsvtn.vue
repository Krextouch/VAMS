<template>
  <div class="card-wrapper">
    <div class="content" v-if="rsvtnToUpdate">
      <form @submit.prevent="updateRsvtn">
        <input type="datetime-local" id="start-time" name="starttime" v-model="starttime" required placeholder="Start Zeit"/>
        <input type="datetime-local" id="end-time" name="endtime" v-model="endtime" required placeholder="End Zeit"/>
        <select class="vehicles" id="vehicles" v-model="vhcl_select">
          <option id="default" disabled value="">-- Fahrzeug wählen --</option>
          <option v-for="vcl in availableVehicles" :key="JSON.stringify(vcl)">{{ vcl.brand }} {{ vcl.model }} in {{ vcl.color }}</option>
        </select>
        <button type="submit">Abschicken</button>
      </form>
      <button id="delete" @click="deleteRsvtn">Stornieren</button>
    </div>
    <div class="info" id="none-selected" v-if="!rsvtnToUpdate">
      <span>Wähle eine Reservierung um zu bearbeiten</span>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "updateRsvtn",
  props: ['rsvtnToUpdate'],
  updated() {
    console.log("received rsvtn: ", this.rsvtnToUpdate)
  },
  data(){
    return {
      starttime: '',
      endtime: '',
      vhcl_select: '',
      availableVehicles: []
    }
  },
  methods: {
    async updateRsvtn() {
      console.log("update rsvtn of id: ", this.rsvtnToUpdate.id)
      const data = {
        "id": this.rsvtnToUpdate.id,
        "startTimeFrame": this.starttime,
        "endTimeFrame": this.endtime,
        "isVerified": false,
        "vehicleVin": null,
        "employeeId": this.showAllEmployees === 'true' ? null : 1,//parseInt(localStorage.getItem('employeeId')),
      }
      await axios.put(`employee/api/v1/updateReservation/${this.rsvtnToUpdate.id}`, data, {
        headers: {
          "Authorization": "Bearer " + localStorage.token
        }
      })
      this.$router.push({ name: this.$router.currentRoute})
    },
    async deleteRsvtn() {
      console.log("delete rsvtn of id: ", this.rsvtnToUpdate.id)
      await axios.delete(`employee/api/v1/deleteReservation/${this.rsvtnToUpdate.id}`, {
        headers: {
          "Authorization": "Bearer " + localStorage.token
        }
      })
      this.$router.push({ name: this.$router.currentRoute})
    }
  }
}
</script>

<style scoped>
.card-wrapper {
  width: 40vw;
  /*height: calc(90vh - 96px - 2px);*/
  background: dimgray;
  padding: 0;
  margin: 5vh 5vw;
  border: 2px solid gray;
  border-radius: 5px;
  box-shadow: inset 0 0 1em black;
}

.info {
  color: lightgray;
  font-weight: bold;
}

#none-selected {
  position: relative;
  top: 50%;
}

h2 {
  margin: 20px 15%;
  padding-bottom: 19px;
  border-bottom: 1px solid black;
}

form {
  padding: 5vh 1vw;
}

input, select {
  width: 66%;
  margin: 0 10% 10% 10%;
  padding: 10px;
  font-size: medium;
  border-radius: 30px;
}

button {
  display: inline-block;
  padding: 12px 25px;
  font-size: 24px;
  cursor: pointer;
  text-align: center;
  text-decoration: none;
  outline: none;
  color: #fff;
  background-color: rgba(105, 105, 105, 0.99);
  border: none;
  border-radius: 15px;
  background-color: cornflowerblue;
}

button:hover, button:focus {
  box-shadow: 0 6px #999;
  transform: translateY(-5px);
}

button:active {
  background-color: #5a86d5;
  box-shadow: 0 4px #555555;
  transform: translateY(5px);
}

button#delete {
  background-color: orangered;
}
</style>