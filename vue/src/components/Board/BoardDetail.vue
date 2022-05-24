<template>
    <section class="section">
        <div class="card board-detail">
            <div class="card-header">
                <p id="detailBoardId" class="d-none"></p>
                <h2 id="detailTitle">{{ $store.state.board.title }}</h2>
                <div class="board-info">
                    <p>작성자<span id="detailWriter">{{ $store.state.board.userName }}</span></p>
                    <p>작성일<span id="detailRegdate">{{ $store.state.board.regDate }} {{ $store.state.board.regTime }}</span></p>
                    <p>조회수<span id="detailCount">{{ $store.state.board.readCount }}</span></p>
                </div>
            </div>
            <div class="card-body">
                <div id="detailContent" v-html="$store.state.board.content"></div>
            
                <div id="detailFile-box">
                    <p class="d-none">첨부파일</p>
                    <ul id="detailFileList" v-if="$store.state.board.fileList.length > 0">
                        <li class="mb-2"  v-for="(file, index) in $store.state.board.fileList" :key="index">
                        <a type="button" class="btn btn-outline btn-default btn-xs" v-bind:href="file.fileUrl" v-bind:download="file.fileName">
                            &nbsp;<i class="fa-solid fa-paperclip"></i>&nbsp;
                            <span class="fileName">{{ file.fileName }}</span>&nbsp;&nbsp;
                            <i class="fa-solid fa-download" stype="margin:0"></i>
                        </a>	
                        </li>
                    </ul>
                </div>

                <div class="btn-box">
                    <router-link to="/board/update">
                        <button v-show="$store.state.board.sameUser" class="btn btn-primary" type="button">
                            글 수정하기
                        </button>
                    </router-link>
                    <button v-show="$store.state.board.sameUser" @click="changeToDelete" class="btn btn-secondary" type="button">글 삭제하기</button>
                </div>
            </div>

            
        </div>

    </section>
</template>

<script>
import Vue from "vue";
import VueAlertify from "vue-alertify";
Vue.use(VueAlertify);

import http from "@/common/axios.js";

export default {
    name: 'BoardDetail',


    data() {
        return {
            
        };
    },
    computed: {
    },
    mounted() {
        
    },
    created() {
    },

    methods: {
        // delete
        changeToDelete() {
            var $this = this; // alertify.confirm-function()에서 this 는 alertify 객체
            this.$alertify.confirmWithTitle(
                "공지사항 삭제",
                "이 글을 삭제하시겠습니까?",
                function () {
                    // board.boardId 사용 X
                    $this.boardDelete(); // $this 사용
                },
                function () {
                    console.log("cancel");
                }
            );
        },

        async boardDelete() {
            // parameter 사용 X
            try {
                let { data } = await http.delete("/boards/" + this.$store.state.board.boardId);
                console.log(data);

                if (data.result == "login") { // 원래는 로그인페이지로 보냈지만 이번에는 doLogout 메소드로 보냄
                    this.doLogout();
                } else {
                    this.$alertify.success("글이 삭제되었습니다.");
                    this.$router.push("/board")
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
        }
    },
};
</script>

<style scoped>
/*-- board-detail --*/
.board-detail, .board-insert, .board-update{box-shadow: 0 0 10px rgba(0, 0, 0, .1); border-radius: 1rem; padding: 40px; margin-top: 30px;}
.board-detail #detailTitle{font-size:20px; text-align: center; background-color: #F5F5F5; padding: 10px; margin-bottom: 15px;}
.board-detail .board-info{display: flex; justify-content: flex-end; padding-right:10px}
.board-detail .board-info p{font-weight: 500;}
.board-detail .board-info p span{font-weight: 400; margin-left: 10px;}
.board-detail .board-info p::before{content:""; display: inline-block; width: 2px; height: 13px; background-color: #AAA; margin: 0 12px;}
.board-detail .board-info p:first-child::before{display: none;}
.board-detail #detailContent{ border-top: 1px solid #AAA; border-bottom:1px solid #AAA; padding: 30px 20px; word-break: keep-all; margin-bottom: 50px;}
.board-detail .move-box ul{padding: 0; margin-bottom: 50px; border-bottom: 2px solid #CCC;border-top: 2px solid #CCC;}
.board-detail .move-box li {height: 40px; display: flex;}
.board-detail .move-box li:first-child{ border-bottom: 1px solid #CCC;}
.board-detail .move-box li .list-title{display: inline-block; width:100px; height: 40px; line-height: 40px; background-color: #EEE; font-size: 14px; text-align: center;}
.board-detail .move-box li .list-title i{margin-left: 8px; margin-right: 0;}
.board-detail .move-box li:first-child .list-title{height: 39px;}
.board-detail .move-box .list-text{display:block; width:824px; height:40px; line-height: 40px; font-size: 14px; color: #555; padding-left: 20px;}
.board-detail .move-box .list-text:hover{color: #333;}
.board-detail .move-box .text-ellipsis{white-space:nowrap;word-break: keep-all; overflow: hidden; text-overflow: ellipsis; display: block;}

ul[id$="FileList"] {padding: 0;}
ul[id$="FileList"] .fileName{font-size: 14px;}
ul[id$="FileList"] a[type="button"]{background-color:#F5F5F5; padding: 0 .5rem;}
.board-detail #detailFileList span{font-weight: 500; line-height: 34px;}
.board-detail #detailFileList a{font-size: 13px;}
</style>