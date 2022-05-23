<template>
<div id="main">
    <header class="mb-3">
        <a href="#" class="burger-btn d-block d-xl-none" @click="sidebarToggle">
            <i class="bi bi-justify fs-3"></i>
        </a>
    </header>

    <main class="page-heading">
        <div class="page-title">
            <div class="row">
                <div class="col-12 col-md-6 order-md-1 order-last">
                    <h3>개인 정보 조회</h3>
                </div>
            </div>
        </div>
        <section class="section">
            <div class="col-md-6 col-12">
                <div class="card">
                    <div class="card-header">
                        <h4 class="card-title">내 정보</h4>
                    </div>
                    <div class="card-content">
                        <div class="card-body">
                            <form class="form form-horizontal">
                                <div class="form-body">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <label>Email</label>
                                        </div>
                                        <div class="col-md-8 form-group">
                                            <input type="email" id="user-email" class="form-control"
                                                name="user-email" placeholder="" v-model="userEmail" readonly>
                                        </div>
                                        <div class="col-md-4">
                                            <label>Name</label>
                                        </div>
                                        <div class="col-md-8 form-group">
                                            <input type="name" id="user-nmae" class="form-control"
                                                name="user-name" placeholder="" v-model="userName" readonly>
                                            
                                            <!-- <input type="name" id="user-nmae" class="form-control"
                                                name="user-name" placeholder="" :class="{ 'is-valid': isUserNameFocusAndValid, 'is-invalid': isUserNameFocusAndInvalid }" v-model="userName" @input="validateUserName" @focus="isUserNameFocus = true" >
                                            <div class="valid-feedback">Valid.</div>
                                            <div class="invalid-feedback">올바른 이름을 입력해 주세요.</div> -->
                                        </div>
                                        <!-- <div class="col-md-4">
                                            <label>Current Password</label>
                                        </div>
                                        <div class="col-md-8 form-group">
                                            <input type="password" id="user-password" class="form-control"
                                                name="user-password" placeholder="" v-model="userPassword">
                                        </div> -->
                                        <div class="col-md-4">
                                            <label>New Password</label>
                                        </div>
                                        <div class="col-md-8 form-group">
                                            <input type="password" id="new-password" class="form-control"
                                                name="new-password" placeholder="" :class="{ 'is-valid': isNewPasswordFocusAndValid, 'is-invalid': isNewPasswordFocusAndInvalid }" v-model="newPassword" @input="validatePassword" @focus="isNewPasswordFocus = true">
                                            <div class="valid-feedback">Valid.</div>
                                            <div class="invalid-feedback">1개 이상의 특수문자, 대소문자 및 숫자를 포함하고 8자리 이상이여야 합니다.</div>
                                        </div>
                                        <div class="col-md-4">
                                            <label>Confirm Password</label>
                                        </div>
                                        <div class="col-md-8 form-group">
                                            <input type="password" id="new-password2" class="form-control"
                                                name="new-password2" placeholder="" :class="{ 'is-valid': isNewPassword2FocusAndValid, 'is-invalid': isNewPassword2FocusAndInvalid }" v-model="newPassword2" @input="validatePassword2" @focus="isNewPassword2Focus = true">
                                            <div class="valid-feedback">Valid.</div>
                                            <div class="invalid-feedback">비밀번호가 일치하지 않습니다.</div>
                                        </div>
                                        
                                        <div class="col-sm-12 d-flex justify-content-end">
                                            <button @click="update" type="button"
                                                class="btn btn-primary me-1 mb-1">변경하기</button>
                                            
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </main>
</div><!-- end of main -->
</template>

<script>
import Vue from "vue";
import VueAlertify from "vue-alertify";
Vue.use(VueAlertify);

import http from "@/common/axios.js";

export default {    
    name: 'UserInfo',

    data() {
        return {
            userEmail: this.$store.state.login.userEmail,
            userName: this.$store.state.login.userName,
            userPassword: "",
            newPassword: "",
            newPassword2: "",

            // focus
            isUserNameFocus: false,
            isUserEmailFocus: false,
            isNewPasswordFocus: false,
            isNewPassword2Focus: false,

            // validation
            isUserNameValid: false,
            isUserEmailValid: false,
            isNewPasswordValid: false,
            isNewPassword2Valid: false,
        };
    },
    computed: {
        isUserNameFocusAndValid() {     
            return this.isUserNameFocus && this.isUserNameValid;
        },
        isUserNameFocusAndInvalid() {
            return this.isUserNameFocus && !this.isUserNameValid;
        },
        isNewPasswordFocusAndValid() {
            return this.isNewPasswordFocus && this.isNewPasswordValid;
        },
        isNewPasswordFocusAndInvalid() {
            return this.isNewPasswordFocus && !this.isNewPasswordValid;
        },
        isNewPassword2FocusAndValid() {
            return this.isNewPassword2Focus && this.isNewPassword2Valid;
        },
        isNewPassword2FocusAndInvalid() {
            return this.isNewPassword2Focus && !this.isNewPassword2Valid;
        },
    },
    mounted() {
    },

    methods: {
        sidebarToggle(){
            document.getElementById('sidebar').classList.toggle('active');
        },
        sidebarHide(){
            document.getElementById('sidebar').classList.remove('active');
        },
        validateUserName() {
            this.isUserNameValid = this.userName.length > 0 ? true : false;
            console.log(this.isUserNameValid);
        },
        validatePassword() {
            let patternEngAtListOne = new RegExp(/[a-zA-Z]+/); // + for at least one
            let patternSpeAtListOne = new RegExp(/[~!@#$%^&*()_+|<>?:{}]+/); // + for at least one
            let patternNumAtListOne = new RegExp(/[0-9]+/); // + for at least one

            this.isNewPasswordValid = patternEngAtListOne.test(this.newPassword) && patternSpeAtListOne.test(this.newPassword) && patternNumAtListOne.test(this.newPassword) && this.newPassword.length >= 8 ? true : false;
        },
        validatePassword2() {
            this.isNewPassword2Valid = this.newPassword == this.newPassword2 ? true : false;
        },
        async update() {
            if (!this.isNewPasswordValid || !this.isNewPassword2Valid) return;

            let updateObj = {
                userName: this.userName,
                userEmail: this.userEmail,
                userPassword: this.newPassword,
            };
            try {
                let { data } = await http.put("/user/" + updateObj.userEmail, updateObj); // JSON Request, { params : registerObj } X params 를 쓰면 get => query string
                console.log("updateVue: data : ");
                console.log(data);

                if (data.result == "success") {
                let $this = this;
                this.$alertify.alert("수정 되었습니다. 다시 로그인이 필요합니다", function () {
                    $this.$router.push("/login");
                });
                } else {
                console.log("updateVue1: error : ");
                this.$alertify.error("서버에 문제가 발생했습니다.");
                }
            } catch (error) {
                console.log("updateVue2: error : ");
                console.log(error);
                this.$alertify.error("서버에 문제가 발생했습니다.");
            }
        },
    },
};
</script>

<style scoped>
</style>