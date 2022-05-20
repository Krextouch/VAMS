<template>
  <div class="card-wrapper">
    <h2>Passwort ändern</h2>
    <div class="account-info">
      <p>Name: <span>{{ this.name }}</span></p>
      <p>Email: <span>{{ this.email }}</span></p>
      <p>Kürzel: <span>{{ this.nameTag }}</span></p>
      <p>Führerschein: <span>{{ this.hasDrivingLicense }}</span></p>
      <p>Admin Berechtigung: <span>{{ this.hasDrivingLicense }}</span></p>
    </div>
    <form @submit.prevent="submit">
      <input type="password" v-model="old_password" placeholder="Altes Passwort" required/>
      <input type="password" v-model="password" placeholder="Neues Passwort" required/>
      <input type="password" v-model="password_repeat" placeholder="Neues Passwort wiederholen" required/>
      <button type="submit">Abschicken</button>
    </form>
  </div>
  <impressum/>

</template>

<script>
import axios from "axios";
import impressum from "@/components/impressum";

export default {
  name: "accountSettings",
  components: {
    impressum
  },
  emits: ['infoPopup'],
  data() {
    return {
      old_password: "",
      password: "",
      password_repeat: "",
      name: localStorage.getItem('fullName'),
      email: localStorage.getItem('email'),
      nameTag: localStorage.getItem('nameTag'),
      hasDrivingLicense: localStorage.getItem('hasLicense') === 'true' ? 'Ja' : 'Nein',
      hasOfficeRights: localStorage.getItem('hasOfficeRights') === 'true' ? 'Ja' : 'Nein'
    }
  },
  methods: {
    async sendData() {
      const response = await axios.post("employee/api/v1/passwordChange", {
        oldPassword: this.old_password,
        newPassword: this.password
      }, {
        headers: {
          "Authorization": "Bearer " + localStorage.token
        }
      }).catch(err => {
        console.log("resetPassword err: ", err)
      })
      if (response && response.status === 200) {
        this.$router.push({ name: 'Home'})
        this.$emit('infoPopup', {status: 'success', msg: 'Passwort wurde geändert'})
      }
    },
    submit() {
      if (this.password === this.password_repeat) {
        this.sendData()
      } else {
        this.$emit('infoPopup', {status: 'info', msg: 'Neue Passörter stimmen nicht überein'})
        this.password = ""
        this.password_repeat = ""
      }
    }
  }
}
</script>

<style scoped>
.card-wrapper {
  width: 40vw;
  min-width: 350px;
  background: dimgray;
  padding: 0;
  margin: 3vh auto;
  border: 2px solid gray;
  border-radius: 5px;
  box-shadow: inset 0 0 1em black;
  overflow-y: hidden;
}

h2 {
  margin: 20px 15%;
  padding-bottom: 19px;
  border-bottom: 1px solid black;
}

.account-info {
  font-size: large;
}

.account-info p {
  margin: 5px;
  padding: 0;
  color: #2c3e50;
  font-weight: bold;
}

.account-info p span {
  color: black;
  font-weight: normal;
}

form {
  padding: 5vh 1vw;
}

input {
  width: 66%;
  margin: 0 10% 5% 10%;
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
</style>