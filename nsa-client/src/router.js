import Vue from 'vue'
import Router from 'vue-router'
import MainPage from './views/MainPage.vue'
import MailingsPage from './views/MailingsPage.vue'
import MailingsCreationPage from './views/MailingsCreationPage.vue'
import ListsPage from './views/ListsPage.vue'
import OffersPage from "@/views/OffersPage.vue";
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

        },
        {
            path: "/lists",
            component: ListsPage
        },
        {
            path: "/mailings/creation",
            component: MailingsCreationPage

        },
        {
            path: "/templates/offers",
            component: OffersPage

        }
    ]
})