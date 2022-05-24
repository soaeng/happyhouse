<template>
<div id="houseDealModal" class="modal fade w-100" tabindex="-1" @click="[$store.commit('SET_HOUSE_SHOW_MAP', false), getBookmarkDealList()]">
    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-xl">
        <div class="modal-content">
            <div class="modal-header bg-primary position-relative">
                <div class="w-100 white">
                    <div class="d-flex">
                        <h2 class="mb-4 white">{{ $store.state.house.AptName }} <button @click="setBlur" class="btn white rounded-pill bg-danger d-none" style="margin-left: 20px;">관심아파트</button> </h2>
                        
                    </div>
                    <div>
                        <p class="mb-0"><i class="bi bi-geo-alt-fill"></i>&nbsp;&nbsp;{{ $store.state.house.address }}</p>
                        <p class="mb-0"><i class="bi bi-calendar2-date"></i>&nbsp;&nbsp;건축년도 : {{ $store.state.house.buildYear }}</p>
                    </div>
                </div>
                <button @click="[$store.commit('SET_HOUSE_SHOW_MAP', false), getBookmarkDealList()]" type="button" class="btn-close position-absolute" data-bs-dismiss="modal" aria-label="Close"></button>
            </div><!-- end of .modal-header -->
            <div class="modal-body">
                <div class="house-content d-flex mb-4">
                    <div class="w-50" style="margin-right: 10px;">
                        <p class="mb-1">총 <span class="text-primary">{{ dealList.length }}</span> 건</p>
                        <div class="th-wrapper">
                            <table class="table mb-0 text-center" style="margin-right:7px;">
                                <colgroup>
                                    <col width="14%">
                                    <col width="17%">
                                    <col width="10%">
                                    <col width="23%">
                                    <col width="17%">
                                    <col width="15%">
                                </colgroup> 
                                <thead>
                                    <tr>
                                        <th>번호</th>
                                        <th>면적(㎡)</th>
                                        <th>층</th>
                                        <th>거래금액</th>
                                        <th>거래일시</th>
                                        <th>관심거래</th>
                                    </tr>
                                </thead>
                            </table>
                        </div>
                        <div class="scroll-wrapper"><!-- div for scroll -->
                            <table class="table text-center mb-0">
                                <colgroup>
                                    <col width="14%">
                                    <col width="17%">
                                    <col width="10%">
                                    <col width="23%">
                                    <col width="17%">
                                    <col width="15%">
                                </colgroup>
                                <tbody>
                                    <tr style="cursor:pointer" v-for="(deal) in dealList" :key="deal.no">
                                        <td>{{ deal.no }}</td>
                                        <td>{{ parseFloat(deal.area).toFixed(1) }}</td>
                                        <td>{{ deal.floor }}</td>
                                        <td>{{ deal.dealAmount }}만 원</td>
                                        <td>{{ deal.dealYear }}-{{ deal.dealMonth | setDate }}-{{ deal.dealDay | setDate }}</td>
                                        <td>
                                            <a @click="addBookmarkDeal" class="text-warning">
                                                <i class="bi bi-star" v-if="!deal.bookmark" :data-no="deal.no" :data-bookmark="deal.bookmark"></i>
                                                <i class="bi bi-star-fill" v-if="deal.bookmark" :data-no="deal.no" :data-bookmark="deal.bookmark"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div><!-- end of .scroll-wrapper-->
                    </div>
                    <div class="house-map w-50">
                        <div class="map-box w-100">
                            <div class="btn-box d-flex justify-content-between mb-2 mt-4">
                                <button class="btn rounded-pill btn-outline-primary" @click="setCenter" @mouseup="setBlur">아파트</button>
                                <div id="category">
                                    <button id="SC4" data-order="0" class="btn rounded-pill btn-outline-success" @click="btnToggle" @mouseup="setCategoryOrder">학교</button>
                                    <button id="PO3" data-order="1" class="btn rounded-pill btn-outline-dark" @click="btnToggle" @mouseup="setCategoryOrder">공공기관</button>
                                    <button id="BK9" data-order="2" class="btn rounded-pill btn-outline-danger" @click="btnToggle" @mouseup="setCategoryOrder">은행</button>
                                    <button id="HP8" data-order="3" class="btn rounded-pill btn-outline-warning" @click="btnToggle" @mouseup="setCategoryOrder">병원</button>
                                    <button id="CE7" data-order="4" class="btn rounded-pill btn-outline-secondary" @click="btnToggle" @mouseup="setCategoryOrder">카페</button>
                                    <button id="CS2" data-order="5" class="btn rounded-pill btn-outline-info" @click="btnToggle" @mouseup="setCategoryOrder">편의점</button>
                                </div>
                            </div>
                            <div id="map" style="width: 100%; height: 500px; background-color: beige;"></div>
                        </div>
                    </div><!-- end of .house-map -->
                </div><!-- end of .house-content -->

<!-- 상권 정보 ....... .. 기타 정보 들어갈 곳 -->

                <div>
                    <ul class="nav nav-tabs mb-4" role="tablist">
                        <li class="nav-item" role="presentation">
                            <a class="nav-link active" data-bs-toggle="tab" data-bs-target="#school" role="tab" aria-controls="school" aria-selected="false">학군정보</a>
                        </li>
                        <li class="nav-item" role="presentation">
                            <a id="SW8" class="nav-link" data-bs-toggle="tab" data-bs-target="#transport" role="tab" aria-controls="transport" aria-selected="false">대중교통</a>
                        </li>
                        <li class="nav-item" role="presentation">
                            <a class="nav-link" data-bs-toggle="tab" data-bs-target="#search" role="tab" aria-controls="search" aria-selected="true">검색결과</a>
                        </li>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane fade show active" id="school" role="tabpanel" aria-labelledby="school-tab">
                            <div v-if="schoolList.length > 0">
                                <ul v-for="(school, index) in schoolList" :key="index">
                                    <li>
                                        <span v-if="school.grade == '초등학교'" class="badge bg-warning">초등학교</span>
                                        <span v-if="school.grade == '중학교'" class="badge bg-success">중학교</span>
                                        <span v-if="school.grade == '고등학교'" class="badge bg-danger">고등학교</span>
                                        {{school.name}} ({{school.address}}) <span class="text-gray-500" style="margin-left: .5rem; font-size: 14px;">{{Math.round(school.distance)}}m</span>
                                    </li>
                                </ul>
                            </div>
                            <div class="w-100 pt-5 pb-5 text-center" v-else><p>조회된 데이터가 없습니다.</p></div>
                        </div>
                        <div class="tab-pane fade" id="transport" role="tabpanel" aria-labelledby="transport-tab">
                            
                            <div v-if="schoolList.length > 0">
                                <h5 class="mb-3">버스</h5>
                                <ul v-for="(busStop, index) in busStopList" :key="index">
                                    <li>
                                        {{busStop.name}} <span class="text-gray-500" style="margin-left: .5rem; font-size: 14px;">{{Math.round(busStop.distance)}}m</span>
                                    </li>
                                </ul>
                            </div>
                            <div class="w-100 pt-5 pb-5 text-center" v-else><p>조회된 데이터가 없습니다.</p></div>
                        </div>
                        <div class="tab-pane fade" id="search" role="tabpanel" aria-labelledby="search-tab">
                            <div v-if="search.results.length > 0">
                                <h5 class="mb-3">{{search.results[0].category_group_name}}</h5>
                                <ul v-for="data in search.results" :key="data.id">
                                    <li>
                                        <span v-if="search.results[0].category_group_name === '학교'">
                                            <span v-if="data.category_name.split(' > ')[2] == '초등학교'" class="badge bg-warning">초등학교</span>
                                            <span v-if="data.category_name.split(' > ')[2] == '중학교'" class="badge bg-success">중학교</span>
                                            <span v-if="data.category_name.split(' > ')[2] == '고등학교'" class="badge bg-danger">고등학교</span>
                                        </span>
                                        {{data.place_name}} ({{data.address_name}})
                                    </li>
                                </ul>
                            </div>
                            <div class="w-100 pt-5 pb-5 text-center" v-else><p>조회된 데이터가 없습니다.</p></div>
                        </div>
                    </div>
                </div><!-- end of .surrounding -->

            </div><!-- end of .modal-body -->
        </div><!-- end of .modal-content -->
    </div><!-- end of .modal-dialog -->
</div><!-- end of #modal-housedeal-->

</template>

<script>

import http from "@/common/axios.js";

export default {
    name: 'HouseDealModal',

    data() {
        return {
            map: null,
            center: null,

            search:{
                order: '',
                results: [],
                currCategory: '',
                pagination: null,
            },

            markers: [],
            infos: [],

            loadedMap: false,
        };
    },

    computed: {
        houseNo(){
            return this.$store.state.house.houseNo;
        },
        dealList(){
            return this.$store.state.house.dealList;
        },
        showMap(){
            return this.$store.state.house.showMap;
        },
        schoolList(){
            return this.$store.getters.getSchoolList;
        },
        busStopList(){
            return this.$store.getters.getBusStopList;
        },
        userSeq(){
            return this.$store.state.login.userSeq;
        }
    },

    watch:{
        showMap(value){
            if(value) setTimeout(() => {this.initMap()}, 200);
            if(!this.loadedMap){
                this.getSchoolList();
                this.getBusStopList();
                this.setControl();
            }
        },
    },

    mounted() {
        if (window.kakao && window.kakao.maps) {
            this.initMap();
        } else {
            const script = document.createElement("script");
            /* global kakao */
            script.onload = () => kakao.maps.load(this.initMap);
            script.src =
                "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=cb9b2686378a8d564494657ed1378fb9&libraries=services";
            document.head.appendChild(script);
        };

    },

    methods: {
        initMap() {
            const container = document.getElementById("map");
            this.getCenter();
            const options = {
                center: this.center,
                level: 4,
            };

            //지도 객체를 등록합니다.
            //지도 객체는 반응형 관리 대상이 아니므로 initMap에서 선언합니다.
            this.map = new kakao.maps.Map(container, options);
            //지도의 마우스 휠, 모바일 터치를 이용한 확대, 축소 기능을 막는다.
	        this.map.setZoomable(false);

            var customOverlay = new kakao.maps.CustomOverlay({
                position: this.center,
                content: `<div class="pt-4"><span class="badge bg-primary rounded-pill mt-2">아파트 위치</span></div>`
	        });
            
            var marker = new kakao.maps.Marker({  
                map: this.map, 
                position: this.center,
                image: new kakao.maps.MarkerImage("assets/images/markers/marker_primary.png", new kakao.maps.Size(24, 35))
	        });

            customOverlay.setMap(this.map);

        },
        setIdleEvent(){
            kakao.maps.event.addListener(this.map, 'idle', this.searchPlace);
        },
        getCenter(){
            this.center = new kakao.maps.LatLng(this.$store.state.house.lat, this.$store.state.house.lng)
        },
        setCenter(){
            this.map.panTo(this.center);
            this.removeMarker();
        },
        setControl(){
            if(!this.loadedMap){
                setTimeout(() => {
                    
                    //일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
                    var mapTypeControl = new kakao.maps.MapTypeControl();
                    //지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
                    var zoomControl = new kakao.maps.ZoomControl();

                    this.map.addControl(mapTypeControl);
                    this.map.addControl(zoomControl);
                    this.loadedMap = true;
                }, 200);
            }
        },
        setCategoryOrder(e){
            console.log(e.target.id);
            let currCategory = e.target.id;
            if (!currCategory) {
                return;
            }
            this.setIdleEvent();
            let order = document.getElementById(currCategory).getAttribute('data-order');
            this.search.currCategory = currCategory;
            this.search.order = order;
            this.searchPlace();
        },
        searchPlace(){
            this.removeMarker();

            const ps = new kakao.maps.services.Places(this.map);
            
            ps.categorySearch(this.search.currCategory, (data, status, pagination) => {
                console.log(data);
                this.search.results = data;
                this.search.pagination = pagination;
                if(this.search.currCategory != 'SW8') this.displayPlaces(data);
            },  {useMapBounds:true});
        },

        displayPlaces(places) {

            for ( var i=0; i<places.length; i++ ) {

                // 마커를 생성하고 지도에 표시합니다
                this.addMarker(new kakao.maps.LatLng(places[i].y, places[i].x), places[i].place_name);
                
            }
        },
        addMarker(position, name){
            var color = '';
            if(this.search.order === '0') color = 'success';
            else if(this.search.order === '1') color = 'dark';
            else if(this.search.order === '2') color = 'danger';
            else if(this.search.order === '3') color = 'warning';
            else if(this.search.order === '4') color = 'secondary';
            else if(this.search.order === '5') color = 'info';
            var marker = new kakao.maps.Marker({
                position: position,
                clickable: true,
                image: new kakao.maps.MarkerImage("assets/images/markers/marker_" + color + ".png", new kakao.maps.Size(24, 35))
	        });

            var overlay = new kakao.maps.CustomOverlay({
                position: position,
                content: `<span class="badge rounded-pill bg-` + color + `">` + name + `</span>`,
                yAnchor: 2.5
            });

            marker.setMap(this.map);
            overlay.setMap(this.map);
            this.markers.push(marker);
            this.infos.push(overlay);
            
            return marker;

        },
        removeMarker() {
            for ( var i = 0; i < this.markers.length; i++ ) {
                this.markers[i].setMap(null);
                this.infos[i].setMap(null);
            }   
            this.markers = [];
            this.infos = [];
        },

        getDealList(){
            this.$store.dispatch("dealList");
        },
        getSchoolList(){
            this.$store.dispatch("schoolList");
        },
        
        getBusStopList(){
            this.$store.dispatch("busStopList");
        },
        btnToggle(e){
            this.setBlur(e);
            this.siblings(e.target, 'active');
        },
        setBlur(e){
            e.target.blur();
        },
        siblings(t, removeClass) { 
            const children = t.parentElement.children;
            for (var i = 0; i < children.length; i++) {
                children[i].classList.remove(removeClass);
            }
            t.classList.add(removeClass)
        },

        async addBookmarkDeal(e){
            let dealNo = e.target.dataset.no;
            let userSeq = this.userSeq;
            let bookmark = e.target.dataset.bookmark;
            let data = null;
            
            try{
                
                if(!bookmark)
                    data = await http.post('/bookmark/deal?dealNo=' + dealNo);
                else data = await http.delete('/bookmark/deal?dealNo=' + dealNo);

                if( data.result == 'login' ){
                    this.doLogout();
                }else{
                    this.getDealList();
                }

            } catch(error){
                console.log("addBookmarkDeal: error ");
                console.log(error);
            }
        },

        getBookmarkDealList(){
            this.$store.dispatch("bookmarkDealList");
        },
    },
    
    filters: {
        setDate: function(date) {return date.length < 2 ? "0" + date : date;}
    },
};
</script>

<style scoped>
    button.btn-close{top:20px; right: 20px;}
    .scroll-wrapper{height: 500px; overflow-y: scroll;}
    .th-wrapper{overflow-y: scroll}
    .th-wrapper::-webkit-scrollbar{width: 7px; height:0}
    .scroll-wrapper::-webkit-scrollbar, .modal-body::-webkit-scrollbar, .tab-content::-webkit-scrollbar{width: 8px;}
    .scroll-wrapper::-webkit-scrollbar-thumb, .modal-body::-webkit-scrollbar-thumb, .tab-content::-webkit-scrollbar-thumb{background-color: #ccc; border-radius: .5rem;}
    .scroll-wrapper::-webkit-scrollbar-track, .modal-body::-webkit-scrollbar-track, .tab-content::-webkit-scrollbar-track{padding-left: 10px;}
    
    tr td{overflow: hidden; text-overflow: ellipsis; white-space: nowrap;}
    .bi.bi-star::before, .bi.bi-star-fill::before{margin-top:.2rem;}

    .btn-box button{font-size:14px;}
    .btn-box div .btn{margin-left: 5px;}

    ul{list-style: none; padding-left: 0;}
    span.badge{width: 70px; margin-right: 10px;}

    .tab-content{max-height: 290px; overflow-y: scroll;}
    .tab-content::-webkit-scrollbar{width: 8px;}
</style>