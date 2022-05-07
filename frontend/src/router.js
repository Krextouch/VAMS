/*import Vue from "vue";
import Router from "vue-router";

Vue.use()

export default new Router({
    mode: 'history',
    routes: [
        //{path: '/', component: Home},
        {path: '/login', component: LoginMask}
    ]
})
*/
import { createRouter, createWebHistory } from 'vue-router'
import LoginMask from './views/login.vue'

const routes = [
        {path: '/login', name: 'Login', component: LoginMask}
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router