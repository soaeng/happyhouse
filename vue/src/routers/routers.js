import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter)

import Login from "@/components/Login.vue";
import UserRegister from "@/components/UserRegister.vue";
import UserInfo from "@/components/UserInfo.vue";

import HouseMain from "@/components/House/HouseMain.vue";

import Bookmark from "@/components/Bookmark/Bookmark.vue";
import BookmarkArea from "@/components/Bookmark/BookmarkArea.vue";
import BookmarkDeal from "@/components/Bookmark/BookmarkDeal.vue";

import BoardMain from "@/components/Board/BoardMain.vue";
import BoardList from "@/components/Board/BoardList.vue";
import BoardEditor from "@/components/Board/BoardEditor.vue";
import BoardDetail from "@/components/Board/BoardDetail.vue";
import BoardUpdate from "@/components/Board/BoardUpdate.vue";

import CommunityMain from "@/components/Community/CommunityMain.vue";
import CommunityList from "@/components/Community/CommunityList.vue";
import CommunityEditor from "@/components/Community/CommunityEditor.vue";
import CommunityDetail from "@/components/Community/CommunityDetail.vue";
import CommunityUpdate  from "@/components/Community/CommunityUpdate.vue";

import News from "@/components/House/News";
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
        { path: 'edit', component: BoardEditor },
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

    
    //////////////////////////////////////////////////////////// COMMUNITY
    {
      name: 'CommunityMain',
      path: '/comm',
      component: CommunityMain,
      children: [
        { path: '', component: CommunityList },
        { path: 'edit', component: CommunityEditor },
        { path: 'detail', component: CommunityDetail },
        { path: 'update', component: CommunityUpdate },
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



