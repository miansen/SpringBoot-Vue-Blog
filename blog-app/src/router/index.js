import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/Home'
/*import Index from '@/views/Index'
import Login from '@/views/Login'
import Register from '@/views/Register'
import Log from '@/views/Log'
import MessageBoard from '@/views/MessageBoard'
import BlogWrite from '@/views/blog/BlogWrite'
import BlogView from '@/views/blog/BlogView'
import BlogAllCategoryTag from '@/views/blog/BlogAllCategoryTag'
import BlogCategoryTag from '@/views/blog/BlogCategoryTag'*/

import AdminLogin from '@/views/admin/AdminLogin.vue'
import AdminHome from '@/views/admin/AdminHome.vue'
import AdminMain from '@/views/admin/AdminMain.vue'
import AdminForm from '@/views/admin/nav1/AdminForm.vue'
import AdminTable from '@/views/admin/nav1/AdminTable.vue'
import AdminUser from '@/views/admin/nav1/AdminUser.vue'

import {Message} from 'element-ui';


import store from '@/store'

import {getToken} from '@/request/token'

Vue.use(Router)

const router = new Router({
  routes: [

    /**************************** Admin Routes Start ****************************/
    {
      path: '/admin/login',
      component: r => require.ensure([], () => r(require('@/views/admin/AdminLogin')), 'AdminLogin')
    },
    {
      path: '/admin/index',
      component: AdminHome,
      name: '文章管理',
      iconCls: 'el-icon-message',//图标样式class
      children: [
        {
          path: '/admin/main',
          component: AdminMain,
          name: "文章列表"
        },
        {
          path: '/admin/table',
          component: AdminTable,
          name: '文章添加'
        },
        {
          path: '/admin/form',
          component: AdminForm,
          name: 'AdminForm'
        },
        {
          path: '/admin/user',
          component: AdminUser,
          name: 'AdminUser'
        },
      ]
    },
    {
      path: '/admin/index',
      component: AdminHome,
      name: '评论管理',
      iconCls: 'el-icon-message',//图标样式class
      children: [
        {
          path: '/admin/main',
          component: AdminMain,
          name: "评论列表"
        }
      ]
    },
    /**************************** Admin Routes End ****************************/

    {
      path: '/write/:id?',
      component: r => require.ensure([], () => r(require('@/views/blog/BlogWrite')), 'blogwrite'),
      meta: {
        requireLogin: true
      },
    },
    {
      path: '',
      name: 'Home',
      component: Home,
      children: [
        {
          path: '/',
          component: r => require.ensure([], () => r(require('@/views/Index')), 'index')
        },
        {
          path: '/log',
          component: r => require.ensure([], () => r(require('@/views/Log')), 'log')
        },
        {
          path: '/archives/:year?/:month?',
          component: r => require.ensure([], () => r(require('@/views/blog/BlogArchive')), 'archives')
        },
        {
          path: '/search',
          component: r => require.ensure([], () => r(require('@/views/blog/BlogSearch')), 'search')
        },
        {
          path: '/integral',
          component: r => require.ensure([], () => r(require('@/views/blog/BlogIntegral')), 'integral')
        },
        {
          path: '/information/:userid',
          component: r => require.ensure([], () => r(require('@/views/blog/myInformation')), 'information')
        },
        {
          path: '/mywenzhang/:userid?/:year?/:month?',
          component: r => require.ensure([], () => r(require('@/views/blog/mywenzhang')), 'mywenzhang')
        },
        {
          path: '/myquanquan/all',
          component: r => require.ensure([], () => r(require('@/views/blog/myquanquan')), 'myquanquan')
        },
        {
          path: '/messageBoard',
          component: r => require.ensure([], () => r(require('@/views/MessageBoard')), 'messageboard')
        },
        {
          path: '/view/:id',
          component: r => require.ensure([], () => r(require('@/views/blog/BlogView')), 'blogview')
        },{
          path: '/guanzhu/:userid',
          component: r => require.ensure([], () => r(require('@/views/blog/BlogView')), 'blogview')
        },
        {
          path: '/:type/all',
          component: r => require.ensure([], () => r(require('@/views/blog/BlogAllCategoryTag')), 'blogallcategorytag')
        },
        {
          path: '/category/:id',
          component: r => require.ensure([], () => r(require('@/views/blog/BlogCategoryTag')), 'blogcategorytag')
        }
      ]
    },
    {
      path: '/login',
      component: r => require.ensure([], () => r(require('@/views/Login')), 'login')
    },
    {
      path: '/register',
      component: r => require.ensure([], () => r(require('@/views/Register')), 'register')
    }

  ],
  scrollBehavior(to, from, savedPosition) {
    return {x: 0, y: 0}
  }
})

router.beforeEach((to, from, next) => {

  if (getToken()) {

    if (to.path === '/login') {
      next({path: '/'})
    } else {
      if (store.state.account.length === 0) {
        store.dispatch('getUserInfo').then(data => { //获取用户信息
          next()
        }).catch(() => {
          next({path: '/'})
        })
      } else {
        next()
      }
    }
  } else {
    if (to.matched.some(r => r.meta.requireLogin)) {
      Message({
        type: 'warning',
        showClose: true,
        message: '请先登录哦'
      })

    }
    else {
      next();
    }
  }
})


export default router
