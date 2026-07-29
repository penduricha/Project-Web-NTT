export default class StringManagement {
    _stringValue;

    getStringValue() {
        return this._stringValue;
    }

    setStringValue(value) {
        this._stringValue = value
    }

    constructor(stringValue) {
        this._stringValue = stringValue;
    }

    trimStringValue() {
        return this._stringValue.trim();
    }

    trimSpaceStringValue() {

    }

    formatNameStringValue() {

    }
}