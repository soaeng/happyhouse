<template>
    <section class="section">
        <div class="card">
            <div class="card-header">
                <input type="text" class="form-control" v-model="storeTitle">
            </div>
            <div class="card-content">
                <div class="card-body pt-0">
                    <div id="divEditorUpdate"></div>
                    <div v-if="$store.state.community.fileList.length > 0" class="mb-4 mt-4">
                        <p class="mb-3 fw-bold">첨부파일</p>
                        <ul class="text-sm" v-for="(file, index) in $store.state.community.fileList" :key="index" style="padding-left: 0;">
                            <li class="list-unstyled">
                                <span class="bg-light-secondary rounded" style="padding: .3rem .6rem">
                                    <i class="bi bi-file-earmark-text"></i>{{ file.fileName }}
                                </span>
                            </li>
                        </ul>
                        <hr>
                    </div>
                    <div class="form-check mt-3 mb-3">
                        <input v-model="attachFile" class="form-check-input" type="checkbox" value="" id="chkFileUploadUpdate" />
                        <label class="form-check-label" for="chkFileUploadUpdate">파일 변경</label>
                    </div>

                    <div class="mb-3" v-show="attachFile" id="imgFileUploadUpdateWrapper">
                        <div class="input-group">
                            <label class="input-group-text" for="inputFileUploadInsert"><i class="bi bi-upload"></i></label>
                            <input @change="changeFile" type="file" class="form-control" id="inputFileUploadUpdate" multiple>
                        </div><!-- end of .input-group -->
                        <div id="imgFileUploadUpdateThumbnail" class="thumbnail-wrapper d-flex flex-column mt-3">
                            <div v-for="(file, index) in FILE" :key="index" >
                                <div class="card-content d-flex align-items-baseline">
                                    <img v-if="file.fileTypeList[index] == 'image'" :src="file.fileList[index]" style="margin-right: .5rem;"/>
                                    <i v-else class="bi bi-file-earmark-text-fill"></i>
                                    <p class="card-text">{{file.fileNameList[index] }}</p>
                                    <!-- <a><i class="bi bi-x-circle-fill text-secondary"></i></a> -->
                                </div>
                            </div>
                        </div>
                    </div><!-- end of #imgFileUploadUpdateWrapper -->
                    <div class="text-end">
                        <button @click="communityUpdate" class="btn btn-primary">수정</button>
                        <router-link to="/comm/detail" class="btn btn-secondary" style="margin-left: 10px;">취소</router-link>
                    </div>
                </div><!-- end of .card-body -->
            </div><!-- end of .card-content -->
        </div><!-- end of .card -->
    </section>
</template>

<script>
import Vue from "vue";
import CKEditor from "@ckeditor/ckeditor5-vue2";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import VueAlertify from "vue-alertify";

Vue.use(CKEditor).use(VueAlertify);

import http from "@/common/axios.js";

export default {
    name: 'CommunityUpdate',

    data() {
        return {
            CKEditor: "",
            attachFile: false,
            fileList: [],
            fileTypeList: [],
            fileNameList:[],
            FILE: [],
        };
    },
    computed: {
        storeTitle: {
            get() {
                return this.$store.state.community.title;
            },
            set(title) {
                this.$store.commit("SET_COMMUNITY_TITLE", title);
            },
        },
    },
    async mounted() {
        try {
            this.CKEditor = await ClassicEditor.create(document.querySelector("#divEditorUpdate"));
            this.initUI();
        } catch (error) {
            console.error(error);
        }
    },
    created() {
    },

    methods: {
        // modal 초기화
        initUI() {
            this.CKEditor.setData(this.$store.state.community.content);
            this.attachFile = false;
            this.fileList = [];
            document.querySelector("#inputFileUploadUpdate").value = "";
        },
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
        // 굳이 actions 에 있을 필요 없다. backend async 작업이지만, 그 결과로 store 를 변경하는 내용이 없다.
        async communityUpdate() {
            // post form data
            let formData = new FormData();
            formData.append("boardId", this.$store.state.community.boardId); // update 에 추가
            formData.append("title", this.$store.state.community.title);
            formData.append("content", this.CKEditor.getData()); // store X !!!!

            console.log(">>>>>> communityUpdate: ");
            console.log(document.querySelector("chkFileUploadUpdate"));
            
            let attachFiles = document.querySelector("#inputFileUploadUpdate").files;

                if (attachFiles.length > 0) {
                    const fileArray = Array.from(attachFiles);
                    fileArray.forEach((file) => formData.append("file", file));
                }

            let options = { headers: { "Content-Type": "multipart/form-data" },};

            try {
                let { data } = await http.post("/community/" + this.$store.state.community.boardId, formData, options);

                if (data.result == "login") {
                this.doLogout();
                } else {
                this.$alertify.success("글이 수정되었습니다.");
                this.$router.push("/comm")
                }
            } catch (error) {
                console.log("UpdateModalVue: error ");
                console.log(error);
            }
        },
        sidebarToggle(){
            document.getElementById('sidebar').classList.toggle('active');
        },
        sidebarHide(){
            document.getElementById('sidebar').classList.remove('active');
        },
        doLogout() { // 기존 값 다 초기화 작업 하고 login 페이지
        this.$store.commit("SET_LOGIN", { isLogin: false, userName: "", userProfileImageUrl: "" });
        this.$router.push("/login");
        },
    },
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

.bi.bi-file-earmark-text::before{margin-top: .2rem; margin-right: .5rem;}
.bi.bi-file-earmark-text-fill::before{margin-top: .35rem;}
.bi.bi-file-earmark-text-fill{margin-right: .5rem;}
.bi.bi-x-circle-fill::before{margin-top: .4rem; margin-left: .5rem;}
</style>