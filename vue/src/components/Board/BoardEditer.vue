<template>
    <section class="section">
        <div class="row">
            <div class="col-12">
                <div class="card">
                    <div class="card-header">
                        <input type="text" class="form-control mb-3 mt-3" v-model="title" name="insertTitle" placeholder="제목을 입력하세요.">                            </div>
                    <div class="card-body">
                        <div id="editor"></div>

                        <div class="mb-3 mt-3" id="imgFileUploadInsertWrapper">
                            <input @change="changeFile" type="file" id="inputFileUploadInsert" class="form-control mb-2" multiple>
                            <div id="imgFileUploadInsertThumbnail" class="thumbnail-wrapper d-flex">
                            <div style="margin-right: 20px">
                                <img v-for="(file, index) in fileList" v-bind:src="file" v-bind:key="index">
                            </div>
                            </div>
                        </div>
                        <div class="btn-box">
                            <button @click="boardInsert" class="btn btn-primary">등록</button>
                            <router-link to="/board">
                                <button class="btn btn-secondary">취소</button>
                            </router-link>
                        </div>
                    </div>

                    

                </div>
            </div>
        </div>
    </section>
</template>

<script>
import Vue from 'vue';
import CKEditor from '@ckeditor/ckeditor5-vue2';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import VueAlertify from 'vue-alertify'; 

Vue.use(CKEditor).use(VueAlertify);

import http from "@/common/axios.js";

export default {
    name: 'BoardEditor',

    data() {
        return {
        title: '',
        CKEditor: '',
        attachFile: false,
        fileList: []
        };
    },
    methods: {
      // modal 초기화
        // initUI(){
        //     this.title = '';
        //     this.CKEditor.setData('');
        //     this.attachFile = false;
        //     this.fileList = [];
        //     document.querySelector("#inputFileUploadInsert").value = '';
        // },
        changeFile(fileEvent) {
            this.fileList = []; // thumbnail 초기화

            const fileArray = Array.from(fileEvent.target.files);
            fileArray.forEach( file => {
                this.fileList.push(URL.createObjectURL(file)); // push : array 에 항목 추가
            });
        },
        // 굳이 actions 에 있을 필요 없다. backend async 작업이지만, 그 결과로 store 를 변경하는 내용이 없다.
        async boardInsert(){
            let formData = new FormData();
            formData.append("title", this.title);
            formData.append("content", this.CKEditor.getData());

            // file upload
            let attachFiles = document.querySelector("#inputFileUploadInsert").files;

            if( attachFiles.length > 0 ) {
            const fileArray = Array.from(attachFiles);
            fileArray.forEach( file => formData.append("file", file) )
            }

            let options = { 
            headers: { 'Content-Type': 'multipart/form-data' }
            }

            try{
            let {data} = await http.post('/boards', formData, options);

            console.log("InsertModalVue: data : ");
            console.log(data);
            if( data.result == 'login' ){
                this.doLogout();
            }else{
                this.$alertify.success('글이 등록되었습니다.');
                this.$router.push("/board");
            }

            }catch(error){
            console.log("InsertModalVue: error ");
            console.log(error);
            }
        },
        // logout 처리 별도 method
        doLogout() { // 기존 값 다 초기화 작업 하고 login 페이지
            this.$store.commit("SET_LOGIN", { isLogin: false, userName: "", userProfileImageUrl: "" });
            this.$router.push("/login");
        },
    },
    async mounted() {
        try{
        this.CKEditor = await ClassicEditor.create( document.querySelector("#editor"));
        }catch(error){
        console.error(error);
        }

        // bootstrap modal show event hook
        // InsertModal 이 보일 때 초기화
        // let $this = this;
        // this.$el.addEventListener('show.bs.modal', function () {
        // $this.initUI();
        // })
    }
};


</script>

<style scoped>
.card >>> .ck-editor__editable {
    min-height: 500px !important;
}

.card >>> .thumbnail-wrapper{
    margin-top: 5px;
}

.card >>> .thumbnail-wrapper img {
    width: 100px !important;
    margin-right: 5px;
    max-width: 100%;
}
</style>