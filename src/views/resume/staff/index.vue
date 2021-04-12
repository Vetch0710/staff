<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--用户数据-->
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="90px">
          <el-form-item label="员工名称" prop="userName">
            <el-input
              v-model="queryParams.userName"
              placeholder="请输入员工名称"
              clearable
              size="small"
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="职位级别" prop="level">
            <el-select
              v-model="queryParams.level"
              placeholder="职位级别"
              clearable
              size="small"
              style="width: 240px"
            >
              <el-option
                v-for="dict in levelOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="项目领导" prop="projectManager">
            <el-input
              v-model="queryParams.projectManager"
              placeholder="请输入项目领导名称"
              clearable
              size="small"
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="所属部门" prop="deptName">
            <el-select
              v-model="queryParams.deptName"
              placeholder="用户状态"
              clearable
              size="small"
              style="width: 240px"
            >
              <el-option
                v-for="dict in departmentOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="所属项目组" prop="projectName">
            <el-select
              v-model="queryParams.projectName"
              placeholder="所属项目组"
              clearable
              size="small"
              style="width: 240px"
            >
              <el-option
                v-for="dict in projectOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="完成进度" prop="isFinish">
            <el-select
              v-model="queryParams.isFinish"
              placeholder="完成进度"
              clearable
              size="small"
              style="width: 240px"
            >
              <el-option
                v-for="dict in finishStatusOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['resume:staff:add']"
            >新增
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['resume:staff:remove']"
            >删除
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              icon="el-icon-download"
              size="mini"
              @click="handleExport(null)"
              v-hasPermi="['resume:staff:export']"
            >导出
            </el-button>
          </el-col>
        </el-row>

        <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="40" align="center"/>
          <el-table-column label="员工编号" align="center" prop="userId"/>
          <el-table-column label="员工姓名" align="center" prop="userName" :show-overflow-tooltip="true"/>
          <el-table-column label="性别" align="center" prop="sex" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              {{scope.row.sex==='0'?'男':'女'}}
            </template>
          </el-table-column>
          <el-table-column label="年龄" align="center" prop="age" :show-overflow-tooltip="true"/>
          <el-table-column label="最高学历" align="center" prop="eduBackground" :show-overflow-tooltip="true"/>
          <el-table-column label="工龄" align="center" prop="workDay" :show-overflow-tooltip="true"/>
          <el-table-column label="级别" align="center" prop="workLevel" :show-overflow-tooltip="true"/>
          <el-table-column label="所属部门" align="center" prop="department" :show-overflow-tooltip="true"/>
          <el-table-column label="所在项目组" width="90" align="center" prop="projectTeam" :show-overflow-tooltip="true"/>
          <el-table-column label="项目领导" align="center" prop="leader"/>
          <el-table-column label="状态" align="center" prop="isFinish">
            <template slot-scope="scope">
              {{scope.row.isFinish==='0'?'未完成':'已完成'}}
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            width="180"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['resume:staff:edit']"
              >修改
              </el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['resume:staff:remove']"
              >删除
              </el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-key"
                @click="handleExport(scope.row)"
                v-hasPermi="['resume:staff:export']"
              >下载
              </el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-col>
    </el-row>

    <edit ref="edit"  @fetch-data="getList"></edit>
    <download ref="download" ></download>
  </div>
</template>

<script>
  import {
    listStaff,
    delStaff,
    addStaff,
    updateStaff,
    exportStaff,
    importTemplate
  } from '@/api/resume/staff'
  import { getToken } from '@/utils/auth'
  import '@riophae/vue-treeselect/dist/vue-treeselect.css'
  import Edit from "./edit/index";
  import Download from "./download/index";

  export default {
    name: 'index',
    components: { Edit,Download},
    data() {
      return {
        //员工编号
        sid:'',
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 总条数
        total: 0,
        // 用户表格数据
        userList: null,
        // 弹出层标题
        title: '',
        // 部门树选项
        deptOptions: undefined,
        // 是否显示弹出层
        open: false,
        // 部门名称
        deptName: undefined,
        // 默认密码
        initPassword: undefined,
        // 日期范围
        // dateRange: [],
        // 完成进度数据字典
        finishStatusOptions: [],
        // 项目组字典
        projectOptions: [],
        // 部门数据字典
        departmentOptions: [],
        // 职工级别数据字典
        levelOptions: [],
        // // 岗位选项
        // postOptions: [],
        // // 角色选项
        // roleOptions: [],
        // 表单参数
        form: {},
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        // 用户导入参数
        upload: {
          // 是否显示弹出层（用户导入）
          open: false,
          // 弹出层标题（用户导入）
          title: '',
          // 是否禁用上传
          isUploading: false,
          // 是否更新已经存在的用户数据
          updateSupport: 0,
          // 设置上传的请求头部
          headers: { Authorization: 'Bearer ' + getToken() },
          // 上传的地址
          url: process.env.VUE_APP_BASE_API + '/system/user/importData'
        },
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          userName: undefined,
          level: undefined,
          projectManager: undefined,
          deptName: undefined,
          projectName: undefined,
          isFinish: undefined
        },
        // 表单校验
        rules: {
          userName: [
            { required: true, message: '用户名称不能为空', trigger: 'blur' }
          ],
          nickName: [
            { required: true, message: '用户昵称不能为空', trigger: 'blur' }
          ],
          deptId: [
            { required: true, message: '归属部门不能为空', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '用户密码不能为空', trigger: 'blur' }
          ],
          email: [
            { required: true, message: '邮箱地址不能为空', trigger: 'blur' },
            {
              type: 'email',
              message: '\'请输入正确的邮箱地址',
              trigger: ['blur', 'change']
            }
          ],
          phonenumber: [
            { required: true, message: '手机号码不能为空', trigger: 'blur' },
            {
              pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
              message: '请输入正确的手机号码',
              trigger: 'blur'
            }
          ]
        }
      }
    },
    watch: {
    },
    created() {

      this.getList()
      this.getDicts('hc_sure_state').then(response => {
        this.finishStatusOptions = response.data
      })
      this.getDicts('resume_staff_project').then(response => {
        this.projectOptions = response.data
      })
      this.getDicts('resume_staff_department').then(response => {
        this.departmentOptions = response.data
      })
      this.getDicts('resume_staff_level').then(response => {
        this.levelOptions = response.data
      })

    },
    methods: {
      /** 查询用户列表 */
      getList() {
        this.loading = true
        listStaff(this.queryParams, this.dateRange).then(response => {
            console.log(response)
            this.userList = response.rows
            this.total = response.total
            this.loading = false
          }
        )
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.page = 1
        this.getList()
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.dateRange = []
        this.resetForm('queryForm')
        this.handleQuery()
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.userId)
        this.single = selection.length != 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.$refs["edit"].showEdit();
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.$refs["edit"].showEdit(row);
      },
      //删除数据
      handleDelete(row) {
        const userIds = row.userId || this.ids
        console.log(userIds)
        this.$confirm('是否确认删除用户编号为"' + userIds + '"的数据项?', '警告', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function() {
          return delStaff(userIds)
        }).then(() => {
          this.getList()
          this.msgSuccess('删除成功')
        }).catch(function() {
        })
      },
      /** 导出按钮操作 */
      handleExport(row) {
        if (row){
          this.$refs["download"].showDownload(row.userId);
        }else{
          this.$confirm('是否确认导出当前用户下所有的员工简历?', '警告', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.$refs["download"].showDownload();

          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消下载'
            });
          });
        }




      },

      /** 下载模板操作 */
      importTemplate() {
        importTemplate().then(response => {
          this.download(response.msg)
        })
      },

    }
  }
</script>
