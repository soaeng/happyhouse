<template>
    <div id="auth">
        <div class="row h-100">
            <div class="col-lg-5 col-12">
                <div id="auth-left">
                    <div class="auth-logo">
                        <h1> Happy House</h1>
                    </div>
                    <h1 class="auth-title">Log in.</h1>

                    <form action="">
                        <div class="form-group position-relative has-icon-left mb-4">
                            <input type="text" class="form-control form-control-xl" placeholder="Email" id="userEmail" v-model="$store.state.login.userEmail">
                            <div class="form-control-icon">
                                <i class="bi bi-person"></i>
                            </div>
                        </div>
                        <div class="form-group position-relative has-icon-left mb-4">
                            <input type="password" class="form-control form-control-xl" id="userPassword" v-model="$store.state.login.userPassword" placeholder="Password">
                            <div class="form-control-icon">
                                <i class="bi bi-shield-lock"></i>
                            </div>
                        </div>
                        <div class="form-check form-check-lg d-flex align-items-end">
                            <input class="form-check-input me-2" type="checkbox" value="" id="flexCheckDefault">
                            <label class="form-check-label text-gray-600" for="flexCheckDefault">
                                Keep me logged in
                            </label>
                        </div>
                        <button type="button" @click="login" class="btn btn-primary btn-block btn-lg shadow-lg mt-5">Log in</button>
                    </form>
                    <div class="text-center mt-5 text-lg fs-4">
                        <p class="text-gray-600">Don't have an account?
                            <router-link to="userRegister" class="font-bold">Sign up</router-link>.</p>
                        <p><a class="font-bold" href="auth-forgot-password.html">Forgot password?</a>.</p>
                    </div>
                </div>
            </div>
            <div class="col-lg-7 d-none d-lg-block">
                <div id="auth-right">
                    <img src="../../public/assets/images/loginView.jpg" alt="" style="position: relative; top: 0; left: 0; width: 100%; height: 100%; object-fit: cover;">
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import Vue from 'vue';
import VueAlertify from 'vue-alertify'; 
Vue.use(VueAlertify);

import http from "@/common/axios.js";

export default {
    name: 'Login',
    methods: {
        async login(){
        let loginObj = {
            userEmail: this.$store.state.login.userEmail,
            userPassword: this.$store.state.login.userPassword
        };

        try{
            let {data} = await http.post('/login', loginObj );

            console.log("LoginVue: data : ");
            console.log(data);

            this.$store.commit( // mutation은 commit으로 호출해라
                'SET_LOGIN', { isLogin: true, userName: data.userName, userEmail: this.$store.state.login.userEmail, userProfileImageUrl: data.userProfileImageUrl} // payload 부분
            );
            sessionStorage.setItem('isLogin', this.$store.state.login.isLogin);
            sessionStorage.setItem('userName', this.$store.state.login.userName);
            sessionStorage.setItem('userEmail', this.$store.state.login.userEmail);
            sessionStorage.setItem('userProfileImageUrl', this.$store.state.login.userProfileImageUrl);            

            // Main 으로 이동
            this.$router.push("/house");

        }catch(error){
            console.log("LoginVue: error : ");
            console.log(error);
            if( error.response.status == '404'){
                this.$alertify.error('이메일 또는 비밀번호를 확인하세요.');
            }else{
                this.$alertify.error('Opps!! 서버에 문제가 발생했습니다.');
            }
        }
        }
}
}
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

.bi.bi-shield-lock::before, .bi.bi-person::before{margin-bottom: .4rem;}
</style>
