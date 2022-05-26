<template>
    <div id="auth">
        <div class="row h-100">
            <div class="col-lg-5 col-12">
                <div id="auth-left">
                    <div class="auth-logo">
                        <a href="index.html"><img src="../../public/assets/images/logo/logo.png" alt="Logo"></a>
                    </div>
                    <h1 class="auth-title">Sign Up</h1>
                    <p class="auth-subtitle mb-5">Input your data to register to our website.</p>

                    <form action="index.html">
                        <div class="form-group position-relative has-icon-left mb-4">
                            <input type="email" class="form-control form-control-xl" placeholder="Email" :class="{ 'is-valid': isUserEmailFocusAndValid, 'is-invalid': isUserEmailFocusAndInValid }" v-model="userEmail" @input="validateEmail" @focus="isUserEmailFocus = true" >
                            <div class="form-control-icon">
                                <i class="bi bi-envelope"></i>
                            </div>
                            <div class="valid-feedback">Valid.</div>
                            <div class="invalid-feedback">올바른 Email 을 입력해 주세요.</div>
                        </div>
                        <div class="form-group position-relative has-icon-left mb-4">
                            <input type="name" class="form-control form-control-xl" placeholder="Username" :class="{ 'is-valid': isUserNameFocusAndValid, 'is-invalid': isUserNameFocusAndInvalid }" v-model="userName" @input="validateUserName" @focus="isUserNameFocus = true" >
                            <div class="form-control-icon">
                                <i class="bi bi-person"></i>
                            </div>
                            <div class="valid-feedback">Valid.</div>
                            <div class="invalid-feedback">올바른 이름을 입력해 주세요.</div>
                        </div>
                        <div class="form-group position-relative has-icon-left mb-4">
                            <input type="password" class="form-control form-control-xl" placeholder="Password" :class="{ 'is-valid': isUserPasswordFocusAndValid, 'is-invalid': isUserPasswordFocusAndInvalid }" v-model="userPassword" @input="validatePassword" @focus="isUserPasswordFocus = true" >
                            <div class="form-control-icon">
                                <i class="bi bi-shield-lock"></i>
                            </div>
                            <div class="valid-feedback">Valid.</div>
                            <div class="invalid-feedback">1개 이상의 특수문자, 대소문자 및 숫자를 포함하고 8자리 이상이여야 합니다.</div>
                        </div>
                        <div class="form-group position-relative has-icon-left mb-4">
                            <input type="password" class="form-control form-control-xl" placeholder="Confirm Password" :class="{ 'is-valid': isUserPassword2FocusAndValid, 'is-invalid': isUserPassword2FocusAndInvalid }" v-model="userPassword2" @input="validatePassword2" @focus="isUserPassword2Focus = true" >
                            <div class="form-control-icon">
                                <i class="bi bi-shield-lock"></i>
                            </div>
                            <div class="valid-feedback">Valid.</div>
                            <div class="invalid-feedback">비밀번호가 일치하지 않습니다.</div>
                        </div>
                        <button @click="register" type="button" class="btn btn-primary btn-block btn-lg shadow-lg mt-5">Sign Up</button>
                    </form>
                    <div class="text-center mt-5 text-lg fs-4">
                        <p class='text-gray-600'>Already have an account? 
                            <router-link to="login"
                                class="font-bold">Log
                                in</router-link>.</p>
                    </div>
                </div>
            </div>
            <div class="col-lg-7 d-none d-lg-block">
                <div id="auth-right">
                    <img src="../../public/assets/images/registerView.jpg" alt="" style="position: relative; top: 0; left: 0; width: 100%; height: 100%; object-fit: cover;">
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import Vue from "vue";
import VueAlertify from "vue-alertify";
Vue.use(VueAlertify);

import http from "@/common/axios.js";

export default {
    name: 'UserRegister',
    data() {
        return {
            // v-model
            userName: "",
            userEmail: "",
            userPassword: "",
            userPassword2: "",

            // focus
            isUserNameFocus: false,
            isUserEmailFocus: false,
            isUserPasswordFocus: false,
            isUserPassword2Focus: false,

            // validation
            isUserNameValid: false,
            isUserEmailValid: false,
            isUserPasswordValid: false,
            isUserPassword2Valid: false,

            // 회원 구분
            groupCode: "001",
            codeList: [],
            userClsf: "001", // 일반회원 default
        };
    },
    computed: {
        isUserNameFocusAndValid() {
            return this.isUserNameFocus && this.isUserNameValid;
        },
        isUserNameFocusAndInvalid() {
            return this.isUserNameFocus && !this.isUserNameValid;
        },
        isUserEmailFocusAndValid() {
            return this.isUserEmailFocus && this.isUserEmailValid;
        },
        isUserEmailFocusAndInValid() {
            return this.isUserEmailFocus && !this.isUserEmailValid;
        },
        isUserPasswordFocusAndValid() {
            return this.isUserPasswordFocus && this.isUserPasswordValid;
        },
        isUserPasswordFocusAndInvalid() {
            return this.isUserPasswordFocus && !this.isUserPasswordValid;
        },
        isUserPassword2FocusAndValid() {
            return this.isUserPassword2Focus && this.isUserPassword2Valid;
        },
        isUserPassword2FocusAndInvalid() {
            return this.isUserPassword2Focus && !this.isUserPassword2Valid;
        },
    },
    methods: {
        validateUserName() {
            this.isUserNameValid = this.userName.length > 0 ? true : false;
            console.log(this.isUserNameValid);
        },
        validateEmail() {
            // ^ 시작일치, $ 끝 일치
            // {2, 3} 2개 ~ 3개
            // * 0회 이상, + 1회 이상
            // [-_.] - 또는 _ 또는 .
            // ? 없거나 1회
            let regexp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
            this.isUserEmailValid = regexp.test(this.userEmail) ? true : false;
            console.log(this.isUserEmailValid);
        },
        validatePassword() {
            let patternEngAtListOne = new RegExp(/[a-zA-Z]+/); // + for at least one
            let patternSpeAtListOne = new RegExp(/[~!@#$%^&*()_+|<>?:{}]+/); // + for at least one
            let patternNumAtListOne = new RegExp(/[0-9]+/); // + for at least one

            this.isUserPasswordValid = patternEngAtListOne.test(this.userPassword) && patternSpeAtListOne.test(this.userPassword) && patternNumAtListOne.test(this.userPassword) && this.userPassword.length >= 8 ? true : false;
        },
        validatePassword2() {
            this.isUserPassword2Valid = this.userPassword == this.userPassword2 ? true : false;
        },
        async register() {
            if (!this.isUserEmailValid || !this.isUserPasswordValid || !this.isUserPassword2Valid) return;

            let registerObj = {
                userName: this.userName,
                userEmail: this.userEmail,
                userPassword: this.userPassword,
                userClsf: this.userClsf,
            };
            try {
                let { data } = await http.post("/user", registerObj); // JSON Request, { params : registerObj } X params 를 쓰면 get => query string
                console.log("RegisterVue: data : ");
                console.log(data);

                if (data.result == "success") {
                let $this = this;
                this.$alertify.alert("회원가입을 축하합니다. 로그인 페이지로 이동합니다", function () {
                    $this.$router.push("/login");
                });
                } else {
                console.log("RegisterVue: error : ");
                this.$alertify.error("서버에 문제가 발생했습니다.");
                }
            } catch (error) {
                console.log("RegisterVue: error : ");
                console.log(error);
                this.$alertify.error("서버에 문제가 발생했습니다.");
            }
        },
    },
    async created() {
        let params = {
            groupCode: this.groupCode,
        };

        try {
            let { data } = await http.get("/codes", { params }); // params : params shorthand property, Not JSON request
            console.log("RegisterVue: data : ");
            console.log(data);

            this.codeList = data;
        } catch (error) {
            console.log("RegisterVue: error : ");
            console.log(error);
            this.$alertify.error("서버에 문제가 발생했습니다.");
        }
    },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
body{background-color:#fff}
#auth{height:100vh;overflow-x:hidden}
#auth #auth-right{
    height:100%;
    background:url(../../public/assets/images/bg/4853433.jpg),
    linear-gradient(90deg,#2d499d,#3f5491)
    }
#auth #auth-left{
    padding:5rem 8rem
    }
#auth #auth-left .auth-title{
    font-size:4rem;margin-bottom:1rem
    }
#auth #auth-left .auth-subtitle{
    font-size:1.7rem;line-height:2.5rem;color:#a8aebb
    }
#auth #auth-left .auth-logo{
        margin-bottom:7rem
    }
#auth #auth-left .auth-logo img{
        height:2rem
}
@media screen and (max-width:767px){
    #auth #auth-left{padding:5rem}
}

</style>
