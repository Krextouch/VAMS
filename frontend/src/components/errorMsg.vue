<template>
  <div class="info-wrapper" :class="info.status">
    <span>{{ info.msg }}</span>
  </div>
</template>

<script>
export default {
  name: "errorMsg",
  props: ['info']
}
</script>

<style scoped>
.info-wrapper {
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  margin: 10px 0;
  padding: 15px 30px;
  border-radius: 10px;
  color: #222222ba;
}

.error {
  background-color: rgba(255, 50, 50, 0.65);
  border: 2px solid darkred;
}
.info {
  background-color: rgba(218, 165, 32, 0.65);
  border: 2px solid darkgoldenrod;
}
.success {
  background-color: rgba(143, 188, 143, 0.65);
  border: 2px solid limegreen;
}
</style>