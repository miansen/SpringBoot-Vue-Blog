<template>
  <div class="me-allct-body" v-title :data-title="categoryTagTitle" >
    <el-container class="me-allct-container">
      <el-main>
        <el-tabs v-model="activeName">
          <el-tab-pane label="我的好友" name="myquanquan">
            <ul class="me-allct-items">
              <li v-for="c in haoyous"  :key="c.id" class="me-allct-item">
                <div class="me-allct-content">
                  <a class="me-allct-info" @click="view(c.id)">
                    <img class="me-allct-img me-header-picture" :src="c.avatar?c.avatar:defaultAvatar"/>
                    <h4 class="me-allct-name">{{c.nickname}}</h4>
                    <p class="me-allct-description">{{c.account}}</p>
                    <p class="me-allct-description">{{c.email}}</p>
                    <p class="me-allct-description">{{c.mobilePhoneNumber}}</p>
                    <a >{{c.createDate}}</a>好友文章
                  </a>

                  <div class="me-allct-meta">

                    <a @click="quxiaoguanzhu(c.id)">取消关注</a>
                  </div>
                </div>
              </li>
            </ul>
          </el-tab-pane>

        </el-tabs>
      </el-main>
    </el-container>
  </div>
</template>

<script>
    import defaultAvatar from '@/assets/img/logo.png'
    import {getAllQuanquan} from '@/api/quanquan'
    import {quxiaoguanzhu} from '@/api/quanquan'


    export default {
        name: 'quanquan',
        created() {
            this.getAllQuanquan()

        },
        data() {
            return {
                defaultAvatar:defaultAvatar,
                haoyous: [],
                currentActiveName: 'myquanquan'
            }
        },
        computed: {
            activeName: {
                get() {
                    return (this.currentActiveName = 'myquanquan')
                },
                set(newValue) {
                    this.currentActiveName = newValue
                }
            }
        },
        methods: {
            view(id) {
                this.$router.push({path: `/mywenzhang/${id}`})
            },
            quxiaoguanzhu(id) {
                let that = this
                quxiaoguanzhu(id).then(data => {
                    that.$message({type: 'success', message: '取消成功', showClose: true});
                    that.getAllQuanquan();
                }).catch(error => {
                    if (error !== 'error') {
                        that.$message({type: 'error', message: '取消加载失败', showClose: true})
                    }
                })
            },
            getAllQuanquan() {
                let that = this
                getAllQuanquan().then(data => {
                    that.haoyous = data.data.quanquan
                }).catch(error => {
                    if (error !== 'error') {
                        that.$message({type: 'error', message: '我的好友加载失败', showClose: true})
                    }
                })
            }
        },
        //组件内的守卫 调整body的背景色
        beforeRouteEnter(to, from, next) {
            window.document.body.style.backgroundColor = '#fff';
            next();
        },
        beforeRouteLeave(to, from, next) {
            window.document.body.style.backgroundColor = '#f5f5f5';
            next();
        }
    }
</script>

<style>
  .me-allct-body {
    margin: 60px auto 140px;
  }

  .me-allct-container {
    width: 1000px;
  }

  .me-allct-items {
    padding-top: 2rem;
  }

  .me-allct-item {
    width: 25%;
    display: inline-block;
    margin-bottom: 2.4rem;
    padding: 0 .7rem;
    box-sizing: border-box;
  }

  .me-allct-content {
    display: inline-block;
    width: 100%;
    background-color: #fff;
    border: 1px solid #f1f1f1;
    transition: border-color .3s;
    text-align: center;
    padding: 1.5rem 0;
  }

  .me-allct-info {
    cursor: pointer;
  }

  .me-allct-img {
    margin: -40px 0 10px;
    width: 60px;
    height: 60px;
    vertical-align: middle;

  }

  .me-allct-name {
    font-size: 21px;
    font-weight: 150;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    margin-top: 4px;
  }

  .me-allct-description {
    min-height: 50px;
    font-size: 13px;
    line-height: 25px;
  }

  .me-allct-meta {
    font-size: 12px;
    color: #969696;
  }
</style>
