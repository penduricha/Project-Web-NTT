export default class RouterManagement {
    _variableRouterPathSession;
    _variableRouterPathLocalStorage;

    getVariableRouterPathSession() {
        return this._variableRouterPathSession;
    }

    setVariableRouterPathSession(value) {
        this._variableRouterPathSession = value;
    }

    getVariableRouterPathLocalStorage() {
        return this._variableRouterPathLocalStorage;
    }

    setVariableRouterPathLocalStorage(value) {
        this._variableRouterPathLocalStorage = value;
    }

    constructor(){
        this.setVariableRouterPathSession('routerPathSessionStorage');
        this.setVariableRouterPathLocalStorage('routerPathLocalStorage');
    }

    savePath_To_SessionStorage(routerPath){
        //Khi chuyển trang khác, path được save vào session.
        if(routerPath){
            sessionStorage.setItem(this.getVariableRouterPathSession(), routerPath);
        }
    }

    getPath_From_SessionStorage(){
        const routerPath = sessionStorage.getItem(this.getVariableRouterPathSession());
        return routerPath || null;
    }

    //other functions local-storage,
    //session storages
    //save path to local-storage
    removePath_From_LocalStorage(){
        //Khi chuyển trang khác, path được save vào session.
        if(this.getPath_From_LocalStorage()) {
            localStorage.removeItem(this.getVariableRouterPathLocalStorage());
        } else {
            console.error('Local storage not found.');
        }
    }

    removePath_From_SessionStorage(){
        //Khi chuyển trang khác, path được save vào session.
        if(this.getPath_From_SessionStorage()) {
            sessionStorage.removeItem(this.getVariableRouterPathSession());
        } else {
            console.error('Session storage not found.');
        }
    }

    savePath_To_LocalStorage(routerPath){
        //Khi chuyển trang khác, path được save vào session.
        localStorage.setItem(this.getVariableRouterPathLocalStorage(), routerPath);
    }

    getPath_From_LocalStorage(){
        const routerPath = localStorage
            .getItem(this.getVariableRouterPathLocalStorage());
        return routerPath || null;
    }
}