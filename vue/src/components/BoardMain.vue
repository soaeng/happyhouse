<template>
<div id="main">
    <header class="mb-3">
        <a href="#" class="burger-btn d-block d-xl-none">
            <i class="bi bi-justify fs-3"></i>
        </a>
    </header>

    <div class="page-heading">
        <div class="page-title">
            <div class="row">
                <div class="col-12 col-md-6 order-md-1 order-last">
                    <h3>공지사항</h3>
                    <p class="text-subtitle text-muted"></p>
                </div>
                <div class="col-12 col-md-6 order-md-2 order-first">
                    <nav aria-label="breadcrumb" class="breadcrumb-header float-start float-lg-end">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item">
                                <router-link to="/house" class='sidebar-link'>Happy House</router-link>
                            </li>
                            <li class="breadcrumb-item active" aria-current="page">공지사항</li>
                        </ol>
                    </nav>
                </div>
            </div>
        </div>
        <router-view></router-view>
    </div>
</div>
</template>

<script>
export default {
    name: 'BoardMain',

    data() {
        return {
            
        };
    },
    
    mounted() {
        
    },

    methods: {

    },

};
</script>

<style scoped>
.board-search {display:flex; justify-content: space-between; margin: 40px 0 10px;}
.board-search .board-info{display: flex; align-items: flex-end;}
.board-search .board-info p{margin: 0 10px 0 0; color: #555; font-size: 13px;}
.board-search form{display: flex; align-items: center; justify-content: center; height: 100%;}
.board-search .form-select{font-size:14px; width: 90px; margin-right: 10px; padding: 5px 8px;}
.board-search input[type="text"]{font-size:14px; width: 250px; margin-right: 10px; padding: 5px 8px;}
.board-search button{font-size:14px; padding: 5px 8px; border: 1px solid #ced4da; color: #555;}
.board-search button:hover{background-color: #F5F5F5;}
</style>