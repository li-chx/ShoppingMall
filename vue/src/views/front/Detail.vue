<template>
  <div>
    <div style="padding: 15px 20px">
      <el-row :gutter="20">
        <el-col :span="12">
          <img :src="goodsData.img" alt="" style="width: 100%; height: 400px; border-radius: 20px">
        </el-col>
        <el-col :span="12">
          <div
            style="font-size: 20px; font-weight: 900; overflow: hidden; text-overflow: ellipsis; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical;">
            {{ goodsData.name }}</div>
          <div style="color: #666666FF; margin-top: 10px">可开发票</div>
          <div style=" margin-top: 15px"><span
              style="font-size: 20px; color: #ff5000;"><b>¥{{ goodsData.price }}/{{ goodsData.unit }}</b></span><span
              style="color: #666666FF;"> · 已售 {{ goodsData.count }} · 不足 {{ goodsData.inventory }}</span></div>
          <!-- <div style="margin-top: 20px">
            <img src="@/assets/imgs/right.png" alt="" style="width: 70%; height: 130px; border-radius: 15px">
          </div> -->
          <div style="color: #666666FF; margin-top: 20px">商家：{{ goodsData.businessName }}</div>
          <div style="color: #666666FF; margin-top: 20px">分类：<a href="#"
              @click.prevent="goTo('/front/category?id=' + goodsData.categoryId)">{{ goodsData.categoryName }}</a></div>
          <div style="color: #666666FF; margin-top: 20px">
            <el-button type="warning" @click="addCart()">加入购物车</el-button>
            <el-button type="warning">收藏</el-button>
          </div>
        </el-col>
      </el-row>
    </div>
    <div style="padding: 15px 20px">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="商品详情" name="first">
          <div style="padding: 10px 175px" v-html="goodsData.description"></div>
        </el-tab-pane>
        <!-- <el-tab-pane label="用户评价" name="second">111</el-tab-pane> -->
      </el-tabs>
    </div>
  </div>
</template>

<script>
import Lottie from 'vue-lottie';
import * as animationData from '../../assets/video/买买买.json';
import {fixUrl} from "@/utils/fixUrl";
export default {
  components: {
    lottie: Lottie
  },
  data() {
    let goodsId = this.$route.query.id
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      goodsId: goodsId,
      goodsData: {},
      activeName: 'first'
    }
  },
  mounted() {
    this.loadGoods()
  },
  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
    loadGoods() {
      this.$request.get('/goods/selectById/' + this.goodsId).then(async res => {
        console.log(res);

        if (res.code === '200') {
          res.data.img = await fixUrl(res.data.img);
          this.goodsData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleClick(tab, event) {
      this.activeName = tab.name
    },
    goTo(url) {
      this.$router.push(url)
    },
    addCart() {
      let data = { num: 1, userId: this.user.id, goodsId: this.goodsId, businessId: this.goodsData.businessId }
      this.$request.post('/cart/add', data).then(res => {
        if (res.code === '200') {
          this.$message.success('加入成功')
        } else {
          this.$message.error(res.msg)
        }
      })
    },
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
  color: #666;
}

a:hover {
  color: red;
}
</style>
