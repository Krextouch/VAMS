<template>
  <div class="card-wrapper">
    <div class="content" v-if="vhclToUpdate">
      <form @submit.prevent="updateVhcl">
        <button type="submit">Abschicken</button>
      </form>
      <button id="delete" @click="deleteVhcl">Löschen</button>
    </div>
    <div class="info" id="none-selected" v-if="!rsvtnToUpdate">
      <span>Wähle eine Reservierung um zu bearbeiten</span>
    </div>
  </div>
</template>

<script>
// import axios from "axios";

export default {
  name: "updateVhcl",
  props: ['vhclToUpdate'],
  updated() {
    console.log("received vhcl: ", this.vhclToUpdate)
  },
  data(){
    return {
    }
  },
  methods: {
    async updateVhcl() {
      console.log("update vhcl of id: ", this.vhclToUpdate.id)
      // const response = await axios.put(`office/api/v1/updateVehicle/${this.vhclToUpdate.id}`, {
      // }, {
      //   headers: {
      //     "Authorization": "Bearer " + localStorage.token
      //   }
      // })
    },
    async deleteVhcl() {
      console.log("delete vhcl of id: ", this.vhclToUpdate.id)
      // const response = await axios.delete(`employee/api/v1/deleteVhcl/${this.vhclToUpdate.id}`, {
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