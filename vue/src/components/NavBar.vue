<template>
    <div id="sidebar" class="active">
            <div class="sidebar-wrapper active">
                <div class="sidebar-header">
                    <div class="d-flex justify-content-between">
                        <div class="logo">
                            <a href="#"><img src="assets/images/logo/logo.png" alt="Logo" srcset=""></a>
                        </div>
                        <div class="toggler">
                            <a href="#" class="sidebar-hide d-xl-none d-block" @click="sidebarHide"><i class="bi bi-x bi-middle"></i></a>
                        </div>
                    </div>
                </div>
                <div class="sidebar-menu">
                    <ul class="menu">

                        <li class="sidebar-item">
                            <a href="/house" class='sidebar-link'>
                                <i class="bi bi-search"></i>
                                <span>아파트 검색</span>
                            </a>
                        </li>

                        <li class="sidebar-item has-sub">
                            <a href="#" class='sidebar-link' @click="sidebarToggle">
                                <i class="bi bi-bookmark-heart-fill"></i>
                                <span>즐겨찾기</span>
                            </a>
                            <ul class="submenu ">
                                <li class="submenu-item ">
                                    <router-link to="/bookmark/area">관심 지역</router-link>
                                </li>
                                <li class="submenu-item ">
                                    <router-link to="/bookmark/deal">관심 거래</router-link>
                                </li>
                            </ul>
                        </li>

                        <li class="sidebar-item">
                            <router-link to="/news" class='sidebar-link'>
                                <i class="bi bi-newspaper"></i>
                                <span>주요 뉴스</span>
                            </router-link>
                        </li>
                        
                        <li class="sidebar-item">
                            <router-link to="/userInfo" class='sidebar-link'>
                                <i class="bi bi-person-fill"></i>
                                <span>개인 정보 수정</span>
                            </router-link>
                        </li>

                        <li class="sidebar-item">
                            <router-link to="/login" class='sidebar-link'>
                                <i class="bi bi-box-arrow-right"></i>
                                <span>로그아웃</span>
                            </router-link>
                        </li>


                    </ul>
                </div>
            </div>
        </div>
</template>

<script>
export default {
    name: 'NavBar',

    data() {
        return {

        };
    },

    mounted() {
    },

    methods: {
        slideToggle(t,e,o){0===t.clientHeight?this.j(t,e,o,!0):this.j(t,e,o)},
        slideUp(t,e,o){this.j(t,e,o)},
        slideDown(t,e,o){this.j(t,e,o,!0)},
        j(t,e,o,i){void 0===e&&(e=400),void 0===i&&(i=!1), t.style.overflow="hidden",i&&(t.style.display="block");var p,l=window.getComputedStyle(t),n=parseFloat(l.getPropertyValue("height")),a=parseFloat(l.getPropertyValue("padding-top")),s=parseFloat(l.getPropertyValue("padding-bottom")),r=parseFloat(l.getPropertyValue("margin-top")),d=parseFloat(l.getPropertyValue("margin-bottom")),g=n/e,y=a/e,m=s/e,u=r/e,h=d/e;window.requestAnimationFrame(function l(x){void 0===p&&(p=x);var f=x-p;i?(t.style.height=g*f+"px",t.style.paddingTop=y*f+"px",t.style.paddingBottom=m*f+"px",t.style.marginTop=u*f+"px",t.style.marginBottom=h*f+"px"):(t.style.height=n-g*f+"px",t.style.paddingTop=a-y*f+"px",t.style.paddingBottom=s-m*f+"px",t.style.marginTop=r-u*f+"px",t.style.marginBottom=d-h*f+"px"),f>=e?(t.style.height="",t.style.paddingTop="",t.style.paddingBottom="",t.style.marginTop="",t.style.marginBottom="",t.style.overflow="",i||(t.style.display="none"),"function"==typeof o&&o()):window.requestAnimationFrame(l)})},

        sidebarToggle(e){
            e.preventDefault();
        
            let submenu = document.querySelector('.submenu');
            if( submenu.classList.contains('active') ) submenu.style.display = "block"

            if( submenu.style.display == "none" ) submenu.classList.add('active')
            else submenu.classList.remove('active')
            this.slideToggle(submenu, 300);
        },
        sidebarHide(){
            document.getElementById('sidebar').classList.toggle('active');
        },
        initScrollbar(){
            // Perfect Scrollbar Init
            if(typeof PerfectScrollbar == 'function') {
                const container = document.querySelector(".sidebar-wrapper");
                const ps = new PerfectScrollbar(container, {
                    wheelPropagation: false
                });
            }
        },
        activeSidebarScroll(){
            // Scroll into active sidebar
            document.querySelector('.sidebar-item.active').scrollIntoView(false);
        },
        siblings(t, removeClass) { 
            const children = t.parentElement.children;
            for (var i = 0; i < children.length; i++) { 
                children[i].classList.remove(removeClass) 
            }
            t.classList.add(removeClass);
        },
    },
};
</script>

<style scoped>
</style>