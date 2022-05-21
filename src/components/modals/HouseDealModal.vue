<template>
<div id="houseDealModal" class="modal fade w-100" tabindex="-1" @click="$store.commit('SET_HOUSE_SHOW_MAP', false)">
    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-full">
        <div class="modal-content">
            <div class="modal-header bg-primary position-relative">
                <div class="w-100 white">
                    <div class="d-flex">
                        <h2 class="mb-4 white">{{ $store.state.house.AptName }} <button class="btn btn-sm rounded-pill btn-light" style="margin-left: 20px;">관심 아파트 등록</button></h2>
                    </div>
                    <div>
                        <p class="mb-0"><i class="bi bi-geo-alt-fill"></i>&nbsp;&nbsp;{{ $store.state.house.address }}</p>
                        <p class="mb-0"><i class="bi bi-calendar2-date"></i>&nbsp;&nbsp;건축년도 : {{ $store.state.house.buildYear }}</p>
                    </div>
                </div>
                <button @click="$store.commit('SET_HOUSE_SHOW_MAP', false)" type="button" class="btn-close position-absolute" data-bs-dismiss="modal" aria-label="Close"><i data-feather="x"></i></button>
            </div><!-- end of .modal-header -->
            <div class="modal-body">
                <div class="house-content d-flex mb-4">
                    <div class="w-50" style="margin-right: 10px;">
                        <p class="mb-1">총 <span class="text-primary">{{ dealList.length }}</span> 건</p>
                        <div class="th-wrapper">
                            <table class="table mb-0 text-center" style="margin-right:7px;">
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
                                <tbody id="tbody-dealList">
                                    <tr style="cursor:pointer" v-for="(deal, index) in dealList" :key="index">
                                        <td>{{ deal.no }}</td>
                                        <td>{{ deal.area }}</td>
                                        <td>{{ deal.floor }}</td>
                                        <td>{{ deal.dealAmount }}만 원</td>
                                        <td>{{ deal.dealYear }}-{{ deal.dealMonth | setDate }}-{{ deal.dealDay | setDate }}</td>
                                        <td>
                                            <a><i class="bi bi-star" v-if="!deal.bookmark"></i></a>
                                            <a><i class="bi bi-star-fill" v-if="deal.bookmark"></i></a>
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
                                <div>
                                    <button class="btn rounded-pill btn-outline-success" @click="btnToggle">학교</button>
                                    <button class="btn rounded-pill btn-outline-danger" @click="btnToggle">카페</button>
                                    <button class="btn rounded-pill btn-outline-warning" @click="btnToggle">버스정류장</button>
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
                            <a class="nav-link active" data-bs-toggle="tab" data-bs-target="#school" role="tab" aria-controls="school" aria-selected="true">학군정보</a>
                        </li>
                        <li class="nav-item" role="presentation">
                            <a class="nav-link" data-bs-toggle="tab" data-bs-target="#transport" role="tab" aria-controls="transport" aria-selected="false">대중교통</a>
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
                    </div>
                </div><!-- end of .surrounding -->

            </div><!-- end of .modal-body -->
        </div><!-- end of .modal-content -->
    </div><!-- end of .modal-dialog -->
</div><!-- end of #modal-housedeal-->

</template>

<script>
export default {
    name: 'HouseDealModal',

    data() {
        return {
            map: null,
            center: null,
            markerPositions: [],
            markers: [],
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
                "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=cb9b2686378a8d564494657ed1378fb9";
            document.head.appendChild(script);
        }
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
                image: new kakao.maps.MarkerImage("assets/images/markers/marker_house.png", new kakao.maps.Size(24, 35))
	        });

            customOverlay.setMap(this.map);
        },
        getCenter(){
            this.center = new kakao.maps.LatLng(this.$store.state.house.lat, this.$store.state.house.lng)
        },
        setCenter(){
            this.map.panTo(this.center);
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
        displayMarker(markerPositions) {
            if (this.markers.length > 0) {
                this.markers.forEach((marker) => marker.setMap(null));
            }

            const positions = markerPositions.map(
                (position) => new kakao.maps.LatLng(...position)
            );

            if (positions.length > 0) {
                this.markers = positions.map(
                (position) =>
                    new kakao.maps.Marker({
                    map: this.map,
                    position,
                    })
                );

                const bounds = positions.reduce(
                (bounds, latlng) => bounds.extend(latlng),
                new kakao.maps.LatLngBounds()
                );

                this.map.setBounds(bounds);
            }
        },
        displayInfoWindow() {
            if (this.infowindow && this.infowindow.getMap()) {
                //이미 생성한 인포윈도우가 있기 때문에 지도 중심좌표를 인포윈도우 좌표로 이동시킨다.
                this.map.setCenter(this.infowindow.getPosition());
                return;
            }

            var iwContent = '<div style="padding:5px;">Hello World!</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
                iwPosition = new kakao.maps.LatLng(33.450701, 126.570667), //인포윈도우 표시 위치입니다
                iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

            this.infowindow = new kakao.maps.InfoWindow({
                map: this.map, // 인포윈도우가 표시될 지도
                position: iwPosition,
                content: iwContent,
                removable: iwRemoveable,
            });

            this.map.setCenter(iwPosition);
        },
        
        getSchoolList(){
            this.$store.dispatch("schoolList");
        },
        
        getBusStopList(){
            this.$store.dispatch("busStopList");
        },
        btnToggle(e){
            e.target.classList.toggle("active");
            this.setBlur(e);
        },
        setBlur(e){
            e.target.blur();
        }
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
    .scroll-wrapper::-webkit-scrollbar, .modal-body::-webkit-scrollbar{width: 8px;}
    .scroll-wrapper::-webkit-scrollbar-thumb, .modal-body::-webkit-scrollbar-thumb{background-color: #ccc; border-radius: .5rem;}
    .scroll-wrapper::-webkit-scrollbar-track, .modal-body::-webkit-scrollbar-track{padding-left: 10px;}
    
    tr td{overflow: hidden; text-overflow: ellipsis; white-space: nowrap;}
    tr td:first-child, tr th:first-child{width: 14%;}
    tr td:nth-child(2), tr th:nth-child(2){width: 17%;}
    tr td:nth-child(3), tr th:nth-child(3){width: 10%;}
    tr td:nth-child(4), tr th:nth-child(4){width: 23%;}
    tr td:nth-child(5), tr th:nth-child(5){width: 17%;}
    tr td:nth-child(6), tr th:nth-child(6){width: 15%;}


    .btn-box button{font-size:14px;}
    .btn-box div .btn{margin-left: 5px;}

    ul{list-style: none; padding-left: 0;}
    span.badge{width: 70px; margin-right: 10px;}
</style>