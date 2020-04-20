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
          <h1 class="me-view-title" style="margin-bottom: 20px;text-align: center;">
            <span style="font-size: 24px;">{{movie.title}}</span>
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
                      <div class="rating_logo ll">豆瓣评分：<strong class="ll rating_num">{{movie.rating.average}}</strong></div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="me-view-footer">
            <section class="chart-container">
              <el-row>
                <el-col :span="12">
                  <div id="chartColumn" style="width:100%; height:400px;"></div>
                </el-col>
              </el-row>
            </section>
          </div>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  import {douban_movie_detail_api} from '@/api/movie';
  import echarts from 'echarts';

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
              aka: [],
              rating:{
                max: 0,
                average: 0,
                details: [0, 0, 0, 0, 0],
                stars: 50,
                min: 0
              }
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
            let details = data.data.rating.details;
            let arr = [];
            for (let i in details) {
              arr.push(details[i]);
            }
            that.movie.rating.details = arr;
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
        },
        drawColumnChart() {
          this.chartColumn = echarts.init(document.getElementById('chartColumn'));
          this.chartColumn.setOption({
            title: { text: '' },
            tooltip: {},
            xAxis: {
              data: ["1星", "2星", "3星", "4星", "5星"]
            },
            yAxis: {},
            series: [{
              name: '豆瓣评分',
              type: 'bar',
              data: this.movie.rating.details
            }]
          });
        }
      },
      computed: {
        title() {
          return '电影详情 - For Fun';
        }
      },
      mounted: function () {
        this.drawColumnChart();
      },
      updated: function () {
        this.drawColumnChart();
      }
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
