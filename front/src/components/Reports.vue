<template>
  <div>
    <div class="root">
      <h1 align="center">{{src.name}}</h1>
      <div  style="display: block">
        <h2>Author:
          <router-link tag="img" :to="`/agents/id/${src.author.first}`"
                       :src="`/static/agents/${src.author.first}.jpg`" height="100" width="100"
                       style="display:inline-block; float: right; background-color: white">
          </router-link>
          <br>
          {{src.author.second}}


        </h2>
      </div>
      <br>
      <div style="display: block">
        <h2>Subject:
          <router-link tag="img" :to="`/agents/id/${src.subject.first}`"
                       :src="`/static/agents/${src.subject.first}.jpg`" height="100"
                       style="float: right; background-color: white">
          </router-link>
          <br>
          {{src.subject.second}}

        </h2>
      </div>
      <br>
      <h2>
        Purpose: {{src.purpose}}
      </h2>
      <br>
      Mission:
      <router-link tag="button" :to="`/missions/id/${src.mission.first}`">
        #{{src.mission.first}} {{src.mission.second}}
      </router-link>
      <p v-if="$route.params.source === 'id'">
        <br>
        Description:
        <br>
        {{src.description}}
      </p>
      <router-link v-else tag="button" :to="`/reports/id/${src.id}`" style="display: inline-block; float: right;">
        See details
      </router-link>
    </div>
  </div>
</template>

<script>
  import Danger from './Danger';

  export default {
    name: 'Reports',
    components: {Danger},
    props: ['src']
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
