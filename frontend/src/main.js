// import './assets/main.css'

// import { createApp } from 'vue'
// import App from './App.vue'
// //import bootstrap
// import 'bootstrap/dist/css/bootstrap.css';
// import 'bootstrap/dist/js/bootstrap.js';

// //import vue routers
// import { createRouter, createWebHistory } from 'vue-router';

// //import vuetify
// //vuetify
// //npm install vuetify@next @mdi/font
// import { createVuetify } from 'vuetify';
// import 'vuetify/styles';
// import 'vuetify/dist/vuetify-labs.min.css';
// const vuetify = createVuetify();
// //font awesome
// //import routers
// // import routers from "@/routes/list-routes/routers.js";
// // import RouterDao from "@/routes/RoutersDao.js";
// // import LecturerLocalStorage from "@/pages/login/LecturerLocalStorage.js";

// //import pie chart
// import CanvasJSChart from '@canvasjs/vue-charts';
// import StudentLocalStorage from "@/local-storage/StudentLocalStorage.js";
// import RouterManagement from "@/routers/RouterManagement.js";
// // import routers from "@/routers/routers.js";
// import routersBeforeLogin from "@/routers/routers-before-login.js";
// import routersAfterLogin from "@/routers/routers-after-login.js";


// const app = createApp(App);
// app.use(CanvasJSChart);
// app.use(vuetify);

// /*
//         B1: Xet local storage student (Object)
//         B2: Neu student local storage duoc luu => Trang dkhp neu nguoc lai thi vao Login
//         B3: Khi dang xuat phai remove local storage
//  */
// function initPage(routers, routerPath) {
//     const router = createRouter({
//         // mode: 'history',
//         history: createWebHistory(),
//         routes: routers,
//     });
//     app.use(router);
//     router.replace(routerPath).catch((error) => {
//         console.error('Error navigating: ', error);
//         //router.replace('/screen-404').catch(err => console.error(err));
//     });
//     //app.unmount();
//     //app.use(Vue3GeoLocation);
//     app.mount('#app')
// }

// function execute() {
//     //duyet ko co field query path
//     const allowedRoutersBeforeLogin = routersBeforeLogin
//         .filter(route => route.allow === true)
//     // .map(route => route.path);
//     // Lấy ra các giá trị path
//     // Lọc các đối tượng có allow === true
//     const allowedRoutersAfterLogin = routersAfterLogin
//         .filter(route => route.allow === true)

//     const routerManagement = new RouterManagement();
//     const studentLocalStorage = new StudentLocalStorage();
//     const startPagePath = '/login';
//     const checkPath_And_ID =
//         routerManagement.getPath_From_LocalStorage() &&
//         studentLocalStorage.getStudentID_From_LocalStorage_StudentID();
//     //người dùng gõ path
//     const currentPath = window.location.pathname;
//     //currentPath = currentPath.trim().split(' ').join('');
//     // Lấy đường dẫn hiện tại
//     // if (currentPath === '/') {
//     //     // Nếu người dùng gõ đường dẫn chính, điều hướng đến đường dẫn từ LocalStorage
//     /* xét thêm trường hợp nếu ko trong list routers thì sẽ navigate trang 404*/
//     /* truong hop tu logout khi treo qua lau */
//     startExecuteNoAutomationLogOut(checkPath_And_ID,
//         routerManagement,
//         allowedRoutersBeforeLogin,
//         allowedRoutersAfterLogin,
//         startPagePath,
//         currentPath
//     );
// }

// function startExecuteNoAutomationLogOut(checkPath_And_ID, routerManagement, routersBeforeLogin, routersAfterLogin, startPagePath, currentPath) {
//     //tinh huong khi muon truy lap lại ko phai dang nhap lai.
//     if(checkPath_And_ID) {
//         const isNotInRoutersAfterLogin = routersAfterLogin.every(router => router.path !== currentPath);
//         const path404Page = '/404-not-found';
//         if(isNotInRoutersAfterLogin) {
//             initPage(
//                 routersAfterLogin, path404Page
//             );
//         } else {
//             if(currentPath === '/') {
//                 initPage(
//                     routersAfterLogin, routerManagement
//                         .getPath_From_LocalStorage()
//                 );
//             } else {
//                 let pathSession = routerManagement
//                     .getPath_From_SessionStorage();
//                 if(currentPath === pathSession) {
//                     initPage(
//                         routersAfterLogin,
//                         pathSession
//                     );
//                 } else {
//                     initPage(
//                         routersAfterLogin,
//                         currentPath
//                     );
//                 }
//             }
//         }
//     } else{
//         //ko co dang nhap vao trang login
//         const isNotInRoutersBeforeLogin = routersBeforeLogin.every(router => router.path !== currentPath);
//         const path404Page = '/404-not-found';
//         if(isNotInRoutersBeforeLogin) {
//             initPage(
//                 routersAfterLogin, path404Page
//             );
//         } else {
//             if(currentPath === '/') {
//                 initPage(
//                     routersBeforeLogin,
//                     startPagePath
//                 );
//             } else {
//                 let pathSession = routerManagement
//                     .getPath_From_SessionStorage();
//                 if(currentPath === pathSession) {
//                     initPage(
//                         routersBeforeLogin,
//                         pathSession
//                     );
//                 } else {
//                     initPage(
//                         routersBeforeLogin,
//                         currentPath
//                     );
//                 }
//             }
//         }
//     }
// }

// function startExecuteAutomationLogOut(checkPath_And_ID, routerManagement, routersBeforeLogin, routersAfterLogin, startPagePath, currentPath) {
//     if (checkPath_And_ID && !routerManagement.getPath_From_SessionStorage()) {
//         // Nếu có path từ LocalStorage và không có path từ SessionStorage
//         // Điều hướng đến đường dẫn người dùng gõ
//         if(currentPath === '/') {
//             initPage(routersAfterLogin, routerManagement.getPath_From_LocalStorage());
//         } else {
//             initPage(routersAfterLogin, currentPath);
//             //duyet ds routers neu ko co thi navigate den trang 404
//         }
//     } else {
//         let pathSession = routerManagement.getPath_From_SessionStorage();
//         if(currentPath !== pathSession) {
//             routerManagement.removePath_From_LocalStorage(pathSession);
//             initPage(
//                 routersBeforeLogin,
//                 currentPath
//             );
//         } else {
//             initPage(
//                 routersBeforeLogin,
//                 pathSession
//             );
//         }
//         // if (!routerManagement.getPath_From_SessionStorage()) {
//         //     //truong hop tu logout khi treo qua lau
//         //     initPage(routersBeforeLogin, startPagePath);
//         // } else {
//         //     //viet them truong hop khi nguoi dung go
//         //     let pathSession = routerManagement.getPath_From_SessionStorage();
//         //     if(currentPath !== pathSession) {
//         //         routerManagement.removePath_From_LocalStorage(pathSession);
//         //         initPage(
//         //             routersBeforeLogin,
//         //             currentPath
//         //         );
//         //     } else {
//         //         initPage(
//         //             routersBeforeLogin,
//         //             pathSession
//         //         );
//         //     }
//         // }
//     }
// }

// //execute
// execute();

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