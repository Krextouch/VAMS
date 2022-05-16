<template>
  <div class="LoginMaskWrapper">
    <h1>VAMS</h1>
    <form @submit.prevent="handleSubmit">
      <input type="text" id="username" name="username" autocomplete="username" v-model="username" required placeholder="Username" />
      <input type="password" id="password" name="password" autocomplete="current-password" v-model="password" required placeholder="Password" />
      <button type="submit">Login</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'LoginMask',
  props: [

  ],
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
        this.$emit('infoPopup', ["error", "Login error"])
      })
      if (response && response.status === 200) {
        localStorage.setItem('token', response.data.token)
        localStorage.setItem('expires', response.data.expiration)
        localStorage.setItem('employeeId', response.data.employeeId)
        localStorage.setItem('hasOfficeRights', response.data.hasOfficeRights)
        this.$router.push({ name: 'Home'})
      } else {
        this.$emit('infoPopup', ["error", "Invalid Login"])
      }
    }
  }
}
</script>

<style scoped>
  .LoginMaskWrapper {
    background: dimgray;
    position: absolute;
    margin: auto;
    padding: 0;
    top: -10vh; left: 0; bottom: 0; right: 0;
    width: 50vw;
    max-width: 750px;
    min-width: 300px;
    height: 35vw;
    max-height: 500px;
    min-height: 400px;
    border: 2px solid gray;
    border-radius: 15px;
    box-shadow: inset 0 0 1em black;
  }

  h1 {
    width: 50%;
    margin: 20px 25%;
    padding-bottom: 19px;
    border-bottom: 1px solid black;
    letter-spacing: 2px;
  }

  p {
    width: 66%;
    margin: 0 10%;
    font-size: large;
    color: white;
  }

  input {
    width: 66%;
    height: 30px;
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
  
  @media (max-width: 600px) {
    input {
      width: 90%;
      margin: 0 0 10% 0;
    }
  }
</style>