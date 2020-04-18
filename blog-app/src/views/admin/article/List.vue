<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;padding: 10px;margin: 10px 0;">
			<el-form :inline="true" :model="articleVo">
				<el-form-item>
					<el-input v-model="articleVo.title" placeholder="标题"></el-input>
				</el-form-item>
        <el-form-item>
          <el-input v-model="articleVo.author" placeholder="作者"></el-input>
        </el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getAdminArticleList">查询</el-button>
				</el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getAllAdminArticleList">全部</el-button>
        </el-form-item>
				<!--<el-form-item>
					<el-button type="primary" @click="handleAdd">新增</el-button>
				</el-form-item>-->
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="articles" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
			<el-table-column type="selection" width="55">
			</el-table-column>
			<el-table-column type="index" width="60">
			</el-table-column>
			<el-table-column prop="title" label="标题" width="400" sortable>
			</el-table-column>
			<el-table-column prop="author.nickname" label="作者" width="100" sortable>
			</el-table-column>
			<el-table-column prop="viewCounts" label="点击数量" width="100" sortable>
			</el-table-column>
			<el-table-column prop="commentCounts" label="评论数量" min-width="50" sortable>
			</el-table-column>
      <el-table-column prop="createDate" label="创建时间" width="150" sortable>
      </el-table-column>
			<el-table-column label="操作" width="250">
				<template scope="scope">
          <el-button type="primary" size="small" @click="handleView(scope.$index, scope.row)">查看</el-button>
					<el-button type="warning" size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
					<el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding: 10px;margin: 10px 0;">
			<el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>
			<el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="pageVo.pageSize" :total="pageVo.pageTotal" style="float:right;">
			</el-pagination>
		</el-col>

		<!--编辑界面-->
		<el-dialog title="编辑" :visible.sync="editFormVisible" :close-on-click-modal="false">
			<el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
        <el-form-item label="ID" prop="title" v-show="false">
          <el-input v-model="editForm.id"></el-input>
        </el-form-item>
				<el-form-item label="标题" prop="title">
					<el-input v-model="editForm.title"></el-input>
				</el-form-item>
        <el-form-item label="摘要" prop="summary">
          <el-input v-model="editForm.summary"></el-input>
        </el-form-item>
        <el-form-item label="正文" prop="body">
          <markdown-editor :editor="editor" class="me-write-editor"></markdown-editor>
        </el-form-item>
        <el-form-item label="文章分类" prop="category">
          <el-select v-model="editForm.category" value-key="id" placeholder="请选择文章分类">
            <el-option v-for="c in categories" :key="c.id" :label="c.categoryname" :value="c"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="文章标签" prop="tags">
          <el-checkbox-group v-model="editForm.tags">
            <el-checkbox v-for="t in tags" :key="t.id" :label="t.id" name="tags" :value="t">{{t.tagname}}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
			</div>
		</el-dialog>

		<!--新增界面-->
		<el-dialog title="新增" v-model="addFormVisible" :close-on-click-modal="false">
			<el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
				<el-form-item label="姓名" prop="name">
					<el-input v-model="addForm.name" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="性别">
					<el-radio-group v-model="addForm.sex">
						<el-radio class="radio" :label="1">男</el-radio>
						<el-radio class="radio" :label="0">女</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item label="年龄">
					<el-input-number v-model="addForm.age" :min="0" :max="200"></el-input-number>
				</el-form-item>
				<el-form-item label="生日">
					<el-date-picker type="date" placeholder="选择日期" v-model="addForm.birth"></el-date-picker>
				</el-form-item>
				<el-form-item label="地址">
					<el-input type="textarea" v-model="addForm.addr"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="addFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
			</div>
		</el-dialog>
	</section>
</template>

<script>
	import util from '@/utils/AdminUtil'
	//import NProgress from 'nprogress'
	import { getUserListPage, removeUser, batchRemoveUser, editUser, addUser } from '@/api/admin';
  import { getArticles } from '@/api/article';
  import { AdminArticleListAPI, AdminArticleUpdateAPI, AdminArticleDeleteAPI, AdminArticleBatchDeleteAPI } from '@/api/admin';
  import { getAllCategorys } from '@/api/category';
  import {getAllTags} from '@/api/tag';
  import MarkdownEditor from '@/components/markdown/MarkdownEditor';

	export default {
    components: {
      'markdown-editor': MarkdownEditor
    },
		data() {
			return {
				filters: {
					name: ''
				},
        // 类别数组
        categories: [],
        // 标签数组
        tags: [],
        // 编辑器
        editor: {
          value: '',
          ref: '',//保存mavonEditor实例  实际不该这样
          default_open: 'edit',
          toolbars: {
            bold: true, // 粗体
            italic: true, // 斜体
            header: true, // 标题
            underline: true, // 下划线
            strikethrough: true, // 中划线
            mark: true, // 标记
            superscript: true, // 上角标
            subscript: true, // 下角标
            quote: true, // 引用
            ol: true, // 有序列表
            ul: true, // 无序列表
            imagelink: true, // 图片链接
            code: true, // code
            fullscreen: true, // 全屏编辑
            readmodel: true, // 沉浸式阅读
            help: true, // 帮助
            undo: true, // 上一步
            redo: true, // 下一步
            trash: true, // 清空
            navigation: true, // 导航目录
            //subfield: true, // 单双栏模式
            preview: true, // 预览
          }
        },
        // 文章列表
				articles: [],
        // 搜索对象
        articleVo: {
				  // 文章标题
				  title: '',
          // 作者名字
          author: ''
        },
        // 分页对象
        pageVo: {
          // 总共有多少条数据
          pageTotal: 0,
          // 当前是第几页
          pageNumber: 1,
          // 每页显示多少条数据
          pageSize: 10,
        },

        // 正在加载中？
				listLoading: false,
        // 列表选中列
				sels: [],
        // 编辑界面是否显示
				editFormVisible: false,
				editLoading: false,
				editFormRules: {
					title: [
						{ required: true, message: '请输入标题', trigger: 'blur' }
					]
				},

				//编辑界面的数据
				editForm: {
				  id: '',
					title: '',
          summary: '',
          body: {},
          category: {},
          tags: []
				},

				addFormVisible: false,//新增界面是否显示
				addLoading: false,
				addFormRules: {
					name: [
						{ required: true, message: '请输入姓名', trigger: 'blur' }
					]
				},
				//新增界面数据
				addForm: {
					name: '',
					sex: -1,
					age: 0,
					birth: '',
					addr: ''
				}

			}
		},
		methods: {
			//性别显示转换
			formatSex: function (row, column) {
				return row.sex == 1 ? '男' : row.sex == 0 ? '女' : '未知';
			},
      // 点击了分页
			handleCurrentChange(pageNumber) {
				this.pageVo.pageNumber = pageNumber;
				this.getAdminArticleList();
			},

      getAllAdminArticleList() {
			  this.articleVo.title = '';
			  this.articleVo.author = '';
        this.getAdminArticleList();
      },

			// 获取文章列表
      getAdminArticleList() {
			  let that = this;
			  let title = that.articleVo.title;
        let author = that.articleVo.author;
        let pageNumber = that.pageVo.pageNumber;
        let pageSize = that.pageVo.pageSize;
        that.listLoading = true;
        AdminArticleListAPI(title, author, pageNumber, pageSize).then((res) => {
          that.articles = res.data.data.content;
          that.pageVo.pageTotal = res.data.data.totalElements;
          that.listLoading = false;
				});
			},

			// 删除
			handleDel: function (index, row) {
				this.$confirm('确认删除该记录吗?', '提示', {
					type: 'warning'
				}).then(() => {
          let that = this;
					this.listLoading = true;
          AdminArticleDeleteAPI({id: row.id}).then((res) => {
						this.listLoading = false;
						//NProgress.done();
						this.$message({
							message: '删除成功',
							type: 'success'
						});
						this.getAdminArticleList();
					}).catch(error => {
            if (error !== 'error') {
              that.editLoading = false;
              that.$message({message: error, type: 'error', showClose: true});
            }
          });
				});
			},

      handleView: function(index, row) {
			  window.open('/#/view/' + row.id);
      },

			// 显示编辑界面
			handleEdit: function (index, row) {
			  console.log(row);
				this.editFormVisible = true;
				this.editForm.id = row.id;
				this.editForm.title = row.title;
				this.editForm.summary = row.summary;
				this.editForm.category = row.category;
				// 把标签对象数组转为 ID 数组，否则 v-model 无法绑定
				this.editForm.tags = row.tags.map(function (item) {
          return item.id;
        });
				this.editor.value = row.body.contentHtml;
        console.log(this.editForm);
			},

			//显示新增界面
			handleAdd: function () {
				this.addFormVisible = true;
				this.addForm = {
					name: '',
					sex: -1,
					age: 0,
					birth: '',
					addr: ''
				};
			},

			// 提交编辑
			editSubmit: function () {
				this.$refs.editForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
						  let that = this;
							this.editLoading = true;
							this.editForm.body.contentHtml = this.editor.value;
							let tagIds = this.editForm.tags;
							// 把 ID 数组转为对象数组，否则无法映射到后台的接口
							this.editForm.tags = tagIds.map(function (id) {
                return {id: id};
              })
              AdminArticleUpdateAPI(this.editForm).then((res) => {
                that.editLoading = false;
                if (res.data.code == 0) {
                  that.$message({message: '更新成功', type: 'success'});
                  this.$refs['editForm'].resetFields();
                  this.editFormVisible = false;
                  this.getAdminArticleList();
                } else {
                  that.$message({message: res.data.errdetail, type: 'error'});
                }
							}).catch(error => {
                if (error !== 'error') {
                  that.editLoading = false;
                  that.$message({message: error, type: 'error', showClose: true});
                }
              });
						});
					}
				});
			},
			//新增
			addSubmit: function () {
				this.$refs.addForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.addLoading = true;
							//NProgress.start();
							let para = Object.assign({}, this.addForm);
							para.birth = (!para.birth || para.birth == '') ? '' : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd');
							addUser(para).then((res) => {
								this.addLoading = false;
								//NProgress.done();
								this.$message({
									message: '提交成功',
									type: 'success'
								});
								this.$refs['addForm'].resetFields();
								this.addFormVisible = false;
								this.getAdminArticleList();
							});
						});
					}
				});
			},
			selsChange: function (sels) {
				this.sels = sels;
			},
			//批量删除
			batchRemove: function () {
				let ids = this.sels.map(item => item.id).toString();
				this.$confirm('确认删除选中记录吗？', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					let para = { ids: ids };
          AdminArticleBatchDeleteAPI(para).then((res) => {
						this.listLoading = false;
						this.$message({
							message: '删除成功',
							type: 'success'
						});
						this.getAdminArticleList();
					});
				}).catch(() => {

				});
			},
      // 加载分类和标签
      getCategorysAndTags() {
        let that = this
        getAllCategorys().then(data => {
          that.categories = data.data
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: '文章分类加载失败', showClose: true})
          }
        })

        getAllTags().then(data => {
          that.tags = data.data
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: '标签加载失败', showClose: true})
          }
        })

      }
		},
		mounted() {
			this.getAdminArticleList();
      this.getCategorysAndTags();
		}
	}

</script>

<style scoped>

</style>
