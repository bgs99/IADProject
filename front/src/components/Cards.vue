<template>

  <div>
    <div class="tab" style="grid-area: tab">
      <router-link tag="button"
                   :class="{
                      tablinks: true,
                      active: tab[0].toLowerCase() + tab.slice(1) === $route.params.filter,
                      menuOpen: true
                   }"
                   v-for="tab in $store.state.tabs" :key="tab"
                   :to="`/${$route.params.collection}/${tab[0].toLowerCase() + tab.slice(1)}/${$route.params.source}/0`">{{tab}}</router-link>
    </div>
    <template v-if="list.length === 10">
      <router-link width="100px" tag="button" :to="`/${$route.params.collection}/${$route.params.filter}/${$route.params.source}/${+$route.params.id-1}`"
                   v-if="$route.params.id > 0">
        Previous
      </router-link>
      <router-link
        width="100px" tag="button" :to="`/${$route.params.collection}/${$route.params.filter}/${$route.params.source}/${+$route.params.id+1}`">
        Next
      </router-link>
    </template>

    <div class="root">
      <component :wide="$route.params.source === 'id'" class="panel" :is="comp" v-for="el in list" :key="el.id" :src="el"></component>
    </div>
    <template v-if="list.length === 10">
      <router-link width="100px" tag="button" :to="`/${$route.params.collection}/${$route.params.filter}/${$route.params.source}/${+$route.params.id-1}`"
                   v-if="$route.params.id > 0">
        Previous
      </router-link>
      <router-link
        width="100px" tag="button" :to="`/${$route.params.collection}/${$route.params.filter}/${$route.params.source}/${+$route.params.id+1}`">
        Next
      </router-link>
    </template>
  </div>
</template>

<script>
  import Registry from './Registry';
  import Agents from './Agents';
  import Missions from './Missions';
  import Targets from './Targets';
  import Equipment from './Equipment';
  import Reports from './Reports';
  import Requests from './Requests';
  import Repair from './Repair';

  export default {
    name: 'Cards',
    components: {
      Registry,
      Agents,
      Missions,
      Targets,
      Equipment,
      Reports,
      Requests,
      Repair
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
        if (!['repair','agents', 'registry', 'missions', 'targets', 'equipment', 'reports', 'requests'].includes(col)) {
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
    width: 98%;
    padding: 0.5%;
    border: white solid 5px;
    border-radius: 4px;
  }
  .panel > div {
    background-color: #000080;
    padding: 0.5%;
    height: 99%;
  }
  .panel[wide=true] {
    grid-column-end: span 2;
  }
  .avatar {
    border: medium solid white;
    margin: 5px;
  }
</style>

<style scoped>
  .root {
    display: grid;
    grid-template-columns: 100%;

  }
  @media screen and (min-width: 1238px){
    .root {
      grid-template-columns: 50% 50%;
    }
  }

</style>
