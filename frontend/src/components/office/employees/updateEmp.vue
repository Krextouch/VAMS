<template>
  <div class="card-wrapper">
    <div class="content" v-if="empToUpdate">
      <form @submit.prevent="updateEmp">
        <input type="text" id="firstname" name="firstname" v-model="firstname" required placeholder="Vorname" />
        <input type="text" id="lastname" name="lastname" v-model="lastname" required placeholder="Nachname" />
        <input type="email" id="email" name="email" v-model="email" required placeholder="Email" />
        <input type="text" id="nameTag" name="nameTag" v-model="nameTag" required placeholder="Kürzel" />
        <input type="date" id="birthday" name="birthday" v-model="birthday" required />
        <input type="text" id="birthplace" name="birthplace" v-model="birthplace" required placeholder="Geburtsort" />
        <div class="inp-wrapper">
          <label for="hasLicense">Besitzt Führerschein</label>
          <input type="checkbox" id="hasLicense" name="hasLicense" v-model="hasLicense" required/>
        </div>
        <div class="inp-wrapper">
          <label for="hasOfficeRights">Ist Office Admin</label>
          <input type="checkbox" id="hasOfficeRights" name="hasOfficeRights" v-model="hasOfficeRights" required />
        </div>
        <button type="submit">Abschicken</button>
      </form>
      <button id="delete" @click="deleteEmp">Mitarbeiter Entfernen</button>
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
  emits: ['infoPopup'],
  props: ['empToUpdate'],
  updated() {
    if (this.empToUpdate) {
      this.firstname = this.empToUpdate.firstname
      this.lastname = this.empToUpdate.lastname
      this.email = this.empToUpdate.email
      this.nameTag = this.empToUpdate.nameTag
      this.birthday = this.empToUpdate.birthday
      this.birthplace = this.empToUpdate.birthplace
      this.hasLicense = this.empToUpdate.hasDrivingLicense
      this.hasOfficeRights = this.empToUpdate.hasOfficeRights
    }
  },
  data(){
    return {
      firstname: "",
      lastname: "",
      email: "",
      nameTag: "",
      birthday: "",
      birthplace: "",
      hasLicense: "",
      hasOfficeRights: ""
    }
  },
  methods: {
    async updateEmp() {
      console.log("update emp of id: ", this.empToUpdate.id)
      // const data = {
      //   "employeeId": this.empToUpdate.id,
      //   "firstname": this.firstname,
      //   "lastname": this.lastname,
      //   "email": this.email,
      //   "nameTag": this.nameTag,
      //
      // }
      // const response = await axios.put(`office/api/v1/updateEmployee/${this.empToUpdate.id}`, {
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
      console.log("delete emp of id: ", this.empToUpdate.id)
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
  padding: 3vh 1vw;
}

label {
  margin-right: 10px;
}

input:not([type="checkbox"]) {
  width: 66%;
  margin: 0 10% 3% 10%;
  padding: 10px;
  font-size: medium;
  border-radius: 30px;
}

input[type="checkbox"] {
  height: 20px;
  width: 20px;
  padding: 3px;
}

.inp-wrapper {
  color: black;
  font-weight: bold;
  padding: 5px 10px;
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
  border: none;
  border-radius: 15px;
  background-color: cornflowerblue;
}

button:hover, button:focus {
  box-shadow: 0 5px #999;
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