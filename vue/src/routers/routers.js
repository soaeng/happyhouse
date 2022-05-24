import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter)

import Login from "@/components/Login.vue";
import UserRegister from "@/components/UserRegister.vue";
import UserInfo from "@/components/UserInfo.vue";
import HouseMain from "@/components/HouseMain.vue";
import Bookmark from "@/components/Bookmark/Bookmark.vue";
import BookmarkArea from "@/components/Bookmark/BookmarkArea.vue";
import BookmarkHouse from "@/components/Bookmark/BookmarkHouse.vue";
import BookmarkDeal from "@/components/Bookmark/BookmarkDeal.vue";

import News from "@/components/News";
import store from "@/store/store.js";

export default new VueRouter({
  routes: [
    {
      path: '/',
      component: HouseMain,
      beforeEnter: (to, from, next) => {
        if (store.state.login.isLogin) {
          next();
        } else {
          return next("/login");
        }
      },
    },
    
    {
      name: 'Login',
      path: '/login',
      component: Login,
      beforeEnter: (to, from, next) => {
        store.commit(
          'SET_LOGIN',
          { isLogin: false }
        );
        next();
      },
    },
    {
      name: 'Register',
      path: '/userRegister',
      component: UserRegister,
      beforeEnter: (to, from, next) => {
        store.commit(
          'SET_LOGIN',
          { isLogin: false }
        );
        next();
      },
    },
    {
      name: 'UserInfo',
      path: '/userInfo',
      component: UserInfo,
      beforeEnter: (to, from, next) => {
        if (store.state.login.isLogin) {
          next();
        } else {
          return next("/login");
        }
      },
    },
    {
      name: 'HouseMain',
      path: '/house',
      component: HouseMain,
      beforeEnter: (to, from, next) => {
        if (store.state.login.isLogin) {
          next();
        } else {
          return next("/login");
        }
      },
    },
    {
      name: 'Bookmark',
      path: '/bookmark',
      component: Bookmark,
      children: [
        { path: '', component: BookmarkArea },
        { path: 'area', component: BookmarkArea },
        { path: 'house', component: BookmarkHouse },
        { path: 'deal', component: BookmarkDeal },
      ],
      beforeEnter: (to, from, next) => {
        if (store.state.login.isLogin) {
          next();
        } else {
          return next("/login");
        }
      },
    },
    {
      name: 'News',
      path: '/news',
      component: News,
    }
  ]
})



