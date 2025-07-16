<template>
  <div>
    <div style="display: flex; width: 80%; margin: 30px auto;">
      <div style="flex: 1; padding: 0 20px">
        <div style="font-size: 18px; color: #000000FF; line-height: 80px; border-bottom: #cccccc 1px solid">
          {{ categoryData.name }}</div>
        <div style="margin: 20px 0">
          <el-row :gutter="20">
            <el-col :span="6" v-for="item in goodsData" :key="item.id">
              <img :src="fixUrl(item.img)" alt="" style="width: 100%; height: 180px; border-radius: 10px;"
                @click="goTo('/front/detail?id=' + item.id)">
              <div class="text-overflow-ellipsis"
                style="margin-top: 10px; font-weight: 500; font-size: 16px; width: 180px; color: #000000FF;">
                {{ item.name }}</div>
              <div style="margin-top: 5px; font-size: 18px; color: #FF5000FF">¥{{ item.price }}/{{ item.unit }}</div>
            </el-col>
          </el-row>
          <el-divider>{{ getRandomBottomText() }}</el-divider>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import Lottie from 'vue-lottie';
import * as animationData from '../../assets/video/买买买.json';
import {fixUrlList} from "@/utils/fixUrl";
export default {
  components: {
    lottie: Lottie
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
  // methods：本页面所有的点击事件或者其他函数定义区
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
      this.$request.get('/goods/selectByCategoryId/' + this.categoryId).then(res => {
        if (res.code === '200') {
          this.goodsData = fixUrlList(res.data, x => x.img, (x,url) => {
            x.img = url;
            return x;
          })
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
.el-col-6 {
  border: 1px solid transparent;
  width: 25%;
  max-width: 25%;
  padding: 10px 10px;
}

.el-col-6:hover {
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
