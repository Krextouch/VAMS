<template>
  <div class="card-wrapper">
    <h2>Neues Fahrzeug anlegen</h2>
    <form @submit.prevent="submitVcl">
      <div class="ident">
        <input type="text" id="vin" name="vin" v-model="vin" required placeholder="ID" />
        <input type="text" id="licensePlate" name="licensePlate" v-model="licensePlate" required placeholder="Kennzeichen" />
      </div>
      <div class="branding">
        <input type="text" id="brand" name="brand" v-model="brand" required placeholder="Hersteller" />
        <input type="text" id="model" name="model" v-model="model" required placeholder="Model" />
        <input type="text" id="ps" name="ps" v-model="ps" required placeholder="PS" />
      </div>
      <input type="text" id="color" name="color" v-model="color" required placeholder="Farbe" />
      <button type="submit">Erstellen</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: "allVehicles",
  emits: ['infoPopup'],
  data() {
    return {
      vin: "",
      licensePlate: "",
      brand: "",
      model: "",
      ps: "",
      color: ""
    }
  },
  methods: {
    formatForDatetimeLocal(date) {
      const datestring = date.getFullYear()+"-"+`${(date.getMonth()+1)<10?'0':''}`+(date.getMonth()+1)+"-"+date.getDate()+"T"+(date.getHours()<10?'0':'')+date.getHours()+":"+(date.getMinutes()<10?'0':'')+date.getMinutes()
      return datestring
    },
    submitVcl() {
      const data = {
        "vin": this.vin,
        "licensePlate": this.licensePlate,
        "brand": this.brand,
        "model": this.model,
        "ps": this.ps,
        "color": this.color,
        "firstRegistration": this.formatForDatetimeLocal(new Date())
      }
      const response = axios.post('office/api/v1/createVehicle', data, {
        headers: {
          "Authorization": "Bearer " + localStorage.token
        }
      }).catch(err => {
        console.log("create err: ", err)
      })
      if (response && response.status === 200) {
        this.$emit('infoPopup', {status: 'success', msg: 'Reservierung wurde angelegt'})
        this.$router.push({ name: 'OfficeHome'})
      }
    }
  }
}
</script>

<style scoped>
.card-wrapper {
  width: 45vw;
  background: dimgray;
  padding: 0;
  margin: 5vh auto;
  border: 2px solid gray;
  border-radius: 5px;
  box-shadow: inset 0 0 1em black;
}

h2 {
  margin: 20px 12%;
  padding-bottom: 19px;
  border-bottom: 1px solid black;
}

form {
  padding: 2vh 1vw;
}


input {
  width: calc(80% - 24px);
  min-width: 176px;
  margin: 5px auto 15px auto;
  padding: 10px;
  font-size: medium;
  border-radius: 30px;
}

.ident input {
  width: calc(40% - 24px);
}

.branding input {
  width: calc(40% - 24px);
}

button {
  display: inline-block;
  padding: 12px 25px;
  margin-top: 20px;
  font-size: 24px;
  cursor: pointer;
  text-align: center;
  text-decoration: none;
  outline: none;
  color: #fff;
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