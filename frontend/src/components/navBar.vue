<template>
  <nav>
      <div class="nav-elmts-group" id="left">
        <ul>
          <li class="icon" id="logo"><img src="@/assets/Logo.png" alt="VAMS Logo"></li>
          <li><router-link :to="{ name: 'Home' }">Meine Reservierungen</router-link></li>
          <li><router-link :to="{ name: 'newReservation' }">Neue Reservierung</router-link></li>
        </ul>
      </div>
      <div class="nav-elmts-group" id="right">
        <ul>
          <li v-if="this.hasOfficeRights === 'true'"><router-link :to="{ name: 'OfficeHome' }">zum Admin Portal</router-link></li>
          <li class="icon" id="account"><router-link :to="{ name: 'Account' }"><img src="@/assets/account.png" alt="Account settings"></router-link></li>
          <li class="icon" id="logout"><a v-on:click="logout()"><img src="@/assets/logout_white.png" alt="Log Out"></a></li>
        </ul>
      </div>
  </nav>
</template>

<script>
import axios from "axios";

export default {
  name: "navBar",
  created() {
    this.hasOfficeRights = localStorage.getItem('hasOfficeRights')
  },
  data() {
    return{
      hasOfficeRights: ''
    }
  },
  methods: {
    async logout() {
      const response = await axios.post("auth/api/v1/logout", {}, {
        headers: {
          "Authorization": "Bearer " + localStorage.token
        }
      })
      if (response.status === 200) {
        localStorage.clear()
        sessionStorage.clear()
        this.$router.push('/login')
      } else {
        this.$emit('infoPopup', ["err", "Konnte nicht ausgeloggt werden"])
        console.log("Logout not completed")
      }
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

#logo {
  padding: 0 3px;
}

#logo img {
  height: 66px;
  width: 66px;
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

.icon img {
  height: 24px;
  width: 24px;
  margin: auto;
}

.icon a {
  padding: 18px 26px;
}

li:hover {
  background-color: rgb(28, 28, 28);
  border-radius: 4px;
}
</style>