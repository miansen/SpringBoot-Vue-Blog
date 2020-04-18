<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;padding: 10px;margin: 10px 0;">
			<el-form :inline="true" :model="userVo">
				<el-form-item>
					<el-input v-model="userVo.account" placeholder="用户名"></el-input>
				</el-form-item>
        <el-form-item>
          <el-input v-model="userVo.nickname" placeholder="昵称"></el-input>
        </el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getAdminUserList">查询</el-button>
				</el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getAllAdminUserList">全部</el-button>
        </el-form-item>
				<el-form-item>
					<el-button type="primary" @click="clickAddHandle">新增</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="users" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
			<el-table-column type="selection" width="55">
			</el-table-column>
			<el-table-column type="index" width="60">
			</el-table-column>
			<el-table-column prop="nickname" label="昵称" width="150" sortable>
			</el-table-column>
			<el-table-column prop="account" label="用户名" width="150" sortable>
			</el-table-column>
      <el-table-column prop="integral" label="积分" width="100" sortable>
      </el-table-column>
			<el-table-column prop="email" label="邮箱" width="200" sortable>
			</el-table-column>
			<el-table-column prop="mobilePhoneNumber" label="手机号码" min-width="150" sortable>
			</el-table-column>
      <el-table-column prop="createDate" label="注册时间" width="150" sortable>
      </el-table-column>
			<el-table-column label="操作" width="250">
				<template scope="scope">
					<el-button type="warning" size="small" @click="clickEditHandle(scope.$index, scope.row)">编辑</el-button>
					<el-button type="danger" size="small" @click="clickDeleteHandle(scope.$index, scope.row)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding: 10px;margin: 10px 0;">
			<el-button type="danger" @click="clickBatchDeleteHandle" :disabled="this.sels.length===0">批量删除</el-button>
			<el-pagination layout="prev, pager, next" @current-change="currentChangeHandle" :page-size="pageVo.pageSize" :total="pageVo.pageTotal" style="float:right;">
			</el-pagination>
		</el-col>

		<!--编辑界面-->
		<el-dialog title="编辑" :visible.sync="editFormVisible" :close-on-click-modal="false">
			<el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
        <el-form-item label="ID" prop="title" v-show="false">
          <el-input v-model="editForm.id"></el-input>
        </el-form-item>
				<el-form-item label="用户名" prop="account">
					<el-input v-model="editForm.account"></el-input>
				</el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="editForm.password"></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="editForm.nickname"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="editForm.email"></el-input>
        </el-form-item>
        <el-form-item label="手机号码" prop="mobilePhoneNumber">
          <el-input v-model="editForm.mobilePhoneNumber"></el-input>
        </el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="submitEditHandle" :loading="editLoading">提交</el-button>
			</div>
		</el-dialog>

		<!--新增界面-->
		<el-dialog title="新增" :visible.sync="addFormVisible" :close-on-click-modal="false">
			<el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
				<el-form-item label="用户名" prop="account">
					<el-input v-model="addForm.account"></el-input>
				</el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="addForm.password"></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="addForm.nickname"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="addForm.email"></el-input>
        </el-form-item>
        <el-form-item label="手机号码" prop="mobilePhoneNumber">
          <el-input v-model="addForm.mobilePhoneNumber"></el-input>
        </el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="addFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="submitAddHandle" :loading="addLoading">提交</el-button>
			</div>
		</el-dialog>
	</section>
</template>

<script>
  import { AdminUserListAPI,
           AdminUserAddAPI,
           AdminUserUpdateAPI,
           AdminUserDeleteAPI,
           AdminUserBatchDeleteAPI
  } from '@/api/admin';

	export default {
		data() {
			return {

        // 用户列表
				users: [],

        // 列表选中列
        sels: [],

        // 正在加载中？
				listLoading: false,

        // 正在提交添加请求？
        addLoading: false,

        // 正在提交编辑请求？
        editLoading: false,

        // 显示添加界面？
        addFormVisible: false,

        // 显示编辑界面？
        editFormVisible: false,

        // 搜索参数
        userVo: {
          account: '',
          nickname: ''
        },

        // 分页参数
        pageVo: {
          // 总共有多少条数据
          pageTotal: 0,
          // 当前是第几页
          pageNumber: 1,
          // 每页显示多少条数据
          pageSize: 10,
        },

        // 新增界面的数据
        addForm: {
          account: '',
          password: '',
          nickname: '',
          email: '',
          mobilePhoneNumber: ''
        },

				// 编辑界面的数据
				editForm: {
				  id: '',
					account: '',
          password: '',
          nickname: '',
          email: '',
          mobilePhoneNumber: ''
				},

        // 新增界面的校验规则
				addFormRules: {
					account: [
						{ required: true, message: '请输入用户名', trigger: 'blur' }
					],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' }
          ],
          nickname: [
            { required: true, message: '请输入昵称', trigger: 'blur' }
          ]
				},

        // 编辑界面的校验规则
        editFormRules: {
          account: [
            { required: true, message: '请输入用户名', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' }
          ],
          nickname: [
            { required: true, message: '请输入昵称', trigger: 'blur' }
          ]
        },

			}
		},
		methods: {

      // 点击了分页
			currentChangeHandle(pageNumber) {
				this.pageVo.pageNumber = pageNumber;
				this.getAdminUserList();
			},

      // 获取全部的用户
      getAllAdminUserList() {
			  this.userVo.account = '';
			  this.userVo.nickname = '';
			  this.pageVo.pageNumber = 1;
			  this.pageVo.pageSize = 10;
        this.getAdminUserList();
      },

			// 获取用户列表
      getAdminUserList() {
			  let that = this;
        let params = {
          account: that.userVo.account,
          nickname: that.userVo.nickname,
          pageNumber: that.pageVo.pageNumber,
          pageSize: that.pageVo.pageSize
        };
        that.listLoading = true;
        AdminUserListAPI(params).then((res) => {
          that.users = res.data.data.content;
          that.pageVo.pageTotal = res.data.data.totalElements;
          that.listLoading = false;
				});
			},

      // 显示新增界面
      clickAddHandle: function () {
        this.addFormVisible = true;
        this.addForm = {
          account: '',
          password: '',
          nickname: '',
          email: '',
          mobilePhoneNumber: ''
        };
      },

			// 显示编辑界面
			clickEditHandle: function (index, row) {
				this.editFormVisible = true;
        this.editForm = Object.assign({}, row);
			},

      // 删除用户
      clickDeleteHandle: function (index, row) {
        this.$confirm('确认删除该记录吗?', '提示', {
          type: 'warning'
        }).then(() => {
          let that = this;
          this.listLoading = true;
          AdminUserDeleteAPI({id: row.id}).then((res) => {
            this.listLoading = false;
            if (res.data.data.code == 0) {
              this.$message({message: '删除成功', type: 'success'});
            } else {
              this.$message({message: res.data.data.errdetail, type: 'success'});
            }
            this.getAdminUserList();
          }).catch(error => {
            // that.$message({message: error, type: 'error', showClose: true});
            this.getAdminUserList();
          });
        });
      },

      // 批量删除用户
      clickBatchDeleteHandle: function () {
        let ids = this.sels.map(item => item.id).toString();
        this.$confirm('确认删除选中记录吗？', '提示', {type: 'warning'}).then(() => {
          let that = this;
          this.listLoading = true;
          let para = { ids: ids };
          AdminUserBatchDeleteAPI(para).then((res) => {
            this.listLoading = false;
            this.$message({message: '删除成功', type: 'success'});
            this.getAdminUserList();
          }).catch((error) => {
            // that.$message({type: 'error', message: '批量删除失败', showClose: true});
          });
        });
      },

      // 新增用户
      submitAddHandle: function () {
        this.$refs.addForm.validate((valid) => {
          if (valid) {
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
              this.addLoading = true;
              let params = Object.assign({}, this.addForm);
              AdminUserAddAPI(params).then((res) => {
                this.addLoading = false;
                this.$message({message: '新增成功', type: 'success'});
                this.$refs['addForm'].resetFields();
                this.addFormVisible = false;
                this.getAdminUserList();
              }).catch(error => {
                this.addLoading = false;
                this.addFormVisible = false;
                this.getAdminUserList();
              });
            });
          }
        });
      },

			// 编辑用户
			submitEditHandle: function () {
				this.$refs.editForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
						  let that = this;
							this.editLoading = true;
              let params = Object.assign({}, this.editForm);
              AdminUserUpdateAPI(params).then((res) => {
                that.editLoading = false;
                if (res.data.code == 0) {
                  that.$message({message: '更新成功', type: 'success'});
                  this.$refs['editForm'].resetFields();
                  this.editFormVisible = false;
                  this.getAdminUserList();
                } else {
                  that.$message({message: res.data.errdetail, type: 'error'});
                }
							}).catch(error => {
                that.editLoading = false;
                that.editFormVisible = false;
                that.getAdminUserList();
              });
						});
					}
				});
			},

			selsChange: function (sels) {
				this.sels = sels;
			},

		},
		mounted() {
			this.getAdminUserList();
		}
	}

</script>

<style scoped>

</style>
