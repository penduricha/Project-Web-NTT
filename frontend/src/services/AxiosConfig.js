import axios from "axios";

export default class AxiosConfig {
    constructor() {

    }

    getAPIClient(){
        const port = '8080';
        return axios.create({
            baseURL: `http://localhost:${port}/api`,
            headers: {
                'Content-Type': 'application/json',
                // Set header Authorization if there are securities
            }
        });
    }
}