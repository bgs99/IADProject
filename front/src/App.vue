<template>
  <div id="app" class="app-root">
    <div class="tab">
      <router-link tag="button" class="tablinks" to="/map">Map</router-link>
      <router-link tag="button" class="tablinks" to="/map">Personnel</router-link>
      <router-link tag="button" class="tablinks" to="/map">Targets</router-link>
      <router-link tag="button" class="tablinks" to="/map">Equipment</router-link>
      <router-link tag="button" class="tablinks" to="/map">Missions</router-link>
      <router-link tag="button" class="tablinks" to="/people">Registry</router-link>
      <router-link tag="button" class="tablinks" to="/map">Requests</router-link>
      <router-link tag="button" class="tablinks" to="/map">Logout</router-link>
    </div>
    <router-view class="app-content"></router-view>
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
