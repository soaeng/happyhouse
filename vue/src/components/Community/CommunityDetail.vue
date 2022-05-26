<template>
    <section class="section">
        <div class="card">
            <div class="card-header">
                <h5 class="text-center mb-4">{{ $store.state.community.title }}</h5>
                <div class="d-flex justify-content-between community-info text-gray-600">
                    <p class="mb-0"><i class="bi bi-person"></i>작성자<span>{{ $store.state.community.userName }}</span></p>
                    <div class="d-flex">
                        <p class="mb-0"><i class="bi bi-calendar-date"></i>작성일<span>{{ $store.state.community.regDate }} {{ $store.state.community.regTime }}</span></p>
                        <p class="mb-0"><i class="bi bi-eye"></i>조회수<span>{{ $store.state.community.readCount }}</span></p>
                    </div>
                </div>
            </div><!-- end of .card-header -->
            <hr class="mt-0 mb-0">
            <div class="card-content">
                <div class="card-body">
                    <div v-html="$store.state.community.content" style="min-height: 300px;"></div>
                    <div>
                        <p class="d-none">첨부파일</p>
                        <div v-if="$store.state.community.fileList.length > 0">
                            <hr>
                            <p class="mb-3 fw-bold">첨부파일</p>
                            <ul class="text-sm" v-for="(file, index) in $store.state.community.fileList" :key="index" style="padding-left: 0;">
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

                    <div class="btn-box text-end" v-show="$store.state.community.sameUser">
                        <router-link to="/community/update" class="btn btn-primary">글 수정하기</router-link>
                        <button @click="changeToDelete" class="btn btn-secondary" style="margin-left: 10px;">글 삭제하기</button>
                    </div>
                </div><!-- end of .card-body -->
            </div><!-- end of .card-content-->
        </div><!-- end of .card -->
    </section>
</template>

<script>
import Vue from "vue";
import VueAlertify from "vue-alertify";
Vue.use(VueAlertify);

import http from "@/common/axios.js";

export default {
    name: 'CommunityDetail',


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
                    // community.communityId 사용 X
                    $this.communityDelete(); // $this 사용
                },
                function () {
                    console.log("cancel");
                }
            );
        },

        async communityDelete() {
            // parameter 사용 X
            try {
                let { data } = await http.delete("/community/" + this.$store.state.community.boardId);
                console.log(data);

                if (data.result == "login") { // 원래는 로그인페이지로 보냈지만 이번에는 doLogout 메소드로 보냄
                    this.doLogout();
                } else {
                    this.$alertify.success("글이 삭제되었습니다.");
                    this.$router.push("/comm")
                }
            } catch (error) {
                console.log("CommunityMainVue: error : ");
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
/*-- community-detail --*/
.community-info i::before{margin-top: .25rem; margin-right: .35rem;}
.community-info p{font-weight: 500; font-size: 14px;}
.community-info p span{font-weight: 400; margin-left: 10px;}
.community-info p::before{content:""; display: inline-block; width: 1px; height: .8rem; background-color: #CCC; margin: 0 .9rem;}
.community-info p:first-child::before{display: none;}
.bi.bi-download::before{margin-top: .3rem; margin-right: .8rem;}
</style>