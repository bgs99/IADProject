<template>
  <form @submit.prevent="enter">
    <label>
      ID:
      <input type="text" v-model="login">
    </label>
    <br>
    <label>
      Password:
      <input type="password" v-model="pass"/>
    </label>
    <br>
    <p style="color: red" v-if="$store.state.failedLogin">Incorrect credentials</p>
    <input type="submit" value="Login"/>
  </form>
</template>

<script>
  export default {
    name: 'Login',
    data () {
      return {
        login: '',
        pass: ''
      }
    },
    methods: {
      enter () {
        this.$store.dispatch('login', {login: login, password: pass});
      }
    }
  };
</script>

<style scoped>

</style>
