<template>
  <div>
    <el-card style="width: 80%; margin: 50px auto">
      <div style="text-align: right; margin-bottom: 20px">
        <el-button type="primary" @click="updatePassword">修改密码</el-button>
      </div>
      <el-form :model="editUserData" label-width="80px" style="padding: 0 20px" ref="editUserDataRef">
        <div style="margin: 15px; text-align: center">
          <el-upload class="avatar-uploader" name="multipartFile" :action="'/api/files/upload'" :show-file-list="false"
                     :on-success="handleAvatarSuccess">
            <img v-if="editUserData.avatar" :src="imgUrl" class="avatar"/>
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </div>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="editUserData.username" placeholder="用户名" disabled></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="editUserData.name" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="editUserData.phone" placeholder="电话"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="editUserData.email" placeholder="邮箱"></el-input>
        </el-form-item>
        <div style="text-align: center; margin-bottom: 20px">
          <el-button type="primary" @click="update">保 存</el-button>
        </div>
      </el-form>
    </el-card>
    <el-dialog title="修改密码" :visible.sync="dialogVisible" width="30%" :close-on-click-modal="false"
               destroy-on-close>
      <el-form :model="editUserData" label-width="80px" style="padding-right: 20px" :rules="rules"
               ref="editUserDataRef">
        <el-form-item label="原始密码" prop="password">
          <el-input show-password v-model="editUserData.password" placeholder="原始密码"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input show-password v-model="editUserData.newPassword" placeholder="新密码"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input show-password v-model="editUserData.confirmPassword" placeholder="确认密码"></el-input>
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
import {fixUrl} from "@/utils/fixUrl";
import {RouterLink} from "vue-router";

export default {
  data() {
    const validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请确认密码'))
      } else if (value !== this.editUserData.newPassword) {
        callback(new Error('与第一次输入不一致'))
      } else {
        callback()
      }
    }
    return {
      user: '',
      editUserData: {},
      dialogVisible: false,
      imgUrl: '',
      rules: {
        password: [
          {required: true, message: '请输入原始密码', trigger: 'blur'},
        ],
        newPassword: [
          {required: true, message: '请输入新密码', trigger: 'blur'},
        ],
        confirmPassword: [
          {validator: validatePassword, required: true, trigger: 'blur'},
        ],
      }
    }
  },
  async mounted() {
    // this.editUserData = JSON.parse(localStorage.getItem('xm-user') || '{}');
    // this.imgUrl = await fixUrl(this.user.avatar);
    await this.loadUser();
  },
  methods: {
    update() {
      // 保存当前的用户信息到数据库
      this.$request.put('/user/update', this.editUserData).then(res => {
        if (res.code === '200') {
          // 成功更新
          this.$message.success('保存成功')
          this.user = Object.assign(this.user, this.editUserData)
          // 更新浏览器缓存里的用户信息
          localStorage.setItem('xm-user', JSON.stringify(this.user))

          // 触发父级的数据更新
          this.$bus.$emit('updateUser')
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    async loadUser() {
      this.$request.get('/user/selectById/' + JSON.parse(localStorage.getItem('xm-user') || '{}').id).then(async res => {
        if (res.code === '200') {
          this.user = res.data;
          this.imgUrl = await fixUrl(this.user.avatar);
          this.editUserData = JSON.parse(JSON.stringify(this.user)); // 深拷贝
        } else {
          this.$message.error(res.msg);
        }
      });
    },
    async handleAvatarSuccess(response, file, fileList) {
      // 把user的头像属性换成上传的图片的链接
      this.$set(this.editUserData, 'avatar', response.data)
      this.imgUrl = await fixUrl(response.data);
    },
    // 修改密码
    updatePassword() {
      this.dialogVisible = true
    },
    save() {
      this.$refs.editUserDataRef.validate((valid) => {
        if (valid) {
          this.$request.get('/user/updatePassword', {
            params: {
              id: this.editUserData.id,
              password: this.editUserData.password,
              newPassword: this.editUserData.newPassword
            }
          }).then(res => {
            if (res.code === '200') {
              // 成功更新
              this.$message.success('修改密码成功')
              this.$router.push('/login')
              this.editUserData.password = ''
              this.editUserData.newPassword = ''
              this.editUserData.confirmPassword = ''
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>
/deep/ .el-form-item__label {
  font-weight: bold;
}

/deep/ .el-upload {
  border-radius: 50%;
}

/deep/ .avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  border-radius: 50%;
}

/deep/ .avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
  border-radius: 50%;
}

.avatar {
  width: 120px;
  height: 120px;
  display: block;
  border-radius: 50%;
}
</style>