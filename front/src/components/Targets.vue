<template>
  <div style="background-color: lightgray; display: inline-block; margin: 20px; width: 40%">
    <div class="person-root">
      <img style="grid-area: person-img" src="~@/assets/logo.png" width="150" height="150">
      <div style="grid-area: person-info">
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
          Status: {{src.status ? 'Alive' : 'Dead'}}
        </p>
      </div>

      <div class="person-id">

      </div>
    </div>
  </div>
</template>

<script>
  import Danger from './Danger';

  export default {
  name: 'People',
    components: {Danger},
    props: ['src']
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  router-link {
    white-space: normal;
    width: 90%;
  }
  .person-root {
    display: grid;
    grid-template-columns: min-content auto;
    grid-template-rows: min-content auto;
    grid-template-areas:
      "person-img person-info"
      "none       person-info";
  }
</style>
