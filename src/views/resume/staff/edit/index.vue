<template>
  <div>
    <!--    @close="Close"-->
    <el-dialog v-bind="$attrs"
               v-on="$listeners"
               @open="onOpen"
               :visible.sync="dialogFormVisible"
               :title="title"
               :before-close="handleClose">
      <el-row :gutter="15">
        <el-form ref="formData" :model="formData" :rules="rules" size="medium" label-width="94px">
          <el-col :span="8">
            <el-form-item label="员工编号" prop="userId">
              <el-input v-model="formData.userId" placeholder="请输入员工编号" show-word-limit clearable
                        prefix-icon='el-icon-mobile' :style="{width: '100%'}"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="姓名" prop="userName">
              <el-input v-model="formData.userName" placeholder="请输入姓名" show-word-limit clearable
                        prefix-icon='el-icon-mobile' :style="{width: '100%'}"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="性别" prop="sex">
              <el-select v-model="formData.sex" placeholder="请输入性别" clearable :style="{width: '100%'}">
                <el-option v-for="(dict, index) in sexOptions" :key="dict.dictValue"
                           :label="dict.dictLabel"
                           :value="dict.dictValue"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="出生日期" prop="birthDay">
              <el-date-picker v-model="formData.birthDay" format="yyyy-MM-dd" value-format="timestamp"
                              :style="{width: '100%'}" placeholder="请输入出生日期" :picker-options="pickerOptions"
                              clearable></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="民族" prop="nation">
              <el-input v-model="formData.nation" placeholder="请输入民族" show-word-limit clearable
                        prefix-icon='el-icon-mobile' :style="{width: '100%'}"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="婚姻状况" prop="marital">
              <el-input v-model="formData.marital" placeholder="请输入婚姻状况" show-word-limit clearable
                        prefix-icon='el-icon-mobile' :style="{width: '100%'}"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="手机号码" prop="phone">
              <el-input v-model="formData.phone" placeholder="请输入手机号码" show-word-limit clearable
                        maxlength="11" prefix-icon='el-icon-mobile' :style="{width: '100%'}"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="职工级别" prop="workLevel">
              <el-select v-model="formData.workLevel" placeholder="请输入职工级别" clearable
                         :style="{width: '100%'}">
                <el-option v-for="(dict, index) in levelOptions" :key="dict.dictValue"
                           :label="dict.dictLabel"
                           :value="dict.dictValue"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="户口所在地" prop="hometown">
              <el-input v-model="formData.hometown" placeholder="请输入户口所在地" show-word-limit clearable
                        prefix-icon='el-icon-mobile' :style="{width: '100%'}"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="最高学历" prop="eduBackground">
              <el-input v-model="formData.eduBackground" placeholder="请输入最高学历" show-word-limit clearable
                        prefix-icon='el-icon-mobile' :style="{width: '100%'}"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="毕业时间" prop="graduationDay">
              <el-date-picker v-model="formData.graduationDay" format="yyyy-MM-dd" value-format="timestamp"
                              :style="{width: '100%'}" placeholder="请输入毕业时间" :picker-options="pickerOptions"
                              clearable></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="专业" prop="major">
              <el-input v-model="formData.major" placeholder="请输入专业" show-word-limit clearable
                        prefix-icon='el-icon-mobile' :style="{width: '100%'}"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="毕业院校" prop="school">
              <el-input v-model="formData.school" placeholder="请输入毕业院校" show-word-limit clearable
                        prefix-icon='el-icon-mobile' :style="{width: '100%'}"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属部门" prop="department">
              <el-select v-model="formData.department" placeholder="请选择所属部门" clearable
                         :style="{width: '100%'}" @change="selectProject(formData.department)">
                <el-option v-for="(dict, index) in departmentOptions" :key="dict"
                           :label="dict"
                           :value="dict"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属项目组" prop="projectTeam">
              <el-select v-model="formData.projectTeam" placeholder="请先选择所属部门" clearable
                         :style="{width: '100%'}" @change="selectProjectManager(formData.projectTeam)">
                <el-option v-for="(dict, index) in projectOptions" :key="dict"
                           :label="dict"
                           :value="dict"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目经理" prop="leader">
              <!--              <el-input v-model="formData.leader" placeholder="请输入项目经理" show-word-limit clearable-->
              <!--                        prefix-icon='el-icon-mobile' :style="{width: '100%'}" disabled></el-input>-->
              <el-select v-model="formData.leader" placeholder="请先选择所属部门" clearable :style="{width: '100%'} ">
                <el-option v-for="(dict, index) in projectManagerOptions"
                           :key="dict"
                           :label="dict"
                           :value="dict"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="13">
            <el-form-item label="身份证正面" prop="idCardFront">
              <div class="receipt-images-wrap">
                <el-upload ref="idCardFront" :action="action+'idCardFront'"
                           v-loading="isUpload"
                           :file-list="idCardFrontArray"
                           :on-success="handleUploadSuccess"
                           :before-upload="beforeUpload" list-type="picture-card" accept="image/*"
                           :headers="{'Authorization': 'Bearer '+token}"
                           :class="{ hide: idCardFrontArray.length > 0 }"
                           :before-remove="() => {
                              idCardFrontArray=[];
                            }"
                >
                  <i class="el-icon-plus"></i>
                  <div slot="tip" class="el-upload__tip">只能上传不超过 2MB 的image/*文件</div>
                </el-upload>
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="13">
            <el-form-item label="身份证反面" prop="idCardBack">
              <div class="receipt-images-wrap">
                <el-upload ref="idCardBack" :action="action+'idCardBack'"
                           v-loading="isUpload"
                           :file-list="idCardBackArray"
                           :on-success="handleUploadSuccess"
                           :before-upload="beforeUpload" list-type="picture-card" accept="image/*"
                           :headers="{'Authorization': 'Bearer '+token}"
                           :class="{ hide: idCardBackArray.length > 0 }"
                           :before-remove="() => {
                              idCardBackArray=[];
                            }"
                >
                  <i class="el-icon-plus"></i>
                  <div slot="tip" class="el-upload__tip">只能上传不超过 2MB 的image/*文件</div>
                </el-upload>
              </div>

            </el-form-item>
          </el-col>
          <el-col :span="13">
            <el-form-item label="毕业证" prop="diploma">
              <div class="receipt-images-wrap">
                <el-upload ref="diploma" :action="action+'diploma'"
                           :file-list="diplomaArray"
                           v-loading="isUpload"
                           :on-success="handleUploadSuccess"
                           :before-upload="beforeUpload" list-type="picture-card" accept="image/*"
                           :headers="{'Authorization': 'Bearer '+token}"
                           :class="{ hide: diplomaArray.length > 0 }"
                           :before-remove="() => {
                              diplomaArray=[];
                            }"
                >
                  <i class="el-icon-plus"></i>
                  <div slot="tip" class="el-upload__tip">只能上传不超过 2MB 的image/*文件</div>
                </el-upload>
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="13">
            <el-form-item label="学位证" prop="degree">
              <div class="receipt-images-wrap">
                <el-upload ref="degree" :action="action+'degree'"
                           :file-list="degreeArray"
                           v-loading="isUpload"
                           :on-success="handleUploadSuccess"
                           :before-upload="beforeUpload" list-type="picture-card" accept="image/*"
                           :headers="{'Authorization': 'Bearer '+token}"
                           :class="{ hide: degreeArray.length > 0 }"
                           :before-remove="() => {
                              degreeArray=[];
                            }">
                  <i class="el-icon-plus"></i>
                  <div slot="tip" class="el-upload__tip">只能上传不超过 2MB 的image/*文件</div>
                </el-upload>
              </div>

            </el-form-item>
          </el-col>
        </el-form>
      </el-row>
      <div slot="footer">
        <el-button @click="Close">取消</el-button>
        <el-button type="primary" @click="handelConfirm">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import { listURP } from '@/api/resume/urp'
  import { updateStaff, addStaff } from '@/api/resume/staff'

  export default {
    inheritAttrs: false,
    components: {},
    data() {
      return {
        //日期选择器配置
        pickerOptions: {
          disabledDate(time) { //当前时间以后的日期不可选
            return time.getTime() > Date.now()
          }
        },
        //uploadList---存储身份证正面照
        idCardFrontArray: [],
        //uploadList---存储身份证反面照
        idCardBackArray: [],
        //uploadList---存储毕业证
        diplomaArray: [],
        //uploadList---存储学位证
        degreeArray: [],
        //证件照图片上传路径
        action: process.env.VUE_APP_BASE_API + '/resume/staff/upload/',
        //上传时发送的header
        token: '',
        //表单数据
        formData: {
          userId: '',
          userName: '',
          sex: undefined,
          birthDay: null,
          nation: '',
          marital: '',
          phone: '',
          workLevel: undefined,
          hometown: '',
          eduBackground: '',
          graduationDay: null,
          major: '',
          school: '',
          department: undefined,
          projectTeam: undefined,
          leader: undefined,
          idCardFront: null,
          idCardBack: null,
          diploma: null,
          degree: null
        },
        //表单验证规则
        rules: {
          userId: [{
            required: true,
            message: '请输入员工编号',
            trigger: 'blur'
          }],
          userName: [{
            required: true,
            message: '请输入姓名',
            trigger: 'blur'
          }],
          sex: [{
            required: true,
            message: '请输入性别',
            trigger: 'change'
          }],
          birthDay: [{
            required: true,
            message: '请输入出生日期',
            trigger: 'change'
          }],
          nation: [{
            required: true,
            message: '请输入民族',
            trigger: 'blur'
          }],
          marital: [{
            required: true,
            message: '请输入婚姻状况',
            trigger: 'blur'
          }],
          phone: [{
            required: true,
            message: '请输入手机号码',
            trigger: 'blur'
          }, {
            pattern: /^1(3|4|5|7|8|9)\d{9}$/,
            message: '手机号格式错误',
            trigger: 'blur'
          }],
          workLevel: [{
            required: true,
            message: '请输入职工级别',
            trigger: 'change'
          }],
          hometown: [],
          eduBackground: [],
          graduationDay: [],
          major: [],
          school: [],
          department: [{
            required: true,
            message: '请输入所属部门',
            trigger: 'change'
          }],
          projectTeam: [{
            required: true,
            message: '请输入所属项目组',
            trigger: 'change'
          }],
          leader: [{
            required: true,
            message: '请输入项目经理',
            trigger: 'change'
          }]
        },
        //性别数据字典
        sexOptions: [],
        // 项目组字典
        projectOptions: [],
        // 部门数据字典
        departmentOptions: [],
        // 职工级别数据字典
        levelOptions: [],
        //项目经理数据字典
        projectManagerOptions: [],
        //弹窗标题头
        title: '',
        //是否显示弹窗
        dialogFormVisible: false,
        //当前登陆用户角色
        role: '',
        //部门与项目匹配关系
        deptAndPro: [],
        //项目与项目经理匹配关系
        proAndManager: [],
        //是否正在加载
        isUpload: false
      }
    },
    computed: {},
    watch: {},
    created() {
      //从store中获取token
      this.token = this.$store.getters.token
    },
    mounted() {
    },
    methods: {
      //上传成功时---根据类型赋值
      handleUploadSuccess(res, file) {
        if (res) {
          console.log(res)
          if (res.imgUrl === null) {
            this.$message.error('上传失败，请检查您的网络', 'error')
          } else {
            //根据上传证件照的类型，给表单及uploadList赋值
            let type = res.type
            if (type === 'degree') {
              this.formData.degree = res.imgUrl
              // this.formData.degree = file.url
              this.degreeArray.push({ 'url': res.imgUrl })

            } else if (type === 'diploma') {
              this.formData.diploma = res.imgUrl
              // this.formData.diploma = file.url
              this.diplomaArray.push({ 'url': res.imgUrl })

            } else if (type === 'idCardFront') {
              this.formData.idCardFront = res.imgUrl
              // this.formData.idCardFront = file.url
              this.idCardFrontArray.push({ 'url': res.imgUrl })

            } else if (type === 'idCardBack') {
              this.formData.idCardBack = res.imgUrl
              // this.formData.idCardBack = file.url
              this.idCardBackArray.push({ 'url': res.imgUrl })

            }
            this.$message.success('上传成功', 'success')
          }
        }
        this.isUpload = false
      },
      //上传之前--判断图片大小及格式是否正确
      beforeUpload(file) {
        const isJPG = file.type === 'image/jpeg' || file.type === 'image/png'
        const isLt2M = file.size / 1024 / 1024 < 2

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG或PNG 格式!')
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!')
        }

        return isJPG && isLt2M
      },
      //显示弹窗--根据父组件是否传值判断是添加还是修改数据
      showEdit(row) {
        //清空表单数据
        this.reset()
        if (!row) {
          this.title = '添加'
        } else {
          //给表单及uploadList赋值
          this.title = '编辑'
          this.formData = row
          row.idCardFront ? this.idCardFrontArray.push({ 'url': row.idCardFront }) : ''
          row.idCardBack ? this.idCardBackArray.push({ 'url': row.idCardBack }) : ''
          row.diploma ? this.diplomaArray.push({ 'url': row.diploma }) : ''
          row.degree ? this.degreeArray.push({ 'url': row.degree }) : ''
        }
        //显示表单
        this.dialogFormVisible = true
      },
      //弹窗关闭之前---确定弹窗是否关闭，即当弹窗非法关闭（鼠标点击弹窗外部，非直接点击弹窗内的取消按钮）时的判断
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done()
            this.dialogFormVisible = false
            this.reset()
          })
          .catch(_ => {
          })
      },
      //弹窗打开时----加载数据（下拉列表等等）
      onOpen() {
        //获取当前用户的角色
        this.role = this.$store.getters.roles[0]
        //获取全部数据，前端根据角色进行筛选
        //获取部门名称集合、部门与项目匹配关系集合、项目与项目经理匹配关系集合、该用户的部门、部门经理、部门名
        listURP().then(response => {
          //部门与项目匹配关系集合
          this.deptAndPro = response.data.deptAndPro
          //项目与项目经理匹配关系集合
          this.proAndManager = response.data.proAndManager
          //该用户的部门、部门经理、部门名
          let udp = response.data.udp
          //当前角色为管理员---先加载部门下拉列表，项目和项目经理下拉列表中的数据根据 所选部门及匹配关系 动态加载
          if (this.role === 'admin') {
            this.departmentOptions = response.data.departmentNames
          } else if (this.role === 'ProjectManager') {
            //当前角色为项目经理---确定部门、项目和项目经理下拉列表
            let arr = [], temp = [], mid = []
            //保存部门名称
            this.formData.department = mid[0] = udp.deptName
            //保存项目名称
            this.formData.projectTeam = arr[0] = udp.projectName
            //保存项目经理名称
            this.formData.leader = temp[0] = udp.projectManager
            this.departmentOptions = mid
            this.projectOptions = arr
            this.projectManagerOptions = temp

          } else if (this.role === 'DepartmentManager') {
            //当前角色为部门经理---确定部门下拉列表，项目和项目经理下拉列表根据 所选部门及匹配关系 动态加载
            let arr = []
            this.formData.department = arr[0] = udp.deptName
            this.departmentOptions = arr
            //根据匹配关系找到该部门的所有项目
            let index = this.deptAndPro.findIndex(item => {
              if (item.departmentName === udp.deptName) {
                return true
              }
            })
            //将筛选的项目加载到项目下拉列表
            this.projectOptions = this.deptAndPro[index].projectName
          }
        })
        //获取职工级别字典数据
        this.getDicts('resume_staff_level').then(response => {
          this.levelOptions = response.data
        })
        //获取性别字典数据
        this.getDicts('sys_user_sex').then(response => {
          this.sexOptions = response.data
        })
      },
      //关闭弹窗
      Close() {
        this.dialogFormVisible = false
        this.reset()
      },
      //重置表单
      reset() {
        this.idCardFrontArray = []
        this.idCardBackArray = []
        this.diplomaArray = []
        this.degreeArray = []
        this.formData = {
          userId: '',
          userName: '',
          sex: undefined,
          birthDay: null,
          nation: '',
          marital: '',
          phone: '',
          workLevel: undefined,
          hometown: '',
          eduBackground: '',
          graduationDay: null,
          major: '',
          school: '',
          department: undefined,
          projectTeam: undefined,
          leader: undefined,
          idCardFront: null,
          idCardBack: null,
          diploma: null,
          degree: null
        }
        this.resetForm('formData')
      },
      //部门下拉列表更改时---根据部门加载项目下拉列表--联动效果
      selectProject(value) {
        //只有管理员可以更改部门
        if (this.role === 'admin') {
          //判断时清空数据还是更改选项
          if (value !== undefined && value !== '') {
            //根据部门名称获取项目集合
            let index = this.deptAndPro.findIndex(item => {  //findindex方法 当为true时返回元素索引 并停止执行  若没有 则返回-1
              if (item.departmentName === value) {
                return true
              }
            })
            //加载项目下拉列表
            this.projectOptions = this.deptAndPro[index].projectName
            //清空项目下拉数据和项目经理数据
            this.formData.projectTeam = ''
            this.formData.leader = ''
          } else {
            // 清空项目数据同时，清空项目下拉数据和项目经理数据
            this.formData.projectTeam = ''
            this.formData.leader = ''
          }
        }
      },
      //部门下拉列表更改时---加载项目经理下拉列表--联动效果
      selectProjectManager(value) {
        if (this.role !== 'ProjectManager') {
          //项目经理不可更改项目
          if (value !== undefined && value !== '') {
            let index = this.proAndManager.findIndex(item => {
              if (item.projectName.trim() === value.trim()) {
                return true
              }
            })
            let arr = []
            arr[0] = this.proAndManager[index].projectManager
            this.projectManagerOptions = arr
            this.formData.leader = ''
          } else {
            this.formData.leader = ''
          }
        }
      },
      //提交表单
      handelConfirm() {
        //验证表单数据
        this.$refs['formData'].validate(valid => {
          if (valid) {
            //将证件照路径传到表单中
            this.formData.degree = this.degreeArray[0] ? this.degreeArray[0].url : null
            this.formData.diploma = this.diplomaArray[0] ? this.diplomaArray[0].url : null
            this.formData.idCardBack = this.idCardBackArray[0] ? this.idCardBackArray[0].url : null
            this.formData.idCardFront = this.idCardFrontArray[0] ? this.idCardFrontArray[0].url : null
            //发送数据到添加接口
            if (this.title === '添加') {
              addStaff(this.formData).then(response => {
                if (response.code === 200) {
                  this.msgSuccess('保存成功')
                  //操作成功---向父组件发送消息，刷新列表，关闭弹窗
                  this.$emit('fetch-data')
                  this.dialogFormVisible = false
                } else {
                  this.msgError('保存失败，请检查您的数据及网络连接')
                }
              })
            } else if (this.title === '') {
              //发送数据到编辑接口
              updateStaff(this.formData).then(response => {
                if (response.code === 200) {
                  this.msgSuccess('保存成功')
                  this.$emit('fetch-data')
                  this.dialogFormVisible = false
                } else {
                  this.msgError('保存失败，请检查您的数据及网络连接')
                }
              })
            }
          }
        })
      }
    }
  }

</script>
<style>
  .el-upload__tip {
    line-height: 1.2;
  }
  /*当上传文件超过1时，隐藏upload上传标签*/
  .receipt-images-wrap .hide .el-upload--picture-card {
    display: none;
  }

</style>
