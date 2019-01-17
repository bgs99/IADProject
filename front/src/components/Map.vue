<template>
  <div class="map-root">
    <svg width="100%" version="1.1" viewBox="0 0 2513 1405"
         xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
      <image xlink:href="~@/assets/map.png" x="0" y="0" width="100%" height="100%">
      </image>
      <router-link tag="circle" v-for="l in locs" :key="l.id" :to="`/map/${l.id}`"
                   :class="{active: l.id === $store.getters.mapId}"
                   :cx="l.x" :cy="l.y" stroke="black" stroke-width="5" fill="grey" r="20">
        asasgasgas
      </router-link>
    </svg>
    <!--img class="map" src="~@/assets/map.png" width="100%"-->
    <div class="info">
      <div align="center">Statistics</div>
      <div align="center">{{map.name}}</div>
      Parent: <router-link tag="button" v-if="map.parentId != null" :to="`/map/${map.parentId}`">
      {{map.parentName}}
      </router-link>
      <button disabled v-else>None</button>
      <div>Danger: <Danger :val="map.danger"></Danger></div>
      <router-link tag="button" :to="`/people/location/${map.id}`">
        {{'Population(' + map.population + ')'}}
      </router-link><br>
      <router-link tag="button" :to="`/agents/location/${map.id}`">
        {{'Agents(' + map.cops + ')'}}
      </router-link><br>
      <router-link tag="button" :to="`/targets/location/${map.id}`">
        {{'Targets(' + map.targets + ')'}}
      </router-link><br>
      <div>Units</div>
      <div style="overflow: auto">
        <router-link tag="button" v-for="unit in map.units"  :to="`/map/${unit.first}`" :key="unit.first">
          {{unit.second.replace(/_/g, ' ')}}
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
  import Danger from './Danger';

  export default {
    name: 'Map',
    components: {Danger},
    computed: {
      map () {
        return this.$store.getters.map;
      },
      locs () {
        return [
          {x: 172, y: 306, id: 100}, {x: 408, y: 392},   {x: 366, y: 583},
          {x: 587, y: 690, id: 0}, {x: 670, y: 618},   {x: 270, y: 857},
          {x: 899, y: 813, id: 1}, {x: 1480, y: 1231}, {x: 2176, y: 2364},
          {x: 239, y: 646}, {x: 662, y: 786},   {x: 22, y: 740}
        ]
      }
    },
    created () {
      this.fetchData()
    },
    watch: {
      '$route': 'fetchData'
    },
    methods: {
      fetchData () {
        this.$store.dispatch('changeMap', this.$route.params.id).then();
      }
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  router-link {
    white-space: normal;
    width: 90%;
  }
</style>

<style scoped>
  circle {
    cursor: pointer;
  }

  circle:hover {
    fill: white;
  }

  circle.active {
    fill: lightgray;
  }
  .map-root {
    display: grid;
  }
  .map-root {
    grid-template-columns: 80% 20%;
    grid-template-rows: auto;
    grid-template-areas:
      "map info";
  }
</style>
