<template>
  <div style="background-color: lightgray; display: inline-block; margin: 20px; width: 40%">
    <div class="root">
      <img style="grid-area: img" src="~@/assets/logo.png" width="150" height="150" v-if="src.isPerson">
      <div style="grid-area: info">
        <h1>
          #{{src.id}} {{src.name}}
        </h1>
        <p>
          Danger level <Danger :val="src.danger"></Danger>
        </p>
        <p>
          Location: <router-link tag="button" :to="`/map/${src.location.first}`">
          {{src.location.second}}
        </router-link>
        </p>
        <p>
          Status: {{src.active ? 'Active' : 'Neutralized'}}
        </p>
      </div>
      <router-link tag="button" style="grid-area: mission" :to="`/missions/new/${src.id}`"
                   v-if="src.active && $store.state.currentMission === undefined">Add mission</router-link>
    </div>
  </div>
</template>

<script>
  import Danger from './Danger';

  export default {
  name: 'Targets',
    components: {Danger},
    props: ['src']
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .root {
    display: grid;
    grid-template-columns: min-content auto;
    grid-template-rows: min-content auto;
    grid-template-areas:
      "img  info"
      "mission info";
  }
</style>
