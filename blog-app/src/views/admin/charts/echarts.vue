<template>
    <section class="chart-container">
        <el-row>
            <el-col :span="12">
                <div id="chartColumn" style="width:100%; height:400px;"></div>
            </el-col>
            <el-col :span="12" style="display: none;">
                <div id="chartBar" style="width:100%; height:400px;"></div>
            </el-col>
            <el-col :span="12" style="display: none">
                <div id="chartLine" style="width:100%; height:400px;"></div>
            </el-col>
            <el-col :span="12">
                <div id="chartPie" style="width:100%; height:400px;"></div>
            </el-col>
            <el-col :span="24" style="display: none">
                <a href="http://echarts.baidu.com/examples.html" target="_blank" style="float: right;">more>></a>
            </el-col>
        </el-row>
    </section>
</template>

<script>
    import echarts from 'echarts'
    import {getAllCategorysDetail} from '@/api/category'

    export default {
        data() {
            return {
                chartColumn: null,
                chartBar: null,
                chartLine: null,
                chartPie: null,
                categorys: [],
            }
        },

        methods: {
            drawColumnChart() {
                let that = this;
                this.chartColumn = echarts.init(document.getElementById('chartColumn'));
                getAllCategorysDetail().then(data => {
                  let categorynames = [];
                  let articles = [];
                  for (let i = 0; i < data.data.length; i++) {
                    let item = data.data[i];
                    categorynames.push(item.categoryname);
                    articles.push(item.articles);
                  }

                  this.chartColumn.setOption({
                    title: { text: '发表文章最多的分类' },
                    tooltip: {},
                    xAxis: {
                      data: categorynames
                    },
                    yAxis: {},
                    series: [{
                      name: '文章',
                      type: 'bar',
                      data: articles
                    }]
                  });
                }).catch(error => {
                  if (error !== 'error') {
                    that.$message({type: 'error', message: '文章分类加载失败', showClose: true})
                  }
                });
            },
            drawBarChart() {
                this.chartBar = echarts.init(document.getElementById('chartBar'));
                this.chartBar.setOption({
                    title: {
                        text: 'Bar Chart',
                        subtext: '数据来自网络'
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    legend: {
                        data: ['2011年', '2012年']
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis: {
                        type: 'value',
                        boundaryGap: [0, 0.01]
                    },
                    yAxis: {
                        type: 'category',
                        data: ['巴西', '印尼', '美国', '印度', '中国', '世界人口(万)']
                    },
                    series: [
                        {
                            name: '2011年',
                            type: 'bar',
                            data: [18203, 23489, 29034, 104970, 131744, 630230]
                        },
                        {
                            name: '2012年',
                            type: 'bar',
                            data: [19325, 23438, 31000, 121594, 134141, 681807]
                        }
                    ]
                });
            },
            drawLineChart() {
                this.chartLine = echarts.init(document.getElementById('chartLine'));
                this.chartLine.setOption({
                    title: {
                        text: 'Line Chart'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        data: ['邮件营销', '联盟广告', '搜索引擎']
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis: {
                        type: 'category',
                        boundaryGap: false,
                        data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [
                        {
                            name: '邮件营销',
                            type: 'line',
                            stack: '总量',
                            data: [120, 132, 101, 134, 90, 230, 210]
                        },
                        {
                            name: '联盟广告',
                            type: 'line',
                            stack: '总量',
                            data: [220, 182, 191, 234, 290, 330, 310]
                        },
                        {
                            name: '搜索引擎',
                            type: 'line',
                            stack: '总量',
                            data: [820, 932, 901, 934, 1290, 1330, 1320]
                        }
                    ]
                });
            },
            drawPieChart() {
                this.chartPie = echarts.init(document.getElementById('chartPie'));

              getAllCategorysDetail().then(data => {
                let categorynames = [];
                let articles = [];
                for (let i = 0; i < data.data.length; i++) {
                  let item = data.data[i];
                  let obj = {};
                  obj.value = item.articles;
                  obj.name = item.categoryname;
                  categorynames.push(item.categoryname);
                  articles.push(obj);
                }

                this.chartPie.setOption({
                  title: {
                    text: '最受欢迎的分类',
                    // subtext: '纯属虚构',
                    x: 'center'
                  },
                  tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                  },
                  legend: {
                    orient: 'vertical',
                    left: 'left',
                    data:  categorynames
                  },
                  series: [
                    {
                      name: '分类名称',
                      type: 'pie',
                      radius: '55%',
                      center: ['50%', '60%'],
                      data: articles,
                      itemStyle: {
                        emphasis: {
                          shadowBlur: 10,
                          shadowOffsetX: 0,
                          shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                      }
                    }
                  ]
                });
              }).catch(error => {
                if (error !== 'error') {
                  that.$message({type: 'error', message: '文章分类加载失败', showClose: true})
                }
              });

            },
            drawCharts() {
                this.drawColumnChart()
                this.drawBarChart()
                this.drawLineChart()
                this.drawPieChart()
            },
        },

        mounted: function () {
            this.drawCharts()
        },
        updated: function () {
            this.drawCharts()
        }
    }
</script>

<style scoped>
    .chart-container {
        width: 100%;
        float: left;
    }
    /*.chart div {
        height: 400px;
        float: left;
    }*/

    .el-col {
        padding: 30px 20px;
    }
</style>
