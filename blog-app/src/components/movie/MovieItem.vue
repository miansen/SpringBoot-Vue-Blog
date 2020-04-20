<template>
    <a @click="view(id)" class="item">
      <div class="cover-wp" style="width: 115px;">
        <img :src="getImages(images.small)" :alt="title" style="width: 100%;"/>
      </div>
      <p>{{title}}&nbsp;&nbsp;<strong>{{rating.average}}</strong></p>
    </a>
</template>

<script>
    export default {
      name: "MovieItem",
      props: {
        id: String,
        title: String,
        images: Object,
        rating: Object,
      },
      data() {
        return {}
      },
      methods: {
        view(id) {
          this.$router.push({path: `/movies/detail/${id}`});
        },
        getImages(_url){
          if( _url !== undefined ){
            let _u = _url.substring( 7 );
            return 'https://images.weserv.nl/?url=' + _u;
          }
        }
      }
    }
</script>

<style scoped>
  .movie-content .item {
    display: block;
    float: left;
    color: #37a;
    font-size: 13px;
    text-align: center;
    margin: 0 25px 10px 0;
    width: 115px;
  }
  .movie-content .cover-wp {
    background-size: 100%;
    height: 161px;
    overflow: hidden;
  }
  .movie-content .item p {
    margin: 5px 0 0;
    height: 40px;
    overflow: hidden;
  }
  .movie-content .item strong {
    color: #e09015;
  }
</style>
