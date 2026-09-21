import axios from "axios";
import AxiosConfiguration from "@/services/AxiosConfiguration.js";

const axiosConfig = new AxiosConfiguration();

export default class SpringConfigHeader {
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
}