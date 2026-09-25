import {StudentAccount} from "@/model/StudentAccount.js";
import SpringConfigHeader from "@/services/spring/SpringConfigHeader.js";
import {TokenLogin} from "@/model/TokenLogin.js";
import StudentLocalStorage from "@/local_storage/StudentLocalStorage.js";

const springConfigHeader = new SpringConfigHeader();

const studentLocalStorage = new StudentLocalStorage();

async function getDataToAutoLoginRefreshToken(refreshToken) {
    const path = "/student/auth/login/auto/refresh-token";
    return await springConfigHeader
        .getAPIClientWithHeadersIsAccessToken(refreshToken).get(path, springConfigHeader.getAuthorization());
}

export class StudentService {
    async postDataToLoginRequest(studentAccount) {
        if (!(studentAccount instanceof StudentAccount)) {
            throw new Error("Invalid StudentAccount instance");
        }

        const data = {
            "studentId": studentAccount.getStudentId(),
            "password": studentAccount.getPassword(),
        };

        const path = '/student/auth/login';
        return springConfigHeader.getAPIClientNoHeaders().post(path, data, springConfigHeader.getAuthorization());
    }

    async postDataToLogoutRequest(tokenLogin) {

        if (!(tokenLogin instanceof TokenLogin)) {
            throw new Error("Invalid TokenLogin instance");
        }

        const data = {
            "accessToken": tokenLogin.getAccessTokenFromStorage(),
            "refreshToken": tokenLogin.getRefreshTokenFromStorage(),
        };

        const path = '/student/auth/logout';
        return springConfigHeader.getAPIClientNoHeaders().post(path, data, springConfigHeader.getAuthorization());
    }

    async getDataToAutoLoginAccessToken(tokenLogin) {
        let statusAutoLogin = false;

        if (!(tokenLogin instanceof TokenLogin)) {
            throw new Error("Invalid TokenLogin instance");
        }

        const path = "/student/auth/login/auto/access-token";

        try {
            // 1. Thử gọi API bằng Access Token hiện tại
            let dataResponse = await springConfigHeader
                .getAPIClientWithHeadersIsAccessToken(tokenLogin.getAccessToken())
                .get(path, springConfigHeader.getAuthorization());

            if (dataResponse.status === 200) {
                let studentId = dataResponse.data.data.studentId;
                studentLocalStorage.setStudentIdToLocalStorage(Number(studentId));
                return true;
            }
        } catch (error) {
            // 2. Bắt lỗi khi Access Token bị hết hạn (HTTP Status 401)
            if (error.response && error.response.status === 401) {
                console.warn('Access Token is expired or not existed. Renewing tokens...');
                try {
                    // Thử đổi Refresh Token lấy cặp Token mới
                    let dataResponseRefreshToken = await getDataToAutoLoginRefreshToken(tokenLogin.getRefreshToken());

                    if (dataResponseRefreshToken && dataResponseRefreshToken.status === 200) {
                        // Cập nhật Token mới vào Object & LocalStorage
                        let accessTokenNew = dataResponseRefreshToken.data.data.accessToken;
                        let refreshTokenNew = dataResponseRefreshToken.data.data.refreshToken;

                        tokenLogin.setAccessToken(accessTokenNew);
                        tokenLogin.setRefreshToken(refreshTokenNew);
                        tokenLogin.setAccessTokenToStorage();
                        tokenLogin.setRefreshTokenToStorage();

                        // 3. Đã có Access Token mới -> Gọi lại API cũ để lấy studentId
                        let retryResponse = await springConfigHeader
                            .getAPIClientWithHeadersIsAccessToken(accessTokenNew)
                            .get(path, springConfigHeader.getAuthorization());

                        if (retryResponse.status === 200) {
                            let studentId = retryResponse.data.data.studentId;
                            studentLocalStorage.setStudentIdToLocalStorage(Number(studentId));
                            statusAutoLogin = true;
                        }
                    } else {
                        // Refresh Token cũng bị từ chối
                        tokenLogin.removeAccessTokenAndRefreshTokenFromStorage();
                        studentLocalStorage.removeStudentIdFromLocalStorage();
                    }
                } catch (refreshError) {
                    // Lỗi khi gọi Refresh Token (ví dụ 401/403 do Refresh Token hết hạn)
                    console.error('Refresh token failed:', refreshError);
                    tokenLogin.removeAccessTokenAndRefreshTokenFromStorage();
                    studentLocalStorage.removeStudentIdFromLocalStorage();
                }
            } else {
                // Lỗi hệ thống khác (500, lỗi mạng...)
                console.error('Auto login error:', error);
                tokenLogin.removeAccessTokenAndRefreshTokenFromStorage();
                studentLocalStorage.removeStudentIdFromLocalStorage();
            }
        }
        return statusAutoLogin;
    }
}