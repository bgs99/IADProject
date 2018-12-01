<template>
  <table>
    <col width="80%">
    <col width="20%">
    <tr>
      <td>
        <img src="~@/assets/map.png" width="100%">
      </td>
      <td>
        <div>Statistics</div>
        <div>{{map.name}}</div>
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
      </td>
    </tr>
  </table>
</template>

<script>
  import axios from 'axios'

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
      this.cur = val;
      this.load()
    },
    load: function () {
      axios('/place', {
        params: {
          id: this.cur
        },
        method: 'GET'
      }).then(response => {
        this.map = response.data
      }).catch(error => {
        console.log(error)
      })
    },
    people () {
      this.$emit('people', this.cur)
    }
  },
  beforeMount () {
    this.cur = this.mid;
    this.load()
  },
  data () {
    return {
      cur: 0,
      map: {}
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
</style>
