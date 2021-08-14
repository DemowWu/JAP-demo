<template>
  <div class="header">
    <el-drawer title="开源赋能开发者. Just auth into any app." :visible.sync="headdrawer" :direction="direction" class="drawer">
      <span>描述项目内容</span>
    </el-drawer>

    <el-row :gutter="3">
      <el-col :span="6" :offset="1">
        <el-link href="https://justauth.plus/">
          <h1 class="maintitle animate__animated animate__bounce">
            <img src="https://justauth.plus/logo.png" height="50" width="50"/>Justauth Plus</h1>
        </el-link>
      </el-col>
      <el-col :span="6" :offset="-1">
        <div class="location-header">
          <div class="demo-design">
            <el-button @click="headdrawer=!headdrawer" class="button1">
              demo介绍
            </el-button>
          </div>
        </div>
      </el-col>
      <el-col :span="6" :offset="-1">
        <div class="location-header">
          <div class="location-header">
            <div class="demo-design">
              <el-button @click="headdrawer=!headdrawer" class="button1">
                demo介绍
              </el-button>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "Headers",
  data(){
    return{
      userAndDemoInfo:{
        dName: '',
        dDescription: '',
        dMark: ''
      },
      direction: 'ttb',
      headdrawer:true
    }
  }
}
</script>

<style scoped>
.location-header{
  text-align: center;
  min-height: 50px;
  margin-top: 20px;
  margin-bottom: 10px;
}
.button1{
  border-radius: 30px;
  background: rgb(140, 197, 255);
  opacity: 0.6;
  margin-top: 20px;
  width: 200px;
  height: 50px;
}
.maintitle{
  margin-top: 40px;
  --animate-duration: 2s;
}
.drawer /deep/ .el-drawer.ttb {
  width: 60%;
  min-height: 300px;
  left: 370px;
  right: 500px;
  opacity: 0.56;
  border-radius: 30px;
}
.drawer /deep/ .el-drawer__header {
  align-items: center;
  color: #f56c6c;
  display: flex;
  margin-bottom: 32px;
  padding: 20px 20px 0;
  text-align: center;
  font-size: 20px;
  font-style: inherit;
}
</style>