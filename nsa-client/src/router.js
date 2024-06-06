import Vue from 'vue'
import Router from 'vue-router'
import MainPage from './views/MainPage.vue'
import MailingsPage from './views/MailingsPage.vue'
Vue.use(Router)

export default new Router({
    mode: 'history',
    routes: [
        {
            path: '/',
            component: MainPage
        },
        {
            path: "/mailings",
            component: MailingsPage

        }
    ]
})