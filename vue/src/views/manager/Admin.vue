<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入账号查询" style="width: 200px" suffix-icon="el-icon-search"
                v-model="username"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load()">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation">
      <el-button type="primary" plain @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-button type="danger" plain @click="delBatch">批量删除 <i class="el-icon-remove-outline"></i></el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" strip @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="70" align="center" sortable></el-table-column>
        <el-table-column prop="username" label="账号" align="center"></el-table-column>
        <el-table-column prop="name" label="姓名" align="center"></el-table-column>
        <el-table-column prop="phone" label="电话" align="center"></el-table-column>
        <el-table-column prop="email" label="邮箱" align="center"></el-table-column>
        <el-table-column label="头像" align="center">
          <template v-slot="scope">
            <div style="display: flex; align-items: center; justify-content: center;">
              <el-image style="width: 40px; height: 40px; border-radius: 50%" v-if="scope.row.avatar"
                        :src="imgUrlMap[scope.row.id]" :preview-src-list="[imgUrlMap[scope.row.id]]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="role" label="角色" align="center"></el-table-column>
        <el-table-column label="操作" align="center" width="180">
          <template v-slot="scope">
            <el-button size="mini" type="primary" icon="el-icon-edit" plain circle
                       @click="handleEdit(scope.row)"></el-button>
            <el-button size="mini" type="danger" icon="el-icon-delete" plain circle
                       @click="del(scope.row.id)"></el-button>
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


    <el-dialog title="管理员" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" placeholder="密码" show-password></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="form.confirmPassword" placeholder="确认密码" show-password></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone" placeholder="电话"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="邮箱"></el-input>
        </el-form-item>
        <el-form-item label="头像">
          <el-upload class="avatar-uploader" name="multipartFile" :action="'/api/files/upload'" :show-file-list="false"
                     :headers="{ token: user.token }"
                     list-type="picture" :on-success="handleAvatarSuccess">
            <el-button type="primary">上传头像</el-button>
            <img v-if="imgUrl" :src="imgUrl" class="avatar" style="width: 120px; height: 120px; display: block;"/>
          </el-upload>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>

import {fixUrl, fixUrlList} from "@/utils/fixUrl";

export default {
  name: "Admin",
  data() {
    return {
      imgUrl: '',
      imgUrlMap: {},
      editUserData: {},
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 5,  // 每页显示的个数
      total: 0,
      username: null,
      fromVisible: false,
      form: {},
      confirmPassword: '',  // 确认密码
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        username: [
          {required: true, message: '请输入账号', trigger: 'blur'},
        ],
        password: [
          {
            pattern: /^[A-Za-z0-9!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]{1,10}$/,
            message: '密码为1-10位数字或英文符号',
            trigger: 'blur'
          }
        ],
        confirmPassword: [
          {
            validator: (rule, value, callback) => {
              if (value === undefined && this.form.password === null)
                callback();
              if (value !== this.form.password) {
                callback(new Error('两次输入的密码不一致'));
                // this.$message.error('两次输入的密码不一致');
              } else {
                callback();
              }
            }, trigger: 'blur'
          }
        ]
      },
      ids: []
    }
  },
  created() {
    this.load()
  },
  methods: {
    handleAdd() {   // 新增数据
      this.form = {}  // 新增数据的时候清空数据
      this.fromVisible = true   // 打开弹窗
    },
    handleEdit(row) {   // 编辑数据
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.imgUrl = this.imgUrlMap[row.id];
      this.fromVisible = true;   // 打开弹窗
    },
    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          const submitForm = {...this.form}
          delete submitForm.confirmPassword  // 删除确认密码字段
          if(submitForm.role === undefined)
            submitForm.role = 'ADMIN';  // 如果没有设置角色，默认设置为管理员角色
          this.$request({
            url: this.form.id ? '/admin/update' : '/admin/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: submitForm
          }).then(res => {
            if (res.code === '200') {  // 表示成功保存
              this.$message.success('保存成功')
              this.load()
              this.fromVisible = false
              if(submitForm.id === this.user.id) {
                // 如果是编辑当前登录用户的信息，更新本地存储的用户信息
                const updatedUser = {...this.user, ...submitForm};
                localStorage.setItem('xm-user', JSON.stringify(updatedUser));
              }
            } else {
              this.$message.error(res.msg)  // 弹出错误的信息
            }
          })
        }
      })
    },
    del(id) {   // 单个删除
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/admin/delete/' + id).then(res => {
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
      this.ids = rows.map(v => v.id)
    },
    delBatch() {   // 批量删除
      if (!this.ids.length) {
        this.$message.warning('请选择数据')
        return
      }
      this.$confirm('您确定批量删除这些数据吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/admin/delete/batch', {data: this.ids}).then(res => {
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
      this.$request.get('/admin/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
        }
      }).then(async res => {
        const imgUrlMap = {};
        (await fixUrlList(res.data?.list, x => x.avatar, (x, url) => [x.id, url]))
            .forEach(id_url => imgUrlMap[id_url[0]] = id_url[1]);
        this.imgUrlMap = imgUrlMap;
        this.tableData = res.data?.list;
        this.total = res.data?.total;
        // this.tableData = await fixUrlList(res.data?.list, x => x.avatar, (x, url) => {
        //   x.avatar = url
        //   return x;
        // });
        // this.total = res.data?.total
      })
    },
    reset() {
      this.username = null
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
    async handleAvatarSuccess(response, file, fileList) {
      // 把头像属性换成上传的图片的链接
      this.form.avatar = response.data;
      this.imgUrl = await fixUrl(response.data);
      // this.form.avatar = response.data
    },
  }
}
</script>

<style scoped></style>