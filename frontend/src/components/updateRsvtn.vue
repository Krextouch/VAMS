<template>
  <div class="list-wrapper">
    <form @submit.prevent="" v-if="rsvtnToUpdate">
      <input type="datetime-local" id="start-time" name="starttime" v-model="starttime" required placeholder="Start Zeit" :value="this.rsvtnToUpdate.startTimeOfReservation"/>
      <input type="datetime-local" id="end-time" name="endtime" v-model="endtime" required placeholder="End Zeit" :value="this.rsvtnToUpdate.endTimeOfReservation"/>
      <select class="vehicles" id="vehicles">
        <option id="default">-- Fahrzeug wählen --</option>
        <option v-for="vcl in availableVehicles" :key="vcl.vin">{{ vcl }}</option>
      </select>
      <button type="submit">Erstellen</button>
    </form>
    <div class="info" id="none-selected" v-if="!rsvtnToUpdate">
      <span>Wähle eine Reservierung um zu bearbeiten</span>
    </div>
  </div>
</template>

<script>
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
      availableVehicles: []
    }
  },
  methods: {

  }
}
</script>

<style scoped>
.list-wrapper {
  width: 40vw;
  height: calc(90vh - 96px - 2px);
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

.form-wrapper {
  width: 40vw;
  /*height: calc(96vh - 96px - 2px);*/
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

/*
Scrollbar styles imported from:
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
</style>