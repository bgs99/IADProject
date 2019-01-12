<template>
  <div class="map-root">
      <img class="map" src="~@/assets/map.png" width="100%">
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
        <router-link tag="button" :to="''">
          {{'Targets(' + map.targets + ')'}}
        </router-link><br>
        <div>Units</div>
        <div style="overflow: auto">
          <router-link tag="button" v-for="unit in map.units"  :to="`/map/${unit.first}`" :key="unit.first">
            {{unit.second}}
          </router-link>
        </div>
      </div>
  </div>
</template>

<script>
  import Danger from "./Danger";

  export default {
    name: 'Map',
    components: {Danger},
    computed: {
      map () {
        return this.$store.getters.map;
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
