<template>
  <el-header class="me-area">
    <el-row class="me-header">

      <el-col :span="4" class="me-header-left">
        <router-link to="/" class="me-title">
          <img src="../assets/background/biao.png"/>
        </router-link>
      </el-col>

      <el-col v-if="!simple" :span="16">
        <el-menu  text-color="#fff" background-color="#458A5B" :router=true menu-trigger="click" active-text-color="#5FB878" :default-active="activeIndex"
                 mode="horizontal">
          <el-menu-item index="/">首页</el-menu-item>
          <el-menu-item index="/category/all">分类</el-menu-item>
          <el-menu-item index="/tag/all">标签</el-menu-item>
          <el-menu-item index="/archives">归档</el-menu-item>
          <el-menu-item index="/log">日志</el-menu-item>
          <el-menu-item index="/admin/login">后台登录</el-menu-item>
          <el-menu-item>
            <el-input v-model="input" size="mini" placeholder="请输入内容" @keydown.enter.native="search(input)">
              <el-button slot="append" size="mini" icon="el-icon-search" @click="search(input)"></el-button>
            </el-input>
          </el-menu-item>
          <!--<el-menu-item index="/messageBoard">留言板</el-menu-item>-->

          <el-col :span="4" :offset="4" style="margin-left: 0px;">
            <el-menu-item index="/write"><i class="el-icon-edit"></i>写文章</el-menu-item>
          </el-col>

        </el-menu>
      </el-col>

      <template v-else>
        <slot></slot>
      </template>

      <el-col :span="4">
        <el-menu text-color="#fff" background-color="#458A5B" :router=true menu-trigger="click" mode="horizontal" active-text-color="#5FB878">

          <template v-if="!user.login">
            <el-menu-item index="/login">
              <el-button type="text">登录</el-button>
            </el-menu-item>
            <el-menu-item index="/register">
              <el-button type="text">注册</el-button>
            </el-menu-item>
          </template>

          <template v-else>
            <el-submenu index>
              <template slot="title">
                <img class="me-header-picture" :src="user.avatar"/>
              </template>



               <el-menu-item index  @click="myquanquanclick">我的圈圈</el-menu-item>
              <el-menu-item index @click="mywenzhangclick">我的文章</el-menu-item>
              <el-menu-item index @click="logout"><i class="el-icon-back"></i>退出</el-menu-item>
            </el-submenu>
          </template>
        </el-menu>
      </el-col>

    </el-row>
  </el-header>
</template>

<script>
  export default {
    name: 'BaseHeader',
    props: {
      activeIndex: String,
      simple: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        input: ''
      }
    },
    computed: {
      user() {
        let login = this.$store.state.account.length != 0
        let avatar = this.$store.state.avatar
        let id = this.$store.state.id
        return {
          login, avatar, id
        }
      }
    },
    inject: [
      'reload'
    ],
    methods: {
      logout() {
        let that = this
        this.$store.dispatch('logout').then(() => {
          this.$router.push({path: '/'})
        }).catch((error) => {
          if (error !== 'error') {
            that.$message({message: error, type: 'error', showClose: true});
          }
        })
      },
        mywenzhangclick() {
          console.error(this.$store.state.id)
            this.$router.push({path: `/mywenzhang/${this.$store.state.id}`})
        },
        myquanquanclick() {
          console.error(this.$store.state.id)
            this.$router.push({path: `/myquanquan/all`})
        },
      search(input) {
        if (input) {
          console.log(this);
          this.$router.push({path: `/search?key=${input}`});
          this.reload();
        }
      }
    }
  }
</script>

<style>
  .el-header {
    position: fixed;
    z-index: 1024;
    min-width: 100%;
    box-shadow: 0 2px 3px hsla(0, 0%, 7%, .1), 0 0 0 1px hsla(0, 0%, 7%, .1);
    background: #458A5B;
  }

  .me-title {
    margin-top: 10px;
    font-size: 24px;
  }

  .me-header-left {
    margin-top: 10px;
  }

  .me-title img {
    max-height: 2.4rem;
    max-width: 100%;
  }

  .me-header-picture {
    width: 36px;
    height: 36px;
    border: 1px solid #ddd;
    border-radius: 50%;
    vertical-align: middle;
    background-color: #5fb878;
  }
</style>
