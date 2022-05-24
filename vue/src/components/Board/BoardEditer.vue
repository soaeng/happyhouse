<template>
    <section class="section">
        <div class="card">
            <div class="card-header">
                <input type="text" class="form-control" v-model="title" name="insertTitle" placeholder="제목을 입력하세요.">
            </div>
            <div class="card-content">
                <div class="card-body pt-0">
                    <div id="editor"></div>
                    <div class="mt-4">
                        <div class="input-group">
                            <label class="input-group-text" for="inputFileUploadInsert"><i class="bi bi-upload"></i></label>
                            <input @change="changeFile" type="file" class="form-control" id="inputFileUploadInsert" multiple>
                        </div>
                        <div class="thumbnail-wrapper d-flex mb-4 mt-4">
                            <div>
                                <img v-for="(file, index) in fileList" v-bind:src="file" v-bind:key="index">
                            </div>
                        </div>
                    </div>
                    <div class="text-end">
                        <button @click="boardInsert" class="btn btn-primary">등록</button>
                        <router-link to="/board" class="btn btn-secondary" style="margin-left: 10px;">취소</router-link>
                    </div>
                </div><!-- end of .card-body -->
            </div><!-- end of .card-content -->
        </div><!-- end of .card -->
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
        changeFile(fileEvent) {
            this.fileList = [];
            const fileArray = Array.from(fileEvent.target.files);
            fileArray.forEach( file => {
                this.fileList.push(URL.createObjectURL(file));
            });
        },
        doLogout() { 
            this.$store.commit("SET_LOGIN", { isLogin: false, userName: "", userProfileImageUrl: "" });
            this.$router.push("/login");
        },
        async boardInsert(){
            let formData = new FormData();
            formData.append("title", this.title);
            formData.append("content", this.CKEditor.getData());

            // file upload
            let attachFiles = document.querySelector("#inputFileUploadInsert").files;
            console.log(attachFiles);


            if( attachFiles.length > 0 ) {
                const fileArray = Array.from(attachFiles);
                    fileArray.forEach( file => formData.append("file", file) )
                }

                let options = { 
                headers: { 'Content-Type': 'multipart/form-data' }
            }

            try{
                let {data} = await http.post('/boards', formData, options);
                if( data.result == 'login' ){
                    this.doLogout();
                }else{
                    console.log(">>>>>> boardInsert");
                    console.log(data);
                    this.$alertify.success('글이 등록되었습니다.');
                    this.$router.push("/board");
                }
            } catch(error){
                console.log("InsertModalVue: error ");
                console.error(error);
            }
        },
    },
    async mounted() {
        try{
            this.CKEditor = await ClassicEditor.create( document.querySelector("#editor"));
        }catch(error){
            console.error(error);
        }
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