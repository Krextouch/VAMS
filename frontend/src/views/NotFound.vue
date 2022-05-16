<template>
  <h2>404</h2>
  <h3>Page <span>{{ $route.fullPath }}</span> not found</h3>
</template>

<script>
export default {
  name: "NotFound"
}
</script>

<style scoped>
h3 span {
  color: #555555;
  font-weight: normal;
  text-decoration: underline;
}
</style>