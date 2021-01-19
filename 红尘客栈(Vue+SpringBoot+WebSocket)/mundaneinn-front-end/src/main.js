import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'
import API from './assets/config/api'
import BaiduMap from 'vue-baidu-map';
import animated from 'animate.css'
Vue.prototype.API = API
Vue.use(BaiduMap, {
  /* Visit http://lbsyun.baidu.com/apiconsole/key for details about app key. */
  ak: 'YOUR_APP_KEY'
})
Vue.use(animated)
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
