import { createRouter, createWebHistory } from 'vue-router'
import LoginMask from './views/login.vue'
import HomePage from "@/views/home.vue";
import NotFound from "@/views/NotFound.vue";
import newReservation from "@/views/newReservation";

const routes = [
        {path: '/login', name: 'Login', component: LoginMask},
        {path: '/home', name: 'Home', component: HomePage},
        {path: '/newReservation', name: 'newReservation', component: newReservation},
        //catchAll 404
        {path: '/:catchAll(.*)', name: 'NotFound', component: NotFound}
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router