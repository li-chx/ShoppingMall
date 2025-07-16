<template>

  <div>


    
    <div style="display: flex; justify-content: space-between; align-items: center; margin: 15px 20px;">
      <div style="color: #81d7ce; font-weight: bold; font-size: 16px;flex:1">ShoppingMall</div>
      <div style="display: flex;flex:5.4">
        <el-autocomplete
        v-model="searchText"
        :fetch-suggestions="querySearchAsync"
        placeholder="请输入心仪的商品"
        style="width: 100%;"
        prefix-icon="el-icon-search"
        @select="handleSelect"
        @keyup.enter.native="handleSearch"
        :trigger-on-focus="false"
        :debounce="300"
        value-key="value"
        clearable
      >
      <template slot-scope="{ item }">
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>{{ item.value }}</span>
          <span style="color: #999; font-size: 12px;" v-if="item.price">¥{{ item.price }}/{{ item.unit }}</span>
        </div>
      </template>
      <el-button slot="append"  icon="el-icon-search" @click="handleSearch">搜索</el-button>

      </el-autocomplete>
      
      </div>
    </div>
    

    <div v-if="!isSearching" style="display: flex; margin: 0 20px">
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
            <el-carousel-item v-for="(item, index) in carousel_top" :key="index">
              <img :src="item" alt="" style="height: 250px; width: 100%;cursor:pointer"
                @click="goTo(carousel_url[index])">
            </el-carousel-item>
          </el-carousel>
        </div>
        <div style="margin-top: 40px; display: flex">
          <div style="flex: 1; margin-right: 2.5px">
            <el-carousel height="200px" style="border-radius: 10px">
              <el-carousel-item v-for="(item, index) in carousel_left" :key="index">
                <img :src="item" alt="" style="height: 200px; width: 100%; cursor:pointer"
                  @click="goTo(carousel_url[index])">
              </el-carousel-item>
            </el-carousel>
          </div>
          <div style="flex: 1; margin-left: 2.5px">
            <el-carousel height="200px" style="border-radius: 10px">
              <el-carousel-item v-for="(item, index) in carousel_right" :key="index">
                <img :src="item" alt="" style="height: 200px; width: 100%; cursor:pointer"
                  @click="goTo(carousel_url[index])">
              </el-carousel-item>
            </el-carousel>
          </div>
        </div>
      </div>
      <div
        style="flex: 3; height: 490px; background-image: linear-gradient(#a2e0d9 0%, #f7f7f7 100%); margin-left:10px; border-radius: 10px">
        <div style="text-align: center; margin-top: 30px">
          <img :src="user.avatar" alt="" @click="goTo('/front/person')"
            style="width: 80px; height: 80px; border-radius: 50%; cursor: pointer">
          <div style="margin-top:10px; font-size: 16px"><b>你好，{{ user.name }}</b></div>
        </div>
        <div style="margin: 20px 10px;">
          <i class="el-icon-bell"></i>
          <span><b>公告：</b></span>
          <div class="text-overflow-ellipsis" style="color: #666666; width: 300px; margin-top: 5px">{{ notice_content }}
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
          <lottie :options="defaultOptions" :height="200" :width="200" />
        </div>
      </div>
    </div>
    <div
      style="display: flex;  margin: 20px 0 0 20px; height: 40px; font-size: 20px; color: #81d7ce; line-height: 40px; align-items: center;">
      <img src="../../assets/icon/热卖.png" alt="" style="height: 30px; width: 30px;">
      <div style="margin-left: 5px; "><b>{{ isSearching ? '搜索结果' : '热卖商品' }}</b></div>
    </div>
    <div style="margin: 0 20px">
      <!-- 商品展示区域 -->
      <div v-infinite-scroll="loadMore" :infinite-scroll-disabled="loading" :infinite-scroll-distance="100">
        <el-row>
          <el-col :span="5" v-for="(item, index) in visibleGoods" :key="index">
            <img :src="item.img" alt="" style="width: 100%; height: 180px; border-radius: 10px;"
              @click="goTo('/front/detail?id=' + item.id)">
            <div class="text-overflow-ellipsis"
              style="margin-top: 10px; font-weight: 500; font-size: 16px; width: 180px; color: #000000FF;">
              {{ item.name }}
            </div>
            <div style="margin-top: 5px; font-size: 18px; color: #FF5000FF">¥{{ item.price }}/{{ item.unit }}</div>
          </el-col>
        </el-row>

        <!-- 骨架屏加载效果 -->
        <div v-if="loading" style="display: flex; flex-wrap: wrap; margin-top: 20px;">
          <div v-for="n in skeletonCount" :key="'skeleton-' + n" style="width: 20%; padding: 10px;">
            <el-skeleton :loading="loading" animated>
              <template slot="template">
                <el-skeleton-item variant="image" style="width: 100%; height: 180px; border-radius: 10px;" />
                <div style="margin-top: 10px;">
                  <el-skeleton-item variant="text" style="width: 80%;" />
                  <el-skeleton-item variant="text" style="width: 60%; margin-top: 10px;" />
                </div>
              </template>
            </el-skeleton>
          </div>
        </div>
        <el-divider>{{ getRandomBottomText() }}</el-divider>
        <!--        <div v-if="noMore" style="text-align: center; padding: 20px; color: #999;">-->
        <!--          {{ getRandomBottomText() }}-->
        <!--        </div>-->
      </div>
    </div>
  </div>

</template>

<script>
import Lottie from 'vue-lottie';
import * as animationData from '../../assets/video/买买买.json';
import { fixUrl, fixUrlList } from "@/utils/fixUrl";

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
      carousel_url: [
        '/front/category?id=4',
        '/front/category?id=5',
        '/front/category?id=10',
      ],
      defaultOptions: { animationData: animationData.default },
      animationSpeed: 1,
      visibleGoods: [],       // 当前显示的商品
      currentPage: 1,         // 当前页码
      pageSize: 10,           // 每页商品数量 (5行 * 5列)
      loading: false,         // 是否正在加载
      noMore: false,          // 是否已加载全部
      skeletonCount: 10,       // 骨架屏数量
      totalGoods: 0,          // 商品总数

      searchText: '', // 搜索文本
      isSearching: false, // 是否处于搜索状态
      searchSuggestions: [], // 搜索建议列表（从后端动态获取）
    }
  },
  async mounted() {
    this.user.avatar = await fixUrl(this.user.avatar);
    this.loadCategory();
    this.loadNotice();
    this.loadGoods(); // 初始加载第一页商品
    this.loadSearchSuggestions(); // 加载搜索建议
  },
  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {

    // 搜索功能
    handleSearch(){
      console.log('搜索内容:', this.searchText);
      // 如果搜索框为空，显示原来的内容
      if (!this.searchText || this.searchText.trim() === '') {
        this.isSearching = false;
      } else {
        // 如果搜索框有内容，隐藏轮播图等内容
        this.isSearching = true;
      }
    },

    // 搜索建议查询方法
    querySearchAsync(queryString, cb) {
      console.log('查询字符串:', queryString); // 调试用
      
      if (!queryString || queryString.trim() === '') {
        cb([]);
        return;
      }
      
      // 直接调用后端搜索接口获取实时建议
      this.$request.get('/goods/search', {
        params: {
          keyword: queryString.trim()
        }
      }).then(res => {
        if (res.code === '200') {
          // 将搜索结果转换为建议格式
          const suggestions = res.data.map(item => ({
            value: item.name,
            id: item.id,
            price: item.price,
            unit: item.unit
          }));
          
          console.log('实时搜索结果:', suggestions); // 调试用
          
          // 返回搜索建议
          cb(suggestions.slice(0, 10)); // 最多显示10个建议
        } else {
          console.error('搜索失败:', res.msg);
          cb([]);
        }
      }).catch(err => {
        console.error('搜索异常:', err);
        // 如果网络异常，回退到本地建议
        const localResults = this.searchSuggestions.filter(item => {
          return item.value.toLowerCase().includes(queryString.toLowerCase());
        });
        cb(localResults.slice(0, 5));
      });
    },

    // 选择建议项后的处理
    handleSelect(item) {
      this.searchText = item.value;
      this.handleSearch();
    },

    // 加载搜索建议数据（备用建议，用于网络异常时）
    loadSearchSuggestions() {
      // 设置一些基础的备用搜索建议
      this.searchSuggestions = [
        { value: '手机' },
        { value: '电脑' },
        { value: '耳机' },
        { value: '冰箱' },
        { value: '护肤品' },
        { value: '水果' },
        { value: '蔬菜' },
        { value: '汽车' },
        { value: '书籍' },
        { value: '包包' }
      ];
      console.log('备用搜索建议已加载');
    },

    getRandomBottomText() {
      console.log(1);

      const arr = [
        '我也是有底线的!',
        '已经到底啦~',
        '没有更多内容了哦~',
        '别滑啦，真的到底了!',
        '感谢您的浏览!'
      ]
      return arr[Math.floor(Math.random() * arr.length)]
    },
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
    // 加载商品数据（后端分页）
    loadGoods() {
      if (this.loading) return;

      this.loading = true;

      // 请求后端分页数据
      this.$request.get('/goods/selectPage', {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize
        }
      }).then(async res => {
        if (res.code === '200') {
          // 修复图片URL
          const newGoods = await fixUrlList(res.data?.list || [], x => x.img, (x, url) => {
            x.img = url;
            return x;
          });

          this.visibleGoods = [...this.visibleGoods, ...newGoods];
          this.totalGoods = res.data?.total || 0;

          // 检查是否还有更多数据
          this.noMore = this.visibleGoods.length >= this.totalGoods;

          // 更新页码
          if (newGoods.length > 0) {
            this.currentPage++;
          }
        } else {
          this.$message.error(res.msg);
        }

        this.loading = false;
      }).catch(err => {
        console.error('加载商品失败:', err);
        this.loading = false;
      });
    },
    // 滚动加载更多
    loadMore() {
      if (this.loading || this.noMore) return;
      this.loadGoods();
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

/* 自动完成建议框样式 */
</style>