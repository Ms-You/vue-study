import { createApp } from 'vue'
import store from './scripts/store'
import router from './scripts/router'
import './style.css'
import App from './App.vue'

const app = createApp(App)

app.use(store)
app.use(router)

store.dispatch('initializeAuthentication') // 로그인 상태 초기화

app.mount('#app')

// createApp(App).use(store).use(router).mount('#app')
