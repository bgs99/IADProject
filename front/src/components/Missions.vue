<template>
  <div style="background-color: lightgray; display: inline-block; margin: 20px; width: 90%">
    <div class="mission-root">
      <img style="grid-area: mission-img" src="~@/assets/logo.png" width="150" height="150">
      <div style="grid-area: mission-target">
        <h1>
          #{{src.id}} {{src.targetName}}
        </h1>
        <h2>
          {{src.type}}
        </h2>
        <p>
          Danger level <Danger :val="src.danger"></Danger>
        </p>
        <p>
          Location: <router-link tag="button" :to="`/map/${src.location.first}`">
          {{src.location.second}}
        </router-link>
        </p>
        <p>
          Status: {{src.status}}
        </p>
      </div>
      <div style="grid-area: mission-team">
        <h2 align="center">
          Team
        </h2>
        <table>
          <tr v-for="ag in src.team" :key="ag.first.id">
            <td>
              <img :src="`/static/people/${ag.first.name}.jpg`" width="100" height="100"/>
              <p>
                #{{ag.first.id}} {{ag.first.name}}
              </p>
            </td>
            <td>
              <img :src="`/static/weapons/${ag.second.id}.jpg`" height="100"/>
              <p>
                {{ag.second.name}}
              </p>
            </td>
          </tr>
        </table>
      </div>
      <div style="grid-area: mission-transp">
        <h2 align="center">Transport</h2>
        <div v-for="ag in src.transport" :key="ag.id">
          <img :src="`/static/transport/${ag.id}.jpg`" height="100"/>
          {{ag.name}}
        </div>
      </div>

    </div>
  </div>
</template>

<script>
  import Danger from './Danger';

  export default {
  name: 'Missions',
    components: {Danger},
    props: ['src']
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .mission-root {
    display: grid;
    grid-template-columns: min-content auto 50%;
    grid-template-rows: auto auto;
    grid-template-areas:
      "mission-img  mission-target  mission-target"
      "mission-team mission-team    mission-transp"
  }
</style>
