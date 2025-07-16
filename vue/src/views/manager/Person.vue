<template>
  <div>
    <el-card style="width: 50%">
      <el-form :model="editUserData" label-width="100px" style="padding-right: 50px">
        <div style="margin: 15px; text-align: center">
          <el-upload class="avatar-uploader" :action="'/api/files/upload'" :show-file-list="false" name='multipartFile'
            :on-success="handleAvatarSuccess">
            <img v-if="imgUrl" :src="imgUrl" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </div>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="editUserData.username" placeholder="用户名" disabled></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name" v-if="editUserData.role === 'ADMIN'">
          <el-input v-model="editUserData.name" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item label="店铺名" prop="name" v-if="editUserData.role === 'BUSINESS'">
          <el-input v-model="editUserData.name" placeholder="店铺名"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="editUserData.phone" placeholder="电话"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="editUserData.email" placeholder="邮箱"></el-input>
        </el-form-item>
        <el-form-item label="店铺介绍" prop="description" v-if="editUserData.role === 'BUSINESS'">
          <el-input type="textarea" v-model="editUserData.description" placeholder="店铺介绍"></el-input>
        </el-form-item>
        <el-form-item label="审核状态" prop="status" v-if="editUserData.role === 'BUSINESS'">
          <el-input v-model="editUserData.status" placeholder="审核状态" disabled></el-input>
        </el-form-item>
        <div style="text-align: center; margin-bottom: 20px">
          <el-button type="primary" @click="update">保 存</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { fixUrl } from "@/utils/fixUrl";

export default {
  name: "AdminPerson",
  data() {
    return {
      user: '',
      editUserData: {},
      imgUrl: ''
    }
  },
  async mounted() {
    // this.user.avatar = await fixUrl(this.user.avatar);
    await this.loadUser();
  },
  methods: {
    update() {
      // 保存当前的用户信息到数据库
      this.$request.put(this.editUserData.role === 'ADMIN' ? '/admin/update' : '/business/update', this.editUserData).then(res => {
        if (res.code === '200') {
          // 成功更新
          this.$message.success('保存成功')
          this.user = Object.assign({}, this.editUserData);
          // 更新浏览器缓存里的用户信息
          localStorage.setItem('xm-user', JSON.stringify(this.user))

          // 触发父级的数据更新
          this.$emit('update:user')
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    async loadUser() {
      this.$request.get('/business/selectById/' + JSON.parse(localStorage.getItem('xm-user') || '{}').id).then(async res => {
        if (res.code === '200') {
          this.user = res.data;
          this.imgUrl = await fixUrl(this.user.avatar);
          this.editUserData = Object.assign({}, this.user);
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    async handleAvatarSuccess(response, file, fileList) {
      // 把user的头像属性换成上传的图片的链接
      this.$set(this.editUserData, 'avatar', response.data)
      this.imgUrl = await fixUrl(response.data);
    },
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