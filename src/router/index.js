import Vue from 'vue'
import VueRouter from 'vue-router'
import IndexForJfinal from '../views/IndexForJfinal'

Vue.use(VueRouter)

const routes = [
  {
    path: '/index',
    name: 'IndexForJfinal',
    redirect: IndexForJfinal
  },
  {
    path: '/',
    name: 'IndexForJfinal',
    component: IndexForJfinal
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
