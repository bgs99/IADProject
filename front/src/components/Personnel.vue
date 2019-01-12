<template>

  <div>
    <Agent v-for="agent in list" :key="agent.id" :src="agent"></Agent>
  </div>
</template>

<script>
  import Agent from './Agent';

  export default {
    name: 'Personnel',
    components: {Agent},
    props: {
      src: {
        type: String,
        default: 'location'
      }
    },
    computed: {
      list () {
        return this.$store.getters.agents;
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
        let query = '';
        switch(this.$route.params.source){
          case 'location':
            query = 'loadAgentsByLocation';
            break;
          case 'page':
          default:
            query = 'loadAllAgents';
            break;
        }
        this.$store.dispatch(query, this.$route.params.id).then();
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
