<template>
  <navBar />
<!--  <h1> Erstelle neue Reservierung </h1>-->
  <div class="card-wrapper">
    <h2>Neue Reservierung erstellen</h2>
    <form @submit.prevent="sendNewRsvtn">
      <input type="datetime-local" id="start-time" name="starttime" v-model="starttime" required placeholder="Start Zeit" />
      <input type="datetime-local" id="end-time" name="endtime" v-model="endtime" required placeholder="End Zeit" />
      <select class="vehicles" id="vehicles">
        <option id="default">-- Fahrzeug wählen --</option>
        <option v-for="vcl in availableVehicles" :key="vcl.vin">{{ vcl }}</option>
      </select>
      <button type="submit">Erstellen</button>
    </form>
  </div>
</template>

<script>
import navBar from '../components/navBar.vue'
import axios from "axios";

export default {
  name: "newReservation",
  components: {
    navBar
  },
  props: {
  },
  created() {
    axios.post('employee/api/v1/getAvailableVehicle', null, {
      headers: {
        'Authorization': "Bearer " + localStorage.token
      },
      params: {
        start: null,
        end: null
      }
    }).then(
        res => {
          console.log("getVehicle response", res)
          // this.availableVehicles = res.data
        }).catch(err => {
      console.log("getVehicle err: ", err)
    })
  },
  data() {
    return {
      starttime: '',
      endtime: '',
      availableVehicles: []
    }
  },
  methods: {
    async sendNewRsvtn() {
      const response = await axios.post('employee/api/v1/createReservation', {
          start_time_of_reservation: null,
          end_time_of_reservation: null,
          vin: null
        }, {
        headers: {
          "Authorization": "Bearer " + localStorage.token
        }
      }).catch(err => {
          console.log("create err: ", err)
      });
      if (response) console.log("create response: ", response)
      // this.$router.push({ name: 'Home'})
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
</style>