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
                            <li class="breadcrumb-item">
                                <router-link to="/house" class='sidebar-link'>Happy House</router-link>
                            </li>
                            <li class="breadcrumb-item active" >즐겨찾기</li>
                            <li class="breadcrumb-item active" aria-current="page">관심지역</li>
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
                            <col width="22.5%">
                            <col width="22.5%">
                            <col width="10%">
                            <col width="10%">
                            <col width="10%">
                        </colgroup>
                        <thead class="bg-primary text-white">
                            <tr>
                                <th>시/도</th>
                                <th>구/군</th>
                                <th>읍/면/동</th>
                                <th>뉴스</th>
                                <th>검색</th>
                                <th>삭제</th>
                            </tr>
                        </thead>
                        <tbody v-if="bookmarkAreaList.length > 0">
                            <tr v-for="(area, index) in bookmarkAreaList" :key="index">
                                <td>{{ area.cityName }}</td>
                                <td>{{ area.gugunName }}</td>
                                <td>{{ area.name }}</td>
                                <td>
                                    <a style="cursor:pointer" @click="getAreaNews" @mouseup="closeNews">
                                        <i class="bi bi-newspaper" :data-code="area.code" :data-city="area.cityCode" :data-gugun="area.gugunCode"></i>
                                    </a>
                                </td>
                                <td>
                                    <a style="cursor:pointer"  class="text-success" @click="go2HouseMain">
                                        <i class="bi bi-arrow-right-circle-fill" :data-code="area.code" :data-city="area.cityCode" :data-gugun="area.gugunCode"></i>
                                    </a>
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

        <section class="section">
            <div class="row">
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">지역별 생활 인구 수</h4>
                        </div>
                        <div class="card-body">
                            <canvas id="bar" ></canvas>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-header pb-0">
                            <h4 class="card-title mb-2">성별/나이 생활 인구 구분</h4>
                        </div>
                        <div class="card-body d-flex">
                            <div class="col-md-6">
                                <canvas id="doughnut1" ></canvas>
                                <p class="text-center mt-2 mb-0" style="font-size: 12px;">남</p>
                            </div>
                            <div class="col-md-6">
                                <canvas id="doughnut2" ></canvas>
                                <p class="text-center mt-2 mb-0" style="font-size: 12px;">여</p>
                            </div>
                            <!-- <div class="" v-else><p class="text-center">지역 선택 시 조회가 가능합니다.</p></div> -->
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <article>
            <div class="card">
                <div class="card-head rounded-top bg-dark d-flex justify-content-between" style="padding: 1rem;">
                    <h3 class="card-title text-white mb-0">관심 지역 주요 뉴스</h3>
                    <i @click="showToggle" id="openNews" class="bi bi-caret-down-fill d-none text-white" style="margin-right: 5px;"></i>
                    <i @click="showToggle" id="closeNews" class="bi bi-caret-up-fill text-white" style="margin-right: 5px;"></i>
                </div>
                <div class="card-body d-none" id="news-box">
                    <ul class="list-group" v-if="areaNewsList.length > 0">
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
                    <ul class="list-group" v-if="areaNewsList.length == 0">
                        <p class="text-center pt-5 pb-5 mb-0">지역 선택 시 조회가 가능합니다.</p>
                    </ul>
                </div><!-- end of .card-body -->
            </div>
        </article>
    </main>
</div>
</template>

<script>
import http from "@/common/axios.js";
import Chart from 'chart.js/auto';
export default {
    name: 'BookmarkArea',
    
    data() {
        return {
            totalChart: null,
            maleChart: null,
            femaleChart: null,
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
        this.populationChart();
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
        populationChart(){
            
            var chartColors = {
                red: 'rgb(255, 99, 132)',
                orange: 'rgb(255, 159, 64)',
                yellow: 'rgb(255, 205, 86)',
                green: 'rgb(75, 192, 192)',
                info: '#41B1F9',
                blue: '#3245D1',
                purple: 'rgb(153, 102, 255)',
                grey: '#EBEFF6'
            };

            var ctxBar = document.getElementById("bar").getContext("2d");
            this.totalChart = new Chart(ctxBar, {
                type: 'bar',
                data: {
                    labels: ["총 생활 인구 수", "총 남자 인구 수", "총 여자 인구 수"],
                    datasets: [{
                        label: '인구 수',
                        backgroundColor: [chartColors.red, chartColors.blue, chartColors.orange],
                        data: [
                            Math.round(this.$store.state.population.totalLocal) * 1 / 1,
                            Math.round(this.$store.state.population.totalMale) * 1 / 1,
                            Math.round(this.$store.state.population.totalFemale) * 1 / 1,
                        ],
                        datalabels: {
                            align: 'end',
                            anchor: 'start'
                        },
                    }]
                },
                options: {
                    responsive: true,
                    barRoundness: 1,
                    title: {
                        display: true,
                        text: "Students in 2020"
                    },
                    legend: {
                        display: false,
                    },
                    showValue:{
                        fontStyle: 'Helvetica',
                        fontSize: 16
                    },
                }
            });

            var ctxDoughnut = document.getElementById("doughnut1").getContext("2d");
            this.maleChart = new Chart(ctxDoughnut, {
                type: 'doughnut',
                data: {
                    labels: ["0~19세", "20~39세", "40~59세", "60~74세"],
                    datasets: [{
                        backgroundColor: [chartColors.yellow, chartColors.green, chartColors.info, chartColors.grey],
                        data: [
                            Math.round(this.$store.state.population.maleTo19) * 1 / 1,
                            Math.round(this.$store.state.population.maleTo39) * 1 / 1,
                            Math.round(this.$store.state.population.maleTo59) * 1 / 1,
                            Math.round(this.$store.state.population.maleTo74) * 1 / 1,
                        ],
                        
                        datalabels:{
                            color: '#555',
                            font:{size:16}
                        }
                    }],
                },
                
                options: {
                    responsive: true,
                    barRoundness: 1,
                    title: {
                        display: true,
                        text: "Students in 2020"
                    },
                    legend: {
                        display: true,
                    },
                    plugins:{
                        datalabels: {
                            formatter: (value, ctx) => {
                                var idx = ctx.dataIndex;
                                if(value != 0){
                                    let sum = 0;
                                    dataArr = ctx.chart.data.datasets[0].data;
                                    dataArr.map(data => {
                                        sum += parseInt(data);
                                    });
                                    let percentage = Math.round((value * 100 / sum )) + "%";
                                    return percentage;
                                } else{
                                    let percentage = "";
                                    return percentage;
                                }
                            },
                        },
                        color: 'blue',
                        labels: {
                            title: {
                                font: {
                                weight: 'bold'
                                }
                            },
                            value: {
                                color: 'green'
                            }
                        }
                    },
                }
            });
            
            var ctxDoughnut = document.getElementById("doughnut2").getContext("2d");
            this.femaleChart = new Chart(ctxDoughnut, {
                type: 'doughnut',
                data: {
                    labels: ["0~19세", "20~39세", "40~59세", "60~74세"],
                    datasets: [{
                        backgroundColor: [chartColors.orange, chartColors.purple, chartColors.blue, chartColors.red],
                        data: [
                            Math.round(this.$store.state.population.maleTo19) * 1 / 1,
                            Math.round(this.$store.state.population.maleTo39) * 1 / 1,
                            Math.round(this.$store.state.population.maleTo59) * 1 / 1,
                            Math.round(this.$store.state.population.maleTo74) * 1 / 1,
                        ]
                    }]
                },
                options: {
                    responsive: true,
                    barRoundness: 1,
                    legend: {
                        display: true,
                    },
                }
            });
        },
        destroyChart(){
            if(this.totalChart != null) this.totalChart.destroy();
            if(this.maleChart != null) this.maleChart.destroy();
            if(this.femaleChart != null) this.femaleChart.destroy();
        },
        sidebarToggle(){
            document.getElementById('sidebar').classList.toggle('active');
        },
        showToggle(){
            document.getElementById('news-box').classList.toggle('d-none');
            document.getElementById('openNews').classList.toggle('d-none');
            document.getElementById('closeNews').classList.toggle('d-none');
        },
        closeNews(){
            document.getElementById('news-box').classList.add('d-none');
            document.getElementById('openNews').classList.remove('d-none');
            document.getElementById('closeNews').classList.add('d-none');
        },
        setBlur(e){
            e.target.blur();
        },

        initArea(){
            this.sido = "0";
        },
        getBookmarkAreaList(){
            this.$store.dispatch("bookmarkAreaList");
        },
        getAreaNews(e){
            console.log(e.target);
            this.$store.commit("SET_NEWS_CITY", e.target.dataset.city);
            this.$store.commit("SET_NEWS_DVSN", e.target.dataset.gugun);

            this.$store.dispatch("areaNewsList");
            document.getElementById('news-box').classList.remove('d-none');

            this.getPopulationInfo(e);
        },
        go2HouseMain(e){
            this.sido = e.target.dataset.city;
            this.gugun = e.target.dataset.gugun;
            console.log(">>>>>>>>go2HouseMain")
            console.log(this.gugun);
            this.dong = e.target.dataset.code;
            this.$router.push("/house");
        },
        
        getSidoList() {
            this.$store.dispatch("getSidoList");
        },
        getGugunList() {
            this.$store.dispatch("getGugunList");
        },
        getDongList() {
            this.$store.dispatch("getDongList");
        },
                
        async addBookmarkArea(e){
            document.querySelector('#news-box').classList.add('d-none');
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
                    this.initArea();
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
                    this.destroyChart();
                }

            } catch(error){
                console.log("removeBookmarkArea: error ");
                console.log(error);
            }
        },

        async getPopulationInfo(e){
            let dongCode = e.target.dataset.code;
            try{
                console.log(dongCode);

                let data = await http.get('/stats/resd?dongCode=' + dongCode);
                
                if( data.result == 'login' ){
                    this.doLogout();
                }else{
                    console.log(data.data);
                    this.$store.commit("SET_POPULATION_INFO", data.data);
                    this.destroyChart();
                    this.populationChart();
                }

            } catch(error){
                console.log("getPopulationInfo: error ");
                console.log(error);
            }
        },
    },
    
};
</script>

<style scoped>
select{width: 130px; margin-right: 10px;}
input[type="text"]{width: 250px; margin-right: 10px;}

thead th{padding: 1rem;}

.bi.bi-caret-down-fill::before,
.bi.bi-caret-up-fill::before,
.bi.bi-dash-circle-fill::before,
.bi.bi-newspaper::before,
.bi.bi-arrow-right-circle-fill::before{font-size: 22px;}
</style>