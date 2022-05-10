<template>
  <navBar />
  <div class="content-wrapper">
    <div class="list-wrapper">
      <ul>
        <li class="rsvtn-wrapper" v-for="rsvtn in reservations" :key="rsvtn.id" v-on:click="updateReservation(rsvtn)">
          <div class="param-wrapper">
            <span class="rsvtn-param" v-for="(value, name) in rsvtn" :key="name" :id="name">{{ value }}</span>
            <span v-if="rsvtn.isVerified">
              <img src="../assets/verified.png" alt="Verified">
            </span>
            <span v-if="!rsvtn.isVerified">
              <img src="../assets/waiting.png" alt="Waiting for verification">
            </span>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import navBar from '../components/navBar.vue'

export default {
  name: "HomePage",
  components: {
    navBar
  },
  data() {
    return {
      reservations: [
        {id: 0, start_time_of_reservation: "today-12.00", end_time_of_reservation: "today-14.30", isVerified: true, vehicleVin: "", employeeId: 0},
        {id: 1, start_time_of_reservation: "today-12.00", end_time_of_reservation: "today-14.30", isVerified: false, vehicleVin: "", employeeId: 0},
        {id: 2, start_time_of_reservation: "today-12.00", end_time_of_reservation: "today-14.30", isVerified: true, vehicleVin: "", employeeId: 0},
        {id: 3, start_time_of_reservation: "today-12.00", end_time_of_reservation: "today-14.30", isVerified: true, vehicleVin: "", employeeId: 0},
        {id: 4, start_time_of_reservation: "today-12.00", end_time_of_reservation: "today-14.30", isVerified: false, vehicleVin: "", employeeId: 0},
        {id: 5, start_time_of_reservation: "today-12.00", end_time_of_reservation: "today-14.30", isVerified: false, vehicleVin: "", employeeId: 0},
        {id: 6, start_time_of_reservation: "today-12.00", end_time_of_reservation: "today-14.30", isVerified: true, vehicleVin: "", employeeId: 0},
        {id: 7, start_time_of_reservation: "today-12.00", end_time_of_reservation: "today-14.30", isVerified: true, vehicleVin: "", employeeId: 0},
        {id: 8, start_time_of_reservation: "today-12.00", end_time_of_reservation: "today-14.30", isVerified: true, vehicleVin: "", employeeId: 0},
        {id: 9, start_time_of_reservation: "today-12.00", end_time_of_reservation: "today-14.30", isVerified: true, vehicleVin: "", employeeId: 0},
        {id: 10, start_time_of_reservation: "today-12.00", end_time_of_reservation: "today-14.30", isVerified: true, vehicleVin: "", employeeId: 0},
        {id: 11, start_time_of_reservation: "today-12.00", end_time_of_reservation: "today-14.30", isVerified: true, vehicleVin: "", employeeId: 0},
        {id: 12, start_time_of_reservation: "today-12.00", end_time_of_reservation: "today-14.30", isVerified: true, vehicleVin: "", employeeId: 0},
        {id: 13, start_time_of_reservation: "today-12.00", end_time_of_reservation: "today-14.30", isVerified: true, vehicleVin: "", employeeId: 0},
        {id: 14, start_time_of_reservation: "today-12.00", end_time_of_reservation: "today-14.30", isVerified: true, vehicleVin: "", employeeId: 0},
        {id: 15, start_time_of_reservation: "today-12.00", end_time_of_reservation: "today-14.30", isVerified: true, vehicleVin: "", employeeId: 0},
        {id: 16, start_time_of_reservation: "today-12.00", end_time_of_reservation: "today-14.30", isVerified: true, vehicleVin: "", employeeId: 0},
        {id: 17, start_time_of_reservation: "today-12.00", end_time_of_reservation: "today-14.30", isVerified: true, vehicleVin: "", employeeId: 0},
        {id: 18, start_time_of_reservation: "today-12.00", end_time_of_reservation: "today-14.30", isVerified: true, vehicleVin: "", employeeId: 0},
        {id: 19, start_time_of_reservation: "today-12.00", end_time_of_reservation: "today-14.30", isVerified: true, vehicleVin: "", employeeId: 0},
        {id: 20, start_time_of_reservation: "today-12.00", end_time_of_reservation: "today-14.30", isVerified: true, vehicleVin: "", employeeId: 0},
        {id: 21, start_time_of_reservation: "today-12.00", end_time_of_reservation: "today-14.30", isVerified: true, vehicleVin: "", employeeId: 0},
        {id: 22, start_time_of_reservation: "today-12.00", end_time_of_reservation: "today-14.30", isVerified: true, vehicleVin: "", employeeId: 0},
        {id: 23, start_time_of_reservation: "today-12.00", end_time_of_reservation: "today-14.30", isVerified: true, vehicleVin: "", employeeId: 0},
        {id: 24, start_time_of_reservation: "today-12.00", end_time_of_reservation: "today-14.30", isVerified: true, vehicleVin: "", employeeId: 0},
        {id: 25, start_time_of_reservation: "today-12.00", end_time_of_reservation: "today-14.30", isVerified: true, vehicleVin: "", employeeId: 0},
        {id: 26, start_time_of_reservation: "today-12.00", end_time_of_reservation: "tomorrow-12.00", isVerified: false, vehicleVin: "", employeeId: 0},
        {id: 27, start_time_of_reservation: "yesterday-08.00", end_time_of_reservation: "yesterday-16.00", isVerified: true, vehicleVin: "", employeeId: 0},
      ]
    }
  },
  methods: {
    updateReservation(rsvtn) {
      console.log("clicked reservation of id:", rsvtn.id)
    }
  }
}
</script>

<style scoped>
.content-wrapper {
  height: fit-content;
  display: flex;
}

ul {
  padding: 0;
  margin: 0;
  list-style: none;
}

.list-wrapper {
  height: calc(96vh - 96px - 2px);
  background: dimgray;
  padding: 0;
  margin: 2vh 5vw;
  border: 2px solid gray;
  border-radius: 5px;
  box-shadow: inset 0 0 1em black; ;
  overflow-y: scroll;
}

.rsvtn-wrapper {
  width: 600px;
  padding: 20px 25px;
  margin: 2px 1px;
  background-color: #ababab;
  border-radius: 2px;
  cursor: pointer;
  user-select: none;
}

.rsvtn-wrapper:first-of-type {
  margin-top: 1px;
}

.rsvtn-wrapper:last-of-type {
  margin-bottom: 1px;
}

.param-wrapper img {
  height: 18px;
  width: 18px;
  padding: 2px;
}

.rsvtn-wrapper:hover {
  background-color: #868686;
}

.rsvtn-param#isVerified{
  display: none;
}

.param-wrapper {
  display: flex;
  justify-content: space-between;
}

/*
Scrollbar styles imported from:
  https://css-tricks.com/the-current-state-of-styling-scrollbars-in-css/
*/

.list-wrapper::-webkit-scrollbar-track
{
  -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
  background-color: #F5F5F5;
}

.list-wrapper::-webkit-scrollbar
{
  width: 10px;
  background-color: #F5F5F5;
}

.list-wrapper::-webkit-scrollbar-thumb
{
  background-color: rgb(18, 18, 18);
  border: 2px solid dimgray;
}

</style>