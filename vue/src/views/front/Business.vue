<template>

    <div style="max-width: 1100px; margin: 40px auto;">
        <!-- 商家信息卡片 -->
        <el-card shadow="hover" style="margin-bottom: 30px;">
            <div style="display: flex; align-items: center;">
                <el-avatar :src="business.avatar" :size="100" style="margin-right: 24px;"></el-avatar>
                <div style="flex: 1;">
                    <div style="font-size: 22px; font-weight: bold;">{{ business.name }}</div>
                    <div style="color: #888; margin: 8px 0;">{{ business.description }}</div>
                    <el-tag :type="statusType()" size="medium">{{ business.status }}</el-tag>
                </div>
            </div>
        </el-card>

        <!-- 商品列表 -->
        <el-card>
            <div slot="header" style="font-size: 18px; font-weight: 500;">商家商品</div>
            <div class="business-goods-flex-row">
                <div class="business-product-card" v-for="item in goodsData" :key="item.id" @click="goTo('/front/detail?id=' + item.id)">
                    <div class="business-product-image-wrapper">
                        <img :src="item.img" alt="" class="business-product-image">
                    </div>
                    <div class="text-overflow-ellipsis" style="margin-top: 10px; font-weight: 500; font-size: 16px; width: 180px; color: #000000FF;">
                        {{ item.name }}
                    </div>
                    <div style="margin-top: 5px; font-size: 18px; color: #FF5000FF">¥{{ item.price }}/{{ item.unit }}</div>
                    <div class="business-add-cart-bottom" @click.stop>
                        <el-button type="primary" size="mini" icon="el-icon-shopping-cart" @click="addCart(item.id, item.businessId)">加入购物车</el-button>
                    </div>
                </div>
            </div>
            <el-divider>{{ getRandomBottomText() }}</el-divider>
        </el-card>
    </div>

</template>

<script>
import { fixUrl, fixUrlList } from '@/utils/fixUrl';
export default ({
    data() {
        return {
            user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
            businessId: null, // 商家ID
            business: {}, // 商家信息
            goodsData: '', // 商品列表
            goodsName: '', // 商品名称搜索
            pageNum: 1, // 当前页码
            pageSize: 10, // 每页显示数量

        };
    },
    methods: {
        getRandomBottomText() {
            const arr = [
                '我也是有底线的!',
                '已经到底啦~',
                '没有更多内容了哦~',
                '别滑啦，真的到底了!',
                '感谢您的浏览!'
            ]
            return arr[Math.floor(Math.random() * arr.length)]
        },
        // 加载商品数据（后端分页）
        loadGoods() {
            // 请求后端分页数据
            this.$request.get('/goods/selectPage', {
                params: {
                    pageNum: this.currentPage,
                    pageSize: this.pageSize,
                    businessId: this.businessId,
                    name: this.goodsName,
                }
            }).then(async res => {
                if (res.code === '200') {
                    // 修复图片URL
                    this.goodsData = await fixUrlList(res.data?.list || [], x => x.img, (x, url) => {
                        x.img = url;
                        return x;
                    });
                } else {
                    this.$message.error(res.msg);
                }

                this.loading = false;
            }).catch(err => {
                console.error('加载商品失败:', err);
                this.loading = false;
            });
        },
        loadBusiness() {
            this.$request.get('/business/selectById/' + this.businessId
            ).then(async res => {
                if (res.code === '200') {
                    res.data.avatar = await fixUrl(res.data.avatar);
                    this.business = res.data;
                } else {
                    this.$message.error(res.msg);
                }
            });
        },
        statusType() {
            return this.business.status == '审核通过' ? 'success' : '审核中' ? 'warning' : 'danger'
        },
        goTo(url) {
            this.$router.push(url);
        },
        addCart(goodsId, businessId) {
            let data = { num: 1, userId: this.user?.id, goodsId: goodsId, businessId: businessId };
            this.$request.post('/cart/add', data).then(res => {
                if (res.code === '200') {
                    this.$message.success('加入成功');
                } else {
                    this.$message.error(res.msg);
                }
            });
        }
    },
    created() {
        this.businessId = this.$route.query.id
    },
    async mounted() {
        await this.loadBusiness();
        await this.loadGoods();
    }
})
</script>

<style scoped>
.business-goods-flex-row {
  display: flex;
  flex-wrap: wrap;
  gap: 20px 0;
}
.business-product-card {
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
  transition: box-shadow 0.4s cubic-bezier(.4,0,.2,1), transform 0.3s;
  width: calc(20% - 16px);
  margin-right: 20px;
  margin-bottom: 20px;
  background: #fff;
  position: relative;
  overflow: visible;
  box-sizing: border-box;
  padding: 10px 10px;
}
.business-goods-flex-row .business-product-card:nth-child(5n) {
  margin-right: 0;
}
.business-product-card:hover {
  box-shadow:
    0 2px 8px rgba(0,0,0,0.08),
    0 16px 48px 0 rgba(129,215,206,0.18),
    0 32px 64px 0 rgba(0,0,0,0.22);
  transform: translateY(-5px);
}
.business-product-card::after {
  content: '';
  display: block;
  position: absolute;
  left: 0;
  right: 0;
  bottom: 0;
  height: 50%;
  pointer-events: none;
  opacity: 0;
  transition: opacity 0.3s;
  border-radius: 0 0 10px 10px;
  background: linear-gradient(to bottom, rgba(129,215,206,0) 0%, rgba(0,0,0,0.18) 100%);
  z-index: 1;
}
.business-product-card:hover::after {
  opacity: 1;
}
.business-product-image-wrapper {
  overflow: hidden;
  border-radius: 10px;
  position: relative;
}
.business-product-image {
  width: 100%;
  height: 180px;
  border-radius: 10px;
  transition: transform 0.3s ease;
  object-fit: cover;
}
.business-product-image:hover {
  transform: scale(1.1);
}
.business-add-cart-bottom {
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
.business-product-card:hover .business-add-cart-bottom {
  opacity: 1;
  transform: translateY(0);
}
.business-add-cart-bottom >>> .el-button {
  background-color: #81d7ce !important;
  border: none !important;
  color: #fff !important;
  border-radius: 24px !important;
  font-weight: bold;
  font-size: 18px;
  padding: 8px 28px;
  transition: background 0.2s;
}
.business-add-cart-bottom >>> .el-button:hover {
  background-color: #68c7bd !important;
  color: #fff !important;
}
.text-overflow-ellipsis {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
