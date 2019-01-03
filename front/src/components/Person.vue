<template>
  <div style="background-color: lightgray; display: inline-block; margin: 20px; width: 40%">
    <div class="person-root">
      <img class="person-img" src="~@/assets/logo.png" width="150" height="150">
      <div class="person-info">
        <h1>
          #{{src.id}} {{src.name}}
        </h1>
        <p>
          Danger level {{src.danger}}
        </p>
        <p>
          Location: <input type="button" :value="src.location.second" @click="map"/>
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
  export default {
  name: 'Person',
  props: ['src'],
  methods: {
    map: function () {
      this.$emit('map', this.src.location.first)
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  input[type="button"] {
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
