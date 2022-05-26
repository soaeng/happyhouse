<template>
<div>
    <section class="section mb-5">
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
    <section class="section">
        <div class="row">
            <div class="col-12">
                <div class="card">
                    <div class="card-header pb-3">
                        <h4 class="mb-0"><i class="bi bi-chat-text-fill"></i>Comments</h4>
                    </div>
                    <div class="form-group mb-4" style="padding: 0 2.3rem;">
                        <div class="d-flex">
                            <textarea v-model="text" class="form-control" rows="3" style="resize: none;"></textarea>
                            <button @click="insertReply" class="btn btn-dark" style="width: 80px; margin-left: 10px;">등록</button>
                        </div>
                    </div>
                    <hr class="mt-0 mb-5">
                    <div class="card-body" v-if="$store.state.community.replyList.length > 0">
                        <div v-for="(reply, index) in $store.state.community.replyList" :key="index" style="padding: 0 1rem;">
                            <div>
                                <div class="d-flex justify-content-between mb-2">
                                    <p class="mb-1 fw-bold d-flex align-content-center"><img :src="reply.userProfileImageUrl" width="26px" class="rounded-circle" style="margin-right: 8px;"><span style="padding-top: 2px;">{{reply.userName}}</span></p>
                                    <p class="text-muted mb-0 text-sm" style="margin-top: .5rem;">{{reply.regDt.date | makeDateStr("-")}}&nbsp;&nbsp;{{reply.regDt.time | makeTimeStr(":")}}</p>
                                </div>
                                <div class="replyText" :data-replyId="reply.replyId">
                                    <p class="bg-light-secondary rounded-3 mb-2" style=" font-size: 15px; padding: 1rem;">{{reply.text }}</p>
                                    <p v-if="reply.sameUser" class="d-flex justify-content-end" style="font-size: 14px;">
                                        <a style="cursor:pointer" @click="textareaToggle" :data-replyId = "reply.replyId">수정</a>
                                        <span class="divBar"></span>
                                        <a @click="changeToDeleteReply" :data-replyId = "reply.replyId" style="cursor:pointer"><span></span>삭제</a>
                                    </p>
                                </div>
                                <div class="replyUpdate form-group mb-3 d-none" :data-replyId="reply.replyId">
                                    <div class="d-flex">
                                        <textarea :data-replyId="reply.replyId" class="form-control replyUpdateText" rows="3" style="resize: none; font-size: 15px;" :value="reply.text"></textarea>
                                        <button @click="updateReply" :data-replyId="reply.replyId"  class="btn btn-primary" style="width: 80px; margin-left: 10px;">수정</button>
                                    </div>
                                </div>
                                <hr class="mt-4 mb-4">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>
</template>

<script>
import Vue from "vue";
import VueAlertify from "vue-alertify";

Vue.use(VueAlertify);

import util from "@/common/util.js";
import http from "@/common/axios.js";

export default {
    name: 'CommunityDetail',


    data() {
        return {
            text: "",
            textUpdate: "",
            disable: true,
            modify: true,
            replyId: 0,
        };
    },
    computed: {
    },
    mounted() {
        
    },
    created() {
    },

    methods: {
        makeDateStr: util.makeDateStr,
        makeTimeStr: util.makeTimeStr,
        // delete
        changeToDelete() {
            var $this = this; // alertify.confirm-function()에서 this 는 alertify 객체
            this.$alertify.confirmWithTitle(
                "게시글 삭제",
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
        changeToDeleteReply(e) {
            var $this = this; // alertify.confirm-function()에서 this 는 alertify 객체
            this.$alertify.confirmWithTitle(
                "댓글 삭제",
                "이 댓글을 삭제하시겠습니까?",
                function () {
                    // community.communityId 사용 X
                    $this.removeReply(e); // $this 사용
                },
                function () {
                    console.log("cancel");
                }
            );
        },
        sidebarToggle(){
            document.getElementById('sidebar').classList.toggle('active');
        },
        sidebarHide(){
            document.getElementById('sidebar').classList.remove('active');
        },
        textareaToggle(e){
            this.reply=e.target.dataset.replyid;
            document.querySelector(`.replyUpdate[data-replyid="` + this.reply + `"]`).classList.toggle('d-none');
            document.querySelector(`.replyText[data-replyid="` + this.reply + `"]`).classList.toggle('d-none');
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
        async insertReply(){
            let formData = new FormData();
            formData.append("text", this.text);
            formData.append("boardId", this.$store.state.community.boardId);

            try{
                let {data} = await http.post('/reply', formData);
                if( data.result == 'login' ){
                    this.doLogout();
                }else{
                    console.log(">>>>>> replyInsert");
                    console.log(data);
                    this.$alertify.success('댓글이 등록되었습니다.');
                    this.communityDetail();
                }
            } catch(error){
                console.log("replyInsert: error ");
                console.error(error);
            }
        },

        async updateReply(e){
            let formData = new FormData();
            let text = document.querySelector(`.replyUpdateText[data-replyid="` + this.reply + `"]`).value;
            
            formData.append("text", text);
            formData.append("replyId",  e.target.dataset.replyid);

            try{
                let {data} = await http.put('/reply', formData);
                if( data.result == 'login' ){
                    this.doLogout();
                }else{
                    console.log(">>>>>> replyUpdate");
                    console.log(data);
                    this.$alertify.success('댓글이 수정되었습니다.');
                    this.textareaToggle(e);
                    this.communityDetail();
                }
            } catch(error){
                console.log("replyInsert: error ");
                console.error(error);
            }
        },
        
        async removeReply(e){
            let replyId = e.target.dataset.replyid;
            
            try{
                console.log(replyId);

                let {data} = await http.delete('/reply?replyId=' + replyId);
                
                if( data.result == 'login' ){
                    this.doLogout();
                }else{
                    console.log(">>>>>> replyRemove");
                    console.log(data);
                    this.$alertify.success('댓글이 삭제되었습니다.');
                    this.communityDetail();
                }

            } catch(error){
                console.log("removeReply: error ");
                console.log(error);
            }
        },

        // detail
        async communityDetail() {
            try {
                let { data } = await http.get("/community/" +  this.$store.state.community.boardId);
                console.log(data);

                if (data.result == "login") {
                    this.doLogout();
                } else {
                    let { dto } = data;
                    this.$store.commit("SET_COMMUNITY_DETAIL", dto);
                    this.text="";
                }
            } catch (error) {
                console.log("CommunityMainVue: error : ");
                console.log(error);
            }
        },
    },
    filters: {
        makeDateStr: function (date, separator) {
            return date.year + separator + (date.month < 10 ? "0" + date.month : date.month) + separator + (date.day < 10 ? "0" + date.day : date.day);
        },
        makeTimeStr: function (time, separator) {
            return (time.hour < 10 ? "0" + time.hour : time.hour) + separator + (time.minute < 10 ? "0" + time.minute : time.minute) + separator + (time.second < 10 ? "0" + time.second : time.second);
        },
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
.bi.bi-download::before, .bi.bi-chat-text-fill::before{margin-top: .3rem; margin-right: .8rem;}
.divBar{margin: .2rem .6rem 0; display: inline-block; width: 1px; height: .9rem; background-color: #CCC;}
</style>