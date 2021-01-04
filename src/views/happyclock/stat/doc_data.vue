<template>
  <div class="app-container">
    
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">

      <el-form-item label="用药月份">
            <el-date-picker
              v-model="queryParams.inputtime"
              size="small"
              style="width: 150px"
              value-format="yyyy-MM"
              type="month"
              
            ></el-date-picker>
          </el-form-item>

      <el-form-item label="医生姓名" prop="doctorname">
        <el-input
          v-model="queryParams.doctorname"
          placeholder="请输入医生姓名"
          clearable
          size="small"
          style="width: 150px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="次数" prop="num">
        <el-input
          v-model="queryParams.num"
          placeholder="请输入次数"
          clearable
          size="small"
          style="width: 125px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item style="width: 240px ;margin-left:68px;">
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['happyclock:info:export']"
        >导出</el-button>
      </el-form-item>
    </el-form>


        <el-table  v-loading="loading" :data="infoList" @selection-change="handleSelectionChange" style="">
              <el-table-column label="序号" align="center" prop="" width="200px" >
                <template slot-scope="scope">
                  <span>{{ scope.row.rowNum}}</span>
                </template>
              </el-table-column>
              <el-table-column label="用药月份" align="center" prop="inputtime" >
                <template slot-scope="scope">
                  <span>{{ parseTime(scope.row.inputtime, '{y}-{m}') }}</span>
                </template>
              </el-table-column>
              <el-table-column label="医生姓名" align="center" prop="doctorname"  />
              <el-table-column label="PT姓名" align="center" prop="patientname"  />

              <el-table-column label="次数" align="center" prop="num" >
                <template slot-scope="scope">
                  <router-link :to="'/happyclock/to/detail/'+scope.row.inputtime+scope.row.doctorname+scope.row.patientname+'?inputtime='+scope.row.inputtime+'&doctorname='+scope.row.doctorname+'&patientname='+scope.row.patientname" 
                    class="link-type">
                    <span>{{ scope.row.num }}</span>
                  </router-link>
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
        <el-form-item label="提醒天数" prop="dayNum" width="50">
          <el-input v-model="form.dayNum" placeholder="请输入提醒天数" />
        </el-form-item>
        <!-- <el-form-item label="状态">
          <el-select v-model="form.sureState" placeholder="请选择状态">
            <el-option
              v-for="dict in sureStateOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listInfo, getInfo, delInfo, addInfo, updateInfo, exportInfo } from "@/api/happyclock/doc_data";

export default {
  name: "ToPT",
  data() {
    return {
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
      open: false,
       // 日期范围
      dateRange: [],
      // 医院名称字典
      hospnameOptions: [],
      // 录入人字典
      inputornameOptions: [],
      // 状态字典
      sureStateOptions: [
        {
          dictValue:0,
          dictLabel:'未确认',
        }
      ],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        inputtime: undefined,
        doctorname: undefined,
        hospname: undefined,
        patientname: undefined,
        sureState: 0,
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
    this.queryParams.inputtime=  this.$route.query.inputtime;
    this.queryParams.doctorname= this.$route.query.doctorname;
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
  // mounted(){
  //   this.queryParams.inputtime=  this.$route.query.inputtime;
  //   this.queryParams.doctorname= this.$route.query.doctorname;
  // },
  //  beforeRouteUpdate() {
  //   this.queryParams.inputtime=  this.$route.query.inputtime;
  //   this.queryParams.doctorname= this.$route.query.doctorname;
  //   },
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
      this.title = "添加PT信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const patientsId = row.patientsId || this.ids
      getInfo(patientsId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改PT信息";
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
  }
};
</script>