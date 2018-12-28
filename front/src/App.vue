<template>
  <div id="app" class="app-root">
    <div class="tab">
      <button class="tablinks" @click="state = 'Map'">Map</button>
      <button class="tablinks" >Personnel</button>
      <button class="tablinks" >Targets</button>
      <button class="tablinks" >Equipment</button>
      <button class="tablinks" >Missions</button>
      <button class="tablinks" @click="state = 'Registry'">Registry</button>
      <button class="tablinks" >Requests</button>
      <button class="tablinks" >Logout</button>
    </div>
    <div class="app-content">
      <Map :mid="mid" v-if="state === 'Map'" @people="mapPeople"></Map>
      <Registry v-else-if="state === 'Registry'" :src="rsrc" :mid="mid" @map="state = 'Map'"></Registry>
    </div>
  </div>
</template>

<script>
  import Map from './components/Map'
  import Registry from './components/Registry'

  export default {
  name: 'App',
  components: {
    Map,
    Registry
  },
  methods: {
    mapPeople () {
      this.state = 'Registry'
      this.rsrc = 'location'
    }
  },
  data () {
    return {
      state: 'Map',
      rsrc: 'location'
    }
  }
}
</script>

<style>
  .tab {
    overflow: hidden;
    border: 1px solid #ccc;
    background-color: #f1f1f1;
  }

  .tab button {
    background-color: inherit;
    float: left;
    border: none;
    outline: none;
    cursor: pointer;
    padding: 14px 16px;
    transition: 0.3s;
  }

  .tab button:hover {
    background-color: #ddd;
  }

  .tab button.active {
    background-color: #ccc;
  }

  .app-root {
    display: grid;
    grid-template-columns: auto;
    grid-template-rows: max-content auto;
    grid-template-areas:
      "tab"
      "app-content";
  }

</style>
