<template>
  <div class="err-wrapper" >
    <span>{{ errmsg }}</span>
  </div>
</template>

<script>
export default {
  name: "errorMsg",
  props: [
    'errmsg'
  ]
}
</script>

<style scoped>
.err-wrapper {
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  background-color: rgba(255, 50, 50, 0.65);
  margin: 10px 0;
  padding: 15px 30px;
  border: 2px solid darkred;
  border-radius: 10px;
  color: #222222ba;
}
</style>