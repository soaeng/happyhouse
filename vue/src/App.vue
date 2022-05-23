<template>
  <div id="app">
    <nav-bar v-if='this.$store.state.login.isLogin' />
    <router-view></router-view>
  </div>
</template>

<script>
import NavBar from './components/NavBar.vue'

export default {
  name: 'App',
  components: {
    NavBar
  },
  methods:{
  }
}
</script>


<style>
#app {
  word-break: keep-all;
  font-family: 'Noto Sans KR', sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
</style>
