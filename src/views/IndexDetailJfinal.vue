<template>
  <div class="main">
    <div class="cardContainer">
      <el-card class="mycard animate__animated animate__fadeInBottomRight"  shadow="always">
        <div slot="header" class="clearfix">
          <span style="margin-top: 20px;font-size: 20px">JFinal</span>
          <el-button style="float: right; padding: 3px 0" type="text" class="right-ico">
              <el-dropdown @command="handleOrder">
                  <i class="el-icon-s-tools" style="font-size: 30px"></i>
                  <el-dropdown-menu slot="dropdown">
                      <el-dropdown-item icon="el-icon-circle-plus" command='1'>JFinal</el-dropdown-item>
                      <el-dropdown-item icon="el-icon-circle-plus" command='2'>Blade</el-dropdown-item>
                      <el-dropdown-item icon="el-icon-circle-plus" command='3'>ActFramework</el-dropdown-item>
                  </el-dropdown-menu>
              </el-dropdown>
          </el-button>
        </div>
        <div class="fourCols" >
          <el-cow gutter="5">
            <el-col span="6">
              <el-button type="primary" class="optionway animate__animated animate__slideInRight animate__fast" style="margin-left: 20px" @click="statusThirdLogin = true">
                第三方登录
              </el-button>
<!--              <ThirdLogin :loginway="JFinal" :index="1"></ThirdLogin>-->
              <el-dialog class="logdialog" :append-to-body="true" :visible.sync="statusThirdLogin">
                <div slot="title">
                  <el-tooltip content="第三方登录,这里选择以Gitee第三方应用为例" placement="top">
                    <i class="el-icon-chat-dot-round" :style="darkorange"></i>
                  </el-tooltip>
                  JFinal-第三方登录
                  <el-popover style="text-align:right;" title="注意事项" placement="top" width="100" trigger="hover" content="第三方登录需要拿到第三方用户信息">
                    <el-link type="primary" slot="reference" href="https://justauth.wiki/oauth/gitee.html#_1-%E7%94%B3%E8%AF%B7%E5%BA%94%E7%94%A8">
                      <i class="el-icon-view el-icon--right"></i>参考链接</el-link>
                  </el-popover>
                  <br />
                  <el-divider>
                    <el-tag type="primary" style="height: 35px; width: 130px; text-align: center; font-size: medium;">
                      <el-link type="primary" href="https://jfinal.com/"><div class="animate__animated animate__fadeInUp">JFinal</div></el-link>
                    </el-tag>
                  </el-divider>
                  <el-form :model="userInfo" label-width="100px">
                    <el-form-item label="ClientId" style="margin-top: 50px">
                      <el-input v-model="userInfo.clientId" autocomplete="off" placeholder="请输入clientId" class="animate__animated animate__fadeInRight animate__slow"></el-input>
                    </el-form-item>
                    <el-form-item label="RedirectURI">
                      <el-input v-model="userInfo.redirectURI" autocomplete="off" placeholder="请输入redirectURI" class="animate__animated animate__fadeInRight animate__slow animate__delay-1s"></el-input>
                    </el-form-item>
                    <el-form-item label="ClientSecret">
                      <el-input type="password" v-model="userInfo.clientSecret" autocomplete="off" placeholder="请输入clientSecret" class="animate__animated animate__fadeInRight animate__slow animate__delay-2s"></el-input>
                    </el-form-item>
                  </el-form>
                  <div slot="footer" class="dilog-footer">
                    <el-button @click="" placement="left">忘记密码</el-button>
                    <el-button type="primary" @click="goThirdLogin" :loading="logining">确认登录</el-button>
                  </div>
                </div>
              </el-dialog>
            </el-col>
            <el-col span="6">
              <el-button type="danger" class="optionway animate__animated animate__slideInRight animate__fast" style="margin-left: 20px" @click="statusOAuthLogin =true">OAuth登录</el-button>
<!--              <ThirdLogin :loginway="JFinal" :index='2'></ThirdLogin>-->
              <el-dialog class="logdialog" :append-to-body="true" :visible.sync="statusOAuthLogin">
                <div slot="title" >
                  <el-tooltip content="第三方登录,这里选择以Gitee第三方应用为例" placement="top">
                    <i class="el-icon-chat-dot-round"></i>
                  </el-tooltip>
                  JAP的OAuth模式demo
                  <el-popover style="text-align:right;" title="注意事项" placement="top" width="100" trigger="hover" content="第三方登录需要拿到第三方用户信息">
                    <el-link type="primary" slot="reference" href="https://justauth.wiki/oauth/gitee.html#_1-%E7%94%B3%E8%AF%B7%E5%BA%94%E7%94%A8"><i class="el-icon-view el-icon--right"></i>参考链接</el-link>
                  </el-popover>
                  <br/>
                  <el-divider>
                    <el-tag type="primary" style="height: 35px; width: 130px; text-align: center; font-size: medium;margin: 0px">
                      <el-link type="primary" href="https://jfinal.com/"><div class="animate__animated animate__fadeInUp">JFinal</div></el-link>
                    </el-tag>
                  </el-divider>
                  <div class="OauthAuthorizationWay">
                    <div class="radios">
                      <el-row type="flex" justify="space-around" class="row-bg animate__animated animate__bounceInLeft">
                        <el-col :span="6"><el-radio v-model="choose" label='1' id="radiway1">authorization code</el-radio></el-col>
                        <el-col :span="6"><el-radio v-model="choose" label='3' id="radiway3">password credentials</el-radio></el-col>
                      </el-row>
<!--                      <el-row type="flex" justify="space-around" class="row-bg animate__animated animate__bounceInRight">-->
<!--                        <el-col :span="6" :pull="1"><el-radio v-model="choose" label='2' id="radiway2">implicit</el-radio></el-col>-->
<!--                        <el-col :span="6"><el-radio v-model="choose" label='4' id="radiway4">client credentials</el-radio></el-col>-->
<!--                      </el-row>-->
                    </div>
                    <div>
                      <div name="slide-fade">
                        <el-form v-if="choose === '0'">
                          <el-skeleton :rows="5" animated />
                        </el-form>
                        <el-form v-if="choose === '1'" status-icon ref="userInfo" :model="userInfo" label-width="100px">
                          <el-form-item label="ClientId" prop="clientId" style="margin-top: 30px" >
                            <el-input v-model="userInfo.clientId" width="300px" autocomplete="off" placeholder="请输入clientId" class="animate__animated animate__fadeInRight animate__slow"></el-input>
                          </el-form-item>
                          <el-form-item label="RedirectURI" prop="redirectURI">
                            <el-input v-model="userInfo.redirectURI" autocomplete="off" placeholder="请输入redirectURI" class="animate__animated animate__fadeInRight animate__slow animate__delay-1s"></el-input>
                          </el-form-item>
                          <el-form-item label="ClientSecret" prop="clientSecret">
                            <el-input type="password" v-model="userInfo.clientSecret" autocomplete="off" placeholder="请输入clientSecret" class="animate__animated animate__fadeInRight animate__slow animate__delay-2s"></el-input>
                          </el-form-item>
                        </el-form>
                        <el-form v-if="choose === '2'" status-icon ref="userInfo" :model="userInfo" label-width="100px">
                            <el-tag type="info">暂未实现</el-tag>
                            <el-skeleton :rows="5" animated />
                        </el-form>
                        <el-form v-if="choose === '3'" status-icon ref="userInfo" :model="userInfo" label-width="100px">
                          <el-form-item label="userName" prop="userName" style="margin-top: 30px">
                            <el-input v-model="userInfo.userName" autocomplete="off" placeholder="请输入账号" class="animate__animated animate__fadeInRight animate__slow"></el-input>
                          </el-form-item>
                          <el-form-item label="passWord" prop="passWord">
                            <el-input  type="password" v-model="userInfo.passWord" autocomplete="off" placeholder="请输入密码" class="animate__animated animate__fadeInRight animate__slow animate__delay-1s"></el-input>
                          </el-form-item>
                        </el-form>
                      </div>
                      <el-form v-if="choose === '4'" status-icon ref="userInfo" :model="userInfo" label-width="100px">
                          <el-tag type="info">暂未实现</el-tag>
                          <el-skeleton :rows="5" animated />
                      </el-form>
                    </div>
                  </div>
                  <div slot="footer" class="dilog-footer">
                    <el-button @click="statusThirdLogin = false" placement="left">忘记密码</el-button>
                    <el-button type="primary" @click = "goAuthorization">确认登录</el-button>
                  </div>
                </div>
              </el-dialog>
            </el-col>
            <el-col span="6">
              <el-button type="primary" class="optionway animate__animated animate__slideInRight animate__fast" style="margin-left: 20px" @click="statusOIDCLogin= true">OIDC登录</el-button>
              <el-dialog :append-to-body="true" class="logdialog" :visible.sync="statusOIDCLogin">
                <div slot="title">
                  <el-tooltip content="第三方登录,这里选择以Gitee第三方应用为例" placement="top">
                    <i class="el-icon-chat-dot-round" :style="darkorange"></i>
                  </el-tooltip>
                  JFinal-OIDC模式demo
                  <el-popover style="text-align:right;" title="注意事项" placement="top" width="100" trigger="hover" content="基于Oauth2的openid，Google">
                    <el-link type="primary" slot="reference" href="https://developers.google.com/identity/protocols/OpenIDConnect">
                      <i class="el-icon-view el-icon--right"></i>参考链接</el-link>
                  </el-popover>
                  <br />
                  <el-divider>
                    <el-tag type="primary" style="height: 35px; width: 130px; text-align: center; font-size: medium;">
                      <el-link type="primary" href="https://jfinal.com/"><div class="animate__animated animate__fadeInUp">JFinal</div></el-link>
                    </el-tag>
                  </el-divider>
                  <el-form :model="userInfo" label-width="100px">
                    <el-form-item label="ClientId">
                      <el-input v-model="userInfo.clientId" autocomplete="off" placeholder="请输入clientId" class="animate__animated animate__fadeInRight animate__slow"></el-input>
                    </el-form-item>
                    <el-form-item label="RedirectURI">
                      <el-input v-model="userInfo.redirectURI" autocomplete="off" placeholder="请输入redirectURI" class="animate__animated animate__fadeInRight animate__slow animate__delay-1s"></el-input>
                    </el-form-item>
                    <el-form-item label="ClientSecret">
                      <el-input type="password" v-model="userInfo.clientSecret" autocomplete="off" placeholder="请输入clientSecret" class="animate__animated animate__fadeInRight animate__slow animate__delay-2s"></el-input>
                    </el-form-item>
                  </el-form>
                  <div slot="footer" class="dilog-footer">
                    <el-button @click="" placement="left">忘记密码</el-button>
                    <el-button type="primary" @click="goOidcLogin">确认登录</el-button>
                  </div>
                </div>
              </el-dialog>
            </el-col>
            <el-col span="6">
              <el-button type="danger" class="optionway animate__animated animate__slideInRight animate__fast" style="margin-left: 20px" @click="statusGeneralLogin= true">账号密码登录</el-button>
              <el-dialog :append-to-body="true" class="logdialog" :visible.sync="statusGeneralLogin">
                <div slot="title">
                  <el-tooltip content="第三方登录,这里选择以Gitee第三方应用为例" placement="top">
                    <i class="el-icon-chat-dot-round" :style="darkorange"></i>
                  </el-tooltip>
                  JFinal-账号密码模式demo
                  <el-popover style="text-align:right;" title="注意事项" placement="top" width="100" trigger="hover" content="第三方登录需要拿到第三方用户信息">
                    <el-link type="primary" slot="reference" href="https://justauth.wiki/oauth/gitee.html#_1-%E7%94%B3%E8%AF%B7%E5%BA%94%E7%94%A8">
                      <i class="el-icon-view el-icon--right"></i>参考链接</el-link>
                  </el-popover>
                  <br />
                  <el-divider>
                    <el-tag type="primary" style="height: 35px; width: 130px; text-align: center; font-size: medium;">
                      <el-link type="primary" href="https://jfinal.com/"><div class="animate__animated animate__fadeInUp">JFinal</div></el-link>
                    </el-tag>
                  </el-divider>
                  <el-form status-icon ref="userInfo" :model="userInfo" label-width="100px">
                    <el-form-item label="userName" prop="userName" style="margin-top: 30px" >
                      <el-input v-model="userInfo.userName" autocomplete="off" placeholder="请输入账号" class="animate__animated animate__fadeInRight animate__slow"></el-input>
                    </el-form-item>
                    <el-form-item label="passWord" prop="passWord">
                      <el-input type="password" v-model="userInfo.passWord" autocomplete="off" placeholder="请输入密码" class="animate__animated animate__fadeInRight animate__slow animate__delay-1s"></el-input>
                    </el-form-item>
                  </el-form>

                  <div slot="footer" class="dilog-footer">
                    <el-button @click="" placement="left">忘记密码</el-button>
                    <el-button type="primary" @click="goGeneralLogin" :loading="logining">确认登录</el-button>
                  </div>
                </div>
              </el-dialog>
            </el-col>
          </el-cow>
        </div>
        <div class="plainske">
            <el-tooltip content="请选择登录方式" placement="top">
              <div v-if ="judge()"><el-skeleton :rows="12" animated/></div>
              <div v-else style="font-size: 20px">正在输入中.....</div>
            </el-tooltip>
        </div>
      </el-card>
    </div>
      <div v-if="alert">
          <el-alert
                  :title=this.message
                  type="error">
          </el-alert>
      </div>
      <div v-if="visibleVlaue">
          <successlogin visible="visibleVlaue"
                        :username= this.userInfo.userName2
                        :password= this.userInfo.passWord2
                        :userId= this.userInfo.uid
                        :token= this.userInfo.token
                        :time= this.time
                        @getClose="setClose">
          </successlogin>
      </div>
  </div>

</template>

<script>
    import successlogin from "@/components/successlogin";
    import {requestJfinal} from "@/api/requestJfinal";
    import {getTime} from "@/utils/dateutil";
export default {
  name: "IndexDetail",
  components: {
      successlogin
  },
  data: function(){
    return{
      alert:false,
      time:'',
      message:"请登录",
      visibleVlaue:false,
      index: '0',
      statusThirdLogin: false,
      statusOAuthLogin: false,
      statusOIDCLogin: false,
      statusGeneralLogin: false,
      status: false,
      logining:false,
      choose: '0',
      userInfo:{
        redirectURI:'',
        clientId: '',
        clientSecret: '',
        code: '',
        userName:'',
        userName2:'',
        passWord:'',
        passWord2:'',
        token:'',
        uid:''
      },
    }
  },
  methods:{
    goAuthorization(){
        if (this.userInfo.clientId == '' || this.userInfo.redirectURI == ''||this.userInfo.clientSecret == ''){
            this.alert=true;
            return null;
        }
        else{
            if (this.choose == 1){
                return requestJfinal({
                    url:'/oauth/code/getData',
                    method:"post",
                    data:this.$qs.stringify({
                        clientId:this.userInfo.clientId,
                        clientSecret:this.userInfo.clientSecret,
                        redirectURI:this.userInfo.redirectURI
                    })
                }).then(res => {
                    console.log("********重定向地址"+res.data.toAuth);
                    window.location = res.data.toAuth;
                    this.toShowUser(res.data.code,res.data.userInfos);
                })
            }
            else if (this.choose == 2){
                return requestJfinal({
                    url:'/oauth/implicit/getData',
                    method:"post",
                    data:this.$qs.stringify({
                        clientId:this.userInfo.clientId,
                        clientSecret:this.userInfo.clientSecret,
                        redirectURI:this.userInfo.redirectURI
                    })
                }).then(res => {
                    console.log("********重定向地址"+res.data.toAuth);
                    window.location = res.data.toAuth;
                    this.toShowUser(res.data.code,res.data.userInfos);
                })
            }
            else if (this.choose == 3){
                //请自行补充第三方应用的相关信息
                // this.userInfo.redirectURI = "";
                // this.userInfo.clientId = "";
                // this.userInfo.clientSecret = "";
                return requestJfinal({
                    url:'/oauth/password/getData',
                    method:"post",
                    data:this.$qs.stringify({
                        clientId:this.userInfo.clientId,
                        clientSecret:this.userInfo.clientSecret,
                        redirectURI:this.userInfo.redirectURI,
                        username:this.userInfo.userName,
                        password:this.userInfo.passWord,
                    })
                }).then(res => {
                    this.toShowUser(res.data.code,res.data.userInfos);
                })
            }
            else if (this.choose == 4){

            }
        }
    },
    goOidcLogin(){
        if (this.userInfo.clientId == '' || this.userInfo.redirectURI == ''||this.userInfo.clientSecret == ''){
            this.alert=true;
            return null;
        }
        else{
            return requestJfinal({
                url:'/oidc/getData',
                method:"post",
                data:this.$qs.stringify({
                    clientId:this.userInfo.clientId,
                    clientSecret:this.userInfo.clientSecret,
                    redirectURI:this.userInfo.redirectURI
                })
            }).then(res => {
                var toAuth = res.data.toAuth;
                console.log(toAuth);
                window.location = res.data.toAuth;
                console.log(res.data.userInfos);
                this.toShowUser(res.data.code,res.data.userInfos)
            })
        }
    },
    goThirdLogin(){
        if (this.userInfo.clientId == '' || this.userInfo.redirectURI == ''||this.userInfo.clientSecret == ''){
            this.alert=true;
            return null;
        }
        else {
            return requestJfinal({
                url: '/social/getData',
                method:"post",
                data:this.$qs.stringify({
                    clientId:this.userInfo.clientId,
                    clientSecret:this.userInfo.clientSecret,
                    redirectURI:this.userInfo.redirectURI
                })
            }).then(res=>{
                var toAuth = res.data.toAuth;
                console.log(toAuth);
                window.location = res.data.toAuth;
                console.log(res.data.userInfos);
                this.toShowUser(res.data.code,res.data.userInfos);
            })
        }
    },
    goGeneralLogin(){
    //  向后台发送待验证数据
      console.log("测试数据");
      console.log(this.userInfo.passWord,this.userInfo.userName);
      if (this.userInfo.passWord == '' || this.userInfo.userName == ''){
          this.alert=true;
          return null;
      }
      else {
      return requestJfinal({
          url:'/logined',
          method:"post",
          data: this.$qs.stringify({
              username:this.userInfo.userName,
              password:this.userInfo.passWord
          }),
      }).then(res =>{
          this.toShowUser(res.data.code,res.data.userInfos);
      }).catch(err=>{
          console.log(err);
      })
    }
    },
    toShowUser:function (code,userInfos){
        var date = new Date((new Date()).valueOf());
        this.time = getTime(date);
        console.log(userInfos.username+'\n');
        if(code == '200'){
            this.visibleVlaue=true;
            this.message="操作成功";
            console.log(this.message);
            this.userInfo.passWord2=userInfos.password;
            this.userInfo.userName2=userInfos.username;
            this.userInfo.token=userInfos.token;
            this.userInfo.uid = userInfos.userId;
        }
        console.log("\nusername "+this.userInfo.userName2
            +"\nuserpassword "+this.userInfo.passWord2
            +"\nusertoken "+this.userInfo.token
            +"\nuserId "+ this.userInfo.uid
            +"\n时间:"+this.time);
    },
    setClose:function (){
        this.visibleVlaue=false;
    },
    judge:function(){
      if(this.statusThirdLogin || this.statusOAuthLogin || this.statusOIDCLogin || this.statusGeneralLogin){
        return  false;
      }
      return true;
    },
    handleOrder:function (num){
        console.log("框架序号为————>"+num);
        this.$emit('choices',num);
    }
  }
}
</script>

<style scoped>
.location{
  margin-top: 60px;
  border-radius: 4px;
  min-height: 30px;
}
.collapse-item1{
  background-color: lightblue;
}
.cardContainer{
  margin-top: -675px;
  margin-left: 580px;
}
.mycard{
  width: 800px;
  height: 700px;
  --animate-duration: 2s;
  margin-left: -100px;
}
.fourCols{
  margin-bottom: 10px;
}
.mycard .optionway{
  border-radius: 10px;
  opacity: 0.76;
  animation-delay: 1s;
}
.plainske{
  margin-top: 100px;
}
.logdialog{
  width: 1000px;
  height: 700px;
  font-size: medium;
  text-align: center;
  position: absolute;
  left: 400px;
  top: 50px;
  opacity: 0.78;
  /*background-color: aliceblue;*/
  /*overflow: hidden;*/
}
.radios .el-radio__label {
  font-size: 15px;
  padding-left: 9px;
}
.radios .el-radio {
  color: #7589b1;
  margin-bottom: 10px;
}
.el-divider--horizontal {
  display: block;
  height: 1.5px;
  width: 100%;
  margin: 28px 0;
}
.el-divider {
  background-color: #3568de;
}
.right-ico.el-button--text {
  color: #409EFF;
  background: 0 0;
  padding-left: 0;
  padding-right: 0px;
  padding-top: 6px;
  margin-top: -6px;
}
.el-tag.el-tag--info {
    background-color: #409EFF;
    border-color: #e9e9eb;
    color: #a31811;
}
.el-icon-s-tools:before {
    content: "\e7ac";
    color: #409EFF;
}
</style>