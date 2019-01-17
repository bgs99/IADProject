<template>
  <div id="app" class="app-root">
    <div class="tab" style="grid-area: tab">
      <router-link tag="button"
                   :class="{tablinks: true, active: $route.name === 'map'}"
                   :to="$store.state.wizard ? '/missions/new' : `/map/${$store.getters.mapId}`">Map</router-link>
      <router-link tag="button"
                   :class="{tablinks: true, active: $route.params.collection === 'agents'}" to="/agents/page/0">Personnel</router-link>
      <router-link tag="button"
                   :class="{tablinks: true, active: $route.params.collection === 'targets'}" to="/targets/page/0">Targets</router-link>
      <router-link tag="button"
                   :class="{tablinks: true, active: $route.params.collection === 'equipment'}" to="/equipment/weapons/0">Equipment</router-link>
      <router-link tag="button"
                   :class="{tablinks: true, active: $route.params.collection === 'missions'}" to="/missions/page/0">Missions</router-link>
      <router-link tag="button"
                   :class="{tablinks: true, active: $route.params.collection === 'people'}" to="/people/page/0">Registry</router-link>
      <router-link tag="button"
                   :class="{tablinks: true, active: $route.params.collection === 'requests'}" to="/requests/page/0">Requests</router-link>
      <router-link tag="button"
                   :class="{tablinks: true, active: $route.params.collection === 'reports'}" to="/reports/page/0">Reports</router-link>
      <router-link tag="button"
                   :class="{tablinks: true, active: false}" to="/map">Logout</router-link>
    </div>
    <router-view style="grid-area: app-content"></router-view>
  </div>
</template>

<script>
  import Map from './components/Map';
  import Registry from './components/Registry';

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
