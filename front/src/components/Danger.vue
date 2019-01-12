<template>
    <div :style="{height: '20px', width: '100px', backgroundColor: 'black'}">
      <div :style="{backgroundColor: 'red', width: val + 'px', height: '20px'}"></div>
    </div>
</template>

<script>
  export default {
    name: "Danger",
    props: {
      val: Number
    }
  }
</script>

<style scoped>

</style>
