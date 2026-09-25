import axios from "axios";
import AxiosConfiguration from "@/services/AxiosConfiguration.js";

const axiosConfig = new AxiosConfiguration();

export default class SpringConfigHeader {

    #authUsername;
    #authPassword;

    constructor() {
        this.#authUsername = import.meta.env.VITE_AUTH_USERNAME || "";
        this.#authPassword = import.meta.env.VITE_AUTH_PASSWORD || "";
    }

    getAPIClientNoHeaders(){
        return axios.create({
            baseURL: axiosConfig.requestPathFromSpringBoot(),
            headers: { 'Content-Type': 'application/json', }
        });
    }

    getAPIClientWithHeadersIsAccessToken(token) {
        return axios.create({
            baseURL: axiosConfig.requestPathFromSpringBoot(),
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });
    }

    getAuthorization() {
        // Nếu cần HTTP Basic Auth
        return {
            auth: {
                username: this.#authUsername,
                password: this.#authPassword,
            }
        };

        // Nếu không cần auth
        // return {};
    }
}