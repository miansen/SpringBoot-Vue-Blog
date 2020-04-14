<template>
  <div v-title :data-title="title">
    <el-container>
      <el-main class="me-articles">
        <div class="me-month-title">积分排行</div>
        <el-table
          :data="tableData"
          border
          style="width: 100%">
          <el-table-column
            prop="nickname"
            label="姓名"
            width="180">
          </el-table-column>
          <el-table-column
            prop="integral"
            label="积分">
          </el-table-column>
          <el-table-column
            prop="createDate"
            label="注册日期"
            width="180">
          </el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </div>

</template>

<script>
  import {integral} from '@/api/user'

  export default {
    name: "BlogIntegral",
    created() {
      this.integral();
    },
    data() {
      return {
        tableData: []
      }
    },
    computed: {
      title() {
        return '积分排行 - For Fun';
      }
    },
    methods: {
      integral() {
        let that = this;
        integral().then(data => {
          that.tableData = data.data;
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: '获取积分信息失败', showClose: true});
          }
        });
      }
    }
  }
</script>

<style scoped>

  .el-container {
    width: 640px;
  }

  .el-aside {
    position: fixed;
    left: 200px;
    margin-right: 50px;
    width: 150px !important;
  }

  .el-main {
    padding: 0px;
    line-height: 16px;
  }

  .me-month-list {
    margin-top: 10px;
    margin-bottom: 10px;
    text-align: center;
    list-style-type: none;
  }

  .me-month-item {
    margin-top: 18px;
    padding: 4px;
    font-size: 18px;
    color: #5FB878;
  }

  .me-order-list {
    float: right;
  }

  .me-month-title {
    margin-left: 4px;
    margin-bottom: 12px;
  }
</style>
