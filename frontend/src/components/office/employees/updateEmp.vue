<template>
  <div class="card-wrapper">
    <div class="content" v-if="empToUpdate">
      <form @submit.prevent="updateEmp">

      </form>
      <button id="delete" @click="deleteEmp">Stornieren</button>
    </div>
    <div class="info" id="none-selected" v-if="!empToUpdate">
      <span>Wähle einen Mitarbeiter um zu bearbeiten</span>
    </div>
  </div>
</template>

<script>
// import axios from "axios";

export default {
  name: "updateEmp",
  props: ['empToUpdate'],
  updated() {
    console.log("received emp: ", this.empToUpdate)
  },
  data(){
    return {
    }
  },
  methods: {
    async updateEmp() {
      console.log("update emp of id: ", this.empToUpdate.id)
      // const response = await axios.put(`employee/api/v1/updateReservation/${this.rsvtnToUpdate.id}`, {
      //   "id": 0,
      //   "startTimeOfReservation": 0,
      //   "endTimeOfReservation": 0,
      //   "isVerified": 0,
      //   "vehicle": 0,
      //   "employee": 0,
      // }, {
      //   headers: {
      //     "Authorization": "Bearer " + localStorage.token
      //   }
      // })
    },
    async deleteEmp() {
      console.log("delete emp of id: ", this.rsvtnToUpdate.id)
      // const response = await axios.delete(`office/api/v1/deleteEmployee/${this.empToUpdate.id}`, {
      //   headers: {
      //     "Authorization": "Bearer " + localStorage.token
      //   }
      // })
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