---
### 说明
项目名称：开发JFinal、Blade、ActFramework集成JustAuthPlus的demo。 <br>
分支说明：目前main存放的是最终后端项目，VuePage分支存放的是最终前端Vue项目。<br>
---

# 一.项目信息

##### 1.项目名称

​         开发JFinal、Blade、ActFramework集成JustAuthPlus的Demo。

##### 2.方案描述

​	采用前后端分离实现开发，前端项目基于Vue进行构建项目，后端分别使用JFinal、Blade和ActFramework框架构建项目，开发设计实现集成JustAuthPlus的**第三方登录、OAuth登录、OIDC登录和账号密码登录**的测试项目。

##### 3.专注领域

​          Dev Tools，登录认证，OAuth，Social，OIDC。

##### 4.技术标签

​          Java，Git，JFinal，Blade，ActFramework，第三方登录，OAuth2，JavaScript，animate，Vue，Axios，	ElementUI等。

##### 5.测试方向

​           支持第三方登录、OAuth登录、OIDC登录、账号密码登录

##### 6.项目主导师及开发者

​            导师：张亚东（zhyd@fujieid.com）

​            开发者：吴豪琪（whhwuemail@gmail.com）

##### 7.提示

​			若测试遇问题，请参考**四.帮助**。



# 二.测试准备

##### 1. 第三方登录、OAuth登录、账号密码方式登录方式

<div style=color:Tomato>注：集成第三方，此次测试选择Gitee。其中OAuth登录，由于作者能力有限，只实现了其中的授权码模式和账号密码模式，另外的<a href="https://justauth.wiki/oauth/gitee.html#_1-%E7%94%B3%E8%AF%B7%E5%BA%94%E7%94%A8">参考链接</a></div>



**1.1** 在已经登录Gitee的情况下访问：https://gitee.com/oauth/applications

​	**1.1.1**截图<img src="https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210924173008079.png" alt="image-20210924173008079" style="zoom: 800%;" />

​	**1.2** 选择右上角，创建应用。按照Gitee要求填写**1.2.1**截图信息即可。

- **应用名称：**一般填写自己的网站首页，这里填写的是该项目测试首页。

- **应用描述：**一般填写自己的应用描述即可。

- <div style= color:red><strong style= color:black>应用回调地址：</strong>由于是三个框架，这里为每个框架中的其中三个测试目标添加三个回调地址，填写信息如1.2.1截图信息。（建议应用回调地址与1.2.1截图信息保持一致，否则你需要自己去修改后端代码接口）</div>

- **权限：**默认即可。

- **上传Logo：**JPG或者PNG格式，文件大小不超过2M。上传Logo不能为空

​       **1.2.1**截图信息

![image-20210925103631645](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210925103631645.png)

<div style="color:blue;font-size:18px"><strong>注：</strong></div>

- **social/auth** 接口表示第三方登录
- **oauth/code** 接口表示OAuth2 登录的授权码模式
- **oauth/password** 接口表示OAuth2 登录的账号密码模式
- **oidc/auth** 接口表示OIDC登录
- 其中OAuth2的账号密码模式，是输入你自己Gitee的账号和密码
- http://127.0.0.1:8091 表示后端JFInal项目运行地址
- http://127.0.0.1:8092 表示后端Blade项目运行地址
- http://127.0.0.1:8093 表示后端ActFramework项目运行地址
- 后三项是跨域的目标basic地址。



##### 2. OIDC登录方式

​	由于Gitee没有Open ID Connect 登录方式，因此，这里稍微麻烦的选择了 [阿里云OIDC登录](https://ram.console.aliyun.com/overview)。

登录阿里云。

**主账号信息：**

![image-20210927145435237](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210927145435237.png)

**RAM用户信息**

![image-20210927145522321](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210927145522321.png)

**进入[访问控制](https://ram.console.aliyun.com/applications)，选择OAuth应用管理，选择“创建应用”。填写以下信息**

![image-20210925112101825](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210925112101825.png)

<div style= color:red><strong>点击保存后，会弹出应用秘钥，请复制保存，后面不再显示该秘钥。注：非AppSecretId</strong></div>

添加OAuth范围：openid，aliuid，profile

![image-20210925230223798](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210925230223798.png)



##### 3. 测试说明

到此，前期准备完成。

# 三.正式测试

### 1.测试界面简述

#####  1.1 项目启动，初始页面

<img src="https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210924202325191.png" alt="image-20210924202325191" />

<div style=color:Tomato>注：框架测试顺序按照项目开发顺序进行</div>

<img src="https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210924202440444.png" alt="image-20210924202440444" />

##### 1.2 选择测试框架，默认是JFinal框架（按照开发顺序）

<img src="https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210924202748106.png" alt="image-20210924202748106" />

##### 1.3 开始测试

​       在后端运行选择的框架项目，前端运行并选择不同的登录方式进行测试即可。其余页面信息可自行运行项目查看。

### 2.测试方式简述

### 3.JFinal框架集成JustAuthPlus的demo

<div style= color:red><strong>运行后端项目JFInal-JAP项目（运行该项目的启动函数即可）。<br>前端项目请一直保持运行状态，进行某一个测试模块测试完成后，务必刷新页面（Chrome浏览器快捷键Ctrl+R）。</strong></div>

#### 3.1 JFinal-第三方登录

##### 3.1.1 **引入依赖**

```XML
<dependency>
            <groupId>com.fujieid</groupId>
            <artifactId>jap-social</artifactId>
            <version>1.0.3</version>
</dependency>
```

##### 3.1.2 测试

- 启动前后端项目，浏览器访问：http://127.0.0.1:8899/。

- 选择 **JFInal**（页面右上角设置按钮或看 **四.帮助 1**），点击第三方登录

- 输入你创建应用的clientId、clientSecret以及对应回调地址(social/oauth接口)。如图

  ![image-20210925172900444](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210925172900444.png)

  点击确认登录，若是第一次登录，则会跳转Gitee授权界面，点击授权后，可页面和后端控制台均会显示用户相关信息。

  **测试结果：**

  页面信息截图：![image-20210925173116441](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210925173116441.png)

  后台信息截图：

  ![image-20210925173211355](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210925173211355.png)

  获取的信息，是通过后端操作gitee的用户接口拿到，属于你自己的Gitee账号信息。

#### 3.2 JFinal-OAuth登录

##### 3.2.1 **引入依赖**

```XML
<dependency>
            <groupId>com.fujieid</groupId>
            <artifactId>jap-oauth2</artifactId>
            <version>1.0.2</version>
</dependency>
```

点击**OAuth登录**

##### 		3.2.2 **选择authorization code模式**

​	![image-20210926141353633](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210926141353633.png)

- 输入你创建应用的clientId、clientSecret以及对应回调地址(oauth/code接口)。

- 确认登录

  ![image-20210926141503379](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210926141503379.png)

  **测试结果：**

![image-20210925213250466](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210925213250466.png)

![image-20210925213657926](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210925213657926.png)

##### 3.2.3 **选择password credentials模式**

![image-20210926141615262](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210926141615262.png)

**准备：** ![image-20210925221902970](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210925221902970.png)

在前端项目中添加测试对应组件：如306 307 308行号所示。另外两个（Blade、ActFramework测试相同）

- 输入Gitee的登录账号和密码
- 确认登录

**测试结果：**

![image-20210925222433981](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210925222433981.png)

![image-20210925222319254](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210925222319254.png)



#### 3.3 JFinal-账号密码登录

##### 3.3.1 **引入依赖**

```XML
<dependency>
            <groupId>com.fujieid</groupId>
            <artifactId>jap-simple</artifactId>
            <version>1.0.1-alpha.1</version>
</dependency>
```

##### 3.3.2 测试

**该模式的账号密码选择后端项目静态数据库中所存储的账号密码：** 输入账号：jap1   密码： jap1

​	![image-20210925222831796](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210925222831796.png)

**测试结果：**

![image-20210925222906347](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210925222906347.png)

![image-20210925223017888](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210925223017888.png)

#### 3.4 JFinal-OIDC登录

##### 3.4.1 **引入依赖**

```XML
<dependency>
            <groupId>com.fujieid</groupId>
            <artifactId>jap-oidc</artifactId>
            <version>1.0.1</version>
</dependency>
```

##### 3.4.2 测试

- 输入在 **二.测试准备**时，阿里云访问控制的自己创建的第三方应用的AppId和已经保存的应用秘钥

  示例截图：

  ![image-20210925224136312](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210925224136312.png)

- 点击确认登录，若没有登录阿里云，会跳转登录页面进行确认授权。

  <div style=color:Tomato>注：第一次授权登录，请使用主账号登录。需要使用RAM用户登录，请先创建RAM角色并提供对应权限。</div>

  **测试结果：**

  <u>主账号：</u>

  ![image-20210925230448236](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210925230448236.png)

  ​	![image-20210925230418008](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210925230418008.png)

​      <u>RAM账号登录：</u>

- ![image-20210926142615916](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210926142615916.png)
- ![image-20210926142520228](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210926142520228.png)

### 4.Blade框架集成JustAuthPlus的demo

<div style= color:red><strong>运行后端项目JFInal-JAP项目（运行该项目的启动函数即可）。<br>前端项目请一直保持运行状态，进行某一个测试模块测试完成后，务必刷新页面（Chrome浏览器快捷键Ctrl+R）。</strong></div>

#### 4.1 Balde-第三方登录

##### 4.1.1 **引入依赖**

```XML
<dependency>
            <groupId>com.fujieid</groupId>
            <artifactId>jap-social</artifactId>
            <version>1.0.3</version>
</dependency>
```

##### 4.1.2 测试

- 启动前后端项目，浏览器访问：http://127.0.0.1:8899/。

- 选择 **Blade**（页面右上角设置按钮或看 **四.帮助 1**），点击第三方登录

  <img src="https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210926145152493.png" alt="image-20210926145152493" style="zoom:50%;" />

- 输入你创建应用的clientId、clientSecret以及对应回调地址(social/oauth接口)。注意此时回调地址的IP地址不同。如图

  ![image-20210926145126536](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210926145126536.png)

  **测试结果**

  ![image-20210926150129325](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210926150129325.png)

  ![image-20210926145736543](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210926145736543.png)

#### 4.2 Balde-OAuth登录

##### 4.2.1 引入依赖

```XML
<dependency>
            <groupId>com.fujieid</groupId>
            <artifactId>jap-social</artifactId>
            <version>1.0.3</version>
</dependency>
```

点击**OAuth登录**

##### 4.2.2 选择authorization code模式

- ​	输入你创建应用的clientId、clientSecret以及对应回调地址(oauth/code接口)。![image-20210926151348248](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210926151348248.png)

- 确认登录

**测试结果**

![image-20210926151530176](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210926151530176.png)

![image-20210926151601671](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210926151601671.png)

##### 4.2.3 选择password credentials模式

**准备：** 见**3.2.2 选择password credentials模式**

​	输入你自己的**Gitee账号和密码**

![image-20210926151959724](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210926151959724.png)

- 确认登录

-

![image-20210926153252294](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210926153252294.png)

#### 4.3 Balde-账号密码登录

##### 4.3.1 引入依赖

```XML
<dependency>
            <groupId>com.fujieid</groupId>
            <artifactId>jap-simple</artifactId>
            <version>1.0.1-alpha.1</version>
</dependency>
```

**该模式的账号密码选择后端项目静态数据库中所存储的账号密码：** 输入账号：jap2   密码： jap2

##### 4.3.2 测试

![image-20210927002313616](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210927002313616.png)

**测试结果**

![image-20210927002347437](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210927002347437.png)

![image-20210927002405685](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210927002405685.png)



#### 4.4 Balde-OIDC登录

##### **4.4.1 引入依赖**

```XML
<dependency>
            <groupId>com.fujieid</groupId>
            <artifactId>jap-oidc</artifactId>
            <version>1.0.1</version>
</dependency>
```

##### 4.4.2 测试

- 输入在 **二.测试准备**时，阿里云访问控制的自己创建的第三方应用的AppId和已经保存的应用秘钥

  截图如下：![image-20210927002846392](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210927002846392.png)

  **测试结果**

  **主账号**

  ![image-20210925230418008](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210925230418008.png)

### 5.ActFramework框架集成JustAuthPlus的demo

#### 5.1 ActFramework-第三方登录

#####  5.1.1 引入依赖

```XML
<dependency>            <groupId>com.fujieid</groupId>            <artifactId>jap-social</artifactId>            <version>1.0.3</version></dependency>
```

##### 5.1.2 测试

- 启动前后端项目，浏览器访问：http://127.0.0.1:8899/。

- 选择 **ActFramework**（页面右上角设置按钮或看 **四.帮助 1**），点击第三方登录

- 输入你创建应用的clientId、clientSecret以及对应回调地址(social/oauth接口)。如图

  ![image-20210927092941176](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210927092941176.png)

  **测试结果**

  ![image-20210927092843609](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210927092843609.png)

  ![image-20210927093117065](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210927093117065.png)



#### 5.2 ActFramework-OAuth登录

##### 5.2.1 引入依赖

```XML
<dependency>            <groupId>com.fujieid</groupId>            <artifactId>jap-social</artifactId>            <version>1.0.3</version></dependency>
```

##### 5.2.2 选择authorization code模式

- 输入你创建应用的clientId、clientSecret以及对应回调地址(oauth/code接口)。

  ![image-20210927093718622](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210927093718622.png)

- 确认登录

  **测试结果**

  ![image-20210927093735859](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210927093735859.png)

  ![image-20210927093751127](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210927093751127.png)

##### 5.2.3 选择password credentials模式

- 输入你自己的**Gitee账号和密码**

  **准备：** 见**3.2.2 选择password credentials模式**

  ![image-20210927095611460](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210927095611460.png)

  **测试结果**

  ![image-20210927095545420](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210927095545420.png)

  ![image-20210927095833417](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210927095833417.png)

​

#### 5.3 ActFramework-账号密码登录

##### 5.3.1 引入依赖

```XML
<dependency>            <groupId>com.fujieid</groupId>            <artifactId>jap-simple</artifactId>            <version>1.0.1-alpha.1</version></dependency>
```

##### 5.3.2 测试

**该模式的账号密码选择后端项目静态数据库中所存储的账号密码：** 输入账号：jap3   密码： jap3

![image-20210927100023298](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210927100023298.png)

**测试结果**

- ![image-20210927100058914](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210927100058914.png)

  ![image-20210927100120321](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210927100120321.png)

#### 5.4 ActFramework-OIDC登录

##### 5.4.1 引入依赖

```XML
<dependency>            <groupId>com.fujieid</groupId>            <artifactId>jap-oidc</artifactId>            <version>1.0.1</version></dependency>
```

##### 5.4.2 测试

- 输入在 **二.测试准备**时，阿里云访问控制的自己创建的第三方应用的AppId和已经保存的应用秘钥

  示例截图：

  ![image-20210927100311007](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210927100311007.png)

  <u>主账号登录：</u>

  ![image-20210927142341416](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210927142341416.png)

  ![image-20210927142435078](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210927142435078.png)

  <u>RAM账号登录：</u>

  ![image-20210927141907001](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210927141907001.png)

  ![image-20210927141948928](https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210927141948928.png)

# 四.帮助

##### 1.前端

当需要测试除开JFinal框架外的项目，而又觉得每次测试需要频繁更换选择目标项目，可在前端项目的**IndexMain.vue**中，手动更改参数isShow的值（默认值为1）。如：<img src="https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210926145345984.png" alt="image-20210926145345984" style="zoom:50%;" />

###### 1.1 前端页面其他功能描述

<img src="https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210927152113041.png" alt="image-20210927152113041" style="zoom:50%;" />

- 页面首页左上角，可直接前往JustAuthPlus 社区官网，可学习并了解JAP、JustAuth详细信息。
- 另外三个框架，此次添加了简要说明和框架功能简要概述。若想了解具体信息，可点击框架**简介**，前往对应官网或者仓库使用文档学习更多内容。

<img src="https://pic-1305721853.cos.ap-chengdu.myqcloud.com/mynotes/image-20210927152544894.png" alt="image-20210927152544894" style="zoom:50%;" />

- 各个登录框或者对话框，均特别添加了该框架下功能的简要介绍和其他具有参考性的链接。

- 所有页面元素，均实现了动画动作。

##### 2.后端

###### 2.1 注释部分

​		后端项目各个测试实现代码，各个测试模块和功能，**均已添加注释**，可自行调试程序和参考。

###### 2.2 项目运行

​		由于此次项目分为一个前端（http://127.0.0.1:8899/），三个后端项目（http://127.0.0.1:8091/，http://127.0.0.1:8092/，http://127.0.0.1:8093/）。因此，项目运行，请务必保证，这四个端口出于未被占有的状态，且前端项目一直出于运行状态。

​		当某个功能完成测试后，继续下一个功能进行测试，若遇显示数据为null或者其他异常问题，**请重新启动该项目**。建议每完成一个功能测试后，重启项目运行。

##### 3.其他

由于作者能力有限，若存在不严谨的问题，望谅解。



​	



