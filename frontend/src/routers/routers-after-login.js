import LoginPage from "@/pages/login-page/LoginPage.vue";
// import RegisterCourses from "@/pages/register-courses-page/RegisterCourses.vue";
import InformationPage from "@/pages/information-page/InformationPage.vue";
// import ListCourses from "@/pages/list-courses/ListCourses.vue";
import Page404NotFound from "@/pages/404-not-found-page/Page404NotFound.vue";
// import UniversitySchedule from "@/pages/university-schedule/UniversitySchedule.vue";


const routers = [
    // '/' khi init trang, prop param de truyen tham so.
    { path: '/', component: InformationPage, allow: true},
    { path: '/login', component: LoginPage, allow: false},
    // { path: '/register-courses', component: RegisterCourses, allow: true},
    { path: '/information-student', component: InformationPage, allow: true},
    // { path: '/list-courses', component: ListCourses, allow: true},
    { path: '/404-not-found', component: Page404NotFound, allow: true},
    // { path: '/university-schedule', component: UniversitySchedule, allow: true},
];
export default routers;
/*
*
* props: (route) => ({ bankTestJavaOopID: route.query.bankTestJavaOopID })
*/