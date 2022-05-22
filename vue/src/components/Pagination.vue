<template>
<!-- a tag href="#" 제외!!
route url 이 board 에서 / 로 변경되는 문제 발생 -->
  <nav aria-label="Page navigation">
    <ul class="pagination justify-content-center">
      <li v-if="prev" class="page-item">
        <a class="page-link" href="#" aria-label="Previous" @click="paginationChanged(startPageIndex - 1)">
          <span aria-hidden="true">«</span>
        </a>
      </li>
      <li v-for="index in ( endPageIndex-startPageIndex + 1 )" :key="index"
          v-bind:class="{active: (startPageIndex + index - 1 == currentPageIndex)}" class="page-item number">
        <a @click="paginationChanged(startPageIndex + index - 1)" 
          class="page-link" href="#">{{ startPageIndex + index - 1 }}</a> <!-- href 는 그대로, 커서 모양 유지-->
      </li>
      <li v-if="next" class="page-item">
        <a class="page-link" href="#" aria-label="Next" @click="paginationChanged(endPageIndex + 1)">
          <span aria-hidden="true">»</span>
        </a>
      </li>
    </ul>
  </nav>
</template>


<script>
export default {
  name: 'Pagination',
  // props 사용 X
  // props: ['listRowCount', 'pageLinkCount', 'currentPageIndex', 'totalListItemCount'],
  data(){
    return {
      target: this.targetObj
    }
  },
  props: {targetObj: String,},
  computed: {
    pageCount: function(){
      if(this.target == "house") return this.$store.getters.getHousePageCount;
      else return this.$store.getters.getPageCount;
    },
    startPageIndex: function(){
      if(this.target == "house") return this.$store.getters.getHouseStartPageIndex;
      else return this.$store.getters.getStartPageIndex;
    },
    endPageIndex: function(){
      if(this.target == "house") return this.$store.getters.getHouseEndPageIndex;
      else return this.$store.getters.getEndPageIndex;
    },
    prev: function(){
      if(this.target == "house") return this.$store.getters.getHousePrev;
      else return this.$store.getters.getPrev;
    },
    next: function(){
      if(this.target == "house") return this.$store.getters.getHouseNext;
      else return this.$store.getters.getNext;
    },
    currentPageIndex: function(){
      if(this.target == "house") return this.$store.state.house.currentPageIndex;
      else return this.$store.state.board.currentPageIndex;
    },
  },
  methods:{
    // 부모에게 event 전달
    paginationChanged(pageIndex){
      console.log("paginationVue : paginationChanged : pageIndex : " + pageIndex );
      this.$emit('call-parent', pageIndex);
    }
  },

}
</script>

<style scoped>
li.number:first-child{margin-right: 0;}
li.number:last-child{margin-left: 0;}
</style>
