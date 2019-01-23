<template>
  <div>
    <div class="root">
      <div>
        <h1>#{{src.mission.first}} Mission target: {{src.mission.second}}</h1>
        <router-link tag="button" :to="`/missions/id/${src.mission.first}`">
          Go to mission
        </router-link>
      </div>
      <br>
      <div>
        <h2 v-if="src.soldiers">Requested soldiers: {{src.soldiers}}</h2>
      </div>
      <br>
      <div v-if="src.weapon !== null">
        <router-link tag="img" :to="`/equipment/gun/${src.weapon.first}`"
                     :src="`/static/weapons/${src.weapon.first}.jpg`" height="100">
        </router-link>
        {{src.weapon.second}}
      </div>
      <br>
      <div v-if="src.transport !== null">
        <router-link tag="img" :to="`/equipment/car/${src.transport.first}`"
                     :src="`/static/transport/${src.transport.first}.jpg`" height="100">
        </router-link>
        {{src.transport.second}}
      </div>
      <br>
      <p v-if="src.data !== null && src.data.length > 0">
        Data:
        <br>
        {{src.data}}
      </p>
      <br>
      <button v-if="!src.seen && $store.getters.admin" @click="$store.dispatch('requestSeen', src.id)">Mark as 'seen'</button>
    </div>
  </div>
</template>

<script>
  import Danger from './Danger';

  export default {
    name: 'Requests',
    components: {Danger},
    props: ['src']
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
