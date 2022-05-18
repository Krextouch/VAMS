<template>
  <div class="card-wrapper">
    <div class="content" v-if="empToUpdate">
      <form @submit.prevent="updateEmp">
        <div class="names">
          <input type="text" id="firstname" name="firstname" v-model="firstname" required placeholder="Vorname" />
          <input type="text" id="lastname" name="lastname" v-model="lastname" required placeholder="Nachname" />
        </div>
        <input type="email" id="email" name="email" v-model="email" required placeholder="Email" />
<!--        <input type="text" id="password" name="password" v-model="password" required placeholder="Passwort" />-->
        <input type="text" id="nameTag" name="nameTag" v-model="nameTag" required placeholder="Kürzel" />
        <div class="birthdata">
          <input type="date" id="birthday" name="birthday" v-model="birthday" required />
          <input type="text" id="birthplace" name="birthplace" v-model="birthplace" required placeholder="Geburtsort" />
        </div>
        <input type="text" id="workCard" name="workCard" v-model="workCard" required placeholder="WorkCard ID" />
        <div class="inp-wrapper">
          <label for="hasLicense">Besitzt Führerschein</label>
          <input type="checkbox" id="hasLicense" name="hasLicense" v-model="hasLicense" />
        </div>
        <div class="inp-wrapper">
          <label for="hasOfficeRights">Ist Office Admin</label>
          <input type="checkbox" id="hasOfficeRights" name="hasOfficeRights" v-model="hasOfficeRights" />
        </div>
        <button type="submit">Änderungen speichern</button>
      </form>
      <button id="delete" @click="deleteEmp">Mitarbeiter Entfernen</button>
    </div>
    <div class="info" id="none-selected" v-if="!empToUpdate">
      <span>Wähle einen Mitarbeiter um zu bearbeiten</span>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "updateEmp",
  emits: ['infoPopup'],
  props: ['empToUpdate'],
  watch: {
    empToUpdate: function() {
      if (this.empToUpdate) {
        this.initForm()
      }
    }
  },
  data(){
    return {
      firstname: "",
      lastname: "",
      email: "",
      nameTag: "",
      password: "",
      birthday: "",
      birthplace: "",
      workCard: "",
      hasLicense: false,
      hasOfficeRights: false,
    }
  },
  methods: {
    initForm() {
      this.firstname = this.empToUpdate.firstname
      this.lastname = this.empToUpdate.lastname
      this.email = this.empToUpdate.email
      this.nameTag = this.empToUpdate.nameTag
      this.birthday = this.empToUpdate.birthday
      this.birthplace = this.empToUpdate.birthplace
      this.workCard = this.empToUpdate.workCard
      this.hasLicense = this.empToUpdate.hasDrivingLicense
      this.hasOfficeRights = this.empToUpdate.hasOfficeRights
    },
    async updateEmp() {
      const data = {
        employeeId: this.empToUpdate.employeeId,
        firstName: this.firstname,
        lastName: this.lastname,
        email: this.email,
        nameTag: this.nameTag,
        password: this.empToUpdate.password,
        workCard: this.workCard,
        birthday: this.birthday,
        birthplace: this.birthplace,
        hasDrivingLicense: this.hasLicense,
        hasOfficeRights: this.hasOfficeRights,
      }
      const response = await axios.put(`office/api/v1/updateEmployee/${this.empToUpdate.employeeId}`, data, {
        headers: {
          "Authorization": "Bearer " + localStorage.token
        }
      }).catch(err => {
        console.log("updateEmp err: ", err)
        this.$router.push({name: "allEmployees"})
        this.$emit('infoPopup', {status: "error", msg: "Mitarbeiterdaten konnten nicht aktualisiert werden"})
      })
      console.log("updateEmp response: ", response)
      if (response && response.status === 200) {
        window.location.reload()
        this.$emit('infoPopup', {status: "success", msg: "Änderungen gespeichert"})
      }
    },
    async deleteEmp() {
      const response = await axios.delete(`office/api/v1/deleteEmployee/${this.empToUpdate.employeeId}`, {
        headers: {
          "Authorization": "Bearer " + localStorage.token
        }
      }).catch(err => {
        console.log("deleteEmp err: ", err)
        this.$router.push({name: "allEmployees"})
        this.$emit('infoPopup', {status: "error", msg: "Mitarbeiter konnte nicht gelöscht werden"})
      })
      if (response && response.status === 200) {
        window.location.reload()
        this.$emit('infoPopup', {status: "success", msg: "Mitarbeiter gelöscht"})
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

.inp-wrapper {
  color: black;
  font-weight: bold;
  padding: 0 10px;
}

input:not([type="checkbox"]) {
  width: calc(80% - 24px);
  min-width: 176px;
  margin: 5px auto 15px auto;
  padding: 10px;
  font-size: medium;
  border-radius: 30px;
}

input[type="checkbox"] {
  height: 15px;
  width: 15px;
  padding: 3px;
  margin: 8px;
}

.names input {
  width: calc(40% - 24px);
}

.birthdata #birthday {
  width: 15%;
  min-width: 133px
}

.birthdata #birthplace {
  width: 48%;
}
.info {
  color: lightgray;
  font-weight: bold;
}

#none-selected {
  position: relative;
  top: 50%;
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