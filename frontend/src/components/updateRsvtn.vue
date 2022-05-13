<template>
  <div class="list-wrapper">
    <div class="info" id="none-selected">
      <span>Wähle eine Reservierung um zu bearbeiten</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "updateRsvtn"
}
</script>

<style scoped>
.list-wrapper {
  width: 40vw;
  height: calc(96vh - 96px - 2px);
  background: dimgray;
  padding: 0;
  margin: 1vh 5vw;
  border: 2px solid gray;
  border-radius: 5px;
  box-shadow: inset 0 0 1em black;
}

.info {
  color: lightgray;
  font-weight: bold;
}

#none-selected {
  position: relative;
  top: 50%;
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