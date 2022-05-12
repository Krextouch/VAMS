import { createRouter, createWebHistory } from 'vue-router'
import LoginMask from './views/login.vue'
import HomePage from "@/views/home.vue";
import NotFound from "@/views/NotFound.vue";
import newReservation from "@/views/newReservation";

const routes = [
        {path: '/login', name: 'Login', component: LoginMask},
        {path: '/home', name: 'Home', component: HomePage, meta: {requiresAuth: true}},
        {path: '/newReservation', name: 'newReservation', component: newReservation, meta: {requiresAuth: true}},
        // catchAll 404
        {path: '/:catchAll(.*)', name: 'NotFound', component: NotFound},
        // redirect
        {path: '/', redirect: '/home'},
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        const currentTime = Date.now()
        const expirationDate = new Date(localStorage.expires)
        if (!localStorage.token || (expirationDate < currentTime)) {
            next({ name: 'Login' })
        } else {
            next()
        }
    } else {
        next()
    }
})

export default router