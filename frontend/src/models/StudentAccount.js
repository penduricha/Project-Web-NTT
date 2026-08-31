export class StudentAccount {
    _studentId;
    _password;

    getStudentId(){
        return this._studentId;
    }

    setStudentId(studentId) {
        this._studentId = studentId;
    }

    getPassword() {
        return this._password;
    }

    setPassword(password) {
        this._password = password;
    }

    constructor(studentId, password) {
        this.setStudentId(studentId);
        this.setPassword(password);
    }

    toString() {
        return `Student account:[studentId: ${this._studentId}, password: ${this._password}]`;
    }
}