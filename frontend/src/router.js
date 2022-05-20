import { createRouter, createWebHistory } from 'vue-router'
import LoginMask from './views/login.vue'
import account from "@/views/account";
import office from "@/views/office/office";
import officeHome from "@/views/office/officeHome";
import vehicles from "@/views/office/newVehicle";
import employees from "@/views/office/employees";
import newEmployee from "@/components/office/employees/newEmployee";
import HomePage from "@/views/home.vue";
import newReservation from "@/components/employee/newReservation";
import NotFound from "@/views/NotFound.vue";

const routes = [
        {
            path: '/login',
            name: 'Login',
            component: LoginMask
        },
        {
            path: '/account',
            name: 'Account',
            component: account,
            meta: {requiresAuth: true}
        },
        {
            path: '/office',
            redirect: '/office/home',
            name: 'Office',
            component: office,
            meta: {requiresAuth: true},
            children: [
                {
                    path: 'home',
                    name: 'OfficeHome',
                    component: officeHome
                },
                {
                    path: 'newVehicle',
                    name: 'newVehicle',
                    component: vehicles
                },
                {
                    path: 'employees',
                    name: 'allEmployees',
                    component: employees
                },
                {
                    path: 'newEemployee',
                    name: 'newEmployee',
                    component: newEmployee
                }
            ]
        },
        {
            path: '/home',
            name: 'Home',
            component: HomePage,
            meta: {requiresAuth: true}
        },
        {
            path: '/newReservation',
            name: 'newReservation',
            component: newReservation,
            meta: {requiresAuth: true}
        },
        // redirect
        {
            path: '/',
            redirect: '/home'
        },
        // catchAll 404
        {
            path: '/:catchAll(.*)',
            name: 'NotFound',
            component: NotFound
        },
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        const currentTime = Date.now()
        const expirationTime = new Date(localStorage.expires)
        if (!localStorage.token || (expirationTime < currentTime)) {
            next({ name: 'Login' })
        } else {
            next()
        }
    } else {
        next()
    }
})

export default router