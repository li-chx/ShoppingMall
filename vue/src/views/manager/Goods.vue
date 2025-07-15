<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入商品名称查询" style="width: 200px" suffix-icon="el-icon-search" v-model="name"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load()">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation" v-if="user.role === 'BUSINESS'">
      <el-button type="primary" plain @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-button type="danger" plain @click="delBatch">批量删除 <i class="el-icon-remove-outline"></i></el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="80" align="center" sortable></el-table-column>
        <el-table-column label="商品主图" align="center">
          <template v-slot="scope">
            <div style="display: flex; align-items: center; justify-content: center;">
              <el-image style="width: 40px; height: 40px;" v-if="scope.row.img" :src="fixUrl(scope.row.img)"
                :preview-src-list="[scope.row.img]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="商品名称" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column prop="description" label="商品描述" align="center">
          <template v-slot="scope">
            <el-button type="success" plain @click="viewEditor(scope.row.description)">查看</el-button>
          </template>
        </el-table-column>
        <el-table-column prop="price" label="商品价格" align="center"></el-table-column>
        <el-table-column prop="unit" label="计价单位" align="center"></el-table-column>
        <el-table-column prop="categoryName" label="商品分类" align="center"></el-table-column>
        <el-table-column prop="inventory" label="商品库存" align="center"></el-table-column>
        <el-table-column prop="businessName" label="所属商家" align="center"></el-table-column>
        <el-table-column prop="count" label="商品销量" align="center"></el-table-column>

        <el-table-column label="操作" width="180" align="center" v-if="user.role === 'BUSINESS'">
          <template v-slot="scope">
            <el-button plain circle type="primary" @click="handleEdit(scope.row)" size="mini"
              icon="el-icon-edit"></el-button>
            <el-button plain circle type="danger" size="mini" @click=del(scope.row.id)
              icon="el-icon-delete"></el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
          :page-sizes="[5, 10, 20]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </div>
    </div>


    <el-dialog title="信息" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close
      @close="cancel">
      <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
        <el-form-item label="商品主图">
          <el-upload class="avatar-uploader" :action="'/api/files/upload'" :headers="{ token: user.token }"
            list-type="picture" :on-success="handleAvatarSuccess">
            <el-button type="primary">上传图片</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item prop="name" label="商品名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="price" label="商品价格">
          <el-input v-model="form.price" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="unit" label="计件单位">
          <el-input v-model="form.unit" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="inventory" label="商品库存">
          <el-input v-model="form.inventory" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="categoryId" label="商品分类">
          <el-select v-model="form.categoryId" placeholder="请选择商品分类" style="width: 100%">
            <el-option v-for="item in categoryData" :label="item.name" :value="item.id" :key="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="businessId" label="所属商家" v-if="user.role === 'ADMIN'">
          <el-select v-model="form.businessId" placeholder="请选择商品所属商家" style="width: 100%">
            <el-option v-for="item in businessData" :label="item.name" :value="item.id" :key="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="description" label="商品介绍">
          <div id="editor" style="width: 100%"></div>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="商品介绍" :visible.sync="editorVisible" width="50%">
      <div v-html="this.viewData" class="w-e-text"></div>
    </el-dialog>
  </div>
</template>

<script>
import E from 'wangeditor'

let editor
function initWangEditor(content) {
  setTimeout(() => {
    if (!editor) {
      editor = new E('#editor')
      editor.config.placeholder = '请输入内容'
      editor.config.uploadFileName = 'file'
      editor.config.uploadImgServer = '/api/files/wang/upload'
      editor.create()
    }
    editor.txt.html(content)
  }, 0)
}


export default {
  name: "Goods",
  data() {
    return {
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 5,  // 每页显示的个数
      total: 0,
      name: null,
      fromVisible: false,
      editorVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        name: [
          { required: true, message: '请输入商品名称', trigger: 'blur' },
        ],
        img: [
          { required: true, message: '请上传商品主图', trigger: 'blur' },
        ]
      },
      ids: [],
      categoryData: [],
      businessData: [],
      businessStatus: null,
      viewData: null
    }
  },
  created() {
    this.load()
    //this.loadCategoryOrBusiness()
  },
  // mounted() {
  //   initWangEditor('')
  // },
  methods: {
    fixUrl(url) {
      if (!url) return '';
      if (url.startsWith('http')) return url;
      return '/api' + url;
    },
    handleAdd() {   // 新增数据
      if (this.user.status !== '审核通过' && this.user.role === 'BUSINESS') {
        this.$message.warning("您的店铺信息尚未审核通过，暂时不允许新增商品")
        return
      }
      this.form = {}  // 新增数据的时候清空数据
      initWangEditor('')
      this.fromVisible = true   // 打开弹窗
    },
    handleEdit(row) {   // 编辑数据
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      initWangEditor(this.form.description || '')
      this.fromVisible = true   // 打开弹窗
    },
    async findBusinessStatus(id) {
      const res = await this.$request.get('/business/selectById/' + id)
      if (res.code === '200') {
        console.log(res.data);

        this.businessStatus = res.data.status
      }
    },
    async save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      await this.findBusinessStatus(this.form.businessId)

      if (this.user.role === 'ADMIN' && this.businessStatus !== '审核通过') {
        console.log(this.businessStatus);

        this.$message.warning("该店铺信息尚未审核通过，请先审核通过后再增加商品")
        return
      }

      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.form.description = editor.txt.html()
          this.$request({
            url: this.form.id ? '/goods/update' : '/goods/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {  // 表示成功保存
              this.$message.success('保存成功')
              // this.load()
              this.fromVisible = false
              location.href = '/goods'
            } else {
              this.$message.error(res.msg)  // 弹出错误的信息
            }
          })
        }
      })
    },
    cancel() {
      this.fromVisible = false
      location.href = '/goods'
    },
    del(id) {   // 单个删除
      this.$confirm('您确定删除吗？', '确认删除', { type: "warning" }).then(response => {
        this.$request.delete('/goods/delete/' + id).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load()
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    handleSelectionChange(rows) {   // 当前选中的所有的行数据
      this.ids = rows.map(v => v.id)   //  [1,2]
    },
    delBatch() {   // 批量删除
      if (!this.ids.length) {
        this.$message.warning('请选择数据')
        return
      }
      this.$confirm('您确定批量删除这些数据吗？', '确认删除', { type: "warning" }).then(response => {
        this.$request.delete('/goods/delete/batch', { data: this.ids }).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load()
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    load() {  // 分页查询
      this.$request.get('/goods/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          businessId: this.user.role === 'BUSINESS' ? this.user.id : null
        }
      }).then(res => {
        console.log(res);

        this.tableData = res.data?.list
        this.total = res.data?.total
      })
    },
    reset() {
      this.name = null
      this.load()
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },
    handleAvatarSuccess(response, file, fileList) {
      this.form.img = response.data
    },
    loadCategoryOrBusiness() {
      this.$request.get('/category/selectAll').then(res => {
        if (res.code === '200') {
          console.log(res.data);
          this.categoryData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })

      this.$request.get('/business/selectAll').then(res => {
        if (res.code === '200') {
          console.log(res.data);
          this.businessData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    viewEditor(content) {
      this.viewData = content
      this.editorVisible = true
    }
  }
}
</script>

<style scoped></style>
