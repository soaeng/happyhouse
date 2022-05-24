<template>
<div id="main">
    <header class="mb-3">
        <a href="#" class="burger-btn d-block d-xl-none" @click="sidebarToggle">
            <i class="bi bi-justify fs-3"></i>
        </a>
    </header>

    <main class="page-heading">
        <div class="page-title mb-4">
            <div class="row">
                <div class="col-12 col-md-6 order-md-1 order-last">
                    <h3><i class="bi bi-search"></i>아파트 검색</h3>
                    <p class="text-subtitle text-muted"></p>
                </div>
            </div>
        </div>
        <section>
            <div class="card">
                <div class="card-header">
                    <fieldset class="form-group d-flex align-items-center justify-content-center mb-0">
                        <select class="form-select" id="sido" v-model="sido" @blur="getGugunList">
                            <option value="0" selected>시/도</option>
                            <option v-for="(sido, index) in sidoList" :key="index" :value="sido.code">{{ sido.name }}</option>
                        </select>
                        <select class="form-select" id="gugun" v-model="gugun" @blur="getDongList">
                            <option value="0" selected>구/군</option>
                            <option v-for="(gugun, index) in gugunList" :key="index" :value="gugun.code">{{ gugun.name }}</option>
                        </select>
                        <select class="form-select d-inline" id="dong" v-model="dong">
                            <option value="0" selected>읍/면/동</option>
                            <option v-for="(dong, index) in dongList" :key="index" :value="dong.code">{{ dong.name }}</option>
                        </select>
                        <input v-model="$store.state.house.keyword" @keydown.enter="[getHouseList(), initPage()]" class="form-control" type="text" placeholder="아파트명">
                        <button @click="[getHouseList(), initPage()]" class="btn btn-primary" type="button">검색</button>

                    </fieldset>
                </div><!-- end of .card-header -->
                <hr class="mb-0 mt-0">
                <div class="card-content">
                    <div class="card-body">
                        <div class="list-info d-flex">
                            <p>총&nbsp;<span class="text-primary">{{$store.state.house.totalListItemCount}}</span>&nbsp;건</p>
                            <p><span class="text-primary">{{$store.state.house.currentPageIndex}}</span>&nbsp;/&nbsp;<span>{{Math.ceil($store.state.house.totalListItemCount/$store.state.house.listRowCount)}}</span>&nbsp;페이지</p>
                        </div><!-- end of .list-info -->
                        <div class="table-responsive">
                            <table class="table table-hover text-center mb-0">
                                <colgroup>
                                    <col width="8.8%">
                                    <col width="28%">
                                    <col width="27%">
                                    <col width="10%">
                                    <col width="14.2%">
                                    <col width="12%">
                                </colgroup>
                                <thead>
                                    <tr>
                                        <th>번호</th>
                                        <th>아파트명</th>
                                        <th>주소</th>
                                        <th>건축연도</th>
                                        <th>최근거래금액</th>
                                        <th>최근거래일</th>
                                    </tr>
                                </thead>
                                <tbody v-if="houseList.length > 0">
                                    <tr style="cursor:pointer" v-for="(house, index) in houseList" :key="index" @click="setHouseNo(house.houseNo)">
                                        <td>{{house.no}}</td>
                                        <td class="text-start">{{house.AptName}}</td>
                                        <td>{{house.address}}</td>
                                        <td>{{house.buildYear}}</td>
                                        <td>{{house.dealAmount}}만 원</td>
                                        <td>{{house.dealYear}}-{{house.dealMonth | setDate }}-{{house.dealDay | setDate }}</td>
                                    </tr>
                                </tbody>
                            </table>
                            <div v-if="houseList.length == 0" class="w-100 text-center" style="border-bottom: 1px solid #DEDEDE">
                                <p class="pt-5 pb-5 mb-0">조회된 데이터가 없습니다.</p>
                            </div>
                        </div><!-- end of .table-responsive -->
                    </div><!-- end of .card-body -->
                    <pagination class="mb-4" v-if="houseList.length > 0" v-on:call-parent="movePage" :targetObj="target"></pagination>
                </div><!-- end of .card-content -->
            </div><!-- end of .card -->
        </section>
    </main>

    <house-deal-modal></house-deal-modal>
</div><!-- end of #main -->
</template>

<script>

import Pagination from "./Pagination.vue";
import houseDealModal from "./modals/HouseDealModal.vue";

import { Modal } from "bootstrap";
export default {
    name: 'HouseMain',
    components: {Pagination, houseDealModal},
    data() {
        return {
            target: "house", // for pagination
            houseDealModal: null,
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
        /////////////////////////////////////////////// computed - HOUSE
        houseList(){
            return this.$store.getters.getHouseList;
        },
    },

    methods: {
        sidebarToggle(){
            document.getElementById('sidebar').classList.toggle('active');
        },

        initPage(){
            this.$store.state.house.currentPageIndex = 1;
            this.$store.state.house.offset = 0;
        },
        // pagination (기존은 모두 props로 처리했지만 이제는 store에서 getters에서 처리)
        movePage(pageIndex) {
            console.log("HouseMainVue : movePage : pageIndex : " + pageIndex);
            this.$store.commit("SET_HOUSE_MOVE_PAGE", pageIndex);

            this.getHouseList();
        },

        ///////////////////////////////////////////////////////// methods - DEAL MODAL
        setHouseNo(houseNo) {
            console.log("houseNo: " + houseNo);
            this.$store.commit("SET_HOUSE_NO", houseNo);
            this.houseDealModal.show();
            this.dealList();
            this.isShowing = true;
        },
        dealList(){
            this.$store.dispatch("dealList");
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
        async getHouseDealList() {
            this.$store.state.address.dong;
        },
        //////////////////////////////////////////// methods - HOUSE
        async getHouseList(){
            if(this.$store.state.house.keyword == "" && this.dong == 0){
        		this.$store.state.house.type = "init"; 	// 전체 목록
        	} else if(this.$store.state.house.keyword == "" && this.dong != 0){
        		this.$store.state.house.type = "dong";	// 동 검색 목록
        	} else if(this.$store.state.house.keyword != "" && this.dong == 0) {
        		this.$store.state.house.type = "name";	// 아파트명 검색 목록
        	} else if(this.$store.state.house.keyword != "" && this.dong != 0) {
        		this.$store.state.house.type = "all";	// 동, 아파트 검색 목록
        	}
            this.$store.state.house.dongCode = this.dong;
            this.$store.dispatch("houseList");
        },

        async getHouseDealList(houseNo){
            this.$store.state.house.houseNo = houseNo;
            this.$store.dispatch("houseDealList");
        },

    },

    watch: {
        dong: function () {
            this.getHouseList();
            this.initPage();
        },
    },

    created() {
        this.getSidoList();
        if(this.sido != "0"){
            this.getGugunList();
            if(this.gugun != "0") this.getDongList();
        }
        this.getHouseList();
    },

    mounted() {
        this.houseDealModal = new Modal(document.getElementById("houseDealModal"));
    },

    filters: {
        setDate: function(date){return date.length < 2 ? "0" + date : date;}
    },
};
</script>

<style scoped>
    select{width: 130px; margin-right: 10px;}
    input[type="text"]{width: 250px; margin-right: 10px;}

    .list-info{font-size: 14px;}
    .list-info p:first-child{margin-right: 20px;}
    tr td{overflow: hidden; text-overflow: ellipsis; white-space: nowrap;}

    th{border-top: 1px solid #dedede; padding: 1.2rem .5rem;}
    td{font-size: 15px; padding: 1.2rem .5rem;}
</style>