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
        this.$store.dispatch('login', {login: this.login, password: this.pass}).then();
      }
    }
  };
</script>

<style scoped>
  form {
    position: absolute;
    width: min-content;
    left: 50%;
    top: 50%;
    transform: translateY(-50%) translateX(-50%);
  }
  label {
    color: white;
  }
  input {
    float: right;
  }
</style>
