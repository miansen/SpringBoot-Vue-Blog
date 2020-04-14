<template>
  <div class="me-ct-body" v-title :data-title="title">
    <el-container class="me-ct-container">
      <el-main>
        <div class="me-ct-title me-area">
          <img class="me-ct-picture" :src="ct.avatar"/>
          <h3 class="me-ct-name">{{ct.nickname}}</h3>
          <p>{{ct.account}}</p>
        </div>

        <div class="me-ct-articles">
          <span class="me-ct-meta">我的文章</span>
          <article-scroll-page v-bind="article"></article-scroll-page>
        </div>

      </el-main>
    </el-container>
  </div>
</template>

<script>
    import {getUserInfo} from '@/api/login'
    import ArticleScrollPage from '@/views/common/ArticleScrollPage'
    import defaultAvatar from '@/assets/img/logo.png'

    export default {
        name: 'myInformation',
        components: {
          ArticleScrollPage
        },
        created() {
            this.getUserInfo();
        },
        computed: {
          title() {
            return '个人中心 - For Fun';
          }
        },
        data() {
            return {
              defaultAvatar: defaultAvatar,
              ct: {},
              article: {
                query: {

                }
              },
            }
        },
        methods: {
          getUserInfo() {
            let that = this;
            getUserInfo().then(data => {
              console.log(data);
              that.ct = data.data;
            }).catch(error => {
              if (error !== 'error') {
                that.$message({type: 'error', message: '获取个人信息失败，请先登录', showClose: true});
              }
            })
          },
        }
    }
</script>

<style>
  .me-ct-body {
    margin: 60px auto 140px;
    min-width: 100%;
  }

  .el-main {
    padding: 0;
  }

  .me-ct-title {
    text-align: center;
    height: 150px;
    padding: 20px;
  }

  .me-ct-picture {
    width: 60px;
    height: 60px;
  }

  .me-ct-name {
    font-size: 28px;
  }

  .me-ct-meta {
    font-size: 12px;
    color: #969696;
  }

  .me-ct-articles {
    width: 640px;
    margin: 30px auto;
  }

  .me-ct-picture {
    width: 72px;
    height: 72px;
    border: 1px solid #ddd;
    border-radius: 50%;
    vertical-align: middle;
    background-color: #5fb878;
  }

</style>
