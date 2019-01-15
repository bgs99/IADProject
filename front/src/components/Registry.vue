<template>

  <div>
    <component :is="comp" v-for="el in list" :key="el.id" :src="el"></component>
  </div>
</template>

<script>
  import People from './People';
  import Agents from './Agents';
  import Missions from './Missions';
  import Targets from './Targets';

  export default {
    name: 'Registry',
    components: {
      People,
      Agents,
      Missions,
      Targets
    },
    computed: {
      list () {
        return this.$store.getters[this.comp[0].toLowerCase() + this.comp.slice(1)];
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
        const col = this.$route.params['collection'];
        const src = this.$route.params['source'];
        if (!['agents', 'people', 'missions', 'targets'].includes(col)) {
          this.$router.replace('/');
          return;
        }
        this.comp = col[0].toUpperCase() + col.slice(1);
        const srcc = src[0].toUpperCase() + src.slice(1);
        this.$store.dispatch('load' + this.comp + 'By' + srcc, this.$route.params.id).then();
      }
    },
    data () {
      return {
        comp: 'div'
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
