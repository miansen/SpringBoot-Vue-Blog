import axios from 'axios';

// TOP250
export const TOP250 = params => {return service.get('/v2/movie/top250', {params: params})};

// 正在上映
export const in_theaters = params => {return service.get('/v2/movie/in_theaters', {params: params})};

// 即将上映
export const coming_soon = params => {return service.get('/v2/movie/coming_soon', {params: params})};

// 新片榜
export const new_movies = params => {return service.get('/v2/movie/new_movies', {params: params})};

// 口碑榜
export const weekly = params => {return service.get('/v2/movie/weekly', {params: params})};

// 北美票房榜
export const us_box = params => {return service.get('/movie/us_box', {params: params})};

// 电影列表
export const douban_movie_list_api = (type, params) => {return service.get(`/v2/movie/${type}`, {params: params})};

// 电影详情
export const douban_movie_detail_api = id => {return service.get(`/v2/movie/subject/${id}`)};

const service = axios.create({
  // baseURL: 'http://api.douban.com/v2/movie',
  timeout: 100000
})

// request 拦截器
service.interceptors.request.use(config => {
  const apikey = "0b2bdeda43b5688921839c8ecb20399b";
  // 如果有 params 参数，则以数组的方式为对象添加值，使其为我们转化为 "&key=value" 的格式。
  if (config["params"]) {
    config.params["apikey"] = apikey;
  } else {
    // 如果没有 params 参数，则增加一个 params 参数，使其为我们转化为 "?key=value" 的格式。
    config["params"] = {apikey: apikey};
  }
  return config
}, error => {
  Promise.reject(error)
});

// respone 拦截器
service.interceptors.response.use(res => {
    return res;
  }
);
