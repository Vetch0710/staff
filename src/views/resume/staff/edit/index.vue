<template>
  <div>
    <el-dialog v-bind="$attrs"
               v-on="$listeners"
               @open="onOpen"
               @close="Close"
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
              <el-date-picker v-model="formData.birthDay" format="yyyy-MM-dd"  value-format="timestamp"
                              :style="{width: '100%'}" placeholder="请输入出生日期" clearable></el-date-picker>
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
                        prefix-icon='el-icon-mobile' :style="{width: '100%'}"></el-input>
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
                              :style="{width: '100%'}" placeholder="请输入毕业时间" clearable></el-date-picker>
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
              <el-select v-model="formData.department" placeholder="请输入所属部门" clearable
                         :style="{width: '100%'}" @change="selectProject(formData.department)">
                <el-option v-for="(dict, index) in departmentOptions" :key="dict"
                           :label="dict"
                           :value="dict"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属项目组" prop="projectTeam">
              <el-select v-model="formData.projectTeam" placeholder="请输入所属项目组" clearable
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
              <el-select v-model="formData.leader" placeholder="请输入项目经理" clearable :style="{width: '100%'} ">
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
                           v-model="formData.idCardFront"
                           v-loading="isUpload"
                           :on-success="handleAvatarSuccess"
                           :before-upload="beforeUpload" list-type="picture-card" accept="image/*"
                           :headers="{'Authorization': 'Bearer '+token}"
                           :class="{ hide: idCardFrontArray.length > 0 }"
                           :before-remove="() => {
                              idCardFrontArray=[];
                              formData.idCardFront=null;
                            }"
                           :on-progress="onProgress"
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
                           v-model="formData.idCardBack"
                           v-loading="isUpload"
                           :on-success="handleAvatarSuccess"
                           :before-upload="beforeUpload" list-type="picture-card" accept="image/*"
                           :headers="{'Authorization': 'Bearer '+token}"
                           :class="{ hide: idCardBackArray.length > 0 }"
                           :before-remove="() => {
                              idCardBackArray=[];
                              formData.idCardBack=null;
                            }"
                           :on-progress="onProgress"
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
                           v-model="formData.diploma"
                           v-loading="isUpload"
                           :on-success="handleAvatarSuccess"
                           :before-upload="beforeUpload" list-type="picture-card" accept="image/*"
                           :headers="{'Authorization': 'Bearer '+token}"
                           :class="{ hide: diplomaArray.length > 0 }"
                           :before-remove="() => {
                              diplomaArray=[];
                               formData.diploma=null;
                            }"
                           :on-progress="onProgress"
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
                           v-model="formData.degree"
                           v-loading="isUpload"
                           :on-success="handleAvatarSuccess"
                           :before-upload="beforeUpload" list-type="picture-card" accept="image/*"
                           :headers="{'Authorization': 'Bearer '+token}"
                           :class="{ hide: degreeArray.length > 0 }"
                           :before-remove="() => {
                              degreeArray=[];
                              formData.degree=null;
                            }"
                           :on-progress="onProgress"
                >
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
  import {
    listURP,

  } from '@/api/resume/urp'
  import {
    updateStaff
  } from '@/api/resume/staff'

  export default {
    inheritAttrs: false,
    components: {},
    data() {
      return {
        idCardFrontArray: [],
        idCardBackArray: [],
        diplomaArray: [],
        degreeArray: [],
        disabled: false,
        action: process.env.VUE_APP_BASE_API + '/resume/staff/upload/',
        token: '',
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

        sexOptions: [],
        // 项目组字典
        projectOptions: [],
        // 部门数据字典
        departmentOptions: [],
        // 职工级别数据字典
        levelOptions: [],
        projectManagerOptions: [],
        title: '',
        dialogFormVisible: false,
        role: '',
        //部门与项目匹配关系
        deptAndPro: [],
        //项目与项目经理匹配关系
        proAndManager: [],
        isUpload: false
      }
    },
    computed: {},
    watch: {},
    created() {

      this.token = this.$store.getters.token
    },
    mounted() {
    },
    methods: {
      onProgress() {
      },
      handleAvatarSuccess(res, file) {
        if (res) {
          if (res.imgUrl === null) {
            this.$message.error('上传失败，请检查您的网络', 'error')
          } else {
            let type = res.type
            if (type === 'degree') {
              this.formData.degree = res.imgUrl
              // this.formData.degree = file.url
              this.degreeArray.push(res.imgUrl)

            } else if (type === 'diploma') {
              this.formData.diploma = res.imgUrl
              // this.formData.diploma = file.url
              this.diplomaArray.push(res.imgUrl)

            } else if (type === 'idCardFront') {
              this.formData.idCardFront = res.imgUrl
              // this.formData.idCardFront = file.url
              this.idCardFrontArray.push(res.imgUrl)

            } else if (type === 'idCardBack') {
              this.formData.idCardBack = res.imgUrl
              // this.formData.idCardBack = file.url
              this.idCardBackArray.push(res.imgUrl)

            }
            // this.imageUrlArray.push(res.imgUrl)
            this.$message.success('上传成功', 'success')
          }
        }
        this.isUpload = false
        console.log(this.formData)
      },
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
      showEdit(row) {
        if (!row) {
          this.title = '添加'
        } else {
          this.title = '编辑'
          this.formData = Object.assign({}, row);
          // this.formData = row
        }
        this.dialogFormVisible = true
      },
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done()
          })
          .catch(_ => {
          })
      },
      onOpen() {
        this.role = this.$store.getters.roles[0]
        //获取部门名称集合、部门与项目匹配关系集合、项目与项目经理匹配关系集合
        listURP().then(response => {
          this.deptAndPro = response.data.deptAndPro
          this.proAndManager = response.data.proAndManager
          let udp = response.data.udp

          if (this.role === 'admin') {
            this.departmentOptions = response.data.departmentNames
          } else if (this.role === 'ProjectManager') {
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
            let arr = []
            this.formData.department = arr[0] = udp.deptName
            this.departmentOptions = arr
            let index = this.deptAndPro.findIndex(item => {  //findindex方法 当为true时返回元素索引 并停止执行  若没有 则返回-1
              if (item.departmentName === udp.deptName) {
                return true
              }
            })
            this.projectOptions = this.deptAndPro[index].projectName
          }
        })
        this.getDicts('resume_staff_level').then(response => {
          this.levelOptions = response.data
        })
        this.getDicts('sys_user_sex').then(response => {
          this.sexOptions = response.data
        })
      },
      Close() {
        // this.$refs['elForm'].resetFields()
        // this.dialogFormVisible = false;
        // this.resetForm('formData')
        this.idCardFrontArray = []
        this.idCardBackArray = []
        this.diplomaArray = []
        this.degreeArray = []
        this.$refs['formData'].resetFields()
        this.formData = this.$options.data().formData
        // this.idCardFrontArray = []
        // this.idCardBackArray = []
        // this.diplomaArray = []
        // this.degreeArray = []

        this.dialogFormVisible = false
        // console.log('----' + this.formData)
      },
      selectProject(value) {
        // console.log(value)
        if (this.role === 'admin') {
          if (value !== undefined && value !== '') {
            let index = this.deptAndPro.findIndex(item => {  //findindex方法 当为true时返回元素索引 并停止执行  若没有 则返回-1
              if (item.departmentName === value) {
                return true
              }
            })
            this.projectOptions = this.deptAndPro[index].projectName
            this.formData.projectTeam = ''
            this.formData.leader = ''
          } else {
            this.formData.projectTeam = ''
            this.formData.leader = ''
          }
        }
      },
      selectProjectManager(value) {
        // console.log(value)
        if (this.role !== 'ProjectManager') {
          if (value !== undefined && value !== '') {
            let index = this.proAndManager.findIndex(item => {  //findindex方法 当为true时返回元素索引 并停止执行  若没有 则返回-1
              if (item.projectName.trim() === value.trim()) {
                return true
              }
            })
            let arr = []
            arr[0] = this.proAndManager[index].projectManager
            this.projectManagerOptions = arr
            // console.log( this.projectManagerOptions[0])
            this.formData.leader = ''
          } else {
            this.formData.leader = ''
          }
        }
      },
      handelConfirm() {
        this.$refs['formData'].validate(valid => {
          if (valid) {
            console.log(this.formData)
          }
          updateStaff(this.formData).then(response => {

          })
          this.$emit('fetch-data')
          this.Close()
        })
      }
    }
  }

</script>
<style>
  .el-upload__tip {
    line-height: 1.2;
  }

  .receipt-images-wrap .hide .el-upload--picture-card {
    display: none;
  }

</style>
