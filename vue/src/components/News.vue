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
                    <h3><i class="bi bi-newspaper"></i>주요 뉴스</h3>
                    <p class="text-subtitle text-muted"></p>
                </div>
            </div>
        </div>
      <div class="card">
        <div class="card-content">
            <div class="card-body">
              <ul class="list-group">
                <li style="cursor:pointer; list-style: none;" v-for="(news, index) in newsList" :key="index" class="rounded-3 mb-3">
                  <a :href="news.url" target="_blank" class="list-group-item list-group-item-action d-flex" style=" padding: 1rem;">
                      <div style="margin-right: 1rem; width: 260px;" class="d-flex">
                        <img class="w-100" :src="news.img" style="max-height: 112px; object-fit: cover;">
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
              </ul>
            </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
export default {
  name: 'News',

  data() {
    return {
      
    };
  },

  computed: {
    newsList(){
      return this.$store.getters.getNewsList;
    },
  },

  mounted() {
    this.getNewsList();
  },

  methods: {
    sidebarToggle(){
        document.getElementById('sidebar').classList.toggle('active');
    },
    async getNewsList() {
      this.$store.dispatch("newsList");
    },
  },
};
</script>

<style lang="scss" scoped>

</style>