<template>
  <nav>
      <div class="nav-elmts-group" id="left">
        <ul>
          <li><a href="./home">VAMS</a></li>
          <li><router-link :to="{ name: 'Home' }">Reservierungen</router-link></li>
          <li><router-link :to="{ name: 'newReservation' }">Neue Reservierung</router-link></li>
        </ul>
      </div>
      <div class="nav-elmts-group" id="right">
        <ul>
          <li><router-link :to="{ name: '' }">Office</router-link></li>
          <li id="logout"><a v-on:click="logout()"><img src="../assets/logout_white.png" alt="Log Out"></a></li>
        </ul>
      </div>
  </nav>
</template>

<script>
export default {
  name: "navBar",
  props: {},
  data() {
    return{}
  },
  methods: {
    logout() {
      console.log("Logout")
      console.log(localStorage.token)
      localStorage.token = "";
      console.log("After logout")
      console.log(localStorage.token)
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>
nav {
  position: sticky;
  top: 0;
  width: 100%;
  margin: 0;
  padding-top: 25px;
  display: flex;
  justify-content: space-between;
  background-color: rgba(60, 60, 60);
}

ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
  overflow: hidden;
}

li {
  float: left;
}

li a, li router-link {
  display: block;
  color: white;
  text-align: center;
  padding: 24px 26px;
  text-decoration: none;
  font-weight: bold;
}

#logout img {
  height: 24px;
  width: 24px;
  margin: auto;
}

#logout a {
  padding: 18px 26px;
}

li a:hover, li router-link:hover {
  background-color: rgb(28, 28, 28);
  border-radius: 4px;
}

</style>