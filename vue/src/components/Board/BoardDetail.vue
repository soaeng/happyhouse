<template>
    <section class="section">
        <div class="card">
            <div class="card-header">
                <h5 class="text-center mb-4">{{ $store.state.board.title }}</h5>
                <div class="d-flex justify-content-between board-info text-gray-600">
                    <p class="mb-0"><i class="bi bi-person"></i>작성자<span>{{ $store.state.board.userName }}</span></p>
                    <div class="d-flex">
                        <p class="mb-0"><i class="bi bi-calendar-date"></i>작성일<span>{{ $store.state.board.regDate }} {{ $store.state.board.regTime }}</span></p>
                        <p class="mb-0"><i class="bi bi-eye"></i>조회수<span>{{ $store.state.board.readCount }}</span></p>
                    </div>
                </div>
            </div><!-- end of .card-header -->
            <hr class="mt-0 mb-0">
            <div class="card-content">
                <div class="card-body">
                    <div v-html="$store.state.board.content" style="min-height: 300px;"></div>
                    <div>
                        <p class="d-none">첨부파일</p>
                        <div v-if="$store.state.board.fileList.length > 0">
                            <hr>
                            <p class="mb-3 fw-bold">첨부파일</p>
                            <ul class="text-sm" v-for="(file, index) in $store.state.board.fileList" :key="index" style="padding-left: 0;">
                                <li class="list-unstyled">
                                    <a v-bind:href="'assets/'+file.fileUrl" v-bind:download="file.fileName">
                                        <span class="bg-light-secondary rounded" style="padding: .3rem .6rem; margin-right: 1rem;">
                                            <i class="bi bi-download"></i>{{ file.fileName }}
                                        </span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>

                    <div class="btn-box text-end" v-show="$store.state.board.sameUser">
                        <router-link to="/board/update" class="btn btn-primary">글 수정하기</router-link>
                        <button @click="changeToDelete" class="btn btn-secondary" style="margin-left: 10px;">글 삭제하기</button>
                    </div>
                </div><!-- end of .card-body -->
            </div><!-- end of .card-content-->
        </div><!-- end of .card -->
        <div class="btn-box text-center">
            <router-link to="/board" class="btn btn-primary">목록으로</router-link>
        </div>
    </section>
</template>

<script>
import Vue from "vue";
import http from "@/common/axios.js";

import Swal from 'vue-sweetalert2';
Vue.use(Swal);

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
            var deleteBtn = this.$swal;

            deleteBtn.fire({
                title: '공지사항 삭제',
                text: "해당 게시글을 삭제하시겠습니까?",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#DC3545',
                cancelButtonColor:'#C6CFCF',
                confirmButtonText: '삭제',
                cancelButtonText: '취소',
            }).then((result) => {
                if(result.isConfirmed){
                    this.boardDelete();
                    
                }
            });
        },

        async boardDelete() {
            // parameter 사용 X
            try {
                let { data } = await http.delete("/boards/" + this.$store.state.board.boardId);
                console.log(data);

                if (data.result == "login") { // 원래는 로그인페이지로 보냈지만 이번에는 doLogout 메소드로 보냄
                    this.doLogout();
                } else {
                    this.$swal.fire({
                        title: '삭제',
                        text: '해당 글이 삭제되었습니다.',
                        icon: 'success',
                        confirmButtonColor: '#198754',
                    });
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
@import url('../../../public/assets/vendors/sweetalert2/sweetalert2.min.css');
/*-- board-detail --*/
.board-info i::before{margin-top: .25rem; margin-right: .35rem;}
.board-info p{font-weight: 500; font-size: 14px;}
.board-info p span{font-weight: 400; margin-left: 10px;}
.board-info p::before{content:""; display: inline-block; width: 1px; height: .8rem; background-color: #CCC; margin: 0 .9rem;}
.board-info p:first-child::before{display: none;}
.bi.bi-download::before{margin-top: .3rem; margin-right: .8rem;}
</style>