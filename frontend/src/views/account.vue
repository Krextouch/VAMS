<template>
  <div class="card-wrapper">
    <h2>Passwort ändern</h2>
    <form @submit.prevent="submit">
      <input type="password" v-model="password" placeholder="Neues Passwort"/>
      <input type="password" v-model="password_repeat" placeholder="Passwort wiederholen"/>
      <button type="submit">Abschicken</button>
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "accountSettings",
  components: {
  },
  data() {
    return {
      password: "",
      password_repeat: ""
    }
  },
  methods: {
    async sendData() {
      const response = await axios.post("employee/api/v1/passwordReset", {
        name: localStorage.getItem("username")
      }, {
        headers: {
          "Authorization": "Bearer " + localStorage.token
        }
      }).catch(err => {
        console.log("resetPassword err: ", err)
      })
      console.log(response)
    },
    submit() {
      this.sendData()
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

form {
  padding: 5vh 1vw;
}

input {
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