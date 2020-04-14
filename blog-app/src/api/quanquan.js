import request from '@/request'

export function getAllQuanquan() {
  return request({
    url: '/articles/myquanquan',
    method: 'get',
  })
}

export function quxiaoguanzhu(id) {
  return request({
    url: `/articles/quxiaoguanzhu/${id}`,
    method: 'get',
  })

}
