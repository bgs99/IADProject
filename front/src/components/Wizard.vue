<template>
  <form class="root" @submit.prevent="submit">
    <div style="grid-area: header">
      <label>
        Type:
        <select v-model="type">
          <option v-for="t in types" :key="t" :value="types.indexOf(t)">{{t}}</option>
        </select>
      </label>
      <br>
      <label>
        Level
        <input type="number" v-model="level" min="1" :max="$store.state.user.level"/>
      </label>
      <br>
      <label>
        Description
        <input type="text" v-model="desc"/>
      </label>
      <br>
      <input type="submit" value="Submit"/>
    </div>
    <Targets style="grid-area: target; width: 90%" :src="target"></Targets>

  </form>
</template>

<script>
  import Targets from './Targets';

  export default {
    name: 'Wizard',
    components: {Targets},
    computed: {
      types () {
        return [
          'Разведка',
          'Ликвидация',
          'Вербовка',
          'Агитация',
          'Охрана',
          'Устранение утечки',
          'Выгулять собаку'
        ]
      },
      target () {
        return this.$store.state.targets[0];
      }
    },
    data () {
      return {
        type: null,
        level: 1,
        desc: ''
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
        this.$store.dispatch('loadTargetsById', this.$route.params.id);
      },
      submit () {
        if (this.type === null) {
          return;
        }
        this.$store.dispatch('addMission', {type: +this.type, level: +this.level, desc: this.desc});
        this.$router.replace('/missions/page/0');
      }
    }
  }
</script>

<style scoped>
  .root {
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-template-rows: min-content;
    grid-template-areas:
      "header target";
  }
</style>
