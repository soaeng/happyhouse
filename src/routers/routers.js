import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter)

import HouseMain from "@/components/HouseMain.vue";
import Bookmark from "@/components/Bookmark.vue";
import store from "@/store/store.js";

export default new VueRouter({
  routes: [
    {
      path: '/',
      component: HouseMain,
    },
    {
      name: 'HouseMain',
      path: '/house',
      component: HouseMain,
    },
    {
      name: 'Bookmark',
      path: '/bookmark',
      component: Bookmark,
    },
  ]
})