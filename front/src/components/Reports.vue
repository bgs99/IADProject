<template>
  <div style="background-color: lightgray; display: inline-block; margin: 20px; width: 40%">
    <div class="root">
      <div>
        <router-link tag="img" :to="`/agents/id/${src.author.first}`" :src="`/static/agents/${src.author.first}.jpg`" height="100">
        </router-link>
        <h1>Author: {{src.author.second}}</h1>
        <h2>Name: {{src.name}}</h2>
      </div>
      <br>
      <div>
        <router-link tag="img" :to="`/agents/id/${src.subject.first}`" :src="`/static/agents/${src.subject.first}.jpg`" height="100">
        </router-link>
        <h1>Subject: {{src.subject.second}}</h1>
      </div>
      <br>
      <h2>
        Purpose: {{src.purpose}}
      </h2>
      <br>
      Mission: #{{src.mission.first}}
      <router-link tag="button" :to="`/missions/id/${src.mission.first}`">
        {{src.mission.second}}
      </router-link>
      <br>
      <p>
        Description:
        <br>
        {{src.description}}
      </p>
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
