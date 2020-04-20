<template>

  <div v-title :data-title="title">
    <el-container>
      <el-main class="me-ct-container">
        <div class="movie-head">
          <a @click="clickHandle('in_theaters')" class="movie-head-item" :style="{'color': type == 'in_theaters' ? '#27a' : '#333'}">正在热映</a>
          <a @click="clickHandle('coming_soon')" class="movie-head-item" :style="{'color': type == 'coming_soon' ? '#27a' : '#333'}">即将上映</a>
          <a @click="clickHandle('new_movies')" class="movie-head-item" :style="{'color': type == 'new_movies' ? '#27a' : '#333'}">新片榜</a>
          <a v-if="false" @click="clickHandle('weekly')" class="movie-head-item" :style="{'color': type == 'weekly' ? '#27a' : '#333'}">口碑榜</a>
          <a @click="clickHandle('top250')" class="movie-head-item" :style="{'color': type == 'top250' ? '#27a' : '#333'}">TOP250</a>
        </div>
        <movie-scroll-page v-bind="article"></movie-scroll-page>
      </el-main>
    </el-container>
  </div>

</template>

<script>
  import MovieScrollPage from '@/views/common/MovieScrollPage'
  import {listSearchArchives} from '@/api/article'

  export default {
    name: "BlogMovie",
    components: {
      "movie-scroll-page": MovieScrollPage
    },
    data() {
      return {
        article: {
          query: {
            title: this.$route.query.key
          }
        },
        type: this.$route.params.type
      }
    },
    inject: [
      'reload'
    ],
    methods: {
      clickHandle(type) {
        this.$router.push({path: `/movies/list/${type}`});
        this.reload();
      }
    },
    computed: {
      title() {
        return '电影 - For Fun';
      }
    },
  }
</script>

<style scoped>
  .me-ct-container {
    width: 1000px;
  }
  .movie-head {
    margin-bottom: 10px;
    border-bottom: 1px solid #e2e2e2;
  }
  .movie-head .movie-head-item{
    padding: 0 10px;
    color: #27a;
  }
</style>
