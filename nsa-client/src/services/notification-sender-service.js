import axios from 'axios';

class RecipientsService {
    constructor(baseUrl, servicePrefix) {
        this.baseUrl = baseUrl;
        this.servicePrefix = servicePrefix;
    }

    getTaskStat(id) {
        return axios.get(`${this.baseUrl}/${this.servicePrefix}/stat/task/${id}`)
            .then(response => response.data)
            .catch(error => {
                console.log(error);
                return Promise.reject(error);
            });
    }
}

export default new RecipientsService('http://localhost:8080', 'notification-sender-service');