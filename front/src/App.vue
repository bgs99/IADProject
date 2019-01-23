<template>
  <Login v-if="$store.state.user === null"></Login>
  <div v-else id="app" class="app-root">
    <div class="tab" style="grid-area: tab">
      <button class="ham" @click="inMenu = !inMenu">
        <svg height="100%" width="20%" viewBox="0 0 100 100" version="1.1"
             xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
          <line x1="20" x2="80" y1="25" y2="25" stroke="white" stroke-width="5"></line>
          <line x1="20" x2="80" y1="50" y2="50" stroke="white" stroke-width="5"></line>
          <line x1="20" x2="80" y1="75" y2="75" stroke="white" stroke-width="5"></line>
        </svg>
        <h2 style="width: 40%; display: inline-block; color: white">
          Menu
        </h2>
      </button>

      <button @click="navigate($store.state.wizard ? '/missions/new' : `/map/${$store.getters.mapId}`)"
                   :class="{tablinks: true, active: $route.name === 'map', menuOpen: inMenu}">Map</button>
      <button @click="navigate('/agents/page/0')"
                   v-if="$store.getters.admin"
                   :class="{tablinks: true, active: $route.params.collection === 'agents', menuOpen: inMenu}">Personnel</button>
      <button @click="navigate('/targets/people/page/0')"
                   v-if="$store.getters.admin || $store.getters.field"
                   :class="{tablinks: true, active: $route.params.collection === 'targets', menuOpen: inMenu}">Targets</button>
      <button @click="navigate($store.getters.field ? '/equipment/weapons/page/0' : ('/repair/' + ($store.getters.transportman ? 'transport' : 'weapons') + '/page/0'))"
              v-if="$store.getters.field || $store.getters.fixer"
              :class="{tablinks: true, active: $route.params.collection === 'equipment', menuOpen: inMenu}">
        {{$store.getters.field ? 'Equipment' : 'Repairs'}}
      </button>
      <button @click="navigate($store.getters.missionId !== null ? `/missions/id/${$store.getters.missionId}` : '/missions/page/0')"
              :class="{tablinks: true, active: $route.params.collection === 'missions', menuOpen: inMenu}">
        Missions</button>
      <button v-if="$store.getters.admin" @click="navigate('/registry/people/page/0')"
              :class="{tablinks: true, active: $route.params.collection === 'registry', menuOpen: inMenu}">Registry</button>
      <button v-if="$store.getters.admin" @click="navigate('/requests/page/0')"
              :class="{tablinks: true, active: $route.params.collection === 'requests', menuOpen: inMenu}">Requests</button>
      <button v-if="$store.getters.admin"
        @click="navigate('/reports/page/0')"
              :class="{tablinks: true, active: $route.params.collection === 'reports', menuOpen: inMenu}">Reports</button>
      <button
        :class="{tablinks: true, active: false, menuOpen: inMenu}" @click="logout">Logout</button>
    </div>
    <router-view :class="{hiddenMain: inMenu}" id="main" style="grid-area: app-content"></router-view>
  </div>
</template>

<script>
  import Map from './components/Map';
  import Registry from './components/Cards';
  import Login from './components/Login';

  export default {
    name: 'App',
    components: {
      Login,
      Map,
      Registry
    },
    methods: {
      logout () {
        this.$store.dispatch('logout').then();
      },
      navigate (path) {
        this.inMenu = false;
        this.$router.push(path);
      }
    },
    data () {
      return {
        inMenu: false
      }
    }
}
</script>

<style>

  @media screen and (max-width: 817px) {
    .ham {
      display: block !important;
    }
    .tab {
      display: grid;
      grid-template-columns: 100%;
      grid-auto-rows: 1fr;
    }
    .tab button.tablinks {
      float: none;
      width: 100%;
      display: none;
    }
    button.tablinks.menuOpen {
      display: block !important;
    }
    .hiddenMain {
      display: none !important;
    }
  }

  .ham {
    width: 100%;
    height: 60px;
    display: none;
  }
  .ham > * {
    float: left;
    vertical-align: center;
    margin: 0;
  }

  .tab {
    width: 100%;
    overflow: hidden;
    border: 1px solid #ccc;
    background-color: #aaaaff;
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
  body {
    background-color: #000050;
    margin: 0;
    color: white;
  }
</style>
