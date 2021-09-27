<template>
  <div class="layout" style="background-color:aliceblue">
    <el-backtop target=".layout" :visibility-height="200">
      <div style="
           {
            height: 100%;
            width: 100%;
            background-color: #f2f5f6;
            box-shadow: 0 0 6px rgba(0, 0, 0, 0.12);
            text-align: center;
            line-height: 40px;
            color: #1989fa;
          }
        " >
        UP
      </div>
    </el-backtop>
    <headers></headers>
    <Aside></Aside>
    <template v-if="this.isShow === '1'"><IndexDetailJfinal @choices = "setOrder"></IndexDetailJfinal></template>
    <template v-else-if="this.isShow === '2'"><IndexDetailBlade @choices = "setOrder"></IndexDetailBlade></template>
    <template v-else-if="this.isShow === '3'"><IndexDetailActframework @choices = "setOrder"></IndexDetailActframework></template>
  </div>
</template>

<script>
import Headers from "@/components/Headers";
import Aside from "@/components/Aside";
import IndexDetailJfinal from "@/views/IndexDetailJfinal";
import IndexDetailBlade from "@/views/IndexDetailBlade";
import IndexDetailActframework from "@/views/IndexDetailActframework";
export default {
  name: "IndexMain",
  components: {Headers,Aside,IndexDetailJfinal,IndexDetailBlade,IndexDetailActframework},
    data: function(){
        return{
            isShow: '1',
        }
  },
  methods:{
    goHome (){
      toIndex().then(function (res) {
        console.log(res)
      });
    },
    setOrder(num){
        console.log(num);
        this.isShow = num;
    }
  }
}
</script>

<style scoped>
.layout{
  height: 680px;
  overflow-x: hidden;
}
</style>