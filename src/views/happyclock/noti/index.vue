<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">

      <el-form-item label="用药日期">
            <el-date-picker
              v-model="dateRange"
              size="small"
              style="width: 240px"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            ></el-date-picker>
          </el-form-item>

      <el-form-item label="医生姓名" prop="doctorname">
        <el-input
          v-model="queryParams.doctorname"
          placeholder="请输入医生姓名"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="医院名称" prop="hospname">
        <el-select v-model="queryParams.hospname" placeholder="请选择医院名称" clearable size="small" style="width: 240px">
          <el-option
            v-for="dict in hospnameOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="PT姓名" prop="patientname">
        <el-input
          v-model="queryParams.patientname"
          placeholder="请输入PT姓名"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="sureState">
        <el-select v-model="queryParams.sureState" placeholder="请选择状态" clearable size="small" style="width: 240px">
          <el-option
            v-for="dict in sureStateOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item style="width: 240px ;margin-left:68px;">
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
<!-- 通知提醒 -->
      <el-form-item style="width: 240px ;margin-left:68px;">
        <el-button type="primary" icon="el-icon-search" size="mini" @click="open1">open1</el-button>
      </el-form-item>


    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['happyclock:info:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['happyclock:info:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['happyclock:info:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['happyclock:info:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="用药日期" align="center" prop="inputtime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.inputtime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="医生姓名" align="center" prop="doctorname" />
      <el-table-column label="医院名称" align="center" prop="hospname" :formatter="hospnameFormat" />
      <el-table-column label="用量" align="center" prop="dosage" />
      <el-table-column label="PT姓名" align="center" prop="patientname" />
      <el-table-column label="录入人" align="center" prop="inputorname" :formatter="inputornameFormat" />
      <el-table-column label="提醒天数" align="center" prop="dayNum" />
      <el-table-column label="提醒日期" align="center" prop="inputtime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.inputtime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="sureState" :formatter="sureStateFormat" />
      <el-table-column label="创建时间" align="center" prop="createTime" />
      <el-table-column label="更新时间" align="center" prop="updateTime" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['happyclock:info:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['happyclock:info:remove']"
          >删除</el-button>
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



    <!-- 添加或修改PT管理-信息维护对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用药日期" prop="inputtime">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.inputtime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择用药日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="医生姓名" prop="doctorname">
          <el-input v-model="form.doctorname" placeholder="请输入医生姓名" />
        </el-form-item>
        <el-form-item label="医院名称">
          <el-select v-model="form.hospname" placeholder="请选择医院名称">
            <el-option
              v-for="dict in hospnameOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用量" prop="dosage">
          <el-input v-model="form.dosage" placeholder="请输入用量" />
        </el-form-item>
        <el-form-item label="PT姓名" prop="patientname">
          <el-input v-model="form.patientname" placeholder="请输入PT姓名" />
        </el-form-item>
        <el-form-item label="录入人">
          <el-select v-model="form.inputorname" placeholder="请选择录入人">
            <el-option
              v-for="dict in inputornameOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="提醒天数" prop="dayNum">
          <el-input v-model="form.dayNum" placeholder="请输入提醒天数" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.sureState" placeholder="请选择状态">
            <el-option
              v-for="dict in sureStateOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listInfo, getInfo, delInfo, addInfo, updateInfo, exportInfo } from "@/api/happyclock/info";

export default {
  name: "Info",
  data() {
    

    return {
      //vnode
    dialogArr : [],
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
      // PT管理-信息维护表格数据
      infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,//true, //
       // 日期范围
      dateRange: [],
      // 医院名称字典
      hospnameOptions: [],
      // 录入人字典
      inputornameOptions: [],
      // 状态字典
      sureStateOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        inputtime: undefined,
        inputtime: undefined,
        doctorname: undefined,
        hospname: undefined,
        patientname: undefined,
        sureState: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        inputtime: [
          { required: true, message: "用药日期不能为空", trigger: "blur" }
        ],
        doctorname: [
          { required: true, message: "医生姓名不能为空", trigger: "blur" }
        ],
        hospname: [
          { required: true, message: "医院名称不能为空", trigger: "blur" }
        ],
        dosage: [
          { required: true, message: "用量（支）不能为空", trigger: "blur" }
        ],
        patientname: [
          { required: true, message: "PT姓名不能为空", trigger: "blur" }
        ],
        inputorname: [
          { required: true, message: "录入人不能为空", trigger: "blur" }
        ],
        inputtime: [
          { required: true, message: "提醒日期不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("hc_v_hospital").then(response => {
      this.hospnameOptions = response.data;
    });
    this.getDicts("hc_inputorname").then(response => {
      this.inputornameOptions = response.data;
    });
    this.getDicts("hc_sure_state").then(response => {
      this.sureStateOptions = response.data;
    });
  },
  methods: {
    /** 查询PT管理-信息维护列表 */
    getList() {
      this.loading = true;
      listInfo(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.infoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 医院名称字典翻译
    hospnameFormat(row, column) {
      return this.selectDictLabel(this.hospnameOptions, row.hospname);
    },
    // 录入人字典翻译
    inputornameFormat(row, column) {
      return this.selectDictLabel(this.inputornameOptions, row.inputorname);
    },
    // 状态字典翻译
    sureStateFormat(row, column) {
      return this.selectDictLabel(this.sureStateOptions, row.sureState);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        patientsId: undefined,
        inputtime: undefined,
        inputtime: undefined,
        inputtime: undefined,
        doctorname: undefined,
        hospname: undefined,
        dosage: undefined,
        patientname: undefined,
        inputorname: undefined,
        dayNum: undefined,
        inputtime: undefined,
        sureState: undefined,
        createTime: undefined,
        updateTime: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.patientsId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加PT管理-信息维护";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const patientsId = row.patientsId || this.ids
      getInfo(patientsId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改PT管理-信息维护";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.patientsId != undefined) {
            updateInfo(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addInfo(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const patientsIds = row.patientsId || this.ids;
      this.$confirm('是否确认删除PT管理-信息维护编号为"' + patientsIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delInfo(patientsIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有PT管理-信息维护数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportInfo(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
// 通知提醒
    ,
    // open1() {
    //     const h = this.$createElement;

    //     this.$notify({
    //       title: '标题名称',
    //       message: h('i', { style: 'color: teal'}, '这是提示文案')
    //     });
    //   },
      open1() {
                const h = this.$createElement;
                this.$notify({
                    title: '标题名称',
                    message: h('p', null, [
                        h('span', null, '内容可以是 '),
                        h('a', {
                            on:{
                                click:this.clickA
                            }
                        }, "可点击的标签"),
                        h('button', {
                            on:{
                                click:this.clickBtn
                            }
                        }, "按钮")
                    ]),
                    position: 'bottom-right',
                    duration: 0
                });
            },
            clickA(){
                console.log(this);
                alert("处理点击标签");
            },
            clickBtn(){
                alert("处理点击按钮");
            }

  }
};
</script>

