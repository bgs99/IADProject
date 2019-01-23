<template>
  <div>
    <div class="agent-root">
      <img class="avatar" style="grid-area: agent-img" src="~@/assets/human.jpg" width="150" height="150">
      <div style="grid-area: agent-info">
        <h1>
          #{{src.id}} {{src.name}}{{src.id === $store.state.user.id ? ', you' : ''}}
        </h1>
        <p v-if="src.level > 0">
          Access level: {{src.level}}
        </p>
        <p v-else-if="src.level === 0">
          Recruit
        </p>
        <b v-else style="color: red;">
          Betrayer
        </b>
        <template v-if="src.level >= 0">
          <p>
            Department: {{src.deptName}}
          </p>
          <p>
            Trust <Danger :val="100 - src.danger"></Danger>
          </p>
          <div v-if="admin">
            Salary:
            <input type="text" v-model.number="salary"/>
            <input type="button" @click="update" value="Change" v-if="changedWage"/>
          </div>
          <div v-if="admin">
            <input type="button" @click="$store.dispatch('promote', src.id)" v-if="src.level + 1 < $store.state.user.level && src.level >= 0" value="Promote"/>
            <input type="button" @click="$store.dispatch('demote', src.id)" v-if="src.level > -1" :value="src.level>0 ? 'Demote' : 'Fire'"/>
          </div>
        </template>
      </div>
    </div>
  </div>
</template>

<script>
  import Danger from './Danger';

  export default {
    name: 'Agent',
    components: {Danger},
    props: ['src'],
    computed: {
      admin: function () {
        return this.$store.getters.admin &&
          this.$store.state.user.id !== this.src.id &&
          this.$store.state.user.deptId === this.src.deptId &&
          this.$store.state.user.level > this.src.level;
      },
      changedWage () {
        return !Number.isNaN(+this.salary) &&
          +this.salary !== this.src.salary;
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
        this.salary = this.src.salary;
      },
      update () {
        this.$store.dispatch('updateSalary', {
          id: this.src.id,
          wage: this.salary
        });
      }
    },
    data () {
      return {
        salary: 0
      };
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  @media screen and (min-width: 1238px){
    .agent-root {
      display: grid;
      grid-template-columns: min-content auto;
      grid-template-rows: min-content auto;
      grid-template-areas:
        "agent-img agent-info"
        "none      agent-info";
    }
  }
  @media screen and (max-width: 817px){
    .agent-root {
      display: grid;
      grid-template-columns: 100%;
      grid-template-rows: min-content auto;
      grid-template-areas:
        "agent-img"
        "agent-info";
    }
  }
</style>
