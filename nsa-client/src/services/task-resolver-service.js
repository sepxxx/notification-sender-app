import axios from 'axios';

class RecipientsService {
    constructor(baseUrl, servicePrefix) {
        this.baseUrl = baseUrl;
        this.servicePrefix = servicePrefix;
    }

    getTasks() {
        return axios.get(`${this.baseUrl}/${this.servicePrefix}`)
            .then(response => response.data)
            .catch(error => {
                console.log(error);
                return Promise.reject(error);
            });
    }

    saveTask(data) {
        return axios.post(
            `${this.baseUrl}/${this.servicePrefix}`,
            data)
            .then(response => response.data)
            .catch(error => {
                console.log(error);
                return Promise.reject(error);
            });
    }
}

export default new RecipientsService('http://localhost:8080', 'task-resolver-service/tasks');