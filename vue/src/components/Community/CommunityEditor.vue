<template>
    <section class="section">
        <div class="card">
            <div class="card-header">
                <input type="text" class="form-control" v-model="title" name="insertTitle" placeholder="제목을 입력하세요.">
            </div>
            <div class="card-content">
                <div class="card-body pt-0">
                    <div id="communityEditor"></div>
                    <div class="mt-4">
                        <div class="input-group">
                            <label class="input-group-text" for="inputFileUploadInsert"><i class="bi bi-upload"></i></label>
                            <input @change="changeFile" type="file" class="form-control" id="inputFileUploadInsert" multiple>
                        </div>
                        <div class="thumbnail-wrapper d-flex flex-column mb-4 mt-4">
                            <div v-for="(file, index) in FILE" :key="index">
                                <div class="card-content d-flex align-items-baseline">
                                    <img v-if="file.fileTypeList[index] == 'image'" :src="file.fileList[index]" style="margin-right: .5rem;"/>
                                    <i v-else class="bi bi-file-earmark-text-fill"></i>
                                    <p class="card-text">{{file.fileNameList[index] }}</p>
                                    <!-- <a href=""><i class="bi bi-x-circle-fill text-secondary"></i></a> -->
                                </div>
                            </div>
                        </div>
                        
                    </div>
                    <div class="text-end">
                        <button @click="communityInsert" class="btn btn-primary">등록</button>
                        <router-link to="/comm" class="btn btn-secondary" style="margin-left: 10px;">취소</router-link>
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
import Swal from "vue-sweetalert2"
Vue.use(Swal);
Vue.use(CKEditor)

import http from "@/common/axios.js";

export default {
    name: 'CommunityEditor',

    data() {
        return {
            title: '',
            CKEditor: '',
            attachFile: false,
            fileList: [],
            fileTypeList: [],
            fileNameList:[],
            FILE: [],
        };
    },
    methods: {
        changeFile(fileEvent) {
            console.log(">>>>>>>>>>>>>>>>> changeFile")
            const fileArray = Array.from(fileEvent.target.files);
            this.fileList = [];
            fileArray.forEach((file) => {
                this.fileList.push(URL.createObjectURL(file)); // push : array 에 항목 추가
                this.fileTypeList.push(file.type.split('/')[0]);
                this.fileNameList.push(file.name);
                this.FILE.push({fileList: this.fileList, fileTypeList: this.fileTypeList, fileNameList: this.fileNameList});
            });
            console.log(this.FILE);
        },
        doLogout() { 
            this.$store.commit("SET_LOGIN", { isLogin: false, userName: "", userProfileImageUrl: "" });
            this.$router.push("/login");
        },
        async communityInsert(){
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
                let {data} = await http.post('/community', formData, options);
                if( data.result == 'login' ){
                    this.doLogout();
                }else{
                    console.log(">>>>>> communityInsert");
                    console.log(data);
                    this.$swal.fire({
                        icon: 'success',
                        title: 'Success',
                        text: '게시글이 등록되었습니다.',
                        showConfirmButton: false,
                        timer: 1200,
                    });
                    this.$router.push("/comm");
                }
            } catch(error){
                console.log("Community Insert: error ");
                console.error(error);
            }
        },
    },
    async mounted() {
        try{
            this.CKEditor = await ClassicEditor.create( document.querySelector("#communityEditor"));
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

.bi.bi-file-earmark-text-fill::before{margin-top: .35rem;}
.bi.bi-file-earmark-text-fill{margin-right: .5rem;}
.bi.bi-x-circle-fill::before{margin-top: .4rem; margin-left: .5rem;}
</style>