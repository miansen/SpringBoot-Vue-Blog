<template>
  <el-form :model="adminUserForm" :rules="rules" ref="adminUserForm" label-position="left" label-width="0px" class="demo-ruleForm login-container">
    <h3 class="title">系统后台登录</h3>
    <el-form-item prop="username">
      <el-input type="text" v-model="adminUserForm.username" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input type="password" v-model="adminUserForm.password" placeholder="密码"></el-input>
    </el-form-item>
    <!--<el-checkbox v-model="checked" checked class="remember">记住密码</el-checkbox>-->
    <el-form-item style="width:100%;">
      <el-button type="primary" style="width:100%;" @click.native.prevent="handleSubmit" :loading="logining">登录</el-button>
      <!--<el-button @click.native.prevent="handleReset2">重置</el-button>-->
    </el-form-item>
  </el-form>
</template>

<script>
  import { AdminLoginAPI } from '@/api/admin';
  import {setToken} from '@/request/token';
  //import NProgress from 'nprogress'
  export default {
    data() {
      return {
        logining: false,
        adminUserForm: {
          username: '',
          password: ''
        },
        rules: {
          username: [
            { required: true, message: '请输入账号', trigger: 'blur' },
            {max: 10, message: '不能大于10个字符', trigger: 'blur'}
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            {max: 10, message: '不能大于16个字符', trigger: 'blur'}
          ]
        },
        // checked: true
      };
    },
    methods: {
      handleReset2() {
        this.$refs.adminUserForm.resetFields();
      },
      handleSubmit(ev) {
        let _this = this;
        this.$refs.adminUserForm.validate((valid) => {
          if (valid) {
            //_this.$router.replace('/table');
            this.logining = true;
            //NProgress.start();
            let loginParams = { username: this.adminUserForm.username, password: this.adminUserForm.password };
            AdminLoginAPI(loginParams).then(data => {
              this.logining = false;
              //NProgress.done();
              console.log(data);
              let { code, msg } = data;
              if (code === 0) {
                sessionStorage.setItem('admin_user', data.data.token);
                this.$router.push({ path: '/admin/index' });
              } else {
                this.$message({
                  message: msg,
                  type: 'error'
                });
              }
            });
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      }
    }
  }

</script>

<style lang="scss" scoped>
  .login-container {
    /*box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);*/
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
    .title {
      margin: 0px auto 40px auto;
      text-align: center;
      color: #505458;
    }
    .remember {
      margin: 0px 0px 35px 0px;
    }
  }
</style>
