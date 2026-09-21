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
import StudentLocalStorage from "@/local_storage/StudentLocalStorage.js"
import RouterManagement from "@/routers/RouterManagement.js"
import routersBeforeLogin from "@/routers/routers_before_login.js"
import routersAfterLogin from "@/routers/routers_after_login.js"
import {StudentService} from "@/services/spring/StudentService.js";
import {TokenLogin} from "@/model/TokenLogin.js";

const studentService = new StudentService();

const tokenLogin = new TokenLogin();

const app = createApp(App)
app.use(createVuetify())

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

async function execute() {
    const routerManagement = new RouterManagement()
    const studentLocalStorage = new StudentLocalStorage()
    const currentPath = window.location.pathname

    // const isLoggedIn = Boolean(
    //     routerManagement.getPath_From_LocalStorage() &&
    //     studentLocalStorage.getStudentId_From_LocalStorage() &&
    //     tokenLogin.getRefreshTokenFromStorage()
    // )
    let statusLoginAuto = false;

    let accessToken = tokenLogin.getAccessTokenFromStorage();
    let refreshToken = tokenLogin.getRefreshTokenFromStorage();
    tokenLogin.setAccessToken(accessToken);
    tokenLogin.setRefreshToken(refreshToken);

    if(refreshToken) {
        statusLoginAuto = await studentService.getDataToAutoLoginAccessToken(tokenLogin);
    }

    if(!accessToken || !refreshToken) {
        routerManagement.removePathFromLocalStorage();
    }

    const activeRouters = statusLoginAuto ? routersAfterLogin : routersBeforeLogin;

    const defaultPath = statusLoginAuto ? routerManagement.getPathFromLocalStorage() : '/login';

    // Tìm route khớp với currentPath
    const matchedRoute = activeRouters.find(route => route.path === currentPath)

    let targetPath = currentPath

    // 1. Kiểm tra nếu chưa đăng nhập và route có cờ allow === false (hoặc route không hợp lệ)
    if (!statusLoginAuto && (matchedRoute?.allow === false || !matchedRoute)) {
        targetPath = '/login'
    }
    // 2. Nếu đã đăng nhập nhưng route không tồn tại trong danh sách
    else if (!matchedRoute) {
        targetPath = '/404-not-found'
    }
    // 3. Nếu đang ở root '/'
    else if (currentPath === '/') {
        targetPath = defaultPath
    }
    // 4. Kiểm traSessionStorage
    else {
        const pathSession = routerManagement.getPathFromSessionStorage()
        if (pathSession && currentPath !== pathSession) {
            targetPath = pathSession
        }
    }

    initApp(activeRouters, targetPath)
}

await execute();