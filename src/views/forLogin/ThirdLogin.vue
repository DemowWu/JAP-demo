<template>
  <div class="containerThird">
    <div><el-dialog class="logdialog" v-show.sync="index === '1'">
      <div slot="title">
        <el-tooltip content="第三方登录,这里选择以Gitee第三方应用为例" placement="top">
          <i class="el-icon-chat-dot-round" :style="darkorange"></i>
        </el-tooltip>
        JFinal-第三方登录{{index}
        <el-popover style="text-align:right;" title="注意事项" placement="top" width="100" trigger="hover" content="第三方登录需要拿到第三方用户信息">
          <el-link type="primary" slot="reference" href="https://justauth.wiki/oauth/gitee.html#_1-%E7%94%B3%E8%AF%B7%E5%BA%94%E7%94%A8">
            <i class="el-icon-view el-icon--right"></i>参考链接</el-link>
        </el-popover>
        <br />
        <el-divider>
          {{loginway}}
        </el-divider>
        <el-form :model="userInfo" label-width="100px">
          <el-form-item label="ClientId">
            <el-input v-model="clientId" autocomplete="off" placeholder="请输入clientId"></el-input>
          </el-form-item>
          <el-form-item label="RedirectURI">
            <el-input v-model="redirectURI" autocomplete="off" placeholder="请输入redirectURI"></el-input>
          </el-form-item>
          <el-form-item label="ClientSecret">
            <el-input v-model="clientSecret" autocomplete="off" placeholder="请输入clientSecret"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dilog-footer">
          <el-button @click="statusThirdLogin = false" placement="left">忘记密码</el-button>
          <el-button type="primary" @click="statusThirdLogin = false">确认登录</el-button>
        </div>
      </div>
    </el-dialog></div>
    <div><el-dialog class="logdialog" v-if="index === '2'">
      <div slot="title">
        <el-tooltip content="第三方登录,这里选择以Gitee第三方应用为例" placement="top">
          <i class="el-icon-chat-dot-round" :style="darkorange"></i>
        </el-tooltip>
        JFinal-第三方登录{{index}}
        <el-popover style="text-align:right;" title="注意事项" placement="top" width="100" trigger="hover" content="第三方登录需要拿到第三方用户信息">
          <el-link type="primary" slot="reference" href="https://justauth.wiki/oauth/gitee.html#_1-%E7%94%B3%E8%AF%B7%E5%BA%94%E7%94%A8">
            <i class="el-icon-view el-icon--right"></i>参考链接</el-link>
        </el-popover>
        <br />
        <el-divider>
          {{loginway}}
        </el-divider>
        <el-form :model="userInfo" label-width="100px">
          <el-form-item label="ClientId">
            <el-input v-model="clientId" autocomplete="off" placeholder="请输入clientId"></el-input>
          </el-form-item>
          <el-form-item label="RedirectURI">
            <el-input v-model="redirectURI" autocomplete="off" placeholder="请输入redirectURI"></el-input>
          </el-form-item>
          <el-form-item label="ClientSecret">
            <el-input v-model="clientSecret" autocomplete="off" placeholder="请输入clientSecret"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dilog-footer">
          <el-button @click="statusThirdLogin = false" placement="left">忘记密码</el-button>
          <el-button type="primary" @click="statusThirdLogin = false">确认登录</el-button>
        </div>
      </div>
    </el-dialog></div>
  </div>
</template>

<script>
export default {
  name: "ThirdLogin",
  props: ["index","loginway"],
  data(){
    return{
      info:{
        dialogTitle: loginway,
      },
      userInfo:{
        clientId: '',
        clientSecret: '',
        code: '',
        redirectURI:''
      },

    }
  },
  methods:{
  }
}
</script>

<style scoped>
.containerThird.title{
  text-align: center;
}
.logdialog{
  width: 1000px;
  height: 500px;
  font-size: medium;
  text-align: center;
  margin-left: -105px;
  margin-right: -100px;
  margin-top: -20px;
  overflow: hidden;
}
</style>