<template>
<div>
    <!-- TODO: 일단 매물 바로가기 하면 셀렉트박스에 설정 안된다 ㅅㅂ 삽질 존나게 햇음 ㅎㅎ

    글구 다시 아파트 검색 누르면 reload 되게 하고 싶은데 페이지 이동만 됨 머 어쩌라고 진 짜 ㅡ ㅡ 
    찾아보기 귀찬코 눈알 처빠질거같음
    
    1. 매물보러가기 누르면 아파트 검색 페이지에서 셀렉트 박스 선택하고 해당 동 보여주게 하던가
    2. 아래에 아파트 목록 리스트만 보여주기

    사실 2번으로 하면 관심 아파트 메뉴는 필요한가 싶기도 하고
    헉 아니면 관심지역 관련 뉴스 크롤링해서 보여주기 ?!??
    사유: 아파트 보여주는건 이미 한 거도 망칠거같아서 ㅜ 귀찬아지면 어케
    흠 그럼 어디서 가져올지 고민해봐야겟군 . ..
    
    아  담타 땡겨~~ ~ ~~~~~~~~~~~~~~~ 낼  고민해야징 까먹지만말어라 이자시가
    
    -->
    <main class="page-heading">
        <div class="page-title">
            <div class="row">
                <div class="col-12 col-md-6 order-md-1 order-last">
                    <h3><i class="bi bi-bookmark-heart-fill"></i>관심 지역 목록</h3>
                    <p class="text-subtitle text-muted"></p>
                </div>
                <div class="col-12 col-md-6 order-md-2 order-first">
                    <nav aria-label="breadcrumb" class="breadcrumb-header float-start float-lg-end">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item">즐겨찾기</li>
                            <li class="breadcrumb-item active"><router-link to="/bookmark/deal">관심거래</router-link></li>
                        </ol>
                    </nav>
                </div>
            </div>
        </div> <!-- end of .page-title -->
        <section class="section">
            <div class="card">
                <div class="card-header">
                    <fieldset class="form-group d-flex align-items-center justify-content-center mb-0">
                        <select class="form-select" id="sido" v-model="sido">
                            <option value="0" selected>시/도</option>
                            <option v-for="(sido, index) in sidoList" :key="index" :value="sido.code">{{ sido.name }}</option>
                        </select>
                        <select class="form-select" id="gugun" v-model="gugun">
                            <option value="0" selected>구/군</option>
                            <option v-for="(gugun, index) in gugunList" :key="index" :value="gugun.code">{{ gugun.name }}</option>
                        </select>
                        <select class="form-select d-inline" id="dong" v-model="dong">
                            <option value="0" selected>읍/면/동</option>
                            <option v-for="(dong, index) in dongList" :key="index" :value="dong.code">{{ dong.name }}</option>
                        </select>
                        <button class="btn btn-primary" type="button" @click="addBookmarkArea">등록</button>
                    </fieldset>
                </div><!-- end of .card-header -->
                <div class="card-body">
                    <table class="table table-hover mb-0 text-center">
                        <colgroup>
                            <col width="25%">
                            <col width="25%">
                            <col width="25%">
                            <col width="20%">
                            <col width="5%">
                        </colgroup>
                        <thead class="bg-primary text-white">
                            <tr>
                                <th>시/도</th>
                                <th>구/군</th>
                                <th>읍/면/동</th>
                                <th></th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody v-if="bookmarkAreaList.length > 0">
                            <tr v-for="(area, index) in bookmarkAreaList" :key="index">
                                <td>{{ area.cityName }}</td>
                                <td>{{ area.gugunName }}</td>
                                <td>{{ area.name }}</td>
                                <td>
                                    <button style="cursor:pointer" class="btn btn-warning text-black-50" @click="getAreaNews">
                                        <span :data-code="area.code" :data-city="area.cityCode" :data-gugun="area.gugunCode">지역별 주요 뉴스</span>
                                    </button>
                                </td>
                                <td>
                                    <a style="cursor:pointer"  class="text-danger" @click="removeBookmarkArea">
                                        <i class="bi bi-dash-circle-fill" :data-code="area.code"></i>
                                    </a>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <div v-if="bookmarkAreaList.length == 0" class="w-100 text-center" style="border-bottom: 1px solid #DEDEDE;">
                        <p class="pt-5 pb-5 text-sm mb-0">등록된 관심거래가 없습니다.</p>
                    </div>
                </div><!-- end of .card-body -->
            </div><!-- end of .card -->
        </section>

        <section>
            <div class="card">
                <div class="card-head bg-primary d-flex justify-content-between" style="padding: 1rem;">
                    <h3 class="card-title text-white mb-0">dd</h3>
                    <a><i class="bi bi-caret-down-fill text-white"></i></a>
                </div>
                <div class="card-body">
                    <ul class="list-group">
                        <li style="cursor:pointer; list-style: none;" v-for="(news, index) in areaNewsList" :key="index" class="rounded-3 mb-3">
                            <a :href="news.url" target="_blank" class="list-group-item list-group-item-action d-flex" style=" padding: 1rem;">
                                <div style="margin-right: 1rem; width: 260px;" class="d-flex">
                                <img class="w-100 bg-light-secondary" :src="news.img" style="max-height: 112px; object-fit: cover;">
                                </div>
                                <div>
                                <div class="d-flex w-100 justify-content-between">
                                    <h5 class="mb-1">{{news.title}}</h5>
                                    <small></small>
                                </div>
                                <p class="mb-3">{{news.content}}</p>
                                <small class="float-end"><span class="text-danger">{{news.publish}}</span> | <span class="text-gray-600">{{news.date}}</span></small>
                                </div>
                            </a>
                        </li>
                    </ul><!-- end of .list-group -->
                </div><!-- end of .card-body -->
            </div>
        </section>
    </main>
</div>
</template>

<script>
import http from "@/common/axios.js";
export default {
    name: 'BookmarkArea',
    
    data() {
        return {
        };
    },

    computed: {
        /////////////////////////////////////////////// computed - ADDRESS
        sidoList() {
            return this.$store.getters.getSidoList;
        },
        gugunList() {
            return this.$store.getters.getGugunList;
        },
        dongList() {
            return this.$store.getters.getDongList;
        },
        sido: {
            get() {
                return this.$store.state.address.sido;
            },
            set(sido) {
                this.$store.commit("SET_ADDRESS_SIDO", sido);
            },
        },
        gugun: {
            get() {
                return this.$store.state.address.gugun;
            },
            set(gugun) {
                this.$store.commit("SET_ADDRESS_GUGUN", gugun);
            },
        },
        dong: {
            get() {
                return this.$store.state.address.dong;
            },
            set(dong) {
                this.$store.commit("SET_ADDRESS_DONG", dong);
            },
        },

        bookmarkAreaList(){
            return this.$store.state.house.bookmarkAreaList;
        },

        areaNewsList(){
            return this.$store.state.news.list;
        },
        
        userSeq(){
            return this.$store.state.login.userSeq;
        }
    },

    created() {
        this.getSidoList();
    },

    mounted() {
        this.getBookmarkAreaList();
    },

    watch: {
        sido: function () {
            if (this.sido != "0") this.getGugunList();
        },
        gugun: function () {
            if (this.gugun != "0") this.getDongList();
        },
    },

    methods: {
        sidebarToggle(){
            document.getElementById('sidebar').classList.toggle('active');
        },

        async getSidoList() {
            this.$store.dispatch("getSidoList");
        },
        async getGugunList() {
            this.$store.dispatch("getGugunList");
        },
        async getDongList() {
            this.$store.dispatch("getDongList");
        },

        getBookmarkAreaList(){
            this.$store.dispatch("bookmarkAreaList");
        },
                
        async addBookmarkArea(e){
            let dongCode = this.dong;
            let userSeq = this.userSeq;
            console.log(e.target);

            try{
                console.log(dongCode);
                console.log(userSeq);

                let data = data = await http.post('/bookmark/area?dongCode=' + dongCode);
                
                if( data.result == 'login' ){
                    this.doLogout();
                }else{
                    this.getBookmarkAreaList();
                }

            } catch(error){
                console.log("addBookmarkArea: error ");
                console.log(error);
            }
        },
        
        async removeBookmarkArea(e){
            let dongCode = e.target.dataset.code;
            let userSeq = this.userSeq;
            console.log(e.target);

            try{
                console.log(dongCode);
                console.log(userSeq);

                let data = data = await http.delete('/bookmark/area?dongCode=' + dongCode);
                
                if( data.result == 'login' ){
                    this.doLogout();
                }else{
                    this.getBookmarkAreaList();
                }

            } catch(error){
                console.log("removeBookmarkArea: error ");
                console.log(error);
            }
        },

        getAreaNews(e){
            console.log(">>>>>>>>>>>>>>>>>>>> getAreaNews");
            console.log(e.target.dataset.city);
            this.$store.commit("SET_NEWS_CITY", e.target.dataset.city);
            this.$store.commit("SET_NEWS_DVSN", e.target.dataset.gugun);

            this.$store.dispatch("areaNewsList");
        }
    },
    
};
</script>

<style scoped>
select{width: 130px; margin-right: 10px;}
input[type="text"]{width: 250px; margin-right: 10px;}

thead th{padding: 1rem;}

.bi.bi-dash-circle-fill::before, .bi.bi-caret-down-fill::before{font-size: 22px;}
</style>