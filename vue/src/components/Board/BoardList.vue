<template>
    <section class="section">
        <div class="card">
            <div class="card-header">
                <div class="d-flex justify-content-between">
                    <div class="list-info d-flex align-items-end">
                        <p class="mb-0" style="line-height: 1rem;">총&nbsp;<span class="text-primary">{{$store.state.board.totalListItemCount}}</span>&nbsp;건</p>
                        <p class="mb-0" style="line-height: 1rem;"><span class="text-primary">{{$store.state.board.currentPageIndex}}</span>&nbsp;/&nbsp;<span>{{Math.ceil($store.state.board.totalListItemCount/$store.state.board.listRowCount)}}</span>&nbsp;페이지</p>
                    </div><!-- end of .list-info -->
                    <!-- end of .board-info -->
                    <fieldset class="form-group d-flex align-items-center justify-content-center mb-0 w-50">
                        <select @change="setType" id="searchType" class="form-select w-auto" style="margin-right: .5rem;">
                            <option value="title" selected>제목</option>
                            <option value="content">내용</option>
                            <option value="all">전체&nbsp;&nbsp;</option>
                        </select>
                        <input v-model="$store.state.board.keyword" @keydown.enter="getBoardList" type="text" class="form-control" placeholder="검색어를 입력하세요"  style="margin-right: .5rem;"/>
                        <button @click="getBoardList" class="btn btn-outline-dark" type="button">검색</button>
                    </fieldset>
                </div>
            </div><!-- end of .card-header -->
            <hr class="mb-0 mt-0">
            <div class="card-content">
                <div class="card-body">
                    <table class="table table-hover table-responsive text-center" style="table-layout:fixed">
                        <colgroup>
                            <col width="10%">
                            <col width="46%">
                            <col width="10%">
                            <col width="10%">
                            <col width="14%">
                            <col width="10%">
                        </colgroup>
                        <thead>
                            <tr class="bg-primary text-white">
                                <th>번호</th>
                                <th>제목</th>
                                <th>첨부파일</th>
                                <th>작성자</th>
                                <th>작성일</th>
                                <th>조회수</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr style="cursor: pointer" v-for="(board, index) in boardList" @click="boardDetail(board.boardId)" v-bind:key="index">
                                <td>{{ board.boardId }}</td>
                                <td class="text-start">{{ board.title }}</td>
                                <td><i class="bi bi-file-earmark-text-fill" v-if="board.fileList.length > 0"></i></td>
                                <td>{{ board.userName }}</td>
                                <td>{{ board.regDt.date | makeDateStr(".") }}</td>
                                <td>{{ board.readCount }}</td>
                            </tr>
                        </tbody>
                    </table>
                    <div v-if="boardList.length == 0" class="w-100 text-center mb-4" style="border-bottom: 1px solid #DEDEDE">
                        <p class="pt-5 pb-5 mb-0">등록된 게시글이 없습니다.</p>
                    </div>
                    <router-link to="/board/edit" class="btn btn-primary float-end" >글쓰기</router-link>
                </div><!-- end of .card-body -->
            </div><!-- end of .card-content -->

            <div class="btn-box mb-5">
            </div>

            <pagination class="mb-4" v-if="boardList.length > 0" v-on:call-parent="movePage"></pagination>

        </div>

    </section>
</template>

<script>
import Vue from "vue";
import VueAlertify from "vue-alertify";
Vue.use(VueAlertify);

import http from "@/common/axios.js";
import util from "@/common/util.js";

import Pagination from "../Pagination.vue";

export default {
    name: 'BoardList',
    components: { Pagination },

    data() {
        return {
            
        };
    },
    computed: {
        boardList() {
            return this.$store.getters.getBoardList;
        },
    },
    methods: {
        // util
        makeDateStr: util.makeDateStr,
        setType(){
            console.log(document.querySelector('#searchType').value);
            this.$store.state.board.type = document.querySelector('#searchType').value;
        },
        getBoardList() {
            console.log(this.$store.state.board.type)
            this.$store.dispatch("boardList");
        },
        initPage(){
            this.$store.state.board.currentPageIndex = 1;
            this.$store.state.board.offset = 0;
        },
        movePage(pageIndex) {
            this.$store.commit("SET_BOARD_MOVE_PAGE", pageIndex);
            this.getBoardList();
        },
        sidebarToggle(){
            document.getElementById('sidebar').classList.toggle('active');
        },
        sidebarHide(){
            document.getElementById('sidebar').classList.remove('active');
        },
        doLogout() {
            this.$store.commit("SET_LOGIN", { isLogin: false, userName: "", userProfileImageUrl: "" });
            this.$router.push("/login");
        },

        // detail
        async boardDetail(boardId) {
            try {
                let { data } = await http.get("/boards/" + boardId);
                console.log(data);

                if (data.result == "login") {
                    this.doLogout();
                } else {
                    let { dto } = data;
                    this.$store.commit("SET_BOARD_DETAIL", dto);
                    this.$router.push("/board/detail");
                }
            } catch (error) {
                console.log("BoardMainVue: error : ");
                console.log(error);
            }
        },
    },
    
    mounted() {
        
    },

    created() {
        this.getBoardList();
        this.setType();
    },

    filters: {
        makeDateStr: function (date, separator) {
            return date.year + separator + (date.month < 10 ? "0" + date.month : date.month) + separator + (date.day < 10 ? "0" + date.day : date.day);
        },
    },
};
</script>

<style scoped>

.list-info{font-size: 14px;}
.list-info p:first-child{margin-right: 20px;}
tr th{ padding: 1.2rem .5rem;}
tr td{ padding: .8rem .5rem;}
tbody tr td.text-start{overflow: hidden; text-overflow: ellipsis; white-space: nowrap;}
.bi.bi-file-earmark-text-fill::before{margin-top: .35rem;}
</style>