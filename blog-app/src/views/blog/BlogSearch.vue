<template>
  <div v-title :data-title="title">
    <el-container>
      <el-main class="me-articles">
        <div class="me-month-title">搜索结果</div>
        <article-scroll-page v-bind="article"></article-scroll-page>
      </el-main>
    </el-container>
  </div>

</template>

<script>
  import ArticleScrollPage from '@/views/common/ArticleScrollPage'
  import {listSearchArchives} from '@/api/article'

  export default {
    name: "BlogArchive",
    components: {
      ArticleScrollPage
    },
    data() {
      return {
        article: {
          query: {
            title: this.$route.query.key
          }
        }
      }
    },
    computed: {
      title() {
        return '搜索结果 - For Fun';
      }
    },
  }
</script>

<style scoped>

  .el-container {
    width: 640px;
  }

  .el-aside {
    position: fixed;
    left: 200px;
    margin-right: 50px;
    width: 150px !important;
  }

  .el-main {
    padding: 0px;
    line-height: 16px;
  }

  .me-month-list {
    margin-top: 10px;
    margin-bottom: 10px;
    text-align: center;
    list-style-type: none;
  }

  .me-month-item {
    margin-top: 18px;
    padding: 4px;
    font-size: 18px;
    color: #5FB878;
  }

  .me-order-list {
    float: right;
  }

  .me-month-title {
    margin-left: 4px;
    margin-bottom: 12px;
  }
</style>
