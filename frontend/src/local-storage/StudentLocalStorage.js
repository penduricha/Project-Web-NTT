export default class StudentLocalStorage {
    // Định nghĩa các key cố định dưới dạng static readonly (hoặc hằng số)
    static STORAGE_KEYS = {
        STUDENT: 'studentLocalStorage',
        STUDENT_ID: 'studentIDLocalStorage',
        REMEMBER_LOGIN: 'studentRememberLogin'
    };

    /**
     * Lưu thông tin student vào localStorage
     * @param {Object} student
     */
    setStudentLocalStorage(student) {
        if (!student) {
            console.error('Student object is empty.');
            return;
        }
        localStorage.setItem(StudentLocalStorage.STORAGE_KEYS.STUDENT, JSON.stringify(student));
    }

    /**
     * Lấy thông tin student từ localStorage
     * @returns {Object|null}
     */
    getStudentLocalStorage() {
        const student = localStorage.getItem(StudentLocalStorage.STORAGE_KEYS.STUDENT);
        if (!student) {
            console.warn('Student local storage is empty.');
            return null;
        }
        return JSON.parse(student);
    }

    /**
     * Lấy studentID từ trong object student đã lưu
     * @returns {string|null}
     */
    getStudentID_From_LocalStorage() {
        const student = this.getStudentLocalStorage();
        return student?.studentID || null;
    }

    /**
     * Xóa thông tin student khỏi localStorage
     */
    removeStudentLocalStorage() {
        const key = StudentLocalStorage.STORAGE_KEYS.STUDENT;
        if (localStorage.getItem(key)) {
            localStorage.removeItem(key);
        } else {
            console.warn('Student local storage is already empty.');
        }
    }

    /**
     * Lưu thông tin Remember Me (studentID & password)
     * @param {string} studentID
     * @param {string} password
     */
    saveLocalStorageRememberMe(studentID, password) {
        if (!studentID || !password) {
            console.error('Student ID or password is null.');
            return;
        }
        const credentials = { studentID, password };
        localStorage.setItem(
            StudentLocalStorage.STORAGE_KEYS.REMEMBER_LOGIN,
            JSON.stringify(credentials)
        );
    }

    /**
     * Lấy thông tin Remember Me
     * @returns {Object}
     */
    getLocalStorageRememberMe() {
        const data = localStorage.getItem(StudentLocalStorage.STORAGE_KEYS.REMEMBER_LOGIN);
        return data ? JSON.parse(data) : {};
    }

    /**
     * Xóa thông tin Remember Me
     */
    removeLocalStorageRememberMe() {
        const key = StudentLocalStorage.STORAGE_KEYS.REMEMBER_LOGIN;
        if (localStorage.getItem(key)) {
            localStorage.removeItem(key);
        }
    }

    /**
     * Lưu riêng studentID vào localStorage
     * @param {string} studentID
     */
    setStudentIDToLocalStorage(studentID) {
        if (!studentID) {
            console.error('Student ID is empty.');
            return;
        }
        localStorage.setItem(StudentLocalStorage.STORAGE_KEYS.STUDENT_ID, studentID);
    }

    /**
     * Lấy riêng studentID đã lưu độc lập
     * @returns {string|null}
     */
    getStudentID_From_LocalStorage_StudentID() {
        const studentID = localStorage.getItem(StudentLocalStorage.STORAGE_KEYS.STUDENT_ID);
        if (!studentID) {
            console.warn('Student ID local storage is empty.');
            return null;
        }
        return studentID;
    }

    /**
     * Xóa riêng studentID khỏi localStorage
     */
    removeStudentIDFromLocalStorage() {
        const key = StudentLocalStorage.STORAGE_KEYS.STUDENT_ID;
        if (localStorage.getItem(key)) {
            localStorage.removeItem(key);
        } else {
            console.warn('Student ID local storage is already empty.');
        }
    }
}