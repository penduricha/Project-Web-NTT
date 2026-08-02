import './assets/main.css'
import { createApp } from 'vue'
import App from './App.vue'

// Bootstrap
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap/dist/js/bootstrap.js'

// Vue Router & Vuetify
import { createRouter, createWebHistory } from 'vue-router'
import { createVuetify } from 'vuetify'
import 'vuetify/styles'
import 'vuetify/dist/vuetify-labs.min.css'

// Plugins & Local Storage / Routers
import CanvasJSChart from '@canvasjs/vue-charts'
import StudentLocalStorage from "@/local-storage/StudentLocalStorage.js"
import RouterManagement from "@/routers/RouterManagement.js"
import routersBeforeLogin from "@/routers/routers-before-login.js"
import routersAfterLogin from "@/routers/routers-after-login.js"

const app = createApp(App)
app.use(CanvasJSChart)
app.use(createVuetify())

/**
 * Khởi tạo Router và Mount ứng dụng
 */
function initApp(routes, targetPath) {
    const router = createRouter({
        history: createWebHistory(),
        routes,
    })

    app.use(router)

    router.replace(targetPath).catch((error) => {
        console.error('Error navigating:', error)
    })

    app.mount('#app')
}

/**
 * Xử lý logic điều hướng dựa trên trạng thái đăng nhập
 */
function execute() {
    const routerManagement = new RouterManagement()
    const studentLocalStorage = new StudentLocalStorage()
    const currentPath = window.location.pathname

    const isLoggedIn = Boolean(
        routerManagement.getPath_From_LocalStorage() &&
        studentLocalStorage.getStudentID_From_LocalStorage_StudentID()
    )

    const path404 = '/404-not-found'
    
    // Chọn danh sách routers và trang mặc định phù hợp với trạng thái đăng nhập
    const activeRouters = isLoggedIn ? routersAfterLogin : routersBeforeLogin
    const defaultPath = isLoggedIn ? routerManagement.getPath_From_LocalStorage() : '/login'

    // Kiểm tra xem path hiện tại có nằm trong danh sách router cho phép không
    const isValidRoute = activeRouters.some(route => route.path === currentPath)

    let targetPath = currentPath

    if (!isValidRoute) {
        targetPath = path404
    } else if (currentPath === '/') {
        targetPath = defaultPath
    } else {
        // Kiểm tra SessionStorage xem có khớp không (nếu cần thiết)
        const pathSession = routerManagement.getPath_From_SessionStorage()
        if (pathSession && currentPath !== pathSession) {
            targetPath = pathSession
        }
    }

    initApp(activeRouters, targetPath)
}

// Chạy ứng dụng
execute()