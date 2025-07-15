<template>
  <div>
    <div style="width: 80%; margin: 30px auto;">
      <div style="display: flex; font-size: 18px; color: #000000FF; line-height: 80px; border-bottom: #cccccc 1px solid;">
        <div style=" flex: 1">全部商品（{{ goodsData.length }}件）</div>
        <div style="flex: 2; text-align: right">
          <el-select v-model="addressId" placeholder="请选择收货地址" style="width: 70%">
            <el-option v-for="item in addressData" :label="item.username + ' - ' + item.useraddress + ' - ' + item.phone" :value="item.id" :key="item.id"></el-option>
          </el-select>
        </div>
      </div>
      <div style="margin: 20px 0;">
        <div class="table">
          <el-table :data="goodsData" strip @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center"></el-table-column>
            <el-table-column label="商品图片" width="120px" align="center">
              <template v-slot="scope">
                <el-image style="width: 80px; height: 60px; border-radius: 3px" v-if="scope.row.goodsImg"
                          :src="scope.row.goodsImg" :preview-src-list="[scope.row.goodsImg]"></el-image>
              </template>
            </el-table-column>
            <el-table-column prop="goodsName" label="商品名称" width="120px" align="center">
              <template v-slot="scope">
                <a href="#" @click="goTo('/front/detail?id=' + scope.row.goodsId)">{{scope.row.goodsName}}</a>
              </template>
            </el-table-column>
            <el-table-column prop="businessName" label="店铺名称" align="center">
              <template v-slot="scope">
                <a href="#" @click="goTo('/front/business?id=' + scope.row.businessId)">{{scope.row.businessName}}</a>
              </template>
            </el-table-column>
            <el-table-column prop="goodsPrice" label="商品价格" align="center"></el-table-column>
            <el-table-column prop="num" label="选择数量" align="center">
              <template v-slot="scope">
                <el-input-number v-model="scope.row.num" style="width: 100px" @change="handleChange(scope.row)" :min="1" :max="scope.row.goodsInventory"></el-input-number>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="180">
              <template v-slot="scope">
                <el-button size="mini" type="danger" icon="el-icon-delete" plain circle @click="del(scope.row.id)"></el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination" style="margin-top: 20px;">
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
          <div style="display: flex; font-size: 16px; text-align: right;">
            <div style="line-height: 40px; flex: 1; margin-right: 20px">合计： ￥ {{totalPrice}}</div>
            <el-button size="medium" type="danger" plain @click="pay">结算</el-button>
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
      goodsData: [],
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      totalPrice: 0,
      total: 0,
      addressId: null,
      addressData: [],
      selectedData: [],
    }
  },
  mounted() {
    this.loadGoods()
    this.loadAddress()
  },
  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
    loadAddress() {
      this.$request.get('/address/selectAll').then(res => {
        if (res.code === '200') {
          this.addressData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    loadGoods() {
      this.$request.get('/cart/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        }
      }).then(res => {
        if (res.code === '200') {
          this.goodsData = res.data?.list

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
        this.$request.delete('/cart/delete/' + id).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.loadGoods()
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.loadGoods()
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.loadGoods()
    },
    handleSelectionChange(rows) {
      this.totalPrice = 0
      this.selectedData = rows
      // 计计算总价格
      this.selectedData.forEach(item => {
        this.totalPrice += (item.goodsPrice * item.num)
      })
    },
    handleChange(row) {
      this.totalPrice = 0
      this.selectedData.forEach(item => {
        this.totalPrice += item.goodsPrice * item.num
      })
    },
    pay() {
      if (!this.addressId) {
        this.$message.warning('请选择收货地址')
        return
      }
      if (!this.selectedData || this.selectedData.length === 0) {
        this.$message.warning('请选择商品')
        return
      }
      let data = {
        userId: this.user.id,
        addressId: this.addressId,
        status: '待发货',
        cartData: this.selectedData
      }
      this.$request.post('/orders/add', data).then(res => {
        if (res.code === '200') {
          this.$message.success('操作成功')
          this.loadGoods()
        } else {
          this.$message.error(res.msg)
        }
      })
      
    }
  }
}
</script>

<style scoped>
.el-col-6{
  border: 1px solid transparent;
  width: 25%;
  max-width: 25%;
  padding: 10px 10px;
}
.el-col-6:hover{
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
