<template>
    <main class="page-heading">
        <div class="page-title">
            <div class="row">
                <div class="col-12 col-md-6 order-md-1 order-last">
                    <h3><i class="bi bi-bookmark-heart-fill"></i>관심 거래 목록</h3>
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
                    <h4 class="card-title">Default Layout</h4>
                </div>
                <div class="card-body">
                    Lorem ipsum dolor sit amet consectetur adipisicing elit. Magnam, commodi? Ullam quaerat
                    similique iusto
                    temporibus, vero aliquam praesentium, odit deserunt eaque nihil saepe hic deleniti? Placeat
                    delectus
                    quibusdam ratione ullam!
                </div>
            </div>
        </section>
    </main>
</template>

<script>
export default {
    name: 'Bookmark',

    data() {
        return {
            
        };
    },

    mounted() {
    },

    methods: {
        sidebarToggle(){
            document.getElementById('sidebar').classList.toggle('active');
        }
    },
};
</script>

<style scoped></style>