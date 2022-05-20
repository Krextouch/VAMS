<template>
  <div class="LoginMaskWrapper">
    <img id="logo" src="@/assets/Logo.png" alt="VAMS Logo">
    <form @submit.prevent="handleSubmit">
      <input type="text" id="username" name="username" autocomplete="username" v-model="username" required placeholder="Email | Kürzel" />
      <input type="password" id="password" name="password" autocomplete="current-password" v-model="password" required placeholder="Passwort" />
      <button type="submit">Login</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'LoginMask',
  emits: ['infoPopup'],
  data() {
    return {
      username: '',
      password: ''
    }
  },
  methods: {
    async handleSubmit() {
      const response = await axios.post('auth/api/v1/login', {
        username: this.username,
        password: this.password
      }).catch(err => {
        console.log(err)
        this.$emit('infoPopup', {status: "error", msg: "Login error"})
      })
      if (response && response.status === 200) {
        localStorage.setItem('token', response.data.token)
        localStorage.setItem('expires', response.data.expiration)
        localStorage.setItem('employeeId', response.data.employeeId)
        localStorage.setItem('firstName', response.data.firstName)
        localStorage.setItem('lastName', response.data.lastName)
        localStorage.setItem('fullName', response.data.firstName + " " + response.data.lastName)
        localStorage.setItem('email', response.data.email)
        localStorage.setItem('nameTag', response.data.nameTag)
        localStorage.setItem('hasLicense', response.data.hasDrivingLicense)
        localStorage.setItem('hasOfficeRights', response.data.hasOfficeRights)
        this.$router.push({ name: 'Home'})
        this.$emit('infoPopup', {status: 'success', msg: 'Eingeloggt als '+localStorage.fullName})
      } else {
        this.$emit('infoPopup', {status: "error", msg: "Invalid Login"})
      }
    }
  }
}
</script>

<style scoped>
  .LoginMaskWrapper {
    background: dimgray;
    margin: 15vh auto;
    padding: 25px;
    top: 10vh;
    width: 50vw;
    max-width: 750px;
    min-width: 300px;
    min-height: 400px;
    border: 2px solid gray;
    border-radius: 15px;
    box-shadow: inset 0 0 1em black;
  }

  img#logo {
    height: 15vh;
  }

  input {
    width: 66%;
    height: 30px;
    margin: 2.5% 10%;
    padding: 10px;
    font-size: medium;
    border-radius: 30px;
  }

  button {
    display: inline-block;
    margin-top: 2%;
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
  
  @media (max-width: 600px) {
    input {
      width: 90%;
      margin: 0 0 10% 0;
    }
  }
</style>