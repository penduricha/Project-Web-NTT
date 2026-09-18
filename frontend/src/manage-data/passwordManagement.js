export default class PasswordManagement {
    _password;

    getPassword() {
        return this._password;
    }

    setPassword(password) {
        this._password = password
    }

    constructor(password) {
        this.setPassword(password);
    }

    async sha512Hash() {
        let buf = await crypto.subtle
            .digest("SHA-512",
                new TextEncoder("utf-8")
                    .encode(this.getPassword().trim()));
        return Array.prototype.map.call(new Uint8Array(buf),
            x => (('00' + x.toString(16)).slice(-2)))
            .join('');
    }
}