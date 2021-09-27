import Vue from 'vue'
import VueRouter from 'vue-router'
import IndexMain from "@/views/IndexMain";

Vue.use(VueRouter)

const routes = [
  {
    path: '/index',
    name: 'IndexMain',
    redirect: IndexMain
  },
  {
    path: '/',
    name: 'IndexMain',
    component: IndexMain
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
