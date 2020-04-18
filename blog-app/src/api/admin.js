import axios from 'axios';
import {Message} from 'element-ui';


export const AdminLoginAPI = params => { return service.post('/login', params).then(res => res.data); };

// 文章列表接口
export const AdminArticleListAPI = (title, author, pageNumber, pageSize) => {
  return service.get('/article/list', {
    params: {
      title: title,
      author: author,
      pageNumber: pageNumber,
      pageSize: pageSize
    }
  });
};

// 更新文章接口
export const AdminArticleUpdateAPI = article => {return service.post('/article/update', article);};

// 删除文章接口
export const AdminArticleDeleteAPI = params => {return service.get('/article/delete', {params: params});};

// 批量删除文章接口
export const AdminArticleBatchDeleteAPI = params => { return service.get('/article/delete/batch', { params: params }); };

export const removeUser = params => { return service.get('/user/remove', { params: params }); };

export const batchRemoveUser = params => { return service.get('/user/batchremove', { params: params }); };

export const editUser = params => { return service.get('/user/edit', { params: params }); };

export const addUser = params => { return service.get('/user/add', { params: params }); };


const service = axios.create({
  baseURL: 'http://localhost:8888/admin',
  timeout: 100000
})

// request 拦截器
service.interceptors.request.use(config => {
  const token = sessionStorage.getItem("admin_user");
  if (token) {
    config.headers['Oauth-Token'] = token;
  }
  return config
}, error => {
  Promise.reject(error)
})


// respone 拦截器
service.interceptors.response.use(res => {

    const code = res.data.code;

    // 90001 Session 超时
    if (code === 90001) {
      Message({
        type: 'warning',
        showClose: true,
        message: '未登录或登录超时，请重新登录哦'
      })
      return Promise.reject('error');
    }

    //20001 用户未登录
    if (code === 20001) {
      Message({
        type: 'warning',
        showClose: true,
        message: '未登录或登录超时，请重新登录哦'
      })
      return Promise.reject('error');
    }

    //70001 权限认证错误
    if (code === 70001) {
      Message({
        type: 'warning',
        showClose: true,
        message: '你没有权限访问哦'
      })
      return Promise.reject('error');
    }

    return res;
  }
);
