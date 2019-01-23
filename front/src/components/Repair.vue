<template>
  <div>
    <div class="root">
      <img style="grid-area: img" :src="`/static/${src.isWeapon ? 'weapons' : 'transport'}/${src.id}.jpg`"
           height="150">
      <div style="grid-area: details">
        <h1 align="center">
          {{src.name}}
        </h1>
        <input v-if="src.end === null" type="button" value="Start"
               @click="$store.dispatch('startRepair', {id:src.id, iw: src.isWeapon})"/>
        <div v-else>
          Time left: {{Math.floor((src.end - Date.now()) / 1000 / 60 / 60 / 24)}} days
          {{Math.floor((src.end - Date.now()) / 1000 / 60 / 60 - Math.floor((src.end - Date.now()) / 1000 / 60 / 60 / 24)*24)}} hours<br>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'Repair',
    props: ['src']
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .root {
    display: grid;
    grid-template-columns: min-content auto;
    grid-template-rows: min-content min-content;
    grid-template-areas:
      "img details"
      ".   details";
  }
</style>
