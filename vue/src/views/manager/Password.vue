<template>
  <div>
    <el-card style="width: 50%">
      <el-form ref="formRef" :model="editUserData" :rules="rules" label-width="100px" style="padding-right: 50px">
        <el-form-item label="原始密码" prop="password">
          <el-input show-password v-model="editUserData.password" placeholder="原始密码"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input show-password v-model="editUserData.newPassword" placeholder="新密码"></el-input>
        </el-form-item>
        <el-form-item label="确认新密码" prop="confirmPassword">
          <el-input show-password v-model="editUserData.confirmPassword" placeholder="确认密码"></el-input>
        </el-form-item>
        <div style="text-align: center; margin-bottom: 20px">
          <el-button type="primary" @click="update">确认修改</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "Password",
  data() {
    const validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请确认密码'))
      } else if (value !== this.user.newPassword) {
        callback(new Error('与第一次输入不一致'))
      } else {
        callback()
      }
    }

    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      editUserData: {},
      rules: {
        password: [
          { required: true, message: '请输入原始密码', trigger: 'blur' },
          {
            validator: (rule, value, callback) => {
              if (value == this.user.password) {
                callback()
              } else {
                callback(new Error('原始密码不正确'))
              }
            },
            trigger: 'blur'
          }
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
        ],
        confirmPassword: [
          { validator: validatePassword, required: true, trigger: 'blur' },
        ],
      }
    }
  },
  created() {
    this.editUserData = JSON.parse(JSON.stringify(this.user)); // 深拷贝用户数据
  },
  methods: {
    update() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          const url = this.user.role === 'ADMIN' ? '/admin/updatePassword' : '/business/updatePassword';
          this.$request.get(url, {
            params: {
              id: this.user.id,
              password: this.user.password,
              newPassword: this.user.newPassword
            }
          } ).then(res => {
            if (res.code === '200') {
              // 成功更新
              localStorage.removeItem('xm-user')   // 清除缓存的用户信息
              this.$message.success('修改密码成功')
              this.$router.push('/login')
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
  }
}
</script>

<style scoped>
/deep/.el-form-item__label {
  font-weight: bold;
}
</style>