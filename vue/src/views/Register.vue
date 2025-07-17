<template>
  <div class="container">
    <div style="width: 400px; padding: 30px; background-color: white; border-radius: 20px;">
      <div style="text-align: center; font-size: 20px; margin-bottom: 20px; color: #333">注  册</div>
      <el-form :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="username">
          <el-input prefix-icon="el-icon-user" placeholder="请输入账号" v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon="el-icon-lock" placeholder="请输入密码" show-password  v-model="form.password"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPass">
          <el-input prefix-icon="el-icon-lock" placeholder="请确认密码" show-password  v-model="form.confirmPass"></el-input>
        </el-form-item>
        <el-form-item prop="email">
          <div style="display: flex; align-items: center">
            <el-input prefix-icon="el-icon-message" placeholder="请输入邮箱" v-model="form.email" style="flex: 4;"></el-input>
            <el-button :disabled="codeBtnDisabled" style="width: 100%; background-color: #333; border-color: #333; color: white; margin-left: 10px; flex: 1;" @click="getCode">{{ codeBtnText }}</el-button>
          </div>
        </el-form-item>
        <el-form-item prop="identifyingCode">
          <el-input placeholder="请输入验证码" v-model="form.identifyingCode"></el-input>
        </el-form-item>
        <el-form-item prop="role">
          <el-select v-model="form.role" placeholder="请选择角色" style="width: 100%">
            <el-option label="商家" value="BUSINESS"></el-option>
            <el-option label="用户" value="USER"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%; background-color: #333; border-color: #333; color: white" @click="register">注 册</el-button>
        </el-form-item>
        <div style="display: flex; align-items: center">
          <div style="flex: 1"></div>
          <div style="flex: 1; text-align: right">
            已有账号？请 <a href="/login">登录</a>
          </div>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Register",
  data() {
    // 验证码校验
    const validatePassword = (rule, confirmPass, callback) => {
      if (confirmPass === '') {
        callback(new Error('请确认密码'))
      } else if (confirmPass !== this.form.password) {
        callback(new Error('两次输入的密码不一致'))
      } else {
        callback()
      }
    }
    return {
      form: {},
      codeBtnDisabled: false,
      codeBtnText: '获取验证码',
      codeTimer: null,
      codeCountdown: 60,
      rules: {
        username: [
          { required: true, message: '请输入账号', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
        role: [
          { required: true, message: '请选择角色', trigger: 'blur' },
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
        ],
        confirmPass: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          { validator: validatePassword, trigger: 'blur' }
        ],
        identifyingCode: [
          { required: true, message: '请输入验证码', trigger: 'blur' }
        ]
      }
    }
  },
  created() {

  },
  methods: {
    getCode() {
      this.$refs['formRef'].validateField('email', (valid) => {
        if (valid === undefined || valid.length === 0) {
          // 验证通过
          this.$request.get('/sendCode/REGISTER', { params: { email: this.form.email } }).then(res => {
            if (res.code === '200') {
              this.$message.success('验证码已发送，请注意查收')
              this.codeBtnDisabled = true;
              this.codeBtnText = '60s后可重新获取';
              this.codeCountdown = 60;
              this.codeTimer = setInterval(() => {
                this.codeCountdown--;
                this.codeBtnText = `${this.codeCountdown}s后可重新获取`;
                if (this.codeCountdown <= 0) {
                  clearInterval(this.codeTimer);
                  this.codeBtnDisabled = false;
                  this.codeBtnText = '获取验证码';
                }
              }, 1000);
            } else {
              this.$message.error(res.msg)
            }
          })
        } else {
          this.$message.error('请输入正确的邮箱地址')
        }
      })
    },
    register() {
      this.$refs['formRef'].validate((valid) => {
        if (valid) {
          // 验证通过
          this.$request.post('/register', this.form, {params: {email: this.form.email, identifyingCode: this.form.identifyingCode}}).then(res => {
            if (res.code === '200') {
              this.$router.push('/login')  // 跳转登录页面
              this.$message.success('注册成功')
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
.container {
  height: 100vh;
  overflow: hidden;
  background-repeat: no-repeat;
  background-color: #81d7ce;
  background-image: url("@/assets/imgs/购物.svg");
  background-size: contain;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #666;
}
a {
  color: #2a60c9;
}
</style>