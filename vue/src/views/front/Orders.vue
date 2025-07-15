<template>
  <div>
    <div style=" width: 80%; margin: 30px auto;">
      <div style="font-size: 18px; color: #000000FF; line-height: 80px; border-bottom: #cccccc 1px solid; display: flex">
        <div style="flex: 1">我的订单（{{ ordersData.length }} 个）</div>
        <div class="search">
          <el-input placeholder="请输入商品名称查询" style="width: 200px" suffix-icon="el-icon-search" v-model="goodsName"></el-input>
          <el-button type="success" plain style="margin-left: 10px" @click="loadOrders()">查询</el-button>
        </div>
      </div>
      
      <div style="margin: 20px 0;">
        <div class="table">
          <el-table :data="ordersData" strip>
            <el-table-column label="商品图片" width="120px" align="center">
              <template v-slot="scope">
                <el-image style="width: 80px; height: 60px; border-radius: 3px" v-if="scope.row.goodsImg"
                          :src="fixUrl(scope.row.goodsImg)" :preview-src-list="[fixUrl(scope.row.goodsImg)]"></el-image>
              </template>
            </el-table-column>
            <el-table-column prop="orderId" label="订单编号" align="center"></el-table-column>
            <el-table-column prop="goodsName" label="商品名称" :show-overflow-tooltip="true" align="center">
              <template v-slot="scope">
                <a href="#" @click.prevent="goTo('/front/detail?id=' + scope.row.goodsId)">{{scope.row.goodsName}}</a>
              </template>
            </el-table-column>
            <el-table-column prop="businessName" label="店铺名称" align="center">
              <template v-slot="scope">
                <a href="#" @click.prevent="'/front/business?id=' + scope.row.businessId">{{scope.row.businessName}}</a>
              </template>
            </el-table-column>
            <el-table-column prop="goodsPrice" label="商品价格" align="center">
              <template v-slot="scope">
                {{scope.row.goodsPrice}} / {{scope.row.goodsUnit}}
              </template>
            </el-table-column>
            <el-table-column prop="num" label="商品数量" align="center"></el-table-column>
            <el-table-column prop="price" label="订单总价" align="center"></el-table-column>
            <el-table-column prop="username" label="收货人" align="center"></el-table-column>
            <el-table-column prop="useraddress" label="收货地址" align="center"></el-table-column>
            <el-table-column prop="phone" label="联系电话" align="center"></el-table-column>
            <el-table-column prop="status" label="订单状态" align="center"></el-table-column>
            <el-table-column label="操作" align="center" width="180">
              <template v-slot="scope">
                <el-button size="mini" type="primary" v-if="scope.row.status === '待收货'" plain @click="updateStatus(scope.row, '已完成')">确认收货</el-button>
                <el-button size="mini" type="danger" plain @click="del(scope.row.id)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination" style="margin-top: 20px">
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="pageNum"
              :page-sizes="[5, 10, 20]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total">
            </el-pagination>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      ordersData: [],
      goodsName: null,
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      total: 0,
      form: {}
    }
  },
  mounted() {
    this.loadOrders()
  },
  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
    fixUrl(url) {
      if (!url) return '';
      if (url.startsWith('http')) return url;
      return '/api' + url;
    },
    loadOrders() {
      this.$request.get('/orders/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          goodsName: this.goodsName
        }
      }).then(res => {
        if (res.code === '200') {
          this.ordersData = res.data?.list
          this.total = res.data?.total
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    goTo(url) {
      location.href = url
    },
    del(id) {
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/orders/delete/' + id).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('删除成功')
            this.loadOrders()
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.loadOrders()
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.loadOrders()
    },
    updateStatus(row, status) {
      this.form = row
      this.form.status = status
      this.$request.put('/orders/update', this.form).then(res => {
        if (res.code === '200') {
          this.$message.success('操作成功')
        } else {
          this.$message.error(res.msg)
        }
      })
    }
  }
}
</script>

<style scoped>
a {
    color: #666;
}
a:hover {
    color: red;
}
</style>>
  
</style>
