<template>
  <div class="myAside">
    <div style="height: 700px " class="mySteps">
      <el-steps direction="vertical" :active ="6" class="steps  animate__animated animate__slideInDown">
        <el-step title="JFinal">
          <template slot="description">
            <div class="desForJfinal">
              <el-collapse class="collapseCommon">
                <el-collapse-item title="Jfinal简介">
                  <div><el-link href="https://jfinal.com/"><div style="color:blue; font-size: 15px;font-style:italic">JFinal</div>
                  </el-link>是基于Java 语言的极速 WEB + ORM + AOP + Template Engine 框架，其核心设计目标是开发迅速、代码量少、学习简单、功能强大、轻量级、易扩展、Restful。</div>
                </el-collapse-item>
                <el-collapse-item title="Jfinal主要特点">
                  <div>1. MVC架构、遵循COC原则，支持零配置，无xml、独创Db + Record模式，灵活便利、AOP支持，拦截器配置灵活</div>
                  <div>2. ActiveRecord支持，使数据库开发极致快速、自动加载修改后的java文件，开发过程中无需重启web server等</div>
                </el-collapse-item>
                <el-collapse-item title="JFinal特别申明">
                  <div>
                    <el-link href="https://jfinal.com/doc/1-11">
                      <div style="color:blue; font-size: 15px;font-style:italic">申明</div>
                    </el-link>
                      指出JFinal 项目是符合 Java Web 规范的普通项目，所以开发者原有的项目 启动 和 部署 知识全部有效，不需要特殊对待 JFinal 项目。
                  </div>
                </el-collapse-item>
              </el-collapse>
            </div>
          </template>
        </el-step>
        <el-step title="Blade">
          <template slot="description">
            <div class="desForJfinal">
              <el-collapse class="collapseCommon">
                <el-collapse-item title="Blade简介">
                  <div><el-link href="https://gitee.com/biezhi/blade"><div style="color:blue; font-size: 15px;font-style:italic">Blade</div></el-link>
                    是一款追求简约、高效的 Web 框架，让 JavaWeb 开发如虎添翼，在性能与灵活性上同时兼顾，
                    框架设计简单,容易理解,不依赖于更多第三方库。Blade框架目标让用户在一天内理解并使用，
                    Blade 支持 REST 风格路由接口, 提供 DSL 语法编写，无侵入式的拦截器，
                    支持 maven 打成 jar 包直接运行。
                  </div>
                </el-collapse-item>
                <el-collapse-item title="Blade主要特点">
                  <div>1. 新一代MVC框架，不依赖更多的库，摆脱SSH的臃肿，模块化设计，源码不到 500kb，学习也简单,Restful风格路由设计</div>
                  <div>2. ActiveRecord支持，使数据库开发极致快速、自动加载修改后的java文件，开发过程中无需重启web server等</div>
                </el-collapse-item>
                <el-collapse-item title="Blade快速入门">
                  <div>
                    <el-link href="https://gitee.com/biezhi/blade#%E5%BF%AB%E9%80%9F%E5%85%A5%E9%97%A8">
                      <div style="color:blue; font-size: 12px;font-style:italic">具体参考</div>
                    </el-link>
                    导入Maven依赖：blade-mvc,在Maven项目基础上，进行系列开发操作。
                  </div>
                </el-collapse-item>
              </el-collapse>
            </div>
          </template>
        </el-step>
        <el-step title="ActFramework">
          <template slot="description">
            <div class="desForJfinal">
              <el-collapse class="collapseCommon">
                <el-collapse-item title="ActFramework简介">
                  <div><el-link href="https://github.com/actframework/actframework"><div style="color:blue; font-size: 15px;font-style:italic">ActFramework</div></el-link>
                    ActFramework 是一款高性能 Java 全栈框架，用于开发传统的 MVC 应用或 RESTful 服务。和其他现有 MVC/RESTful 框架相比，ActFramework 的优势在于表达力和简洁易用。
                  </div>
                </el-collapse-item>
                <el-collapse-item title="ActFramework主要特点">
                  <div>1. A full stack MVC framework</div>
                  <div>2. Unbeatable development experience w/ great performance</div>
                  <div>3. Fully JSR330 Dependency Injection support</div>
                  <div>4. Superb SPA/Mobile app support an so on</div>
                </el-collapse-item>
                <el-collapse-item title="ActFramework快速入门">
                  <div>
                    <el-link href="https://github.com/actframework/actframework#install">
                      <div style="color:blue; font-size: 12px;font-style:italic">具体参考</div>
                    </el-link>
                    first: Add act-starter-parent into into your pom.xml file...
                  </div>
                </el-collapse-item>
              </el-collapse>
            </div>
          </template>
        </el-step>
        <el-step title=""></el-step>
        <el-step title=""></el-step>
        <el-step title=""></el-step>
      </el-steps>
    </div>
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: "Aside",

}
</script>

<style scoped>
.steps{
  margin-top: 5px;
  margin-left: 7px;
  width: 300px;
}
.mySteps /deep/ .el-step__title,step{
  color: red;
  font-size: 25px;
  animation: animate__backInRight;
  animation-delay: 5s;
}
.partTitle{
  color: blue;
  font-size: 25px;
  animation: animate__backInRight;
  animation-delay: 5s;
}
.mySteps /deep/ .el-step__line{
  border-top: 3px dotted;
}
.mySteps /deep/ .el-step__line-inner{
  border-width: 0 !important;
}
.mySteps /deep/ .el-step__description{
  color: #B3DBFF;

}
.mySteps /deep/ .el-step__head.is-finish {
    color: #fff;
    border-color: #66b1ff;
  }
.mySteps /deep/ .el-step__icon{
  background: #66b1ff;
}
.collapseCommon{
  width:320px ;
  animation: rotateInDownRight;
}
</style>