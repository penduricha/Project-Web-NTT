import StudentLocalStorage from "@/local_storage/StudentLocalStorage.js";
import {StudentAccount} from "@/model/StudentAccount.js";
import {StudentService} from "@/services/spring/StudentService.js";

import {TokenLogin} from "@/model/TokenLogin.js";
import RouterManagement from "@/routers/RouterManagement.js";

const studentLocalStorage = new StudentLocalStorage();

const studentService = new StudentService();

export default class StudentRepository {
    accessToken;
    refreshToken;

    async getResponseFromLoginRequest(studentId, password) {
        let statusLogin = "";
        const studentAccount = new StudentAccount(studentId, password);
        try{
            let dataReturn = await studentService.postDataToLoginRequest(studentAccount);
            console.log(dataReturn);
            console.log("Data:", dataReturn.data);

            if(dataReturn.data.success) {
                //save localstorage
                let accessToken = String(dataReturn.data.data.accessToken);
                let refreshToken = String(dataReturn.data.data.refreshToken);
                const tokenLogin = new TokenLogin(accessToken, refreshToken);

                console.log(tokenLogin.toString());

                tokenLogin.setAccessTokenToStorage();
                tokenLogin.setRefreshTokenToStorage();
                studentLocalStorage.setStudentIdToLocalStorage(studentAccount.getStudentId());

                statusLogin = "OK";
            } else {
                statusLogin = "Failed";
            }
        } catch (e) {
            statusLogin = "Exception system:";
            console.error(statusLogin);
            console.error(e);
            alert(e);

        }
        return statusLogin;
        //save localstorage
    }

    async getResponseFromLogoutRequest() {
        let statusLogout = "";

        try {
            const tokenLogin = new TokenLogin();
            let dataReturn = await studentService.postDataToLogoutRequest(tokenLogin);

            if(dataReturn.data.status === 200) {
                studentLocalStorage.removeStudentIdFromLocalStorage();
                tokenLogin.removeAccessTokenAndRefreshTokenFromStorage();

                const routerManagement = new RouterManagement();
                routerManagement.removePathFromLocalStorage();
                routerManagement.removePathFromSessionStorage();

                statusLogout = "OK";
            } else {
                statusLogout = "Failed"
            }
        } catch (e) {
            statusLogout = "Exception system:";
            console.error(statusLogout);
            console.error(e);
            alert(e);
        }
        return statusLogout
    }

}