<template>

  <div>
    <div class="tab" style="grid-area: tab">
      <router-link tag="button"
                   :class="{tablinks: true, active: tab[0].toLowerCase() + tab.slice(1) === $route.params.source}"
                   v-for="tab in $store.state.tabs" :key="tab"
                   :to="`/${$route.params.collection}/${tab[0].toLowerCase() + tab.slice(1)}/0`">{{tab}}</router-link>
    </div>
    <component class="panel" :is="comp" v-for="el in list" :key="el.id" :src="el"></component>
  </div>
</template>

<script>
  import People from './People';
  import Agents from './Agents';
  import Missions from './Missions';
  import Targets from './Targets';
  import Equipment from './Equipment';
  import Reports from './Reports';
  import Requests from './Requests';

  export default {
    name: 'Registry',
    components: {
      People,
      Agents,
      Missions,
      Targets,
      Equipment,
      Reports,
      Requests
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
        let filter = this.$route.params['filter'];
        filter = filter === 'all' ? '' : filter[0].toUpperCase() + filter.slice(1);
        if (!['agents', 'people', 'missions', 'targets', 'equipment', 'reports', 'requests'].includes(col)) {
          this.$router.replace('/');
          return;
        }
        this.comp = col[0].toUpperCase() + col.slice(1);
        const srcc = src[0].toUpperCase() + src.slice(1);
        this.$store.dispatch('load' + this.comp + filter + 'By' + srcc, this.$route.params.id).then();
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
<style>
  .panel {
    box-sizing: border-box;
    background-color: lightgray;
    display: inline-block;
    margin: 1%;
    width: 48%;
    padding: 0.5%;
    border-style: solid;
    border-width: 5px;
    border-color: white;
    border-radius: 4px;
  }
  .panel > div {
    background-color: #000080;
    padding: 0.5%
  }
  .panel[wide] {
    width: 98%;
  }
  .avatar {
    border-style: solid;
    border-width: medium;
    border-color: white;
    margin: 5px;
  }
</style>
