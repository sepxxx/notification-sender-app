import Vue from 'vue'
import Router from 'vue-router'
import MainPage from './views/MainPage.vue'
// import MailingsPage from './views/MailingsPage.vue'
// import MailingsCreationPage from './views/MailingsCreationPage.vue'
// import ListsPage from './views/ListsPage.vue'
// import OffersPage from "@/views/OffersPage.vue";

// export default new Router({
//     mode: 'history',
//     routes: [
//         {
//             path: '/',
//             component: MainPage
//         },
//         {
//             path: "/mailings",
//             component: MailingsPage
//
//         },
//         {
//             path: "/lists",
//             component: ListsPage
//         },
//         {
//             path: "/mailings/creation",
//             component: MailingsCreationPage
//
//         },
//         {
//             path: "/templates/offers",
//             component: OffersPage
//
//         }
//     ]
// })
//
Vue.use(Router)

const router = new Router({
    mode: 'history',
    base: '/',
    routes: [
        {
            path: '/',
            name: 'MainPage',
            component: MainPage
        },
        {
            path: '/mailings',
            name: 'MailingsPage',
            component: () => import(/* webpackChunkName: "secret" */ './views/MailingsPage.vue'),
            // meta: {
            //     requiresAuth: true
            // }
        }
        ,
        {
            path: '/lists',
            name: 'ListsPage',
            component: () => import(/* webpackChunkName: "secret" */ './views/ListsPage.vue'),
            // meta: {
            //     requiresAuth: true
            // }
        }
        ,
        {
            path: '/mailings/creation',
            name: 'MailingsCreationPage',
            component: () => import(/* webpackChunkName: "secret" */ './views/MailingsCreationPage.vue'),
            // meta: {
            //     requiresAuth: true
            // }
        }
        ,
        {
            path: '/templates/offers',
            name: 'OffersPage',
            component: () => import(/* webpackChunkName: "secret" */ './views/OffersPage.vue'),
            // meta: {
            //     requiresAuth: true
            // }
        }
    ]
})

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms))
}

router.beforeEach(async (to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        // We wait for Keycloak init, then we can call all methods safely
        while (router.app.$keycloak.createLoginUrl === null) {
            await sleep(100)
        }
        if (router.app.$keycloak.authenticated) {
            next()
        } else {
            const loginUrl = router.app.$keycloak.createLoginUrl()
            window.location.replace(loginUrl)
        }
    } else {
        next()
    }
})

export default router