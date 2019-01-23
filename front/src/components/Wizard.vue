<template>
  <form class="root" @submit.prevent="submit">
    <div style="grid-area: header" class="wizard-panel">
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
      <label for="wizard-desc">
        Description
      </label>
      <br>
      <textarea id="wizard-desc" v-model="desc" cols="50" rows="20">
      </textarea>
      <br>
      <input type="submit"
             :value="(type === null || desc === '') ? 'Add type and description' : 'Submit'"
             :disabled="type === null || desc === ''"/>
    </div>
    <Targets class="panel"  style="grid-area: target; width: 90%" :src="target"></Targets>

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
        return this.$store.getters.targets[0];
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
      }
    }
  }
</script>

<style scoped>
  .panel {
    box-sizing: border-box;
    background-color: lightgray;
    display: inline-block;
    margin: 1%;
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
  .wizard-panel {
    margin: 10px;
    padding: 10px;
    border-style: solid;
    border-color: white;
    background-color: #000080;
  }
  .wizard-panel > * {
    margin: 5px;
    vertical-align: middle;
  }
  .wizard-panel > label > * {
    float: right;
  }
  .root {
    display: grid;
    grid-template-columns: max-content auto 50%;
    grid-template-rows: min-content min-content;
    grid-template-areas:
      "header . target"
      "header . ."
  }
</style>
