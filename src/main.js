import Vue from 'vue'
import App from './App.vue'
import router from './router'
import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'
import animated from 'animate.css'
Vue.config.productionTip = false

//在其他地方就可以通过使用 this.$axios发起axios请求
Vue.prototype.$axios=axios
Vue.use(animated)
Vue.use(Element)

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')

