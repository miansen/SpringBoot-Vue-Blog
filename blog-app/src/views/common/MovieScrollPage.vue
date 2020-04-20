<template>
  <div class="movie-content">
    <div class="list-wp">
      <div class="list">
        <scroll-page :loading="loading" :offset="offset" :no-data="noData" v-on:load="load">
          <movie-item v-for="a in articles" :key="a.id" v-bind="a"></movie-item>
        </scroll-page>
      </div>
    </div>
  </div>
</template>

<script>
    import MovieItem from '@/components/movie/MovieItem'
    import ScrollPage from '@/components/scrollpage'
    import {douban_movie_list_api} from '@/api/movie'

    export default {
        name: "ArticleScrollPage",
        components: {
          'movie-item': MovieItem,
          'scroll-page': ScrollPage
        },
        props: {
            offset: {
                type: Number,
                default: 100
            },
            page: {
                type: Object,
                default() {
                    return {}
                }
            },
            query: {
                type: Object,
                default() {
                    return {}
                }
            }
        },
        watch: {
            'query': {
                handler() {

                    this.noData = false

                    this.articles = []
                    this.innerPage.pageNumber = 1
                    this.getArticles()
                },
                deep: true
            },
            'page': {
                handler() {
                    this.noData = false
                    this.articles = []
                    this.innerPage = this.page
                    this.getArticles()
                },
                deep: true
            }
        },
        created() {
            this.getArticles()
        },
        data() {
            return {
                loading: false,
                noData: false,
                innerPage: {
                    pageSize: 20,
                    pageNumber: 0,
                    name: 'a.createDate',
                    sort: 'desc'
                },
                articles: []
            }
        },
        methods: {
            load() {
                this.getArticles()
            },
            view(id) {
                this.$router.push({path: `/movies/detail/${id}`})
            },
            getArticles() {
                let that = this
                that.loading = true
                let params = {
                  start: that.innerPage.pageNumber,
                  count: that.innerPage.pageSize
                };
              douban_movie_list_api(that.$route.params.type, params).then(data => {
                    let newArticles = data.data.subjects;
                    if (newArticles && newArticles.length > 0) {
                        that.innerPage.pageNumber += that.innerPage.pageSize;
                        that.articles = that.articles.concat(newArticles)
                    } else {
                        that.noData = true
                    }

                }).catch(error => {
                    if (error !== 'error') {
                        that.$message({type: 'error', message: '电影加载失败!', showClose: true})
                    }
                }).finally(() => {
                    that.loading = false
                })

            }
        },
    }
</script>

<style scoped>
  .movie-content {
    padding: 0 10px;
  }
</style>
