<template>

  <div>
    <Person v-for="person in list" :key="person.id" :src="person" @map="$emit('map',$event)"></Person>
  </div>
</template>

<script>
  import Person from './Person';

  export default {
    name: 'Registry',
    components: {Person},
    props: {
      src: {
        type: String,
        default: 'location'
      }
    },
    computed: {
      list () {
        return this.$store.getters.people;
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
        switch (this.$route.params.source) {
          case 'location':
            query = 'loadPeopleByLocation';
            break;
          case 'page':
          default:
            query = 'loadAllPeople';
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
