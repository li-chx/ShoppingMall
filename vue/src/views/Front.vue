<template>
  <div>
    <!-- <div class="front-notice"><i class="el-icon-bell" style="margin-right: 2px"></i>公告：{{ top }}</div> -->
    <!--头部-->
    <div class="front-header" style="position: fixed; top: 0; width: 100%; z-index: 1000;">
      <div class="front-header-left" @click="closeSearchFunc">
        <img src="@/assets/imgs/logo.png" alt="">
        <!-- <div class="title"><i>ShoppingMall</i></div> -->
        <div class="title" style="font-weight: bold; font-size: 50px;flex:1;font-family:'Nanum Pen Script'">ShoppingMall
        </div>
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
              <img :src="imgUrl" alt="" @click="goTo('/front/person')" style="cursor:pointer">
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
    <div class="main-body" style="margin-top: 60px;">
      <div class="main-content">
        <div style="flex: 5; height: 30px; background-color: #81d7ce"></div>
        <div style="display: flex; min-height: calc(100vh - 120px);">
          <!-- 修复左侧图片显示问题 -->
          <div class="left" :style="{ backgroundImage: 'url(' + leftBgImage + ')' }"></div>

          <div style="width: 76%; border-radius: 15px; background-color: white; overflow-y: auto;">
            <router-view ref="child" @update:user="updateUser"/>
            <footer style="height:30px"></footer>
          </div>

          <!-- 修复右侧图片显示问题 -->
          <div class="right" :style="{ backgroundImage: 'url(' + rightBgImage + ')' }"></div>
        </div>
        <footer style="height: 2em;"></footer>
      </div>
    </div>
  </div>

</template>

<script>

import {fixUrl} from "@/utils/fixUrl";

export default {
  name: "FrontLayout",

  data() {
    return {
      top: '',
      notice: [],
      user: JSON.parse(localStorage.getItem("xm-user") || '{}'),
      leftBgImage: require('@/assets/imgs/购物车.svg'),
      rightBgImage: require('@/assets/imgs/购物.svg'),
      imgUrl: '',
    }
  },
  async mounted() {
    this.$bus.$on('updateUser', async () => {
      await this.updateUser()
    });
    this.loadNotice();

    // 修复图片路径问题 - 动态设置背景图片
    this.$nextTick(() => {
      const leftDiv = document.querySelector('.left');
      const rightDiv = document.querySelector('.right');
      if (leftDiv) {
        leftDiv.style.backgroundImage = `url(${require('@/assets/imgs/购物车.svg')})`;
      }
      if (rightDiv) {
        rightDiv.style.backgroundImage = `url(${require('@/assets/imgs/购物.svg')})`;
      }
    });
    await this.updateUser();
  },
  beforeDestroy() {
    this.$bus.$off('updateUser');
  },
  methods: {
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
    async updateUser() {
      this.user = JSON.parse(localStorage.getItem('xm-user') || '{}')   // 重新获取下用户的最新信息
      this.imgUrl = await fixUrl(this.user.avatar);
    },
    // 退出登录
    logout() {
      localStorage.removeItem("xm-user");
      this.$router.push("/login");
    },
    goTo(url) {
      this.$router.push(url)
    },
    closeSearchFunc() {
      this.$bus.$emit('closeSearch');
      this.goTo('/front/home');
    }
  }

}
</script>

<style scoped>
@import "@/assets/css/front.css";

.main-content {
  min-height: calc(100vh - 60px);
  /*overflow: hidden;*/
  background-size: 100%;
  background-image: url('@/assets/imgs/男生购物.svg');
  /* background-color: #81d7ce; */
  /* background-color: ; */
}

/* 修复左右两侧样式 */
.left {
  width: 17%;
  min-height: 500px;
  background-size: contain;
  background-position: center;
  background-repeat: no-repeat;
  position: sticky;
  top: 60px;
  align-self: flex-start;
}

.right {
  width: 17%;
  min-height: 500px;
  background-size: contain;
  background-position: center;
  background-repeat: no-repeat;
  position: sticky;
  top: 60px;
  align-self: flex-start;
}


</style>