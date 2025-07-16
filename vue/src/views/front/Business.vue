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
            <el-row :gutter="20">
                <el-col :span="6" v-for="item in goodsData" :key="item.id" style="margin-bottom: 24px;">
                    <el-card shadow="hover" :body-style="{ padding: '10px' }">
                        <img :src="item.img" alt=""
                            style="width: 100%; height: 200px; object-fit: cover; border-radius: 6px;">
                        <div style="margin-top: 10px; font-weight: 500;">{{ item.name }}</div>
                        <div style="color: #FF5000; margin: 5px 0;">¥{{ item.price }}/{{ item.unit }}</div>
                        <el-button type="primary" size="mini"
                            @click="goTo('/front/detail?id=' + item.id)">查看详情</el-button>
                    </el-card>
                </el-col>
            </el-row>
            <el-divider>{{ getRandomBottomText() }}</el-divider>
        </el-card>
    </div>

</template>

<script>
import { fixUrl, fixUrlList } from '@/utils/fixUrl';
export default ({
    data() {
        return {
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
