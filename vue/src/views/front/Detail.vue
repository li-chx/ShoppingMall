<template>
  <div class="product-detail-container">
    <div class="product-header">
      <div class="product-image">
        <img :src="goodsData.img" alt="" style="width: 100%; height: auto; border-radius: 10px; transition: transform 0.3s ease-in-out;" @mouseover="scaleImage(true)" @mouseout="scaleImage(false)">
      </div>
      <div class="product-info">
        <h1 class="product-title">{{ goodsData.name }}</h1>
        <p class="product-invoice">可开发票</p>
        <p class="product-price">
          <span class="price-value">¥{{ goodsData.price }}/{{ goodsData.unit }}</span>
          <span class="sales-info"> · 已售 {{ goodsData.count }} · 不足 {{ goodsData.inventory }}</span>
        </p>
        <p class="product-merchant">商家：<a href="#" @click.prevent="goTo('/front/business?id=' + goodsData.businessId)">{{ goodsData.businessName }}</a></p>
        <p class="product-category">分类：<a href="#" @click.prevent="goTo('/front/category?id=' + goodsData.categoryId)">{{ goodsData.categoryName }}</a></p>
        <div class="product-actions">
          <el-button type="primary" class="custom-button" @click="addCart()">加入购物车</el-button>
          <el-button type="primary" class="custom-button">收藏</el-button>
        </div>
      </div>
    </div>
    <div class="product-tabs">
      <el-tabs v-model="activeName" @tab-click="handleClick" >
        <el-tab-pane label="商品详情" name="first" class="custom-tabs">
          <div class="product-description" v-html="goodsData.description"></div>
        </el-tab-pane>
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
      activeName: 'first',
      isImageScaled: false
    }
  },
  mounted() {
    this.loadGoods()
  },
  methods: {
    loadGoods() {
      this.$request.get('/goods/selectById/' + this.goodsId).then(async res => {
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
    scaleImage(isScaled) {
      this.isImageScaled = isScaled;
    }
  },
}
</script>

<style scoped>
.product-detail-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  background-color: #fff;
  /* box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); */
  border-radius: 10px;
}

.product-header {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
}

.product-image {
  flex: 1;
  overflow: hidden;
}

.product-image img {
  transform: scale(1);
}

.product-image img:hover {
  transform: scale(1.1);
}

.product-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
  /* justify-content: center; */
}

.product-title {
  font-size: 32px;
  font-weight: 700;
  margin-bottom: 10px;
  color: #333;
}

.product-invoice {
  color: #666;
  margin-bottom: 15px;
}

.product-price {
  font-size: 24px;
  color: #ff5000;
  margin-bottom: 15px;
}

.price-value {
  font-weight: 700;
}

.sales-info {
  font-size: 16px;
  color: #666;
}

.product-merchant,
.product-category {
  color: #666;
  margin-bottom: 15px;
}

.product-actions {
  display: flex;
  gap: 10px;
}

.custom-button {
  background-color: #81d7ce;
  border-color: #81d7ce;
  border-radius: 20px;
  padding: 10px 20px;
  font-size: 16px;
  transition: background-color 0.3s ease;
}

.custom-button:hover {
  background-color: #49968e;
  border-color: #49968e;
}

.product-tabs {
  border-top: 1px solid #eee;
  padding-top: 20px;
}

.custom-tabs .el-tabs__item {
  font-weight: 600;
  font-size: 30px; 
  color: #81d7ce;
  padding: 10px 20px;
}

.custom-tabs .el-tabs__item.is-active {
  color: #49968e;
}

.custom-tabs .el-tabs__active-bar {
  background-color: #49968e;
}

.product-description {
  padding: 20px;
  line-height: 1.6;
}

a {
  color: #666;
}

a:hover {
  color: red;
}
</style>