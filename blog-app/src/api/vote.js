import request from '@/request'


export function publishVote(vote) {
  return request({
    url: `/vote/publish`,
    method: 'post',
    data: vote
  })
}

export function getRate(articleId) {
  return request({
    url: '/vote/rate/' + articleId,
    method: 'get'
  })
}

