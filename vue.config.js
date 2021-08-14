/* 覆盖webpack的配置 */
module.exports = {
        devServer: { // 自定义服务配置
            open: true, // 自动打开浏览器
            port: 8091,
            host:"127.0.0.1",
            hotOnly:false, //启用热模块替换，而无需页面刷新作为构建失败时的回退。
        },
}
module.exports = {
    publicPath: '/static/', // 这个指向的地址就是打包后,html文件引用js文件的路径地址.
}