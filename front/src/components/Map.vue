<template>
  <div class="map-root">
      <img class="map" src="~@/assets/map.png" width="100%">
      <div class="info">
        <div align="center">Statistics</div>
        <div align="center">{{map.name}}</div>
        Parent: <input type="button" v-if="map.parentId != null"
                       :value="map.parentName" @click="load(map.parentId)">
        <div>Danger: {{map.danger}}</div>
        <input type="button" :value="'Population(' + map.population + ')'" @click="people">
        <input type="button" :value="'Agents(' + map.cops + ')'">
        <input type="button" :value="'Targets(' + map.targets + ')'">
        <div>Units</div>
        <div style="overflow: auto">
          <input type="button" v-for="unit in map.units" :value="unit.second" @click="setCur(unit.first)" :key="unit.first">
        </div>
      </div>
  </div>
</template>

<script>
  export default {
    name: 'Map',
    props: {
      mid: {
        type: Number,
        default: 0
      }
    },
    methods: {
      setCur: function (val) {
        this.$store.dispatch('changeMap', val);
      },
      people () {
        this.$emit('people', this.cur)
      }
    },
    computed: {
      map () {
        return this.$store.getters.map;
      }
    },
    beforeMount () {
      this.$store.dispatch('loadMap');
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  input[type="button"] {
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
