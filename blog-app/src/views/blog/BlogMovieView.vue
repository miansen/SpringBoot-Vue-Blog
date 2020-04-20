<template>
  <div class="me-view-body" v-title :data-title="title">
    <el-container class="me-view-container">
      <el-main class="me-ct-container">
        <div class="movie-head">
          <a @click="clickHandle('in_theaters')" class="movie-head-item" :style="{'color': type == 'in_theaters' ? '#27a' : '#333'}">正在热映</a>
          <a @click="clickHandle('coming_soon')" class="movie-head-item" :style="{'color': type == 'coming_soon' ? '#27a' : '#333'}">即将上映</a>
          <a @click="clickHandle('new_movies')" class="movie-head-item" :style="{'color': type == 'new_movies' ? '#27a' : '#333'}">新片榜</a>
          <a v-if="false" @click="clickHandle('weekly')" class="movie-head-item" :style="{'color': type == 'weekly' ? '#27a' : '#333'}">口碑榜</a>
          <a @click="clickHandle('top250')" class="movie-head-item" :style="{'color': type == 'top250' ? '#27a' : '#333'}">TOP250</a>
        </div>
        <div class="movie-content">
          <h1 class="me-view-title" style="margin-bottom: 20px">
            <span>{{movie.title}}</span>
            <span class="year">({{movie.year}})</span>
          </h1>
          <div class="me-view-content">
            <div class="indent clearfix">
              <div class="subjectwrap clearfix">
                <div class="subject clearfix">
                  <div class="mainpic">
                    <img :src="getImages(movie.images.small)" :alt="movie.title" tyle="width: 100%;"/>
                  </div>
                  <div class="info">
                    <span>
                      <span class="pl">导演</span>:&nbsp;&nbsp;<span class="attrs">{{formatArrayWithName(movie.directors)}}</span>
                    </span>
                    <br/>
                    <span>
                      <span class="pl">编剧</span>:&nbsp;<span class="attrs">{{formatArrayWithName(movie.writers)}}</span>
                    </span>
                    <br/>
                    <span>
                      <span class="pl">主演</span>:&nbsp;<span class="attrs">{{formatArrayWithName(movie.casts)}}</span>
                    </span>
                    <br/>
                    <span>
                      <span class="pl">类型</span>:&nbsp;<span class="attrs">{{formatArray(movie.genres)}}</span>
                    </span>
                    <br/>
                    <span>
                      <span class="pl">制片国家/地区</span>:&nbsp;<span class="attrs">{{formatArray(movie.countries)}}</span>
                    </span>
                    <br/>
                    <span>
                      <span class="pl">语言</span>:&nbsp;<span class="attrs">{{formatArray(movie.languages)}}</span>
                    </span>
                    <br/>
                    <span>
                      <span class="pl">上映日期</span>:&nbsp;<span class="attrs">{{formatArray(movie.pubdates)}}</span>
                    </span>
                    <br/>
                    <span>
                      <span class="pl">片长</span>:&nbsp;<span class="attrs">{{formatArray(movie.durations)}}</span>
                    </span>
                    <br/>
                    <span>
                      <span class="pl">又名</span>:&nbsp;<span class="attrs">{{formatArray(movie.aka)}}</span>
                    </span>
                    <div class="interest_sectl">
                      <div class="rating_logo ll">豆瓣评分：<strong class="ll rating_num">9.7</strong></div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="me-view-footer">
            <div>5星：<el-progress :text-inside="true" :percentage="50" :stroke-width="20" status="primary" style="display: inline">5星</el-progress></div>
            <div>4星：<el-progress :text-inside="true" :percentage="50" :stroke-width="20" status="success" style="display: inline">5星</el-progress></div>
            <div>3星：<el-progress :text-inside="true" :percentage="50" :stroke-width="20" status="warning" style="display: inline">5星</el-progress></div>
            <div>2星：<el-progress :text-inside="true" :percentage="50" :stroke-width="20" status="exception" style="display: inline">5星</el-progress></div>
            <div>1星：<el-progress :text-inside="true" :percentage="50" :stroke-width="20" status="exception" style="display: inline">5星</el-progress></div>
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
              summary: '',
              directors: {
                name: ''
              },
              writers: [],
              casts: [],
              genres: [],
              countries: [],
              languages: [],
              pubdates: [],
              durations: [],
              aka: []
            },
            type: ''
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
        },
        formatArray(array) {
          let str = '';
          array.map(a => {
            str += a + '/';
          });
          return str.substring(0, str.length - 1);
        },
        formatArrayWithName(array) {
          let str = '';
          array.map(a => {
            str += a.name + '/';
          });
          return str.substring(0, str.length - 1);
        },
        clickHandle(type) {
          this.$router.push({path: `/movies/list/${type}`});
          this.reload();
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
  .me-ct-container {
    width: 1000px;
  }
  .movie-content {
    padding: 0 10px;
  }
  .movie-head {
    margin-bottom: 10px;
    border-bottom: 1px solid #e2e2e2;
  }
  .movie-head .movie-head-item{
    padding: 0 10px;
    color: #27a;
  }
  .subject {
    width: 1000px;
  }
  .clearfix {
    display: block;
  }
  .subject .mainpic {
    margin-right: 15px;
  }
  .mainpic {
    margin: 3px 0 0 0;
    float: left;
    text-align: center;
    margin: 3px 12px 0 0;
    max-width: 270px;
    overflow: hidden;
  }
  .info {
    max-width: 333px;
  }
  .info {
    float: left;
    max-width: 680px;
    word-wrap: break-word;
  }
  .pl {
    font: 14px Arial, Helvetica, sans-serif;
    line-height: 150%;
    color: #666666;
  }
  .attrs {
    font: 14px Arial, Helvetica, sans-serif;
    line-height: 150%;
  }
  .rating_logo {
    margin: 10px 0;
    color: red;
  }
</style>
