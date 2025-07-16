<template>
  <div class="category-page">
    <!-- 分类标题 -->
    <div class="category-title">
      <h1>{{ categoryData.name }}</h1>
    </div>

    <!-- 分类描述 -->
    <div class="category-description">
      <el-card>
        <div v-html="safeDescription"></div>
      </el-card>
    </div>

    <!-- 商品展示 -->
    <div style="margin: 0 20px">
      <div class="goods-flex-row">
        <div class="product-card" v-for="item in goodsData" :key="item.id">
          <div class="product-image-wrapper">
            <img :src="item.img" alt="" class="product-image" @click="goTo('/front/detail?id=' + item.id)">
          </div>
          <div class="text-overflow-ellipsis" style="margin-top: 10px; font-weight: 500; font-size: 16px; width: 180px; color: #000000FF;">
            {{ item.name }}
          </div>
          <div style="margin-top: 5px; font-size: 18px; color: #FF5000FF">¥{{ item.price }}/{{ item.unit }}</div>
          <div class="add-cart-bottom">
            <el-button type="primary" size="mini" icon="el-icon-shopping-cart" @click="addCart(item.id)">加入购物车</el-button>
          </div>
        </div>
      </div>
    </div>

    <!-- 底部提示 -->
    <el-divider>{{ getRandomBottomText() }}</el-divider>
  </div>
</template>

<script>
import Lottie from 'vue-lottie';
import * as animationData from '../../assets/video/买买买.json';
import { fixUrlList } from "@/utils/fixUrl";
import DOMPurify from 'dompurify';

export default {
  components: {
    lottie: Lottie
  },
  computed: {
    safeDescription() {
      return DOMPurify.sanitize(
        this.categoryData.description.replace(/\n/g, '<br>'))
    }
  },
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      categoryId: this.$route.query.id,
      goodsData: [],
      categoryData: {}
    }
  },
  mounted() {
    this.loadGoods()
    this.loadCategory()
  },
  methods: {
    getRandomBottomText() {
      const arr = [
        '我也是有底线的!',
        '已经到底啦~',
        '没有更多内容了哦',
        '别滑啦，真的到底了',
        '感谢您的浏览！'
      ]
      return arr[Math.floor(Math.random() * arr.length)]
    },
    loadCategory() {
      this.$request.get('/category/selectById/' + this.categoryId).then(res => {
        if (res.code === '200') {
          this.categoryData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    loadGoods() {
      this.$request.get('/goods/selectByCategoryId/' + this.categoryId).then(async res => {
        if (res.code === '200') {
          this.goodsData = await fixUrlList(res.data, x => x.img, (x, url) => {
            x.img = url;
            return x;
          });
          console.log(this.goodsData);
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    goTo(url) {
      this.$router.push(url)
    },
    addCart(goodsId) {
      let data = { num: 1, userId: this.user.id, goodsId: goodsId }
      this.$request.post('/cart/add', data).then(res => {
        if (res.code === '200') {
          this.$message.success('加入成功')
        } else {
          this.$message.error(res.msg)
        }
      })
    }
  },
}
</script>

<style scoped>
.category-page {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.category-title {
  /* text-align: center; */
  color: #81d7ce;
  font-weight: bold;
  font-size: 15px;
  margin-bottom: 20px;
}

.category-description {
  margin-bottom: 30px;
}

.goods-flex-row {
  display: flex;
  flex-wrap: wrap;
  gap: 0;
}
.product-card {
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
  transition: box-shadow 0.4s cubic-bezier(.4,0,.2,1), transform 0.3s;
  width: 20%;
  max-width: 20%;
  padding: 10px 10px 20px 10px;
  box-sizing: border-box;
  margin-bottom: 0;
  background: #fff;
  position: relative;
  overflow: visible;
}
.product-card:hover {
  box-shadow:
    0 2px 8px rgba(0,0,0,0.08),
    0 8px 24px 8px rgba(129,215,206,0.10),
    0 16px 32px 16px rgba(0,0,0,0.18);
  transform: translateY(-5px);
}
.product-card::after {
  content: '';
  display: block;
  position: absolute;
  left: 10px;
  right: 10px;
  bottom: 0;
  height: 32px;
  pointer-events: none;
  opacity: 0;
  transition: opacity 0.3s;
  border-radius: 0 0 10px 10px;
  background: linear-gradient(to bottom, rgba(129,215,206,0) 0%, rgba(0,0,0,0.18) 100%);
  z-index: 1;
}
.product-card:hover::after {
  opacity: 1;
}
.product-image-wrapper {
  overflow: hidden;
  border-radius: 10px;
  position: relative;
}
.product-image {
  width: 100%;
  height: 180px;
  border-radius: 10px;
  transition: transform 0.3s ease;
}
.product-image:hover {
  transform: scale(1.1);
}
.add-cart-button {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  opacity: 0;
  transition: opacity 0.3s ease;
}
.product-image-wrapper:hover .add-cart-button {
  opacity: 1;
}
.add-cart-button .el-button {
  background-color: #81d7ce;
  border-color: #81d7ce;
  color: white;
  border-radius: 6px;
  transition: all 0.3s ease;
}
.add-cart-button .el-button:hover {
  background-color: #66c2b8;
  border-color: #66c2b8;
  transform: translateY(-2px);
}
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
.add-cart-bottom {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 12px;
  opacity: 0;
  transform: translateY(10px);
  transition: opacity 0.3s, transform 0.3s;
  z-index: 2;
  position: relative;
}
.product-card:hover .add-cart-bottom {
  opacity: 1;
  transform: translateY(0);
}
.add-cart-bottom >>> .el-button {
  background-color: #81d7ce !important;
  border: none !important;
  color: #fff !important;
  border-radius: 24px !important;
  font-weight: bold;
  font-size: 18px;
  padding: 8px 28px;
  transition: background 0.2s;
}
.add-cart-bottom >>> .el-button:hover {
  background-color: #68c7bd !important;
  color: #fff !important;
}
</style>