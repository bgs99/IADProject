<template>

  <div style="background-color: lightgray; display: inline-block; margin: 20px; width: 40%">
    <table>
      <tr>
        <td>
          <img src="~@/assets/logo.png" width="150" height="150">
        </td>
        <td>
          <h1>
            {{name}}
          </h1>
        </td>
      </tr>
      <tr>
        <td>
          #{{pid}}
        </td>
        <td>
          Danger level
          {{danger}}
        </td>
      </tr>
      <tr>
        <td></td>
        <td>
          Location: <input type="button" :value="loc.name" @click="map"/>
        </td>
      </tr>
      <tr>
        <td></td>
        <td>
          Status: Alive
        </td>
      </tr>
    </table>


  </div>
</template>

<script>
  export default {
  name: 'Person',
  props: ['src'],
  methods: {
    map: function () {
      this.$emit('map', this.loc.id)
    }
  },
  beforeMount () {
    this.name = this.src.name
    this.pid = this.src.id
    this.danger = this.src.danger
    this.loc = this.src.location
  },
  data () {
    return {
      name,
      pid,
      danger,
      loc
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
