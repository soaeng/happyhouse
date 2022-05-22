<template>
<div>
    <main class="page-heading">
        <div class="page-title">
            <div class="row">
                <div class="col-12 col-md-6 order-md-1 order-last">
                    <h3><i class="bi bi-bookmark-heart-fill"></i>관심 거래 목록</h3>
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
                <div class="card-body">
                    <table class="table table-md table-hover mb-0 text-center">
                        <colgroup>
                            <col width="10%">
                            <col width="27%">
                            <col width="12%">
                            <col width="10%">
                            <col width="15%">
                            <col width="14%">
                            <col width="12%">
                        </colgroup>
                        <thead class="bg-primary text-white">
                            <tr>
                                <th>번호</th>
                                <th>아파트명</th>
                                <th>면적(㎡)</th>
                                <th>층</th>
                                <th>거래금액</th>
                                <th>거래일시</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody v-if="bookmarkDealList.length > 0">
                            <tr v-for="(deal, index) in bookmarkDealList" :key="index">
                                <td>{{ deal.no }}</td>
                                <td style="cursor:pointer" @click="setHouseNo(deal.houseNo)">{{ deal.AptName }}</td>
                                <td>{{ deal.area }}</td>
                                <td>{{ deal.floor }}</td>
                                <td>{{ deal.dealAmount }}만 원</td>
                                <td>{{ deal.dealYear }}-{{ deal.dealMonth | setDate }}-{{ deal.dealDay | setDate }}</td>
                                <td>
                                    <a style="cursor:pointer"  class="text-danger" @click="removeBookmarkDeal">
                                        <i class="bi bi-dash-circle-fill" :data-no="deal.no"></i>
                                    </a>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <div v-if="bookmarkDealList.length == 0" class="w-100 text-center" style="border-bottom: 1px solid #DEDEDE;">
                        <p class="pt-5 pb-5 text-sm mb-0">등록된 관심거래가 없습니다.</p>
                    </div>
                </div><!-- end of .card-body -->
            </div><!-- end of .card -->
        </section>
    </main>
    
    <house-deal-modal></house-deal-modal>
</div>
</template>

<script>

import http from "@/common/axios.js";
import houseDealModal from "../modals/HouseDealModal.vue";
import { Modal } from "bootstrap";

export default {
    name: 'BookmarkDeal',
    components: {houseDealModal},
    data() {
        return {
            houseDealModal: null,
        };
    },

    computed: {
        bookmarkDealList(){
            return this.$store.state.house.bookmarkDealList;
        },
        
        userSeq(){
            return this.$store.state.login.userSeq;
        }
    },

    mounted() {
        this.getBookmarkDealList();
        
        this.houseDealModal = new Modal(document.getElementById("houseDealModal"));
    },

    methods: {
        sidebarToggle(){
            document.getElementById('sidebar').classList.toggle('active');
        },

        getBookmarkDealList(){
            this.$store.dispatch("bookmarkDealList");
        },
        
        async removeBookmarkDeal(e){
            let dealNo = e.target.dataset.no;
            let userSeq = this.userSeq;
            console.log(e.target);

            try{
                console.log(dealNo);
                console.log(userSeq);

                let data = data = await http.delete('/bookmark/deal?dealNo=' + dealNo + '&userSeq=' + userSeq);
                
                if( data.result == 'login' ){
                    this.doLogout();
                }else{
                    this.getBookmarkDealList();
                }

            } catch(error){
                console.log("removeBookmarkDeal: error ");
                console.log(error);
            }
        },

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
    },
    
    filters: {
        setDate: function(date) {return date.length < 2 ? "0" + date : date;}
    },
};
</script>

<style scoped>
.bi.bi-dash-circle-fill::before{font-size: 22px;}
</style>