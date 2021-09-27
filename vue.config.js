module.exports = {
        devServer: { // 自定义服务配置
            // open: true, // 自动打开浏览器
            host:'127.0.0.1',
            port:8899,
            hotOnly:false, //启用热模块替换，而无需页面刷新作为构建失败时的回退。
            proxy:{
                "/Jfinal":{
                    target: 'http://127.0.0.1:8091',
                    changeOrigin: true,
                    ws:true,//如果要代理 websockets，配置这个参数
                    pathRewrite: {
                        '^/Jfinal':''
                    }
                },
                "/Blade":{
                    target:'http://127.0.0.1:8092',
                    changeOrigin:true,
                    pathRewrite:{
                        '^/Blade':''
                    }
                },
                "/ActFramework":{
                    target:'http://127.0.0.1:8093',
                    changeOrigin:true,
                    pathRewrite:{
                        '^/ActFramework':''
                    }
                }
            }
        },
}