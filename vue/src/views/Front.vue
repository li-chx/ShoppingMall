<template>
  <div>
    <!-- <div class="front-notice"><i class="el-icon-bell" style="margin-right: 2px"></i>公告：{{ top }}</div> -->
    <!--头部-->
    <div class="front-header">
      <div class="front-header-left" @click="goTo('/front/home')">
        <img src="@/assets/imgs/logo.png" alt="" >
        <div class="title"><i>ShoppingMall</i></div>
      </div>
      <!-- <div class="front-header-center">
        <div class="front-header-nav">
          <el-menu :default-active="$route.path" mode="horizontal" router>
						<el-menu-item index="/front/home">首页</el-menu-item>
						<el-menu-item index="/front/person">个人中心</el-menu-item>
          </el-menu>
        </div>
      </div> -->
      <div class="front-header-right">
        <div v-if="!user.username">
          <el-button @click="$router.push('/login')">登录</el-button>
          <el-button @click="$router.push('/register')">注册</el-button>
        </div>
        <div v-else>
          <el-dropdown>
            <div class="front-header-dropdown">
              <img :src="fixUrl(user.avatar)" alt="" @click="goTo('/front/person')">
              <div style="margin-left: 10px">
                <span>{{ user.name }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
              </div>
            </div>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <div style="text-decoration: none" @click="logout">退出</div>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>
    </div>
    <!--主体-->
    <div class="main-body">
      <div class="main-content">
        <div style="flex: 5; height: 60px; background-color: #81d7ce"></div>
        <div style="display: flex; ">
          <div class="left"></div>
          <div style="width: 66%;  height: 1000px; border-radius: 15px; background-color: white">
            <router-view ref="child" @update:user="updateUser" />
          </div>
          <div class="right"></div>
        </div>
      </div>
    </div>
  </div>

</template>

<script>

export default {
  name: "FrontLayout",

  data () {
    return {
      top: '',
      notice: [],
      user: JSON.parse(localStorage.getItem("xm-user") || '{}'),
    }
  },

  mounted() {
    this.loadNotice()
  },
  methods: {
    fixUrl(url) {
      if (!url) return '';
      if (url.startsWith('http')) return url;
      return '/api' + url;
    },
    loadNotice() {
      this.$request.get('/notice/selectAll').then(res => {
        this.notice = res.data
        let i = 0
        if (this.notice && this.notice.length) {
          this.top = this.notice[0].content
          setInterval(() => {
            this.top = this.notice[i].content
            i++
            if (i === this.notice.length) {
              i = 0
            }
          }, 2500)
        }
      })
    },
    updateUser() {
      this.user = JSON.parse(localStorage.getItem('xm-user') || '{}')   // 重新获取下用户的最新信息
    },
    // 退出登录
    logout() {
      localStorage.removeItem("xm-user");
      this.$router.push("/login");
    },
    goTo(url) {
      this.$router.push(url)
    }
  }

}
</script>

<style scoped>
@import "@/assets/css/front.css";
.main-content {
  min-height: 100vh;
  /*overflow: hidden;*/
  background-size: 100%;
  background-image: url('@/assets/imgs/男生购物.svg');
  /* background-color: #81d7ce; */
  /* background-color: ; */
}
.left {
  width: 17%;
  background-repeat: no-repeat;
  background-image: url('@/assets/imgs/购物车.svg');
  background-size: contain;
}
.right {
  width: 17%;
  background-repeat: no-repeat;
  background-image: url('@/assets/imgs/购物.svg');
  background-size: contain;
}
</style>