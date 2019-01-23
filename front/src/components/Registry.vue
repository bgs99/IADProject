<template>
  <div>
    <div class="person-root">
      <img class="avatar" style="grid-area: img" src="~@/assets/human.jpg" width="150" height="150" v-if="src.isPerson">
      <img class="avatar" style="grid-area: img" src="~@/assets/org.jpg" width="150" height="150" v-else>
      <div style="grid-area: person-info">
        <h1>
          #{{src.id}} {{src.name}}
        </h1>
        <p>
          Danger level <Danger :val="src.danger"></Danger>
        </p>
        <p>
          Location: <router-link tag="button" :to="`/map/${src.location.first}`">
          {{src.location.second}}
        </router-link>
        </p>
        <p>
          Status: {{src.status ? 'Alive' : 'Dead'}}
        </p>

        <template v-if="$store.getters.missionId !== null
                        && $store.state.currentMission.ready === false
                        && src.isPerson">
          <button @click="select">Select as cover</button>
        </template>
        <router-link tag="button" :to="`/targets/id/${src.target}`" v-if="src.target !== null">
          Go to target
        </router-link>
        <input type="button" @click="$store.dispatch('makeTarget', {id: src.id, prs: src.isPerson})" value="Make target"
               v-else-if="src.danger > 60 && $store.getters.admin"/>
      </div>

    </div>
  </div>
</template>

<script>
  import Danger from './Danger';

  export default {
  name: 'Registry',
    components: {Danger},
    props: ['src'],
    methods: {
      select () {
        this.$store.commit('selectCover', this.src.id);
        this.$router.push(`/missions/id/${this.$store.getters.missionId}`);
      }
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .person-root {
    display: grid;
    grid-template-columns: min-content auto;
    grid-template-rows: min-content auto;
    grid-template-areas:
      "img  person-info"
      "none person-info";
  }
</style>
