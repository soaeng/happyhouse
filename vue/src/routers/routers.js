import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter)

import HouseMain from "@/components/HouseMain.vue";
import Bookmark from "@/components/Bookmark.vue";
import BookmarkArea from "@/components/Bookmark/BookmarkArea.vue";
import BookmarkHouse from "@/components/Bookmark/BookmarkHouse.vue";
import BookmarkDeal from "@/components/Bookmark/BookmarkDeal.vue";
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
      children: [
        { path: '', component: BookmarkArea },
        { path: 'area', component: BookmarkArea },
        { path: 'house', component: BookmarkHouse },
        { path: 'deal', component: BookmarkDeal },
      ],
    },
  ]
})



