<template>
  <div>
    <div style="display: flex; width: 80%; margin: 30px auto;">
      <div style=" flex: 1">
        <div style="display: flex; font-size: 18px; color: #000000FF; line-height: 80px; border-bottom: #cccccc 1px solid;">
          <div style="flex: 3;">我的地址</div>
          <div style="flex: 1; text-align: right;">
            <el-button type="primary" plain @click="addAddress">添加收货地址<i class="el-icon-circle-plus-outline"></i></el-button>
            <el-button type="danger" plain @click="delBatch">批量删除 <i class="el-icon-remove-outline"></i></el-button>
          </div>
        </div>
        <div style="margin: 20px 0; ">
          <div class="table">
            <el-table :data="addressData" strip @selection-change="handleSelectionChange">
              <el-table-column type="selection" width="55" align="center"></el-table-column>
              <el-table-column prop="username" label="收货人" width="100px" align="center"></el-table-column>
              <el-table-column prop="useraddress" label="收货地址" align="center"></el-table-column>
              <el-table-column prop="phone" label="联系电话" align="center"></el-table-column>
              <el-table-column label="操作" align="center" width="180">
                <template v-slot="scope">
                  <el-button size="mini" type="primary" icon="el-icon-edit" plain circle @click="editAddress(scope.row)"></el-button>
                  <el-button size="mini" type="danger" icon="el-icon-delete" plain circle @click="del(scope.row.id)"></el-button>
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
    <el-dialog title="地址信息" :visible.sync="formVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="username" label="收货人">
          <el-input v-model="form.username" autocomplete="off" ></el-input>
        </el-form-item>
        <el-form-item prop="useraddress" label="收货地址">
          <el-input v-model="form.useraddress" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="phone" label="联系电话">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="formVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>  
  </div>
</template>

<script>
export default {
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      addressData: [],
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      total: 0,
      formVisible: false,
      form: {},
      ids: [],
      rules: {
        username: [
          {required: true, message: '请输入收货人', trigger: 'blur'},
        ],
        useraddress: [
          {required: true, message: '请输入收货地址', trigger: 'blur'},
        ],
        phone: [
          {required: true, message: '请输入联系电话', trigger: 'blur'},
        ],
      },
    }
  },
  mounted() {
    this.loadAddress()
  },
  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
    addAddress() {
      this.form = {}
      this.formVisible = true
    },
    editAddress(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.formVisible = true
    },
    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.form.userId = this.user.id
          this.$request({
            url: this.form.id ? '/address/update' : '/address/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {  // 表示成功保存
              this.$message.success('保存成功')
              this.loadAddress()
              this.formVisible = false
            } else {
              this.$message.error(res.msg)  // 弹出错误的信息
            }
          })
        }
      })
    },
    loadAddress() {
      this.$request.get('/address/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          userId: this.user.id
        }
      }).then(res => {
        if (res.code === '200') {
          this.addressData = res.data?.list
          this.total = res.data?.total
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    goTo(url) {
      this.$router.push(url)
    },
    del(id) {
      this.$request.delete('/address/delete/' + id).then(res => {
        if (res.code === '200') {
          this.$message.success('删除成功')
          this.loadAddress()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleSelectionChange(rows) {   // 当前选中的所有的行数据
      this.ids = rows.map(v => v.id)
    },
    delBatch() {   // 批量删除
      if (!this.ids.length) {
        this.$message.warning('请选择数据')
        return
      }
      this.$confirm('您确定批量删除这些数据吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/address/delete/batch', {data: this.ids}).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.loadAddress()
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.loadAddress()
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.loadAddress()
    },
    
  },
}
</script>

<style scoped>
.el-col-5{
  border: 1px solid transparent;
  width: 20%;
  max-width: 20%;
  padding: 10px 10px;
}
.el-col-5:hover{
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
