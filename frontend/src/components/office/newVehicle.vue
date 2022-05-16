<template>
  <div class="card-wrapper">
    <h2>Neue Reservierung erstellen</h2>
    <form @submit.prevent="sendNewRsvtn">
      <div class="inp-wrapper">
        <label for="start-time">Beginn</label>
        <input type="datetime-local" id="start-time" name="starttime" v-model="starttime" required placeholder="Start Zeit" />
      </div>
      <div class="inp-wrapper">
        <label for="end-time">Ende</label>
        <input type="datetime-local" id="end-time" name="endtime" v-model="endtime" required placeholder="End Zeit" />
      </div>
      <div class="inp-wrapper">
        <label for="vehicles">Verfügbare Fahrzeuge</label>
        <select class="vehicles" id="vehicles">
          <option id="default">-- Fahrzeug wählen --</option>
          <option v-for="vcl in availableVehicles" :key="vcl.vin">{{ vcl }}</option>
        </select>
      </div>
      <button type="submit">Erstellen</button>
    </form>
  </div>
</template>

<script>
export default {
  name: "newVehicle",
  data() {
    return {
      starttime: '',
      endtime: '',
      availableVehicles: []
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