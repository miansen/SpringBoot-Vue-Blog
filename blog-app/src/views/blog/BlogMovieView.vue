<template>

  <div class="me-view-body" v-title :data-title="title">
    <el-container class="me-view-container" style="width: 700px;">
      <el-main>
        <div class="me-view-card">
          <h1 class="me-view-title" style="margin-bottom: 20px">{{movie.title}}</h1>
          <div class="me-view-content">
            <div class="cover-wp" style="width: 315px;">
              <img :src="getImages(movie.images.small)" style="width: 100%;"/>
          </div>
            <p style="margin: 10px 0"><strong>评分：</strong>{{movie.rating.average}}</p>
            <p style="margin: 10px 0"><strong>年代：</strong>{{movie.year}}</p>
            <p style="margin: 10px 0"><strong>语言：</strong>{{movie.languages}}</p>
            <p style="margin: 10px 0"><strong>片长：</strong>{{movie.durations}}</p>
            <p style="margin: 10px 0"><strong>制片国家/地区：</strong>{{movie.countries}}</p>
            <p style="margin: 10px 0"><strong>简介：</strong>{{movie.summary}}</p>
          </div>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  import {douban_movie_detail_api} from '@/api/movie'

    export default {
      name: "BlogMovieView",
      data() {
          return {
            movie: {
              title: '',
              images: {
                small: ''
              },
              rating: {
                average: 0
              },
              year: '',
              languages: '',
              durations: '',
              countries: '',
              summary: ''
            }
          }
      },
      created() {
        this.getMovie();
      },
      watch: {
        '$route': 'getMovie'
      },
      methods: {
        getMovie() {
          let that = this
          douban_movie_detail_api(that.$route.params.id).then(data => {
            Object.assign(that.movie, data.data);
          }).catch(error => {
            if (error !== 'error') {
              that.$message({type: 'error', message: '电影加载失败', showClose: true})
            }
          })
        },
        getImages(_url){
          if( _url !== undefined ){
            let _u = _url.substring( 7 );
            return 'https://images.weserv.nl/?url=' + _u;
          }
        }
      },
      computed: {
        title() {
          return '电影详情 - For Fun';
        }
      },
    }
</script>

<style scoped>

</style>
