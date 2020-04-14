import request from '@/request'


export function integral() {
  return request({
    url: `/users/integral`,
    method: 'get'
  })
}
