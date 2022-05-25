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
  },
  created() {
    if(sessionStorage.getItem('isLogin') == 'true'){
      this.$store.commit(
        'SET_LOGIN',
        { isLogin: sessionStorage.getItem('isLogin'), userName: sessionStorage.getItem('userName'), userEmail: sessionStorage.getItem('userEmail'), userProfileImageUrl: sessionStorage.getItem('userProfileImageUrl')} // payload 부분
      );

      this.$router.push(sessionStorage.getItem('lastUrl'))
    }
  },
  updated() {
    sessionStorage.setItem('lastUrl', this.$router.currentRoute.fullPath);
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
