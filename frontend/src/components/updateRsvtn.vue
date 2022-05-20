<template>
  <div class="card-wrapper">
    <div class="content" v-if="rsvtnToUpdate">
      <form @submit.prevent="updateRsvtn">
        <input type="datetime-local" id="start-time" name="starttime" v-model="starttime" required placeholder="Start Zeit"/>
        <input type="datetime-local" id="end-time" name="endtime" v-model="endtime" required placeholder="End Zeit"/>
        <select class="vehicles" id="vehicles" v-model="vhcl_select" required>
          <option id="default" disabled value="">-- Fahrzeug wählen --</option>
          <option v-for="vcl in availableVehicles" :key="JSON.stringify(vcl)">{{ vcl.vin }}: {{ vcl.brand }} {{ vcl.model }} in {{ vcl.color }}</option>
        </select>
        <button type="submit">Abschicken</button>
      </form>
      <button id="verify" v-if="!rsvtnToUpdate.isVerified" @click="verifyRsvtn">Verifizieren</button>
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
  watch: {
    rsvtnToUpdate: function() {
      if (this.rsvtnToUpdate) {
        console.log(this.rsvtnToUpdate)
        this.initForm()
        this.getVehicles()
      }
    },
    starttime: {
      handler() {
        this.getVehicles()
      }
    },
    endtime: {
      handler() {
        this.getVehicles()
      }
    }
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
    initForm() {
      this.starttime = this.rsvtnToUpdate.startTimeString
      this.endtime = this.rsvtnToUpdate.endTimeString
    },
    async updateRsvtn() {
      const data = {
        "id": this.rsvtnToUpdate.id,
        "startTimeFrame": this.starttime,
        "endTimeFrame": this.endtime,
        "isVerified": false,
        "vehicleVin": null,
        "employeeId": this.showAllEmployees === 'true' ? null : 1,//parseInt(localStorage.getItem('employeeId')),
      }
      const response = await axios.put(`employee/api/v1/updateReservation/${this.rsvtnToUpdate.id}`, data, {
        headers: {
          "Authorization": "Bearer " + localStorage.token
        }
      })
      if (response && response.status === 200) {
        this.$emit('infoPopup', {status: 'success', msg: 'Änderungen wurden gespeichert'})
        window.location.reload()
      }
    },
    async verifyRsvtn() {
      const response = await axios.put(`office/api/v1/verifyReservation/${this.rsvtnToUpdate.id}`, true,{
        headers: {
          "Authorization": "Bearer " + localStorage.token,
          "Content-Type": "application/json"
        }
      }).catch(err => {
        console.log(err)
        this.$emit('infoPopup', {status: 'error', msg: 'Verifizierung fehlgeschlagen'})
      })
      if (response && response.status === 200) {
        this.$emit('infoPopup', {status: 'success', msg: 'Reservierung wurde verifiziert'})
        window.location.reload()
      }
    },
    async deleteRsvtn() {
      // const response = await axios.put(`office/api/v1/verifyReservation/${this.rsvtnToUpdate.id}`, false,{
      const response = await axios.delete(`employee/api/v1/deleteReservation/${this.rsvtnToUpdate.id}`, {
        headers: {
          "Authorization": "Bearer " + localStorage.token,
          "Content-Type": "application/json"
        }
      }).catch(err => {
        console.log(err)
        this.$emit('infoPopup', {status: 'error', msg: 'Reservierung konnte nicht gelöscht werden'})
      })
      if (response && response.status === 200) {
        this.$emit('infoPopup', {status: 'success', msg: 'Reservierung wurde gelöscht'})
        window.location.reload()
      }
    },
    getVehicles() {
      if (this.starttime !== '' && this.endtime !== '') {
        let data = {
          start: this.starttime,
          end: this.endtime
        }
        console.log("getAvlblVhcl", data)
        axios.get('employee/api/v1/getAvailableVehicle', {
          headers: {
            'Authorization': "Bearer " + localStorage.token
          },
          params: data
        }).then(
            response => {
              if (response.status === 200) {
                this.availableVehicles = response.data.availableVehicleParamList
                if (this.availableVehicles === []) {
                  this.$emit('infoPopup', {status: 'info', msg: "Keine Fahrzeuge verfügbar"})
                }
              }
            }
        ).catch(err => {
          this.$emit('infoPopup', {status: 'error', msg: "Fahrzeug-Request fehlgeschlagen"})
          console.log("getVehicle err: ", err)
        })
      }
    }
  }
}
</script>

<style scoped>
.card-wrapper {
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
  background-image: linear-gradient(to right, orange 0%, orangered 100%);
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
  background-image: none;
  background-color: orangered;
  margin: 0 1%;
}

button#verify {
  background-image: none;
  background-color: darkseagreen;
  margin: 0 1%;
}
</style>