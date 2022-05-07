<template>
  <nav>
      <div class="nav-elmts-group" id="left">
        <ul>
          <li><a href="#">New Reservation</a></li>
          <li><a href="#">Change Reservation</a></li>
          <li><a href="#">Delete Reservation</a></li>
        </ul>
      </div>
      <div class="nav-elmts-group" id="right">
        <ul>
          <li id="logout"><a href="#">Log Out</a></li>
        </ul>
      </div>
  </nav>
</template>

<script>
export default {
  name: "menuBar",
  props: {},
  data() {
    return{}
  },
  methods: {}
}
</script>

<style scoped>
nav {
  position: sticky;
  top: 0;
  width: 70vw;
  margin: 0 15vw;
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

li a {
  display: block;
  color: rgba(255, 255, 255);
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
  font-weight: bold;
}

#logout {

}

li a:hover {
  background-color: rgb(26, 26, 26);
}

@media (max-width:1000px) {
  nav {
    width: 100vw;
    margin: 0 15px 0 0;
  }
}
</style>