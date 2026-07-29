export class StudentAccount {
    _studentID;
    _password;

    getStudentID(){
        return this._studentID;
    }

    setStudentID(studentID) {
        this._studentID = studentID;
    }

    getPassword() {
        return this._password;
    }

    setPassword(password) {
        this._password = password;
    }

    constructor(studentID, password) {
        this.setStudentID(studentID);
        this.setPassword(password);
    }

    toString() {
        return `Student account:[studentID: ${this._studentID}, password: ${this._password}]`;
    }
}