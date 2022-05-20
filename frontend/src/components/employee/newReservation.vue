<template>
  <div class="card-wrapper">
    <h2>Neue Reservierung erstellen</h2>
    <form @submit.prevent="sendNewRsvtn">
      <div class="inp-wrapper">
        <label for="start-time">Beginn</label>
        <input type="datetime-local" id="start-time" name="starttime" v-model="starttime" required />
      </div>
      <div class="inp-wrapper">
        <label for="end-time">Ende</label>
        <input type="datetime-local" id="end-time" name="endtime" v-model="endtime" required />
      </div>
      <div class="inp-wrapper">
        <label for="vehicles">Verfügbare Fahrzeuge</label>
        <select class="vehicles" id="vehicles" v-model="vhcl_select" required>
          <option id="default" disabled value="">-- Fahrzeug wählen --</option>
          <option v-for="vcl in availableVehicles" :key="JSON.stringify(vcl)">{{ vcl.vin }}: {{ vcl.brand }} {{ vcl.model }} in {{ vcl.color }} - {{ vcl.ps }}PS</option>
        </select>
      </div>
      <button type="submit">Erstellen</button>
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "newReservation",
  emits: ['infoPopup'],
  watch: {
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
  created() {
    const initDate = new Date()
    this.starttime = this.formatForDatetimeLocal(initDate)
    this.endtime = this.formatForDatetimeLocal(this.addDays(3, initDate))
  },
  data() {
    return {
      starttime: "",
      endtime: "",
      vhcl_select: "",
      selected: "",
      availableVehicles: []
    }
  },
  methods: {
    formatForDatetimeLocal(date) {
      const datestring = date.getFullYear()+"-"+`${(date.getMonth()+1)<10?'0':''}`+(date.getMonth()+1)+"-"+date.getDate()+"T"+(date.getHours()<10?'0':'')+date.getHours()+":"+(date.getMinutes()<10?'0':'')+date.getMinutes()
      return datestring
    },
    addDays(numberOfDays, _date = new Date()) {
      _date.setTime(_date.getTime() + numberOfDays * 24 * 60 * 60 * 1000)
      return new Date(_date)
    },
    retrieveVhclFromString(searchStr) {
      const subStrings = searchStr.split(":")
      const vin = subStrings[0]
      for (let vcl of this.availableVehicles) {
        if (vcl.vin === vin) {
          return vcl.vin
        }
      }
    },
    async sendNewRsvtn() {
      const vin = this.retrieveVhclFromString(this.vhcl_select)
      const data = {
        "startTimeOfReservation": this.starttime,
        "endTimeOfReservation": this.endtime,
        "isVerified": false,
        "vehicle": {"vin": vin},
        "employee": {"employeeId": parseInt(localStorage.getItem('employeeId'))}
      }
      axios.post('employee/api/v1/createReservation', data, {
        headers: {
          "Authorization": "Bearer " + localStorage.token
        }
      }).then(response => {
        console.log(response)
        if (response && response.status === 200) {
          this.$emit('infoPopup', {status: 'success', msg: 'Reservierung wurde angelegt'})
          this.$router.push({ name: 'Home'})
        }
      }).catch(err => {
          console.log("create err: ", err)
      })
    },
    getVehicles() {
      if (this.starttime !== '' && this.endtime !== '') {
        let data = {
          start: this.starttime,
          end: this.endtime
        }
        axios.get('employee/api/v1/getAvailableVehicle', {
          headers: {
            'Authorization': "Bearer " + localStorage.token
          },
          params: data
        }).then(response => {
          console.log(response)
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
  width: 40vw;
  background: dimgray;
  padding: 0;
  margin: 5vh auto;
  border: 2px solid gray;
  border-radius: 5px;
  box-shadow: inset 0 0 1em black;
}

h2 {
  margin: 20px 15%;
  padding-bottom: 19px;
  border-bottom: 1px solid black;
}

form {
  padding: 5vh 1vw;
}

.inp-wrapper {
  color: black;
  font-weight: bold;
  padding: 0 50px;
}

label {
  margin: 5px 0;
}

input, select {
  margin: 1% 10% 5% 10%;
  padding: 10px;
  font-size: medium;
  border-radius: 30px;
}

select {
  width: 80%;
  min-width: 200px;
}

input {
  width: calc(80% - 24px);
  min-width: 176px;
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
</style>