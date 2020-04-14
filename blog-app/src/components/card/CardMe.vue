<template>
  <el-card>
    <h1 class="me-author-name">“圈圈”好友交流网</h1>
    <div class="me-author-description">
      <span><i class="el-icon-location-outline"></i> &nbsp;中国</span>
      <span><i class="me-icon-job"></i> &nbsp;vue</span>
    </div>
    <div class="me-author-tool">
      <el-button type="success" icon="el-icon-no-smoking" round @click="integral">积分排行</el-button>
      <el-button type="success" icon="el-icon-no-smoking" round @click="information" v-if="this.$store.state.id">个人中心</el-button>
      <!--<i @click="integral" :title="qq.title" class="me-icon-QQ"></i>-->
      <!--<i @click="showTool(github)" :title="github.title" class="me-icon-github"></i>-->
    </div>
  </el-card>

</template>

<script>
  import integralPNG from '@/assets/img/integral.png';
  export default {
    name: 'CardMe',
    data() {
      return {
        qq: {title: 'QQ', message: '123456'},
        github: {
          title: '首页',
          message: '<a target="_blank" href="http://localhost:8081/#/">http://localhost:8081/#/</a>'
        },
        integralPNG: integralPNG,
        small: "small"
      }
    },
    methods: {
      integral() {
        this.$router.push({path: `/integral`});
      },
      information() {
        this.$router.push({path: `/information/${this.$store.state.id}`});
      }
    }
  }
</script>

<style scoped>
  .me-author-name {
    text-align: center;
    font-size: 30px;
    border-bottom: 1px solid #5FB878;
  }

  .me-author-description {
    padding: 8px 0;
  }

  .me-icon-job {
    padding-left: 16px;
  }

  .me-author-tool {
    text-align: center;
    padding-top: 10px;
  }

  .me-author-tool i {
    cursor: pointer;
    padding: 4px 10px;
    font-size: 30px;
  }
</style>
