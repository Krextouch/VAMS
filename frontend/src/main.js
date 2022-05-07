import { createApp } from 'vue'
// import Vue from 'vue'
import App from './App.vue'
import Router from "./router"

createApp(App).use(Router).mount('#app')

/*Vue.config.productionTip = false

new Vue({
    router,
    render: h => h(App),
}).$mount('#app')*/