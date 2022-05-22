<template>
<div>
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
                    <table class="table table-md table-hover mb-0 text-center">
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
                                    <button style="cursor:pointer" class="btn btn-warning text-black-50" @click="go2HouseMain">
                                        <span :data-code="area.code">매물 보러 가기</span>
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

                let data = data = await http.post('/bookmark/area?dongCode=' + dongCode + '&userSeq=' + userSeq);
                
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

                let data = data = await http.delete('/bookmark/area?dongCode=' + dongCode + '&userSeq=' + userSeq);
                
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

        go2HouseMain(e){
            this.$store.commit("SET_ADDRESS_DONG", e.target.dataset.code);
            this.$router.push('/house');
        }
    },
    
};
</script>

<style scoped>
select{width: 130px; margin-right: 10px;}
input[type="text"]{width: 250px; margin-right: 10px;}

.bi.bi-dash-circle-fill::before{font-size: 22px;}
</style>