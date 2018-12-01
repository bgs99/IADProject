<template>

  <div>
    <Person v-for="person in list" :key="person.id" :src="person" @map="$emit('map',$event)"></Person>
  </div>
</template>

<script>
  import axios from 'axios'
  import Person from "./Person";

  export default {
  name: 'Registry',
  components: {Person},
  props: {
    src: {
      type: String,
      default: 'location'
    },
    mid: {
      type: Number,
      default: 0
    }
  },
  methods: {
    loadLocation: function () {
      axios('/place/locals', {
        params: {
          id: this.loc
        },
        method: 'GET'
      }).then(response => {
        this.list = response.data
      }).catch(error => {
        console.log(error)
      })
    }
  },
  beforeMount () {
    if(this.src === 'location') {
      this.loc = this.mid
      this.loadLocation()
    }
  },
  data () {
    return {
      list: [],
      loc: 0
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
