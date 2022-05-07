<template>
  <div class="LoginMaskWrapper">
    <h1>LOGIN</h1>
    <form action="">
      <input type="text" id="username" name="username" v-model="username" required placeholder="Username" />
      <input type="password" id="password" name="password" v-model="password" required placeholder="Password" />
      <button type="submit" v-on:click="handleSubmit()">Login</button>
    </form>
  </div>
</template>

<script>
export default {
  name: 'LoginMask',
  props: {
  },
  data() {
    return {
      username: "",
      password: ""
    }
  },
  methods: {
    handleSubmit() {
      if (this.username != "" && this.password != "") {
        const data = {
          username: this.username,
          password: this.password
        }
        console.log(data)
      } else {
        console.log("Empty input")
      }
    }
  }
}
</script>

<style scoped>
  .LoginMaskWrapper {
    background: dimgray;
    width: 50vw;
    max-width: 750px;
    min-width: 300px;
    height: 35vw;
    max-height: 500px;
    min-height: 400px;
    margin: 0 25%;
    padding: 15px;
    border: 2px solid gray;
    border-radius: 15px;
    box-shadow: inset 0 0 1em black;
  }
  h1 {
    width: 50%;
    margin: 20px 25%;
    padding-bottom: 19px;
    border-bottom: 1px solid black;
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
    border-radius: 15px;
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
  }
  button:hover, button:focus {
    background-color: cornflowerblue;
    box-shadow: 0 6px #999;
  }
  button:active {
    background-color: #5a86d5;
    box-shadow: 0 4px #555555;
    transform: translateY(5px);
  }
  
  @media screen {
    
  }
</style>