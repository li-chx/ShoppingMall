<template>
  <div>
    <div class="card" style="padding: 15px">
      您好，{{ user?.name }}！欢迎使用本系统
    </div>

    <div style="display: flex; margin: 10px 0">
      <div style="width: 50%;" class="card">
        <div style="margin-bottom: 30px; font-size: 20px; font-weight: bold">公告列表</div>
        <div>
          <el-timeline reverse slot="reference">
            <el-timeline-item v-for="item in notices" :key="item.id" :timestamp="item.time">
              <el-popover placement="right" width="200" trigger="hover" :content="item.content">
                <span slot="reference">{{ item.title }}</span>
              </el-popover>
            </el-timeline-item>
          </el-timeline>
        </div>
      </div>
    </div>

    <!-- 新增：商品销量柱状图 + 订单状态饼图（左右排列） -->
    <div style="display: flex; gap: 20px; margin-top: 20px" v-if="user.role === 'BUSINESS'">
      <!-- 左侧：商品销量柱状图 -->
      <el-card style="flex: 1">
        <div ref="goodsChart" style="width: 100%; height: 400px;"></div>
      </el-card>

      <!-- 右侧：订单状态饼图 -->
      <el-card style="flex: 1">
        <div ref="orderPieChart" style="width: 100%; height: 400px;"></div>
      </el-card>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
export default {
  name: 'Home',
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      notices: [],
      business: '',
      goods: [],
      echartsData: {},
      orders: []
    }
  },
  created() {
    this.$request.get('/notice/selectAll').then(res => {
      this.notices = res.data || []
    })
    if (this.user.role === 'BUSINESS') {
      this.$request.get('/goods/selectAll', {
        params: {
          businessId: this.user.id
        }
      }).then(res => {
        this.goods = res.data || []
        console.log(this.goods);
        this.loadEChartsData_goods()
      })
      this.$request.get('/orders/selectAll', {
        params: {
          businessId: this.user.id,
          userId: 0
        }
      }).then(res => {
        this.orders = res.data || []
        console.log(this.orders);
        this.loadEChartsData_orders()
      })

    }

  },
  methods: {
    loadEChartsData_goods() {
      // 1. 处理数据
      const sortedGoods = [...this.goods].sort((a, b) => b.count - a.count);
      const top5Goods = sortedGoods.slice(0, 5);
      const xAxisData = top5Goods.map(item => item.name);
      const seriesData = top5Goods.map(item => item.count);

      // 2. 初始化 ECharts
      const chart = echarts.init(this.$refs.goodsChart);

      // 3. 配置图表
      const option = {
        title: {
          text: "销量 Top5 商品",
        },
        tooltip: {},
        xAxis: {
          type: "category",
          data: xAxisData,
        },
        yAxis: {
          type: "value",
          name: "销量",
        },
        series: [
          {
            name: "销量",
            type: "bar",
            data: seriesData,
            itemStyle: {
              color: "#409EFF",
            },
          },
        ],
      };

      // 4. 渲染图表
      chart.setOption(option);
    },
    loadEChartsData_orders() {
      // 1. 统计订单状态
      const statusCount = this.orders.reduce((acc, order) => {
        acc[order.status] = (acc[order.status] || 0) + 1;
        return acc;
      }, {});

      // 2. 转换为饼图数据格式
      const pieData = [
        { value: statusCount["待发货"] || 0, name: "待发货" },
        { value: statusCount["已完成"] || 0, name: "已完成" },
      ];

      // 3. 初始化图表
      const chart = echarts.init(this.$refs.orderPieChart);

      // 4. 配置图表
      const option = {
        title: { text: "订单状态分布", left: "center" },
        tooltip: { trigger: "item", formatter: "{a} <br/>{b}: {c} ({d}%)" },
        legend: {
          data: ["待发货", "已完成"],
          right: 20,    // 距离右侧 20px
          top: 20       // 距离顶部 20px
        },
        series: [
          {
            name: "订单状态",
            type: "pie",
            radius: "50%",
            data: pieData,
            label: { formatter: "{b}: {c} ({d}%)" },
          },
        ],
      };

      // 5. 渲染图表
      chart.setOption(option);
    }
  }
}
</script>
