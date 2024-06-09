import axios from 'axios';

class RecipientsService {
    constructor(baseUrl, servicePrefix) {
        this.baseUrl = baseUrl;
        this.servicePrefix = servicePrefix;
    }

    getRecipientsLists() {
        return axios.get(`${this.baseUrl}/${this.servicePrefix}/lists`)
            .then(response => response.data)
            .catch(error => {
                console.log(error);
                return Promise.reject(error);
            });
    }

    getSub() {
        return axios.get(`${this.baseUrl}/myone`)
            .then(response => response.data)
            .catch(error => {
                console.log(error);
                return Promise.reject(error);
            });
    }
}

export default new RecipientsService('http://localhost:8080', 'recipients-service');