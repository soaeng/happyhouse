<template>
    <div id="auth">
        <div class="row h-100">
            <div class="col-lg-5 col-12">
                <div id="auth-left">
                    <div class="auth-logo">
                        <h1><a href="#" class="fw-bold"><i class="bi bi-house-fill"></i>HAPPY HOUSE</a></h1>
                    </div>
                    <h1 class="auth-title">로그인</h1>

                    <form>
                        <div class="form-group position-relative has-icon-left mb-3">
                            <input type="text" class="form-control form-control-xl" placeholder="Email" id="userEmail" v-model="$store.state.login.userEmail">
                            <div class="form-control-icon"><i class="bi bi-person"></i></div>
                        </div>
                        <div class="form-group position-relative has-icon-left mb-3">
                            <input type="password" class="form-control form-control-xl" id="userPassword" v-model="$store.state.login.userPassword" placeholder="Password">
                            <div class="form-control-icon"><i class="bi bi-shield-lock"></i></div>
                        </div>
                        <div class="form-check form-check-lg d-flex align-items-end mb-5">
                            <input class="form-check-input me-2" type="checkbox" value="" id="flexCheckDefault">
                            <label class="form-check-label text-gray-600" for="flexCheckDefault">로그인 상태 유지</label>
                        </div>
                        <button type="button" @click="login" class="btn btn-primary btn-block btn-lg shadow-lg mb-2" style="font-size: 14px; height: 42px;">로그인</button>
                        <button type="button" @click="kakaoLogin" class="btn btn-block btn-lg shadow-lg mb-3 position-relative" style="font-size: 14px; height: 42px; color: #3b1c1c; background-color: #f9e000;">
                            <img src="../../public/assets/images/kakao_login.png" width="28px;" height="26px;" class="position-absolute" style="top:8px; left: 10px;">카카오 계정으로 로그인
                        </button>
                    </form>
                    <div class="d-flex justify-content-center">
                        <router-link to="userRegister" class="text-secondary" style="font-size: 13px;" >회원가입</router-link>
                        <span class="divBar"></span>
                        <a href="auth-forgot-password.html" class="text-secondary" style="font-size: 13px;">비밀번호 찾기</a>
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
    created() {
        let script = document.createElement('script')
        script.onload = () => window.Kakao.init('ea30855eb11a01ae45464e12f440cde3');
        script.src =
            "https://developers.kakao.com/sdk/js/kakao.js";
        
        // script.setAttribute('src', 'https://developers.kakao.com/sdk/js/kakao.js')
        document.head.appendChild(script)
    },
    methods: {
        async login(account){
            if(typeof account.email == 'undefined'){
                let loginObj = {
                    userEmail: this.$store.state.login.userEmail,
                    userPassword: this.$store.state.login.userPassword
                };

                try{
                    let {data} = await http.post('/login', loginObj );

                    console.log(data);

                    this.$store.commit( // mutation은 commit으로 호출해라
                    'SET_LOGIN',
                    { isLogin: true, userName: data.userName, userEmail: this.$store.state.login.userEmail, userProfileImageUrl: data.userProfileImageUrl} // payload 부분
                    );
                    sessionStorage.setItem('isLogin', this.$store.state.login.isLogin);
                    sessionStorage.setItem('userName', this.$store.state.login.userName);
                    sessionStorage.setItem('userEmail', this.$store.state.login.userEmail);
                    sessionStorage.setItem('userProfileImageUrl', this.$store.state.login.userProfileImageUrl);            

                    // Main 으로 이동
                    this.$router.push("/house");

                }catch(error){
                    if( error.response.status == '404'){
                        this.$alertify.error('이메일 또는 비밀번호를 확인하세요.');
                    }else{
                        this.$alertify.error('서버에 문제가 발생했습니다.');
                    }
                }
            } else{ // kakao Login
                let loginObj = {
                    userEmail: account.email,
                    userPassword: 'kakao_account'
                };
                try{
                    let {data} = await http.post('/login', loginObj );
                    console.log(data);

                    this.$store.commit( // mutation은 commit으로 호출해라
                        'SET_LOGIN',
                        { isLogin: true, userName: account.profile.nickname, userEmail: account.email, userProfileImageUrl: account.profile.profile_image_url} // payload 부분
                    );
                    sessionStorage.setItem('isLogin', this.$store.state.login.isLogin);
                    sessionStorage.setItem('userName', this.$store.state.login.userName);
                    sessionStorage.setItem('userEmail', this.$store.state.login.userEmail);
                    sessionStorage.setItem('userProfileImageUrl', this.$store.state.login.userProfileImageUrl);            

                    // Main 으로 이동
                    this.$router.push("/house");

                }catch(error){
                    if( error.response.status == '404'){
                        console.clear();
                        this.register(account);
                    }else{
                        this.$alertify.error('Opps!! 서버에 문제가 발생했습니다.');
                    }
                }
            }
        },
        async register(account) {
            let registerObj = {
                userName: account.profile.nickname,
                userEmail: account.email,
                userPassword: 'kakao_account',
            };
            try {
                let { data } = await http.post("/user", registerObj); // JSON Request, { params : registerObj } X params 를 쓰면 get => query string

                if (data.result == "success") {
                let $this = this;
                this.login(account);

                } else {
                this.$alertify.error("서버에 문제가 발생했습니다.");
                }
            } catch (error) {
                console.log(error);
                this.$alertify.error("서버에 문제가 발생했습니다.");
            }
        },
        kakaoLogin() {
            let $this = this;
            window.Kakao.Auth.login({
                scope: 'profile_nickname, profile_image, account_email', //동의항목 페이지에 있는 개인정보 보호 테이블의 활성화된 ID값을 넣습니다.
                success: function(response) {
                    // console.log(response) // 로그인 성공하면 받아오는 데이터
                    window.Kakao.API.request({ // 사용자 정보 가져오기 
                        url: '/v2/user/me',
                        success: (res) => {
                            const kakao_account = res.kakao_account;
                            $this.login(kakao_account)
                        }
                    });
                    // window.location.href='/ex/kakao_login.html' //리다이렉트 되는 코드
                },
                fail: function(error) {
                }
            })
        },
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
    padding:4rem 2.5rem 4rem 3.5rem;
}
#auth #auth-left .auth-title{
    font-size:1.3rem; margin-bottom:1.2rem;
    }
#auth #auth-left .auth-subtitle{
    font-size:1.7rem;line-height:2.5rem;color:#a8aebb;
    }
#auth #auth-left .auth-logo{
        margin-bottom:7rem;
    }
.form-group input[type="text"], .form-group input[type="password"]{font-size: 1rem;}
@media screen and (max-width:767px){
    #auth #auth-left{padding:5rem}
    .auth-logo h1 a{font-size: 2.34rem;}
}

.bi.bi-house-fill::before{
    margin-top: .5rem;
    margin-right: .5rem;
}
.form-control-icon .bi::before{margin-bottom: .7rem; padding-left: .3rem; font-size: 1.4rem;}
.divBar { display: inline-block; height: .9rem; width: 1px; margin: .2rem .5rem 0; background-color: #CCC;}
</style>
