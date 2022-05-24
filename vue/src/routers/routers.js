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

import BoardMain from "@/components/BoardMain.vue";
import BoardList from "@/components/Board/BoardList.vue";
import BoardEditer from "@/components/Board/BoardEditer.vue";
import BoardDetail from "@/components/Board/BoardDetail.vue";
import BoardUpdate  from "@/components/Board/BoardUpdate.vue";

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
    
    //////////////////////////////////////////////////////////// USER
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

    //////////////////////////////////////////////////////////// HOUSE
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
    },

    //////////////////////////////////////////////////////////// BOARD
    {
      name: 'BoardMain',
      path: '/board',
      component: BoardMain,
      children: [
        { path: '', component: BoardList },
        { path: 'edit', component: BoardEditer },
        { path: 'detail', component: BoardDetail },
        { path: 'update', component: BoardUpdate },
      ],
      beforeEnter: (to, from, next) => {
        if (store.state.login.isLogin) {
          next();
        } else {
          return next("/login");
        }
      },
    },
  ]
})



