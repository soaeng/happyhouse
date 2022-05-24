<template>
<div id="main">
    <header class="mb-3">
        <a href="#" class="burger-btn d-block d-xl-none">
            <i class="bi bi-justify fs-3"></i>
        </a>
    </header>

    <div class="page-heading">
        <div class="page-title">
            <div class="row">
                <div class="col-12 col-md-6 order-md-1 order-last">
                    <h3>공지사항</h3>
                    <p class="text-subtitle text-muted"></p>
                </div>
                <div class="col-12 col-md-6 order-md-2 order-first">
                    <nav aria-label="breadcrumb" class="breadcrumb-header float-start float-lg-end">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item">
                                <router-link to="/house" class='sidebar-link'>
                                Happy House
                                </router-link>
                            </li>
                            <li class="breadcrumb-item active" aria-current="page">공지사항</li>
                        </ol>
                    </nav>
                </div>
            </div>
        </div>
        <router-view></router-view>
    </div>
</div>
</template>

<script>
import Vue from "vue";
import VueAlertify from "vue-alertify";
Vue.use(VueAlertify);

import http from "@/common/axios.js";
import util from "@/common/util.js";

import Pagination from "./Pagination.vue";

export default {
    name: 'BoardMain',
    components: { Pagination },

    data() {
        return {
            
        };
    },
    computed: {
        // gttters 이용
        // computed 사용 안하고 위에서(26line) 주석처리된 것처럼 사용해도 됨
        listGetters() {
            return this.$store.getters.getBoardList; // no getBoardList()
        },
    },
    mounted() {
        
    },
    created() {
        this.boardList();
    },
    filters: {
        makeDateStr: function (date, separator) {
            return date.year + separator + (date.month < 10 ? "0" + date.month : date.month) + separator + (date.day < 10 ? "0" + date.day : date.day);
        },
    },
    methods: {
        // list
        // store actions 에 구현
        // 가능한 한 가지 방법
        boardList() {
            this.$store.dispatch("boardList"); // 비동기 처리 시에는 dispatch
        },

        // pagination (기존은 모두 props로 처리했지만 이제는 store에서 getters에서 처리)
        movePage(pageIndex) {
            console.log("BoardMainVue : movePage : pageIndex : " + pageIndex);

            // store commit 으로 변경
            // this.offset = (pageIndex - 1) * this.listRowCount;
            // this.currentPageIndex = pageIndex;
            this.$store.commit("SET_BOARD_MOVE_PAGE", pageIndex);

            this.boardList();
        },

        // util
        makeDateStr: util.makeDateStr,

        // detail
        async boardDetail(boardId) {
            // store 변경
            // this.boardId = boardId;
            // this.$store.commit('mutateSetBoardBoardId', boardId);

            try {
                let { data } = await http.get("/boards/" + boardId);
                console.log(data);

                if (data.result == "login") {
                this.doLogout(); // this.$router.push("/login"); 에서 변경
                } else {
                let { dto } = data;
                this.$store.commit("SET_BOARD_DETAIL", dto);

                this.$router.push("/boardDetail");
                }
            } catch (error) {
                console.log("BoardMainVue: error : ");
                console.log(error);
            }
            
        },
        
        sidebarToggle(){
            document.getElementById('sidebar').classList.toggle('active');
        },
        sidebarHide(){
            document.getElementById('sidebar').classList.remove('active');
        },
        doLogout() { // 기존 값 다 초기화 작업 하고 login 페이지
        this.$store.commit("SET_LOGIN", { isLogin: false, userName: "", userProfileImageUrl: "" });
        this.$router.push("/login");
        },
    },
};
</script>

<style scoped>
.board-search {display:flex; justify-content: space-between; margin: 40px 0 10px;}
.board-search .board-info{display: flex; align-items: flex-end;}
.board-search .board-info p{margin: 0 10px 0 0; color: #555; font-size: 13px;}
.board-search form{display: flex; align-items: center; justify-content: center; height: 100%;}
.board-search .form-select{font-size:14px; width: 90px; margin-right: 10px; padding: 5px 8px;}
.board-search input[type="text"]{font-size:14px; width: 250px; margin-right: 10px; padding: 5px 8px;}
.board-search button{font-size:14px; padding: 5px 8px; border: 1px solid #ced4da; color: #555;}
.board-search button:hover{background-color: #F5F5F5;}
</style>