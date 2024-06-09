import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios';
import VueKeycloakJs from '@dsb-norge/vue-keycloak-js'
Vue.config.productionTip = false
Vue.use(ElementUI);
import RecipientsService from './services/recipients-service';
Vue.prototype.$recipientsService = RecipientsService


function tokenInterceptor () {
  axios.interceptors.request.use(config => {
    if (Vue.prototype.$keycloak.authenticated) {
      // const session = localStorage.getItem('session');
      // console.log(session)
      config.headers.Authorization = `Bearer ${Vue.prototype.$keycloak.token}`
      // config.headers.Cookie = `session=${session}`;
      // config.withCredentials = true;
    }
    return config
  }, error => {
    return Promise.reject(error)
  })
}

Vue.use(VueKeycloakJs, {
  init: {
    // Use 'login-required' to always require authentication
    // If using 'login-required', there is no need for the router guards in router.js
    // onLoad: 'check-sso',
    onLoad: 'login-required',
    // silentCheckSsoRedirectUri: window.location.origin + "/silent-check-sso.html"
  },
  config: {
    url: 'http://localhost:8180/',
    clientId: 'spring-with-test-scope',
    realm: 'demo'
  },
  onReady: kc => {
    tokenInterceptor ()
    new Vue({
      router,
      render: h => h(App)
    }).$mount('#app')
    console.log(kc);
  }
})

// new Vue({
//   router,
//   render: h => h(App)
// }).$mount('#app')
