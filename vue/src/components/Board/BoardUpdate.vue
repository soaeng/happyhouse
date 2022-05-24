<template>
    <section class="section">
        <div class="card board-update">
            <div class="card-header">
                <input type="text" class="form-control mb-3 mt-3" id="updateTitle" v-model="storeTitle">
            </div>
            <div class="card-body">
                <div id="divEditorUpdate"></div>

                <div v-if="$store.state.board.fileList.length > 0" class="mb-3">
                    첨부파일 :
                    <ul id="updateFileList">
                        <div v-for="(file, index) in $store.state.board.fileList" class="fileName" :key="index"><i class="fa-solid fa-file"></i>{{ file.fileName }}</div>
                    </ul>
                </div>

                <div class="form-check mb-3">
                    <input v-model="attachFile" class="form-check-input" type="checkbox" value="" id="chkFileUploadUpdate" />
                    <label class="form-check-label" for="chkFileUploadUpdate">파일 변경</label>
                </div>

                <div class="mb-3" v-show="attachFile" id="imgFileUploadUpdateWrapper">
                    <input @change="changeFile" type="file" id="inputFileUploadUpdate" multiple />
                    <div id="imgFileUploadUpdateThumbnail" class="thumbnail-wrapper">
                        <img v-for="(file, index) in fileList" v-bind:src="file" v-bind:key="index" />
                    </div>
                </div>
            </div>

            <div class="btn-box">
                <button @click="boardUpdate" class="btn btn-primary">수정</button>
                <router-link to="/board/detail" >
                    <button class="btn btn-secondary" aria-label="Close">취소</button>     
                </router-link></div>
            
        </div>

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
    name: 'BoardUpdate',

    data() {
        return {
        CKEditor: "",
        attachFile: false,
         fileList: [], // store 의 fileList 와 구분됨. 새로 첨부되는 파일을 위한.
        };
    },
    computed: {
        storeTitle: {
            get() {
                return this.$store.state.board.title;
            },
            set(title) {
                this.$store.commit("SET_BOARD_TITLE", title);
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
            this.CKEditor.setData(this.$store.state.board.content);
            this.attachFile = false;
            this.fileList = [];
            document.querySelector("#inputFileUploadUpdate").value = "";
        },
        changeFile(fileEvent) {
            this.fileList = []; // thumbnail 초기화

            const fileArray = Array.from(fileEvent.target.files);
            fileArray.forEach((file) => {
                this.fileList.push(URL.createObjectURL(file)); // push : array 에 항목 추가
            });
        },
        // 굳이 actions 에 있을 필요 없다. backend async 작업이지만, 그 결과로 store 를 변경하는 내용이 없다.
        async boardUpdate() {
            // post form data
            let formData = new FormData();
            formData.append("boardId", this.$store.state.board.boardId); // update 에 추가
            formData.append("title", this.$store.state.board.title);
            formData.append("content", this.CKEditor.getData()); // store X !!!!

            // file upload
            let attachFiles = document.querySelector("#inputFileUploadUpdate").files;

            if (attachFiles.length > 0) {
            const fileArray = Array.from(attachFiles);
            fileArray.forEach((file) => formData.append("file", file));
            }

            let options = {
            headers: { "Content-Type": "multipart/form-data" },
            };

            // not put, REST but FileUpload
            try {
                let { data } = await http.post("/boards/" + this.$store.state.board.boardId, formData, options);

                console.log("UpdateModalVue: data : ");
                console.log(data);
                if (data.result == "login") {
                this.doLogout();
                } else {
                this.$alertify.success("글이 수정되었습니다.");
                this.$router.push("/board")
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

/*-- board-detail --*/
.board-detail, .board-insert, .board-update{box-shadow: 0 0 10px rgba(0, 0, 0, .1); border-radius: 1rem; padding: 40px; margin-top: 30px;}

ul[id$="FileList"] {padding: 0;}
ul[id$="FileList"] .fileName{font-size: 14px;}
ul[id$="FileList"] a[type="button"]{background-color:#F5F5F5; padding: 0 .5rem;}


/*-- board-update --*/
#imgFileUploadUpdateWrapper{display:none}
.btn-box{display: flex; justify-content: flex-end; margin-bottom: 10px;}
.modal-body .btn-box .btn{margin-left: 10px; padding: .5rem 2rem; font-size: 14px;}
.ck.ck-editor__editable{height: 650px;}
</style>