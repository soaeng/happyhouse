<template>

<div id="houseDealModal" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-xl">
        <div class="modal-content house-detail">
            <div class="modal-title">
              <div class="d-flex justify-content-end">
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
    </div>
                <div class="house-title">
                  <p class="d-none" id="house-houseNo"></p>
                    <h3 class="house-name"></h3>
                    <div class="house-info d-flex justify-content-between">
                    <div class="deal-info d-flex">
                      <p>총 <span id="total-data-detail"></span> 건</p>
                    </div>
                    <div class="d-none"	>
                      <p class="house-code"></p>
                      <p class="house-dong"></p>
                    </div>
                      <div>
                          <p>
                              <i class="fa-solid fa-map-location-dot fa-sm"></i><span class="house-addr"></span>
                          </p>
                          <p>건축년도 : <span class="house-buildYear"></span></p>
                      </div>
                    </div><!-- end of .house-info -->
                </div><!-- end of .house-title -->
            </div><!--  end of .modal-title -->
            <div class="modal-body">
                <div class="house-content">
                    <div class="house-inner">
                    
                        <table class="table">
                            <colgroup>
                                <col width="60px">
                                <col width="100px">
                                <col width="50px">
                                <col width="155px">
                                <col width="105px">
                            </colgroup>
                            <thead>
                                <tr>
                                    <th class="th-no">번호</th>
                                    <th class="th-area">면적(㎡)</th>
                                    <th class="th-floor">층</th>
                                    <th class="th-dealAmount">거래금액</th>
                                    <th class="th-dealDate">거래일시</th>
                                </tr>
                            </thead>
                        </table>
                        <div class="deal-list d-flex flex-column justify-content-between">
                            <table class="table">
                                <colgroup>
                                    <col width="60px">
                                    <col width="100px">
                                    <col width="50px">
                                    <col width="155px">
                                    <col width="105px">
                                </colgroup>
                                <tbody id="tbody-dealList">
                                    <tr>
                                        <td class="td-no"></td>
                                        <td class="td-area"></td>
                                        <td class="td-floor"></td>
                                        <td class="td-dealAmount"></td>
                                        <td class="td-dealDate"></td>
                                    </tr>
                                </tbody>
                            </table>
                            
                        </div><!-- end of .deal-list -->
                    </div><!-- end of .house-inner -->
                    <div class="house-map">
                        <iframe id="map-box"></iframe>
                    </div><!-- end of .house-map -->
                </div><!-- end of .house-content -->
    
    <!-- 상권 정보 ....... .. 기타 정보 들어갈 곳 -->
    
                <div class="surrounding">
                    <div class="surrounding-inner">
                        <div class="surrounding-title">
                          <ul class="nav nav-tabs" id="surroundingTab" role="tablist">
              <li class="nav-item" role="presentation">
                <button class="nav-link active" id="tab-info" data-bs-toggle="tab" data-bs-target="#info" type="button" role="tab" aria-controls="info" aria-selected="true">주변시설</button>
              </li>
              <li class="nav-item" role="presentation">
                <button class="nav-link" id="tab-transport" data-bs-toggle="tab" data-bs-target="#transport" type="button" role="tab" aria-controls="transport" aria-selected="false">대중교통</button>
              </li>
          </ul>
        </div>
        <div class="tab-content" id="surroundingTabContent">
          <div class="tab-pane fade show active" id="info" role="tabpanel" aria-labelledby="tab-info">
              <div class="surrounding-body">
                                <h5 id="school-title"></h5>
                                <ul id="school-list"></ul>
                            </div><!-- end of .surrounding-body-->
                            
                            <div class="surrounding-body">
                                <h5 id="cafe-title"></h5>
                                <ul id="cafe-list"></ul>
                            </div><!-- end of .surrounding-body-->
          </div>
          <div class="tab-pane fade" id="transport" role="tabpanel" aria-labelledby="tab-transport">
              <div class="surrounding-body">
                                <h5 id="bus-title"></h5>
                                <ul id="bus-list"></ul>
                            </div><!-- end of .surrounding-body-->
          </div>
        </div>
                        

                    </div><!-- end of .surrounding-surrounding-inner -->
                </div><!-- end of .surrounding -->

    
            </div><!-- end of .modal-body -->

        </div><!-- end of .modal-content -->
    </div><!-- end of .modal-dialog -->
</div><!-- end of #modal-housedeal-->

</template>

<script>
export default {
  name: 'VueCliHouseDetail',

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

<style lang="scss" scoped>

</style>