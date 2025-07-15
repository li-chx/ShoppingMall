<template>
  <div>
    <div style="color: #81d7ce; margin: 15px 0 15px 18px; font-weight: bold; font-size: 16px">ShoppingMall</div>
    <div style="display: flex; margin: 0 20px">
      <div style="flex: 1.5; background-color: #81d7ce; border-radius: 10px; margin-right: 10px; color: white;">
        <div style="margin: 10px 10px; font-size: 16px"><b>分类</b></div>
        <div style="display: flex;  margin: 14px 10px" v-for="item in categoryData" :key="item.id">
          <img :src="item.img" alt="" style="height: 20px; width: 20px; filter: invert(1)">
          <div style="margin-left: 10px; font-size: 14px"><a href="#"
                                                             @click.prevent="goTo('/front/category?id=' + item.id)">{{ item.name }}</a>
          </div>
        </div>
      </div>
      <div style="flex: 5.5; margin-top: 0px">
        <div>
          <el-carousel height="250px" style="border-radius: 10px">
            <el-carousel-item v-for="(item,index) in carousel_top" :key="index">
              <img :src="item" alt="" style="height: 250px; width: 100%">
            </el-carousel-item>
          </el-carousel>
        </div>
        <div style="margin-top: 40px; display: flex">
          <div style="flex: 1; margin-right: 2.5px">
            <el-carousel height="200px" style="border-radius: 10px">
              <el-carousel-item v-for="(item,index) in carousel_left" :key="index">
                <img :src="item" alt="" style="height: 200px; width: 100%">
              </el-carousel-item>
            </el-carousel>
          </div>
          <div style="flex: 1; margin-left: 2.5px">
            <el-carousel height="200px" style="border-radius: 10px">
              <el-carousel-item v-for="(item,index) in carousel_right" :key="index">
                <img :src="item" alt="" style="height: 200px; width: 100%">
              </el-carousel-item>
            </el-carousel>
          </div>
        </div>
      </div>
      <div
          style="flex: 3; height: 490px; background-image: linear-gradient(#a2e0d9 0%, #f7f7f7 100%); margin-left:10px; border-radius: 10px">
        <div style="text-align: center; margin-top: 30px">
          <img :src="user.avatar" alt="" @click="goTo('/front/person')"
               style="width: 80px; height: 80px; border-radius: 50%">
          <div style="margin-top:10px; font-size: 16px"><b>你好，{{ user.name }}</b></div>
        </div>
        <div style="margin: 20px 10px;">
          <i class="el-icon-bell"></i>
          <span><b>公告：</b></span>
          <div class="text-overflow-ellipsis" style="color: #666666; width: 300px; margin-top: 5px">{{
              notice_content
            }}
          </div>
        </div>
        <div style="display: flex; margin-top: 30px">
          <div style="flex: 1; text-align: center">
            <img src="../../assets/imgs/shoucang.png" alt="" style="height: 25px; width: 25px">
            <div>我的收藏</div>
          </div>
          <div style="flex: 1; text-align: center" @click="goTo('/front/cart')">
            <img src="../../assets/imgs/gouwuche.png" alt="" style="height: 25px; width: 25px">
            <div>我的购物车</div>
          </div>
          <div style="flex: 1; text-align: center" @click="goTo('/front/orders')">
            <img src="../../assets/imgs/dingdan.png" alt="" style="height: 25px; width: 25px">
            <div>我的订单</div>
          </div>
          <div style="flex: 1; text-align: center" @click="goTo('/front/address')">
            <img src="../../assets/imgs/dizhi.png" alt="" style="height: 25px; width: 25px">
            <div>我的地址</div>
          </div>
        </div>
        <div>
          <lottie :options="defaultOptions" :height="200" :width="200"/>
        </div>
      </div>
    </div>
    <div
        style="display: flex;  margin: 20px 0 0 20px; height: 40px; font-size: 20px; color: #81d7ce; line-height: 40px; align-items: center;">
      <img src="../../assets/icon/热卖.png" alt="" style="height: 30px; width: 30px;">
      <div style="margin-left: 5px; "><b>热卖商品</b></div>
    </div>
    <div style="margin: 0 20px">
      <el-row>
        <el-col :span="5" v-for="item in goodsData" :key="item.id">
          <img :src="item.img" alt="" style="width: 100%; height: 180px; border-radius: 10px;"
               @click="goTo('/front/detail?id=' + item.id)">
          <div class="text-overflow-ellipsis"
               style="margin-top: 10px; font-weight: 500; font-size: 16px; width: 180px; color: #000000FF;">
            {{ item.name }}
          </div>
          <div style="margin-top: 5px; font-size: 18px; color: #FF5000FF">¥{{ item.price }}/{{ item.unit }}</div>
        </el-col>
      </el-row>
    </div>
  </div>

</template>

<script>
import Lottie from 'vue-lottie';
import * as animationData from '../../assets/video/买买买.json';
import {fixUrl, fixUrlList} from "@/utils/fixUrl";

export default {
  components: {
    lottie: Lottie
  },
  data() {
    return {
      user: JSON.parse(localStorage.getItem("xm-user") || '{}'),
      categoryData: [],
      notice: [],
      notice_content: null,
      carousel_top: [
        require('@/assets/imgs/carousel-1.png'),
        require('@/assets/imgs/carousel-2.png'),
        require('@/assets/imgs/carousel-9.png'),
      ],
      carousel_left: [
        require('@/assets/imgs/carousel-3.png'),
        require('@/assets/imgs/carousel-4.png'),
        require('@/assets/imgs/carousel-5.png'),
      ],
      carousel_right: [
        require('@/assets/imgs/carousel-6.png'),
        require('@/assets/imgs/carousel-7.png'),
        require('@/assets/imgs/carousel-8.png'),
      ],
      defaultOptions: {animationData: animationData.default}, // 注意：有些 JSON 文件可能需要 .default
      animationSpeed: 1,
      goodsData: [],
    }
  },
  async mounted() {
    this.user.avatar = await fixUrl(this.user.avatar);
    this.loadCategory(),
        this.loadNotice(),
        this.loadGoods()
  },
  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
    loadCategory() {
      this.$request.get('/category/selectAll').then(async res => {
        if (res.code === '200') {
          this.categoryData = await fixUrlList(res.data, x => x.img, (x, url) => {
            x.img = url;
            return x;
          });
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    loadNotice() {
      this.$request.get('/notice/selectAll').then(res => {
        this.notice = res.data
        let i = 0
        if (this.notice && this.notice.length) {
          this.notice_content = this.notice[0].content
          setInterval(() => {
            this.notice_content = this.notice[i].content
            i++
            if (i === this.notice.length) {
              i = 0
            }
          }, 2500)
        }
      })
    },
    loadGoods() {
      this.$request.get('/goods/selectTop5').then(async res => {
        if (res.code === '200') {
          this.goodsData = await fixUrlList(res.data, x => x.img, (x, url) => {
            x.img = url;
            return x;
          });
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    goTo(url) {
      this.$router.push(url)
    }
  },
}
</script>

<style scoped>
.el-col-5 {
  border: 1px solid transparent;
  width: 20%;
  max-width: 20%;
  padding: 10px 10px;
}

.el-col-5:hover {
  border-radius: 10px;
  border: 1px solid #81d7ce;
}

/* 文本溢出省略 */
.text-overflow-ellipsis {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;

}

a {
  color: white;
}

a:hover {
  color: #666;
}
</style>
