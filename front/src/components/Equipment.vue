<template>
  <div>
    <div class="root">
      <img style="grid-area: img" :src="`/static/${src.size === undefined ? 'weapons' : 'transport'}/${src.id}.jpg`"
           height="150">
      <div style="grid-area: info">
        <h1 align="center">
          {{src.name}}
        </h1>
        <h2 v-if="src.count > 0">
          {{src.count}} available
        </h2>
        <h2 v-else>
          Not available
        </h2>
      </div>
      <div style="grid-area: details">

        <p>
          Power <Danger :val="src.power"></Danger>
        </p>
        <p v-if="src.size">
          Can carry {{src.size}}
        </p>
        <template v-if="$store.getters.missionId !== null
                        && $store.state.currentMission.ready === false
                        && src.count > 0">
          <button @click="select"
                  v-if="src.size === undefined
                        && $store.state.currentMission.weapon !== src.id">Select for mission</button>
          <button @click="select"
                  v-else-if="src.size !== undefined
                        && $store.state.currentMission.transport !== src.id">Select for mission</button>
        </template>
      </div>
    </div>
  </div>
</template>

<script>
  import Danger from './Danger';

  export default {
    name: 'Equipment',
    components: {Danger},
    props: ['src'],
    methods: {
      select () {
        if (this.src.size === undefined) {
          this.$store.commit('selectWeapon', this.src.id);
        } else {
          this.$store.commit('selectTransport', this.src.id);
        }
        this.$store.dispatch('loadMissionsById', this.$store.getters.missionId);
        this.$router.push(`/missions/id/${this.$store.getters.missionId}`);
      }
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .root {
    display: grid;
    grid-template-columns: auto;
    grid-template-rows: min-content min-content auto;
    grid-template-areas:
      "info"
      "img"
      "details";
  }
</style>
