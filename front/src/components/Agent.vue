<template>
  <div style="background-color: lightgray; display: inline-block; margin: 20px; width: 40%">
    <div class="agent-root">
      <img class="agent-img" src="~@/assets/logo.png" width="150" height="150">
      <div class="agent-info">
        <h1>
          #{{src.id}} {{src.name}}{{src.id === $store.state.user.id ? ', you' : ''}}
        </h1>
        <p>
          Access level: {{src.level}}
        </p>
        <p>
          Department: {{src.deptName}}
        </p>
        <p>
          Trust <Danger :val="100 - src.danger"></Danger>
        </p>
        <div v-if="admin">
          <router-link tag="button" v-if="!src.free" :to="``">
            View mission
          </router-link>
          <button v-else disabled>
            Free
          </button>
        </div>
        <div v-if="admin">
          Salary:
          <input type="text" v-model.number="salary"/>
          <input type="button" @click="update" value="Change" v-if="changedWage"/>
        </div>
        <div v-if="admin">
          <input type="button" @click="$store.dispatch('promote', src.id)" v-if="src.level + 1 < $store.state.user.level && src.level >= 0" value="Promote"/>
          <input type="button" @click="$store.dispatch('demote', src.id)" v-if="src.level > -1" :value="src.level>0 ? 'Demote' : 'Fire'"/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import Danger from "./Danger";

  export default {
    name: 'Agent',
    components: {Danger},
    props: ['src'],
    computed: {
      admin () {
        return this.$store.state.user.role === 'admin'
          && this.$store.state.user.id !== this.src.id
          && this.$store.state.user.deptId === this.src.deptId;
      },
      changedWage () {
        return !Number.isNaN(+this.salary) && +this.salary !== this.src.salary;
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
  router-link {
    white-space: normal;
    width: 90%;
  }
  .agent-root {
    display: grid;
    grid-template-columns: min-content auto;
    grid-template-rows: min-content auto;
    grid-template-areas:
      "agent-img agent-info"
      "none      agent-info";
  }
</style>
