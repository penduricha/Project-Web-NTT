export default class StudentLocalStorage {

    _studentLocalStorageName;
    _studentIDLocalStorageName;
    _studentRememberLoginName;

    getStudentIDLocalStorageName() {
        return this._studentIDLocalStorageName;
    }

    setStudentIDLocalStorageName(studentIDLocalStorageName) {
        this._studentIDLocalStorageName = studentIDLocalStorageName;
    }

    getStudentLocalStorageName() {
        return this._studentLocalStorageName;
    }

    setStudentLocalStorageName(value) {
        this._studentLocalStorageName = value;
    }

    getStudentRememberLoginName() {
        return this._studentRememberLoginName;
    }

    setStudentRememberLoginName(value) {
        this._studentRememberLoginName = value;
    }

    constructor() {
        this.setStudentLocalStorageName('studentLocalStorage');
        this.setStudentRememberLoginName('studentRememberLogin');
        this.setStudentIDLocalStorageName('studentIDLocalStorage')
    }

    setStudentLocalStorage(student) {
        if (student) {
            localStorage.setItem(
                this.getStudentLocalStorageName()
                , JSON.stringify(student)
            );
        } else {
            console.error('Student is empty.');
        }
    }

    getStudentLocalStorage() {
        let student = localStorage
            .getItem(
                this.getStudentLocalStorageName()
            );
        if (student) {
            return JSON.parse(student);
        } else {
            console.error('Student local storage is empty.');
        }
    }

    getStudentID_From_LocalStorage() {
        let student = localStorage.getItem(
            this.getStudentLocalStorageName()
        );
        if (student) {
            let studentObject = JSON.parse(student);
            return studentObject.studentID || null;
        } else {
            console.error('Student local storage is empty.');
        }
    }

    removeStudentLocalStorage() {
        let student = localStorage.getItem(
            this.getStudentLocalStorageName()
        );
        if (student) {
            localStorage.removeItem(
                this.getStudentLocalStorageName()
            );
        } else {
            console.error('Student local storage is empty.');
        }
    }

    saveLocalStorageRememberMe(studentID, password) {
        if(studentID && password) {
            const student = {
                studentID: studentID,
                password: password,
            };
            localStorage.setItem(
                this.getStudentRememberLoginName(),
                JSON.stringify(student)
            );
        }  else {
            console.error('Student id or password is null.');
        }
    }

    getLocalStorageRememberMe() {
        const studentRemember = localStorage.getItem(
            this.getStudentRememberLoginName());
        return studentRemember ? JSON.parse(studentRemember) : {};
    }

    removeLocalStorageRememberMe() {
        const studentRemember = localStorage.getItem(
            this.getStudentRememberLoginName());
        if(studentRemember) {
            localStorage.removeItem(
                this.getStudentRememberLoginName()
            );
        }
    }

    //save studentID
    setStudentIDToLocalStorage(studentID) {
        if (studentID) {
            localStorage.setItem(
                this.getStudentIDLocalStorageName()
                , studentID
            );
        } else {
            console.error('Student ID is empty.');
        }
    }

    getStudentID_From_LocalStorage_StudentID() {
        let studentID = localStorage.getItem(
            this.getStudentIDLocalStorageName()
        );
        if (studentID) {
            return studentID;
        } else {
            console.error('Student ID local storage is empty.');
            return null;
        }
    }

    removeStudentIDFromLocalStorage() {
        let student = localStorage.getItem(
            this.getStudentIDLocalStorageName()
        );
        if (student) {
            localStorage.removeItem(
                this.getStudentIDLocalStorageName()
            );
        } else {
            console.error('Student ID local storage is empty.');
        }
    }
}