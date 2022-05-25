import Vue from "vue"
import Vuex from "vuex"

Vue.use(Vuex);

import http from "@/common/axios.js";
import util from "@/common/util.js";

import router from "@/routers/routers.js";

export default new Vuex.Store({
  state: {
    // login, NavBar
    ///////////////////////////////////////////////////////////////////////// state - LOGIN
    login: {
        // NavBar
      isLogin: true,

      userName: "",
      userProfileImageUrl: "",
      // Login
      userEmail: "dskim@my.com",
      userPassword: "1234",
    },
    ///////////////////////////////////////////////////////////////////////// state - BOARD
    board: {
      // list
      list: [],
      limit: 10,
      offset: 0,
      type: "",
      keyword: "",

      // pagination
      listRowCount: 10,
      pageLinkCount: 10,
      currentPageIndex: 1,

      totalListItemCount: 0,

      // detail, update, delete

      boardId: 0,
      title: "",
      content: "",
      userName: "",
      regDate: "",
      regTime: "",
      readCount: 0,
      fileList: [],
      sameUser: false,
    },

    ///////////////////////////////////////////////////////////////////////// state - ADDRESS
    address: {
      sidoList: [],
      sido: "0", // 최초 선택 항목 지정
      gugunList: [],
      gugun: "0",
      dongList: [],
      dong: "0",
    },

    ///////////////////////////////////////////////////////////////////////// state - HOUSE
    house: {
      // list
      list: [], // 아파트목록
      limit: 10,
      offset: 0,
      type: "init",
      keyword: "",
      dongCode: "",

      // pagination
      listRowCount: 10,
      pageLinkCount: 10,
      currentPageIndex: 1,
      totalListItemCount: 0,

      // deal
      dealList: [], // 아파트거래목록
      houseNo: 0,
      AptName: "",
      address: "",
      buildYear: "",
      code: "",
      dong: "",
      lat: "",
      lng: "",

      ///////////////////////////////////////////////////////////////////////// state - SURROUNDING
      schoolList: [],
      busStopList: [],
      busStopList: [],
      showMap: false,


      ///////////////////////////////////////////////////////////////////////// state - BOOKMARK
      bookmarkDealList: [],
      bookmarkHouseList: [],
      bookmarkAreaList: [],
    },
    

    ///////////////////////////////////////////////////////////////////////// state - NEWS
    news: {
      list: [],
      city: '', // 시도번호
      dvsn: '', // 구군번호
    },
  },
   // state 상태를 변경하는 유일한 방법
  mutations: {
    ///////////////////////////////////////////////////////////////////////// mutations - LOGIN
    SET_LOGIN(state, payload) { // state는 위에 있는 애, payload는 parameter(객체면 보통 payload)
      state.login.isLogin = payload.isLogin;
      state.login.userName = payload.userName;
      state.login.userProfileImageUrl = payload.userProfileImageUrl;
    },


    ///////////////////////////////////////////////////////////////////////// mutations - BOARD
    SET_BOARD_LIST(state, list) {
      state.board.list = list;
    },
    SET_BOARD_TOTAL_LIST_ITEM_COUNT(state, count) {
      state.board.totalListItemCount = count;
    },
    SET_BOARD_MOVE_PAGE(state, pageIndex) {
      state.board.offset = (pageIndex - 1) * state.board.listRowCount;
      state.board.currentPageIndex = pageIndex;
    },
    SET_BOARD_DETAIL(state, payload) {
      state.board.boardId = payload.boardId;
      state.board.title = payload.title;
      state.board.content = payload.content;
      state.board.userName = payload.userName;
      state.board.regDate = util.makeDateStr(payload.regDt.date.year, payload.regDt.date.month, payload.regDt.date.day, ".");
      state.board.regTime = util.makeTimeStr(payload.regDt.time.hour, payload.regDt.time.minute, payload.regDt.time.second, ":");
      state.board.readCount = payload.readCount;
      state.board.fileList = payload.fileList;
      state.board.sameUser = payload.sameUser;
    },
    // for UpdateModal title v-modal
    SET_BOARD_TITLE(state, title) {
      state.board.title = title;
    },


    ///////////////////////////////////////////////////////////////////////// mutations - ADDRESS
    SET_ADDRESS_SIDOLIST(state, payload) {
      state.address.sidoList = payload;
    },
    SET_ADDRESS_GUGUNLIST(state, payload) {
      state.address.gugunList = payload;
    },
    SET_ADDRESS_DONGLIST(state, payload) {
      state.address.dongList = payload;
    },
    SET_ADDRESS_SIDO(state, payload) {
      state.address.sido = payload;

      state.address.gugun = "0";
      state.address.dong = "0";
      state.address.gugunList = [];
      state.address.dongList = [];
    },
    SET_ADDRESS_GUGUN(state, payload) {
      state.address.gugun = payload;
      state.address.dong = "0";
      state.address.dongList = [];
    },
    SET_ADDRESS_DONG(state, payload) {
      state.address.dong = payload;
    },

    ///////////////////////////////////////////////////////////////////////// mutations - HOUSE
    
    SET_HOUSE_LIST(state, list) {
      state.house.list = list;
    },
    SET_HOUSE_TOTAL_LIST_ITEM_COUNT(state, count) {
      state.house.totalListItemCount = count;
    },
    SET_HOUSE_MOVE_PAGE(state, pageIndex) {
      state.house.offset = (pageIndex - 1) * state.house.listRowCount;
      state.house.currentPageIndex = pageIndex;
    },
    SET_HOUSE_DEAL_LIST(state, list) {
      state.house.dealList = list;
    },
    SET_HOUSE_NO(state, houseNo) {
      state.house.houseNo = houseNo;
    },
    SET_HOUSE_DETAIL(state, payload) {
      state.house.AptName = payload.AptName;
      state.house.address = payload.address;
      state.house.buildYear = payload.buildYear;
      state.house.code = payload.code;
      state.house.dong = payload.dong;
      state.house.lat = payload.lat;
      state.house.lng = payload.lng;
    },
    SET_HOUSE_SHOW_MAP(state, value) {
      state.house.showMap = value;
    },


    ///////////////////////////////////////////////////////////////////////// mutations - SURROUNDING
    SET_SCHOOL_LIST(state, list) {
      state.house.schoolList = list;
    },
    SET_BUSSTOP_LIST(state, list) {
      state.house.busStopList = list;
    },


    ///////////////////////////////////////////////////////////////////////// mutations - BOOKMARK
    SET_BOOKMARK_AREA_LIST(state, list) {
      state.house.bookmarkAreaList = list;
    },
    SET_BOOKMARK_HOUSE_LIST(state, list) {
      state.house.bookmarkHouseList = list;
    },
    SET_BOOKMARK_DEAL_LIST(state, list) {
      state.house.bookmarkDealList = list;
    },

    ///////////////////////////////////////////////////////////////////////// mutations - NEWS
    SET_NEWS_LIST(state, list) {
      state.news.list = list;
    },
    SET_NEWS_CITY(state, city) {
      state.news.city = city;
    },
    SET_NEWS_DVSN(state, dvsn) {
      state.news.dvsn = dvsn;
    },
  },


  // for async method
  actions: {
    ///////////////////////////////////////////////////////////////////////// actions - BOARD
    async boardList(context) {
      let params = { // vuex에서 가져옴
          limit: this.state.board.limit,
          offset: this.state.board.offset,
          type: this.state.board.type,
          keyword: this.state.board.keyword,
      };

      try {
          let { data } = await http.get("/boards", { params }); // params: params shorthand property, let response 도 제거
          console.log("BoardMainVue: data : ");
          console.log(data);
          if (data.result == "login") {
            router.push("/login");
          } else {
            context.commit("SET_BOARD_LIST", data.list); // commit으로 요청하면 mutations에서 처리
            context.commit("SET_BOARD_TOTAL_LIST_ITEM_COUNT", data.count);
          }
      } catch (error) {
          console.error(error);
      }

    },


    ///////////////////////////////////////////////////////////////////////// actions - ADDRESS
    async getSidoList(context) {
      try {
          let { data } = await http.get("/sido");
          context.commit("SET_ADDRESS_SIDOLIST", data);
      } catch (error) {
          console.log(error);
      }
    },
    async getGugunList(context) {
      try {
          let { data } = await http.get("/gugun/" + this.state.address.sido);
          context.commit("SET_ADDRESS_GUGUNLIST", data);
      } catch (error) {
          console.log(error);
      }
    },
    async getDongList(context) {
      try {
          let { data } = await http.get("/dong/" + this.state.address.gugun);
          context.commit("SET_ADDRESS_DONGLIST", data);
      } catch (error) {
          console.log(error);
      }
    },


    ///////////////////////////////////////////////////////////////////////// actions - HOUSE
    async houseList(context) {
      let params = { // vuex에서 가져옴
          limit: this.state.house.limit,
          offset: this.state.house.offset,
          type: this.state.house.type,
          dongCode: this.state.house.dongCode,
          keyword: this.state.house.keyword,
      };
      try {
          let { data } = await http.get("/house/deal", { params }); // params: params shorthand property, let response 도 제거
          console.log("houseList params: ")
          console.log(params)
          console.log(data);
          
          context.commit("SET_HOUSE_LIST", data.list); // commit으로 요청하면 mutations에서 처리
          context.commit("SET_HOUSE_TOTAL_LIST_ITEM_COUNT", data.count);
        
      } catch (error) {
          console.error(error);
      }
    },

    async dealList(context) {
      try {
          let { data } = await http.get("/house/deal/" + this.state.house.houseNo);
          console.log("dealList data: ")
        console.log(data);
        
          context.commit("SET_HOUSE_DEAL_LIST", data.list);
          context.commit("SET_HOUSE_DETAIL", data.list[0]);
          context.commit("SET_HOUSE_SHOW_MAP", true);
      } catch (error) {
          console.error(error);
      }
    },

    async bookmarkAreaList(context) {
      try {
        let { data } = await http.get("/bookmark/area");
        console.log(data);
          console.log("bookmarkAreaList data: ")
          context.commit("SET_BOOKMARK_AREA_LIST", data);
      } catch (error) {
          console.error(error);
      }
    },

    // async bookmarkHouseList(context) {
    //   let params = { 
    //     userSeq: this.state.login.userSeq,
    //   };
    //   try {
    //     let { data } = await http.get("/bookmark/house");
    //       console.log("bookmarkHouseList data: ")
    //       context.commit("SET_BOOKMARK_HOUSE_LIST", data);
    //   } catch (error) {
    //       console.error(error);
    //   }
    // },
    
    async bookmarkDealList(context) {
      try {
        let { data } = await http.get("/bookmark/deal");
          console.log("bookmarkDealList data: ")
          context.commit("SET_BOOKMARK_DEAL_LIST", data);
      } catch (error) {
          console.error(error);
      }
    },
    ///////////////////////////////////////////////////////////////////////// actions - SURROUNDING
    
    async schoolList(context) {
      let params = { // vuex에서 가져옴
        lat: this.state.house.lat,
        lng: this.state.house.lng,
      }
      try {
          let { data } = await http.get("/house/school", {params});
          console.log("schoolList data: ")
          console.log(data);
          context.commit("SET_SCHOOL_LIST", data);
      } catch (error) {
          console.error(error);
      }
    },
    async busStopList(context) {
      let params = { // vuex에서 가져옴
        lat: this.state.house.lat,
        lng: this.state.house.lng,
      }
      try {
          let { data } = await http.get("/house/bus", {params});
          console.log("busStopList data: ")
          console.log(data);
          context.commit("SET_BUSSTOP_LIST", data);
      } catch (error) {
          console.error(error);
      }
    },

    ///////////////////////////////////////////////////////////////////////// actions - BOARD
    async newsList(context) {
      try {
        let { data } = await http.get("/craw/news");
        console.log("newsList data: ");
        console.log(data);
        context.commit("SET_NEWS_LIST", data);
      } catch (error) {
        console.error(error);
      }
    },
    
    async areaNewsList(context) {
      let params = {
        cityCode: this.state.news.city,
        gugunCode: this.state.news.dvsn,
      }
      console.log(this.state.news.city);
      console.log(this.state.news.dvsn);
      try {
        let { data } = await http.get("/craw/news/area", {params});
        console.log("areaNewsList data: ");
        console.log(data);
        context.commit("SET_NEWS_LIST", data);
      } catch (error) {
        console.error(error);
      }
    },
  },


    
    
    
  getters: {
    ///////////////////////////////////////////////////////////////////////// getters - LOGIN
    isLogin: function (state) {
      return state.login.isLogin; // 로그인여부
    },


    ///////////////////////////////////////////////////////////////////////// getters - BOARD
    getBoardList: function (state) {
      return state.board.list; // 게시글 목록
    },

    // pagination
    getPageCount: function (state) {
      return Math.ceil(state.board.totalListItemCount / state.board.listRowCount);
    },
    getStartPageIndex: function (state) {
      if (state.board.currentPageIndex % state.board.pageLinkCount == 0) {
          //10, 20...맨마지막
          return (state.board.currentPageIndex / state.board.pageLinkCount - 1) * state.board.pageLinkCount + 1;
      } else {
          return Math.floor(state.board.currentPageIndex / state.board.pageLinkCount) * state.board.pageLinkCount + 1;
      }
    },
    getEndPageIndex: function (state, getters) {
      let ret = 0;
      if (state.board.currentPageIndex % state.board.pageLinkCount == 0) {
          //10, 20...맨마지막
          ret = (state.board.currentPageIndex / state.board.pageLinkCount - 1) * state.board.pageLinkCount + state.board.pageLinkCount;
      } else {
          ret = Math.floor(state.board.currentPageIndex / state.board.pageLinkCount) * state.board.pageLinkCount + state.board.pageLinkCount;
      }
      // 위 오류나는 코드를 아래와 같이 비교해서 처리
      return ret > getters.getPageCount ? getters.getPageCount : ret;
    },
    getPrev: function (state) {
      if (state.board.currentPageIndex <= state.board.pageLinkCount) {
          return false;
      } else {
          return true;
      }
    },
    getNext: function (state, getters) {
      if (Math.floor(getters.getPageCount / state.board.pageLinkCount) * state.board.pageLinkCount < state.board.currentPageIndex) {
          return false;
      } else {
          return true;
      }
    },


    ///////////////////////////////////////////////////////////////////////// getters - ADDRESS
    getSidoList(state) {
      return state.address.sidoList;
    },
    getGugunList(state) {
      return state.address.gugunList;
    },
    getDongList(state) {
      return state.address.dongList;
    },

    getSido(state) {
      return state.address.sido;
    },
    getGugun(state) {
      return state.address.gugun;
    },
    getDong(state) {
      return state.address.dong;
    },


    ///////////////////////////////////////////////////////////////////////// getters - HOUSE
    getHouseList: function (state) {
      return state.house.list; // 아파트 목록
    },
    getDealList: function (state) {
      return state.house.dealList; // 아파트 거래 목록
    },
    // pagination
    getHousePageCount: function (state) {
      return Math.ceil(state.house.totalListItemCount / state.house.listRowCount);
    },
    getHouseStartPageIndex: function (state) {
      if (state.house.currentPageIndex % state.house.pageLinkCount == 0) {
          return (state.house.currentPageIndex / state.house.pageLinkCount - 1) * state.house.pageLinkCount + 1;
      } else {
          return Math.floor(state.house.currentPageIndex / state.house.pageLinkCount) * state.house.pageLinkCount + 1;
      }
    },
    getHouseEndPageIndex: function (state, getters) {
      let ret = 0;
      if (state.house.currentPageIndex % state.house.pageLinkCount == 0) {
          ret = (state.house.currentPageIndex / state.house.pageLinkCount - 1) * state.house.pageLinkCount + state.house.pageLinkCount;
      } else {
          ret = Math.floor(state.house.currentPageIndex / state.house.pageLinkCount) * state.house.pageLinkCount + state.house.pageLinkCount;
      }
      return ret > getters.getHousePageCount ? getters.getHousePageCount : ret;
    },
    getHousePrev: function (state) {
      if (state.house.currentPageIndex <= state.house.pageLinkCount) {
          return false;
      } else {
          return true;
      }
    },
    getHouseNext: function (state, getters) {
      if (Math.floor(getters.getHousePageCount / state.house.pageLinkCount) * state.house.pageLinkCount < state.house.currentPageIndex) {
          return false;
      } else {
          return true;
      }
    },

    ///////////////////////////////////////////////////////////////////////// getters - SURROUNDING
    getSchoolList: function (state) {
      return state.house.schoolList; // 아파트 거래 목록
    },
    getBusStopList: function (state) {
      return state.house.busStopList; // 아파트 거래 목록
    },

    ///////////////////////////////////////////////////////////////////////// getters - BOOKMARK
    getDealList: function (state) {
      return state.house.dealList; 
    },
    getBookmarkDealList: function (state) {
      return state.house.bookmarkDealList;
    },

    ///////////////////////////////////////////////////////////////////////// getters - NEWS
    getNewsList: function (state) {
      return state.news.list;
    }
  },
});
