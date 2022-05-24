<template>
<div id="main">
    <header class="mb-3">
        <a href="#" class="burger-btn d-block d-xl-none" @click="sidebarToggle">
            <i class="bi bi-justify fs-3"></i>
        </a>
    </header>
    <router-view></router-view>
</div><!-- end of main -->
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