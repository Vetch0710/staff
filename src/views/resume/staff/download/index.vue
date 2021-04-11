<template>
  <div>
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <div class="el-upload__text">
        请先上传简历模板，然后点击确认进行下载
      </div>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".docx, .doc"
        :headers="upload.headers"
        :action="upload.url"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :show-file-list="true"
        :auto-upload="false"
        drag
        :before-upload="beforeUpload"
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="down()">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>

    </el-dialog>
  </div>
</template>
<script>
  import { exportStaff, uploadTemp } from '@/api/resume/staff'
  import { getToken } from '@/utils/auth'

  export default {
    inheritAttrs: false,
    components: {},
    data() {
      return {
        active: 0,
        //上传时发送的header
        token: '',
        upload: {
          // 是否显示弹出层（用户导入）
          open: false,
          // 弹出层标题（用户导入）
          title: '下载简历',
          // 是否禁用上传
          isUploading: false,
          // 设置上传的请求头部
          headers: { Authorization: 'Bearer ' + getToken() },
          // 上传的地址
          url: process.env.VUE_APP_BASE_API + '/resume/staff/upload',
          file: {}
        }
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
      showDownload() {
        this.upload.open = true

      },
      handleFileUploadProgress(event, file, fileList) {
        this.upload.isUploading = true
      },
      // 上传预处理
      beforeUpload(file) {
        console.log(file);
        this.file = file
      },
      // 提交上传文件
      async submitFileForm() {
        this.$refs.upload.submit()
      },
      // 提交上传文件
      async down() {
        return new Promise(((resolve) => {
          this.$confirm('是否确认导出所有用户数据项?', '警告', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.submitFileForm()
            resolve(true)
          }).then(() => {
            let formData = new FormData()
            formData.append('file', this.file)
           return  exportStaff(formData)
          }).then(response => {
            this.download(response.msg)
          }).catch(function() {

          })
        }))

      }
    }
  }

</script>
<style>


</style>
