import axios from "axios";

export default class AxiosConfig {
    constructor() {

    }

    requestConfigFromSpringBoot(){
        const ip_address= 'localhost';
        const port = '8080';
        return axios.create({
            baseURL: `http://${ip_address}:${port}/api`,
            headers: {
                'Content-Type': 'application/json',
                // Set header Authorization if there are securities
            }
        });
    }
}