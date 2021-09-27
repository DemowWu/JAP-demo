<template>
    <div class="head" v-if="visible">
        <el-dialog
                title="登录成功"
                :visible.sync="visible"
                width="50%"
                :before-close="handleClose">
        <span>
            <div class="dialogIn">
                <el-descriptions class="margin-top" title="你的信息如下" :column="1" :size="size" border>
                        <el-descriptions-item>
                          <template slot="label">
                            <i class="el-icon-user"></i>
                            用户名
                          </template>
                          {{ username }}
                        </el-descriptions-item>
                        <el-descriptions-item>
                          <template slot="label">
                            <i class="el-icon-key"></i>
                            密码
                          </template>
                           {{ password }}
                        </el-descriptions-item>
                        <el-descriptions-item>
                          <template slot="label">
                            <i class="el-icon-user-solid"></i>
                            id
                          </template>
                          {{ userId }}
                        </el-descriptions-item>
                        <el-descriptions-item>
                          <template slot="label">
                            <i class="el-icon-place"></i>
                            token
                          </template>
                         {{ token }}
                        </el-descriptions-item>
                </el-descriptions>
            </div>
        </span>
            <span slot="footer" class="dialog-footer">
              <div style="font-size: 12px">{{time}}
                    <el-button type="primary" @click="closeD">确 定</el-button>
                  </div>
        </span>
        </el-dialog>
    </div>
</template>
<script>
    export default {
        name: "successlogin",
        data() {
            return {
                size:'120px',
                dialogVisible: false
            };
        },
        props:{
            visible:{
                default:false
            },
            username:{},
            password:{},
            token:{},
            userId:{},
            time:{},
        },
        methods: {
            closeD(){
              this.$emit('getClose',false);
            },
            handleClose(done) {
                this.$confirm('确认关闭？')
                    .then(res => {
                        done();
                        this.$emit('getClose',false);
                        console.log(res);
                    })
                    .catch(res => {
                        console.log(res);
                    });
            }
        }
    }
</script>

<style scoped>
    /deep/ .el-descriptions-item__label.is-bordered-label {
    width: 120px;
    text-align: center;
}
</style>